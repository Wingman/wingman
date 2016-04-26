package com.wingman.client.ui.style;

import org.pushingpixels.substance.api.*;
import org.pushingpixels.substance.api.colorscheme.BaseDarkColorScheme;
import org.pushingpixels.substance.api.painter.border.ClassicBorderPainter;
import org.pushingpixels.substance.api.painter.decoration.MatteDecorationPainter;
import org.pushingpixels.substance.api.painter.fill.StandardFillPainter;
import org.pushingpixels.substance.api.painter.highlight.ClassicHighlightPainter;
import org.pushingpixels.substance.api.painter.overlay.BottomLineOverlayPainter;
import org.pushingpixels.substance.api.painter.overlay.BottomShadowOverlayPainter;
import org.pushingpixels.substance.api.painter.overlay.TopBezelOverlayPainter;
import org.pushingpixels.substance.api.painter.overlay.TopLineOverlayPainter;
import org.pushingpixels.substance.api.shaper.StandardButtonShaper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OnyxSkin extends SubstanceSkin {

    public static final Color VERY_DARK_BLACK = new Color(15, 15, 15);
    public static final Color VERY_LIGHT_WHITE = new Color(238, 238, 238);
    public static final Color LIGHT_BROWN = new Color(199, 173, 129);
    public static final Color LIGHT_BLUE = new Color(0, 157, 230);
    public static final Color DARK_GRAY = new Color(91, 89, 82);

    public OnyxSkin() {
        SubstanceSkin.ColorSchemes schemes = getColorScheme();

        SubstanceColorScheme activeScheme = schemes.get("Onyx Active");
        SubstanceColorScheme enabledScheme = schemes.get("Onyx Enabled");

        // Default scheme bundle
        SubstanceColorSchemeBundle defaultSchemeBundle = new SubstanceColorSchemeBundle(activeScheme, enabledScheme, enabledScheme);
        defaultSchemeBundle.registerColorScheme(enabledScheme, 0.5f, ComponentState.DISABLED_UNSELECTED);

        // Decorations scheme bundle
        SubstanceColorSchemeBundle decorationsSchemeBundle = new SubstanceColorSchemeBundle(activeScheme, enabledScheme, enabledScheme);
        decorationsSchemeBundle.registerColorScheme(enabledScheme, 0.5f, ComponentState.DISABLED_UNSELECTED);

        // Header scheme bundle
        SubstanceColorSchemeBundle headerSchemeBundle = new SubstanceColorSchemeBundle(activeScheme, enabledScheme, enabledScheme);
        headerSchemeBundle.registerColorScheme(enabledScheme, 0.5f, ComponentState.DISABLED_UNSELECTED);

        // Borders
        SubstanceColorScheme borderDisabledSelectedScheme = schemes.get("Onyx Selected Disabled Border");
        SubstanceColorScheme borderScheme = schemes.get("Onyx Border");
        defaultSchemeBundle.registerColorScheme(borderDisabledSelectedScheme, ColorSchemeAssociationKind.BORDER, ComponentState.DISABLED_SELECTED);
        defaultSchemeBundle.registerColorScheme(borderScheme, ColorSchemeAssociationKind.BORDER);

        decorationsSchemeBundle.registerColorScheme(borderDisabledSelectedScheme, ColorSchemeAssociationKind.BORDER, ComponentState.DISABLED_SELECTED);
        decorationsSchemeBundle.registerColorScheme(borderScheme, ColorSchemeAssociationKind.BORDER);

        SubstanceColorScheme headerBorderScheme = schemes.get("Onyx Header Border");
        headerSchemeBundle.registerColorScheme(borderDisabledSelectedScheme, ColorSchemeAssociationKind.BORDER, ComponentState.DISABLED_SELECTED);
        headerSchemeBundle.registerColorScheme(headerBorderScheme, ColorSchemeAssociationKind.BORDER);

        // Marks
        SubstanceColorScheme markActiveScheme = schemes.get("Onyx Mark Active");
        defaultSchemeBundle.registerColorScheme(markActiveScheme, ColorSchemeAssociationKind.MARK, ComponentState.getActiveStates());

        decorationsSchemeBundle.registerColorScheme(markActiveScheme, ColorSchemeAssociationKind.MARK, ComponentState.getActiveStates());

        headerSchemeBundle.registerColorScheme(markActiveScheme, ColorSchemeAssociationKind.MARK, ComponentState.getActiveStates());

        // Separators
        SubstanceColorScheme separatorScheme = schemes.get("Onyx Separator");
        defaultSchemeBundle.registerColorScheme(separatorScheme, ColorSchemeAssociationKind.SEPARATOR);

        SubstanceColorScheme separatorDecorationsScheme = schemes.get("Onyx Decorations Separator");
        decorationsSchemeBundle.registerColorScheme(separatorDecorationsScheme, ColorSchemeAssociationKind.SEPARATOR);

        // Watermarks
        SubstanceColorScheme watermarkScheme = schemes.get("Onyx Watermark");

        SubstanceColorScheme decorationsWatermarkScheme = schemes.get("Onyx Decorations Watermark");

        SubstanceColorScheme headerWatermarkScheme = schemes.get("Onyx Header Watermark");

        headerSchemeBundle.registerHighlightColorScheme(activeScheme, 0.7f, ComponentState.ROLLOVER_UNSELECTED, ComponentState.ROLLOVER_ARMED, ComponentState.ARMED);
        headerSchemeBundle.registerHighlightColorScheme(activeScheme, 0.8f, ComponentState.SELECTED);
        headerSchemeBundle.registerHighlightColorScheme(activeScheme, 1.0f, ComponentState.ROLLOVER_SELECTED);

        this.registerDecorationAreaSchemeBundle(defaultSchemeBundle, watermarkScheme, DecorationAreaType.NONE);
        this.registerDecorationAreaSchemeBundle(decorationsSchemeBundle, decorationsWatermarkScheme, DecorationAreaType.TOOLBAR, DecorationAreaType.GENERAL, DecorationAreaType.FOOTER);
        this.registerDecorationAreaSchemeBundle(headerSchemeBundle, headerWatermarkScheme, DecorationAreaType.PRIMARY_TITLE_PANE, DecorationAreaType.SECONDARY_TITLE_PANE, DecorationAreaType.HEADER);

        setSelectedTabFadeStart(0.2);
        setSelectedTabFadeEnd(0.9);

        // Add overlay painters to paint drop shadows along the bottom edges of toolbars and footers
        this.addOverlayPainter(BottomShadowOverlayPainter.getInstance(), DecorationAreaType.TOOLBAR);
        this.addOverlayPainter(BottomShadowOverlayPainter.getInstance(), DecorationAreaType.FOOTER);

        // Add an overlay painter to paint a dark line along the bottom edge of toolbars
        BottomLineOverlayPainter toolbarBottomLineOverlayPainter = new BottomLineOverlayPainter(new ColorSchemeSingleColorQuery() {
            @Override
            public Color query(SubstanceColorScheme scheme) {
                return scheme.getUltraDarkColor().darker();
            }
        });
        this.addOverlayPainter(toolbarBottomLineOverlayPainter, DecorationAreaType.TOOLBAR);

        // Add an overlay painter to paint a dark line along the bottom edge of toolbars
        TopLineOverlayPainter toolbarTopLineOverlayPainter = new TopLineOverlayPainter(new ColorSchemeSingleColorQuery() {
            @Override
            public Color query(SubstanceColorScheme scheme) {
                Color foregroundColor = scheme.getForegroundColor();
                return new Color(foregroundColor.getRed(),
                        foregroundColor.getGreen(),
                        foregroundColor.getBlue(),
                        32);
            }
        });
        this.addOverlayPainter(toolbarTopLineOverlayPainter, DecorationAreaType.TOOLBAR);

        // Add an overlay painter to paint a bezel line along the top edge of footer
        TopBezelOverlayPainter footerTopBezelOverlayPainter = new TopBezelOverlayPainter(new ColorSchemeSingleColorQuery() {
            @Override
            public Color query(SubstanceColorScheme scheme) {
                return scheme.getUltraDarkColor().darker();
            }
        }, new ColorSchemeSingleColorQuery() {
            @Override
            public Color query(SubstanceColorScheme scheme) {
                Color foregroundColor = scheme.getForegroundColor();
                return new Color(foregroundColor.getRed(),
                        foregroundColor.getGreen(),
                        foregroundColor.getBlue(),
                        32);
            }
        });
        this.addOverlayPainter(footerTopBezelOverlayPainter, DecorationAreaType.FOOTER);

        this.buttonShaper = new StandardButtonShaper();
        this.watermark = null;
        this.fillPainter = new StandardFillPainter();
        this.decorationPainter = new MatteDecorationPainter();
        this.highlightPainter = new ClassicHighlightPainter();
        this.borderPainter = new ClassicBorderPainter();
    }

    private static SubstanceSkin.ColorSchemes getColorScheme() {
        List<SubstanceColorScheme> schemes = new ArrayList<>();

        schemes.add(new BaseDarkColorScheme("Onyx Enabled") {
            // Title bar text color
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            // Unchecked checkboxes
            @Override
            public Color getUltraLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            // Background/border of text input fields
            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            // Background/border of text input fields
            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            // Hovering unchecked checkboxes background
            @Override
            public Color getDarkColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Active") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            // Hovered/checked checkboxes background
            // JList item background
            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            // Hovered/checked checkboxes small upper background
            @Override
            public Color getLightColor() {
                return DARK_GRAY;
            }

            // Hovered/checked checkboxes background
            // JList item background
            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            // Hovered/checked checkboxes upper background
            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            // Selected text in text fields
            @Override
            public Color getUltraDarkColor() {
                return VERY_LIGHT_WHITE;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Selected Disabled Border") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Border") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            // Borders of all inner components
            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            // Focus border
            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Mark Active") {
            // Check icons in checkboxes
            @Override
            public Color getForegroundColor() {
                return LIGHT_BLUE;
            }

            // Check icons in checkboxes
            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            // Check icons in checkboxes
            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            // Focus border when checkbox checked
            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Highlight") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return LIGHT_BROWN;
            }

            @Override
            public Color getExtraLightColor() {
                return LIGHT_BROWN;
            }

            @Override
            public Color getLightColor() {
                return LIGHT_BROWN;
            }

            @Override
            public Color getMidColor() {
                return LIGHT_BROWN;
            }

            @Override
            public Color getDarkColor() {
                return LIGHT_BROWN;
            }

            @Override
            public Color getUltraDarkColor() {
                return LIGHT_BROWN;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Watermark") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            // Panel backgrounds
            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Decorations Watermark") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Separator") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getExtraLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getMidColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getDarkColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getUltraDarkColor() {
                return DARK_GRAY;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Decorations Separator") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getExtraLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getLightColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getMidColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getDarkColor() {
                return DARK_GRAY;
            }

            @Override
            public Color getUltraDarkColor() {
                return DARK_GRAY;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Header Watermark") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            // JMenuBar background - is gradiented
            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            // Lower JMenuBar background - is gradiented and darker
            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });

        schemes.add(new BaseDarkColorScheme("Onyx Header Border") {
            @Override
            public Color getForegroundColor() {
                return VERY_LIGHT_WHITE;
            }

            @Override
            public Color getUltraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getExtraLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getLightColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getMidColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getDarkColor() {
                return VERY_DARK_BLACK;
            }

            @Override
            public Color getUltraDarkColor() {
                return VERY_DARK_BLACK;
            }
        });
        return new SubstanceSkin.ColorSchemes(schemes);
    }

    @Override
    public String getDisplayName() {
        return "Onyx";
    }
}
