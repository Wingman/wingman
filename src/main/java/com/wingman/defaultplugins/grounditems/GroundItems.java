package com.wingman.defaultplugins.grounditems;

import com.wingman.client.api.generated.*;
import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.api.plugin.PluginDependency;
import com.wingman.client.api.plugin.PluginHelper;
import com.wingman.defaultplugins.devutils.enums.GameState;
import com.wingman.defaultplugins.devutils.game.world.Perspective;
import com.wingman.defaultplugins.devutils.util.external.ItemPriceCache;

import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@PluginDependency(
        id = "DevUtils-defaultplugins",
        version = ">=0.0.1"
)
@Plugin(
        id = "GroundItems-defaultplugins",
        name = "Ground Items",
        description = "Diplays the item name, quantity and price of an item on the ground.",
        version = "0.0.1"
)
public class GroundItems {

    @Plugin.Helper
    public PluginHelper helper;

    private boolean miniMapDotEnabled = true;

    private int MINIMUM_VERY_EXPENSIVE = 1_000_000;
    private int MINIMUM_EXPENSIVE = 50_000;

    private Color COLOR_VERY_EXPENSIVE = new Color(212, 175, 55);
    private Color COLOR_EXPENSIVE = new Color(50, 205, 50);
    private Color COLOR_UNTRADABLE = new Color(255, 20, 147);
    private Color COLOR_LOW_PRICE = new Color(255, 255, 255);

    private Font textFont = new Font("Verdana", Font.PLAIN, 11);
    private FontMetrics fontMetrics = null;
    private final StringBuilder itemStringBuilder = new StringBuilder();

    @Plugin.Activate
    public void activate() {
        Overlay overlay = new Overlay(false) {
            @Override
            public boolean shouldDraw() {
                return GameAPI.getGameState() == GameState.PLAYING;
            }

            @Override
            public boolean shouldUpdate() {
                return GameAPI.getGameState() == GameState.PLAYING;
            }

            @Override
            public Dimension getDimension() {
                return new Dimension(
                        GameAPI.getAppletWidth(),
                        GameAPI.getAppletHeight()
                );
            }

            @Override
            public void update(Graphics2D g) {
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

                g.setFont(textFont);

                if (fontMetrics == null) {
                    fontMetrics = g.getFontMetrics();
                }

                int plane = GameAPI.getClientPlane();

                LandscapeTile[][][] tiles = GameAPI
                        .getLandscape()
                        .getLandscapeTiles();

                for (LandscapeTile[] planeTiles : tiles[plane]) {
                    for (LandscapeTile tile : planeTiles) {
                        if (tile != null) {
                            ItemLayer itemLayer = tile.getItemLayer();
                            if (itemLayer != null) {
                                int x = itemLayer.getX();
                                int y = itemLayer.getY();

                                Point point = Perspective.worldToScreen(x, y, plane, itemLayer.getHeight());
                                if (point.x != -1 && point.y != -1) {
                                    ArrayList<Integer> itemIds = new ArrayList<>();
                                    Map<Integer, Integer> itemQuantities = new HashMap<>();

                                    Node bottomLayer = itemLayer.getBottom();
                                    addItemToMap((Item) bottomLayer, itemIds, itemQuantities);
                                    Node next = bottomLayer.getNext();
                                    while (next != bottomLayer && next instanceof Item) {
                                        addItemToMap((Item) next, itemIds, itemQuantities);
                                        next = next.getNext();
                                    }

                                    Collections.reverse(itemIds);

                                    for (int i = 0; i < itemIds.size(); i++) {
                                        Integer id = itemIds.get(i);
                                        Integer qty = itemQuantities.get(id);
                                        String itemName;
                                        long price;

                                        if (id == 995) {
                                            price = qty;
                                            itemName = "Coins";
                                            itemStringBuilder.append(MessageFormat.format("{0} ({1})", itemName, formatValue(price)));
                                        } else {
                                            if (qty > 1) {
                                                if (qty == 65535) {
                                                    itemStringBuilder.append("Lots of ");
                                                } else {
                                                    itemStringBuilder.append(qty).append("x ");
                                                }
                                            }

                                            itemName = GameAPI.getItemDefinition(id).getName();
                                            itemStringBuilder.append(itemName);

                                            price = ItemPriceCache.getItemPrice(id) * qty;
                                            if (price > 0) {
                                                itemStringBuilder.append(MessageFormat.format(" ({0})", formatValue(price)));
                                            }
                                        }

                                        String itemString = itemStringBuilder.toString();
                                        itemStringBuilder.setLength(0);
                                        int screenX = point.x + 2 - (fontMetrics.stringWidth(itemString) / 2);

                                        g.setColor(Color.BLACK);
                                        g.drawString(itemString, screenX + 1, point.y - (15 * i) + 1);
                                        g.setColor(getValueColor(price));
                                        g.drawString(itemString, screenX, point.y - (15 * i));

                                        if (miniMapDotEnabled) {
                                            if (price >= MINIMUM_EXPENSIVE) {
                                                if (itemName != null) {
                                                    Point miniMapPoint = Perspective.worldToMiniMap(x, y);
                                                    g.setColor(Color.BLACK);
                                                    g.drawString(itemName, miniMapPoint.x + 1, miniMapPoint.y + 1);
                                                    g.setColor(getValueColor(price));
                                                    g.drawString(itemName, miniMapPoint.x, miniMapPoint.y);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };

        helper.registerOverlay(overlay);
    }

    private void addItemToMap(Item item, ArrayList<Integer> itemIds, Map<Integer, Integer> itemQuantities) {
        int id = item.getId();
        if (itemIds.contains(id)) {
            itemQuantities.put(id, itemQuantities.get(id) + item.getQuantity());
        } else {
            itemIds.add(id);
            itemQuantities.put(id, item.getQuantity());
        }
    }

    private Color getValueColor(long amount) {
        if (amount >= MINIMUM_VERY_EXPENSIVE) {
            return COLOR_VERY_EXPENSIVE;
        } else if (amount >= MINIMUM_EXPENSIVE) {
            return COLOR_EXPENSIVE;
        } else if (amount == 0) {
            return COLOR_UNTRADABLE;
        }
        return COLOR_LOW_PRICE;
    }

    private String formatValue(long n) {
        if (n == 65535) {
            return "65K+";
        } else if (n >= 1000) {
            return formatNumber(n);
        } else {
            return formatNumber(n) + "gp";
        }
    }

    private String formatNumber(double n) {
        return formatNumber(n, 0);
    }

    private String[] suffixes = {"", "K", "M", "B", "T", "Q", "W", "T", "F"};

    private String formatNumber(double n, int iteration) {
        if (n >= 1000) {
            return formatNumber(((int) (n / 100D)) / 10D, iteration + 1);
        } else {
            return ((int) n) + suffixes[iteration];
        }
    }
}
