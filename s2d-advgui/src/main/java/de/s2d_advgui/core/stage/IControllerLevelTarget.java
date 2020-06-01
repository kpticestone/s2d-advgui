package de.s2d_advgui.core.stage;

import de.s2d_advgui.core.awidget.ControllerLevelImpl;
import de.s2d_advgui.core.awidget.IControllerLevel;

public interface IControllerLevelTarget {
    // -------------------------------------------------------------------------------------------------------------------------
    void setCurrentControllerLevel(IControllerLevel pControllerLevel);

    // -------------------------------------------------------------------------------------------------------------------------
    IControllerLevel removeCurrentControllerLevel();
    void removeControllerLevel(IControllerLevel level);

    // -------------------------------------------------------------------------------------------------------------------------
    IControllerLevel getControllerLevel();

    // -------------------------------------------------------------------------------------------------------------------------
}
