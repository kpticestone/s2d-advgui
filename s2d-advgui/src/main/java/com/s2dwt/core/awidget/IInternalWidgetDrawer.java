package com.s2dwt.core.awidget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

@FunctionalInterface
public interface IInternalWidgetDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    void drawIt(Batch pBatch, Vector2 pScreenCoords, Rectangle pDims);

    // -------------------------------------------------------------------------------------------------------------------------
}