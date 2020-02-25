package com.s2dwt.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.rendering.SwtDrawer_Batch;

public abstract class InternalWidgetDrawerBatch implements IInternalWidgetDrawer
{
    @Override
    public final void drawIt(ISwtDrawerManager<?> pDrawerManager, Vector2 pScreenCoords, Rectangle pDims)
    {
        try (SwtDrawer_Batch<?> batch = pDrawerManager.startBatchDrawer())
        {
            this._drawIt(batch, pScreenCoords, pDims);
        }
    }

    protected abstract void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims);
}
