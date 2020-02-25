package com.s2dwt.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.s2dwt.core.rendering.ISwtDrawerManager;

@FunctionalInterface
public interface IInternalWidgetDrawer
{
    // -------------------------------------------------------------------------------------------------------------------------
    void drawIt(ISwtDrawerManager<?> pDrawerManager, Vector2 pScreenCoords, Rectangle pDims);

    // -------------------------------------------------------------------------------------------------------------------------
}
