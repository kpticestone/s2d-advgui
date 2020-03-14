package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.rendering.ISwtBatchSaver;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;

public abstract class InternalWidgetDrawerShapeRenderer implements IInternalWidgetDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void drawIt(ISwtDrawerManager<?> pDrawerManager, Vector2 pScreenCoords, Rectangle pDims) {
        try (ISwtBatchSaver kj = pDrawerManager.batchSave(true)) {
            try (SwtDrawer_Shapes batch = pDrawerManager.startShapesDrawer()) {
                this._drawIt(batch, pScreenCoords, pDims);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void _drawIt(SwtDrawer_Shapes pShapeDrawer, Vector2 pScreenCoords, Rectangle pDims);

    // -------------------------------------------------------------------------------------------------------------------------
}
