package com.wingman.defaultplugins.grounditems;

import com.wingman.client.api.generated.*;
import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.api.plugin.PluginDependency;
import com.wingman.client.api.plugin.PluginHelper;
import com.wingman.client.api.ui.settingscreen.SettingsItem;
import com.wingman.client.api.ui.settingscreen.SettingsSection;
import com.wingman.defaultplugins.devutils.enums.GameState;
import com.wingman.defaultplugins.devutils.game.world.Perspective;
import com.wingman.defaultplugins.devutils.util.external.ItemPriceCache;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;

import java.awt.*;
import java.text.MessageFormat;
import java.util.*;

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

    private GroundItemsSettings settings;

    private Font textFont = new Font("Verdana", Font.PLAIN, 11);
    private FontMetrics fontMetrics = null;
    private final StringBuilder itemStringBuilder = new StringBuilder();

    @Plugin.Setup
    public void setup() {
        settings = new GroundItemsSettings(helper);

        SettingsSection settingsSection = new SettingsSection(
                helper.getContainer().getInfo(),
                newState -> {
                    settings.setEnabled(newState);
                    settings.saveToFile();
                },
                true
        );

        {
            SettingsItem settingsItem = new SettingsItem("Display expensive items on the mini-map");

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(settings.isMiniMapDotEnabled());
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                settings.setMiniMapDotEnabled(newValue);
                settings.saveToFile();
            });

            settingsItem.add(checkBox);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Minimum item price to be marked as expensive");

            Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE,
                    settings.getExpensive(), 1000);

            spinner.valueProperty().addListener(((observable, oldValue, newValue) -> {
                settings.setExpensive(newValue);
                settings.saveToFile();
            }));

            settingsItem.add(spinner);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Minimum item price to be marked as very expensive");

            Spinner<Integer> spinner = new Spinner<>(0, Integer.MAX_VALUE,
                    settings.getVeryExpensive(), 1000);

            spinner.valueProperty().addListener(((observable, oldValue, newValue) -> {
                settings.setVeryExpensive(newValue);
                settings.saveToFile();
            }));

            settingsItem.add(spinner);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Text color for items marked as very expensive");

            ColorPicker colorPicker = new ColorPicker(javafx.scene.paint.Color.web(settings.getVeryExpensiveColor()));

            colorPicker.setOnAction((e) -> {
                javafx.scene.paint.Color color = colorPicker.getValue();

                settings.setVeryExpensiveColor(String.format(
                        "#%02X%02X%02X",
                        (int)(color.getRed() * 255),
                        (int)(color.getGreen() * 255),
                        (int)(color.getBlue() * 255 ))
                );

                settings.saveToFile();
            });

            settingsItem.add(colorPicker);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Text color for items marked as expensive");

            ColorPicker colorPicker = new ColorPicker(javafx.scene.paint.Color.web(settings.getExpensiveColor()));

            colorPicker.setOnAction((e) -> {
                javafx.scene.paint.Color color = colorPicker.getValue();

                settings.setExpensiveColor(String.format(
                        "#%02X%02X%02X",
                        (int)(color.getRed() * 255),
                        (int)(color.getGreen() * 255),
                        (int)(color.getBlue() * 255 ))
                );

                settings.saveToFile();
            });

            settingsItem.add(colorPicker);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Text color for items marked as cheap");

            ColorPicker colorPicker = new ColorPicker(javafx.scene.paint.Color.web(settings.getCheapColor()));

            colorPicker.setOnAction((e) -> {
                javafx.scene.paint.Color color = colorPicker.getValue();

                settings.setCheapColor(String.format(
                        "#%02X%02X%02X",
                        (int)(color.getRed() * 255),
                        (int)(color.getGreen() * 255),
                        (int)(color.getBlue() * 255 ))
                );

                settings.saveToFile();
            });

            settingsItem.add(colorPicker);
            settingsSection.add(settingsItem);
        }

        {
            SettingsItem settingsItem = new SettingsItem("Text color for items marked as untradeable");

            ColorPicker colorPicker = new ColorPicker(javafx.scene.paint.Color.web(settings.getUnTradeableColor()));

            colorPicker.setOnAction((e) -> {
                javafx.scene.paint.Color color = colorPicker.getValue();

                settings.setUnTradeableColor(String.format(
                        "#%02X%02X%02X",
                        (int)(color.getRed() * 255),
                        (int)(color.getGreen() * 255),
                        (int)(color.getBlue() * 255 ))
                );

                settings.saveToFile();
            });

            settingsItem.add(colorPicker);
            settingsSection.add(settingsItem);
        }

        helper.registerSettingsSection(settingsSection);
    }

    @Plugin.Activate
    public void activate() {
        Overlay overlay = new Overlay(false) {
            @Override
            public boolean shouldDraw() {
                return settings.isEnabled()
                        && GameAPI.getGameState() == GameState.PLAYING;
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

                                Optional<Point> point = Perspective
                                        .worldToScreen(x, y, plane, itemLayer.getHeight());

                                if (point.isPresent()) {
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
                                        long price = 0;

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

                                            Optional<Integer> optionalPrice = ItemPriceCache.getItemPrice(id);

                                            if (optionalPrice.isPresent()) {
                                                price = optionalPrice.get() * qty;
                                                itemStringBuilder.append(MessageFormat.format(" ({0})", formatValue(price)));
                                            }
                                        }

                                        String itemString = itemStringBuilder.toString();
                                        itemStringBuilder.setLength(0);
                                        int screenX = point.get().x + 2 - (fontMetrics.stringWidth(itemString) / 2);

                                        g.setColor(Color.BLACK);
                                        g.drawString(itemString, screenX + 1, point.get().y - (15 * i) + 1);
                                        g.setColor(getValueColor(price));
                                        g.drawString(itemString, screenX, point.get().y - (15 * i));

                                        if (settings.isMiniMapDotEnabled()) {
                                            if (price >= settings.getExpensive()) {
                                                if (itemName != null) {
                                                    Optional<Point> miniMapPoint = Perspective
                                                            .worldToMiniMap(x, y);

                                                    if (miniMapPoint.isPresent()) {
                                                        g.setColor(Color.BLACK);
                                                        g.drawString(itemName,
                                                                miniMapPoint.get().x + 1,
                                                                miniMapPoint.get().y + 1);
                                                        g.setColor(getValueColor(price));
                                                        g.drawString(itemName,
                                                                miniMapPoint.get().x,
                                                                miniMapPoint.get().y);
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
        if (amount >= settings.getVeryExpensive()) {
            return Color.decode(settings.getVeryExpensiveColor());
        } else if (amount >= settings.getExpensive()) {
            return Color.decode(settings.getExpensiveColor());
        } else if (amount == 0) {
            return Color.decode(settings.getUnTradeableColor());
        }
        return Color.decode(settings.getCheapColor());
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
