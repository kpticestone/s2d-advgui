package de.s2d_advgui.core.resourcemanager;

import com.badlogic.gdx.graphics.Color;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.SwtColor;

@SuppressWarnings("nls")
public class ATheme {
    // -------------------------------------------------------------------------------------------------------------------------
    private Color widgetPrimaryBackgroundColor = new Color(1f, .75f, .5f, 1f);
    public static final String BORDERS_WHITE_NB_ROUND_5_PNG = "borders/white-nb-round-5.png";
    public static final String BORDERS_FOCUS_ROUND_5_PNG = "borders/focus-round-5.png";
    public static final String UI_CORNER_2_PNG = "ui/corner-2.png";
    public static final String UI_BORDER_1_HOR_PNG = "ui/border-1-hor.png";
    public static final String UI_BORDER_1_VER_PNG = "ui/border-1-ver.png";
    public static final String UI_PIPE1_PNG = "ui/pipe1.png";
    public static final String UI_SLOT_TYPE_MINOR_PNG = "ui/slotType_minor.png";
    public static final String ICONS_128_ANGEL_PNG = "icons/128/angel.png";
    public static final String ICONS_128_COW_PNG = "icons/128/cow.png";
    public static final String ICONS_128_APPLICATION_PNG = "icons/128/application.png";
    public static final String UI_CORNER_1_PNG = "ui/corner-1.png";
    public static final String UI_BACKGROUND_1_PNG = "ui/background-1.png";
    public static final String ICONS_128_SIGNALING_DISK_GREEN_PNG = "icons/128/signaling_disk_green.png";
    public static final String ICONS_128_SIGNALING_DISK_RED_PNG = "icons/128/signaling_disk_red.png";
    public static final String ICONS_128_SNOWFLAKE_PNG = "icons/128/snowflake.png";
    public static final String BORDERS_WHITE_ROUND_5_PNG = "borders/white-round-5.png";
    public static final String UI_BACKGROUND_1 = "ui/background-1.png";

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetPrimaryBackgroundColor() {
        if (TOldCompatibilityCode.TRUE) {
            return SwtColor.BORDER_COLOR_CYAN_HALF;
        }
        return this.widgetPrimaryBackgroundColor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetPrimaryBackgroundColorDisabled() {
        return Color.DARK_GRAY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetPrimaryBorderColorDisabled() {
        return Color.GRAY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetPrimaryBackgroundColorFocused() {
        return SwtColor.FOCUS;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetPrimaryBorderColor() {
        return SwtColor.BORDER_COLOR_CYAN;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetPrimaryBorderColorHovered() {
        return new Color(1f, 0f, 0f, 1f);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getWidgetWindowOverlayColor() {
        return new Color(.0f, .0f, .0f, 0.75f);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getLoadingScreenSpeed() {
        return 50f;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getLabelColor() {
        return this.getWidgetPrimaryBorderColor();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getLoadingScreenSplashScreen() {
        return null; // "splashscreens/s2d-advgui.jpg";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getLoadingScreenSplashScreen2() {
        return null; // "icons/128/remotecontrol2.png";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Color getLabelColorDisabled() {
        return Color.GRAY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
