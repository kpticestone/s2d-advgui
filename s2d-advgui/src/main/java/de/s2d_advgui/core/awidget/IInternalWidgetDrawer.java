package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;

@FunctionalInterface
public interface IInternalWidgetDrawer
{
    // -------------------------------------------------------------------------------------------------------------------------
    void drawIt(ISwtDrawerManager<?> pDrawerManager, Vector2 pScreenCoords, Rectangle pDims);

    // -------------------------------------------------------------------------------------------------------------------------
}
