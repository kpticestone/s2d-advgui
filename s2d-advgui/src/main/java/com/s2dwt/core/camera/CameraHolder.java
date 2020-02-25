package com.s2dwt.core.camera;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.leo.spheres.core.utils.CalcUtils;

public final class CameraHolder {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final OrthographicCamera camera = new OrthographicCamera();

    // -------------------------------------------------------------------------------------------------------------------------
    private float wantedZoom = 0.02f;

    // -------------------------------------------------------------------------------------------------------------------------
    private Polygon cullingArea;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Rectangle lastDims = new Rectangle();

    // -------------------------------------------------------------------------------------------------------------------------
    public CameraHolder() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Polygon getCullingArea() {
        return this.cullingArea;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setCullingArea(Polygon cullingArea) {
        this.cullingArea = cullingArea;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getWantedZoom() {
        return this.wantedZoom;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setWantedZoom(float wantedZoom) {
        this.wantedZoom = wantedZoom;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean adjustZoom(int pAmount) {
        float distance = this.wantedZoom - this.camera.zoom;
        if (CalcUtils.sign(distance) != CalcUtils.sign(pAmount)) {
            this.wantedZoom = this.wantedZoom - (distance / 2f);
        }
        float mod = pAmount / (10f / this.wantedZoom);
        this.wantedZoom = Math.max(this.wantedZoom + mod, .01f);
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void unproject(Vector2 in, Vector2 out) {
        OrthographicCamera cam = this.camera;
        Vector3 totrans = new Vector3(in.x, in.y, 0);
        Vector3 oxx = unproject(cam, totrans, this.getLastDims().x, this.getLastDims().y, this.getLastDims().width, this.getLastDims().height);
        out.x = oxx.x;
        out.y = oxx.y;

    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * Die unproject-Methoden an der Standard-Camera-Implementierung unterstützt
     * leider unsere Canvas-Platzierungen nicht richtig, weil diese immer von einer
     * vollen Fenster-Höhe ausgehen. Deshalb gibt es nun eine leicht abgewandelte
     * Fassung hier... :D
     * 
     * @param cam
     * @param screenCoords
     * @param viewportX
     * @param viewportY
     * @param viewportWidth
     * @param viewportHeight
     * @return
     */
    public Vector3 unproject(Camera cam, Vector3 screenCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        float x = screenCoords.x, y = screenCoords.y;
        x = x - viewportX;
        y = y - viewportY;
        screenCoords.x = (2 * x) / viewportWidth - 1;
        screenCoords.y = -((2 * y) / viewportHeight - 1);
        screenCoords.prj(cam.invProjectionView);
        return screenCoords;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setViewport(float x, float y, float width, float height) {
//        System.err.println("CameraHolder.setViewport(" + x + ", " + y + ", " + width + ", " + height + ");");
        // height+= 500;
        // y+= 400;
        this.getLastDims().set(x, y, width, height);
        this.camera.viewportWidth = width;
        this.camera.viewportHeight = height;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Rectangle getLastDims() {
        return this.lastDims;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}