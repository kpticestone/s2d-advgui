package de.s2d_advgui.core.input;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public abstract class ASwtMouseMoveListenerCanvas<RM extends AResourceManager> implements ISwtMouseMoveListener {
    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtCanvas<RM, SwtDrawerManager<RM>> canvas;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtMouseMoveListenerCanvas(@Nonnull SwtCanvas<RM, SwtDrawerManager<RM>> pCanvas) {
        this.canvas = pCanvas;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean onMouseMove(float pX, float pY, int pButton) {
        Vector2 dke = this.canvas.sceneCoordsToCanvasCoords(pX, pY);
        return this.onCanvasMouseMove(pX, pY, dke.x, dke.y, pButton);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param pSceneX
     * @param pSceneY
     * @param pCanvasX
     * @param pCanvasY
     * @param pButton
     * @return
     */
    protected boolean onCanvasMouseMove(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY, int pButton) {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean onMouseDown(float pX, float pY, int pButton) {
        Vector2 dke = this.canvas.sceneCoordsToCanvasCoords(pX, pY);
        return this.onCanvasMouseDown(pX, pY, dke.x, dke.y, pButton);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param pSceneX
     * @param pSceneY
     * @param pCanvasX
     * @param pCanvasY
     * @param pButton
     * @return
     */
    protected boolean onCanvasMouseDown(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY, int pButton) {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean onMouseUp(float pX, float pY, int pButton) {
        Vector2 dke = this.canvas.sceneCoordsToCanvasCoords(pX, pY);
        return this.onCanvasMouseUp(pX, pY, dke.x, dke.y, pButton);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param pSceneX
     * @param pSceneY
     * @param pCanvasX
     * @param pCanvasY
     * @param pButton
     * @return
     */
    protected boolean onCanvasMouseUp(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY, int pButton) {
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
