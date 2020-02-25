package com.s2dwt.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leo.commons.utils.TOldCompatibilityCode;
import com.s2dwt.core.camera.CameraHolder;

public final class MasterViewport extends Viewport {
    // -------------------------------------------------------------------------------------------------------------------------
    public MasterViewport() {
        setCamera(new OrthographicCamera());
        update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SlaveViewport runSlaveViewport(CameraHolder dims) {
        return this.runSlaveViewport(dims.getLastDims());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SlaveViewport runSlaveViewport(Rectangle dims) {
        int wasX = this.getScreenX();
        int wasW = this.getScreenWidth();
        int wasY = this.getScreenY();
        int wasH = this.getScreenHeight();
        this.setScreenX((int) dims.x + wasX);
        this.setScreenWidth((int) dims.width);
        this.setScreenY((int) dims.y + wasY);
        this.setScreenHeight((int) dims.height);
        this.apply();
        return new SlaveViewport() {
            @Override
            public void close() {
                MasterViewport.this.setScreenX(wasX);
                MasterViewport.this.setScreenY(wasY);
                MasterViewport.this.setScreenWidth(wasW);
                MasterViewport.this.setScreenHeight(wasH);
                MasterViewport.this.apply();
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setMasterDims(float bx, float by, float bw, float bh, float pGuiScale) {

        if (TOldCompatibilityCode.TRUE) {
            this.setWorldSize(bw, bh);
            this.setScreenBounds((int) bx, (int) by, (int) (bw*pGuiScale), (int) (bh*pGuiScale));
            OrthographicCamera cam = (OrthographicCamera) this.getCamera();
            cam.viewportWidth = bw;
            cam.viewportHeight = bh;
            cam.zoom = 1f;
            cam.position.x = 0;
            cam.position.y = 0;
            cam.position.z = 0;
            cam.update(true);
            this.apply(true);
            return;
        }

        this.setWorldSize(bw, bh);
        this.setScreenBounds((int) bx, (int) by, (int) bw, (int) bh);
        OrthographicCamera cam = (OrthographicCamera) this.getCamera();
        cam.viewportWidth = bw;
        cam.viewportHeight = bh;
        cam.zoom = 1f;
        cam.position.x = 0;
        cam.position.y = 0;
        cam.position.z = 0;
        cam.update(true);
        this.apply(true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setMasterDims(Rectangle stageDimensions, float pGuiScale) {
        this.setMasterDims(stageDimensions.x, stageDimensions.y, stageDimensions.width, stageDimensions.height, pGuiScale);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
