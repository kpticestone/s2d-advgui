package de.s2d_advgui.core.rendering;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public interface ISwtDrawerManager<RM extends AResourceManager> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    SwtDrawer_Batch<RM> startBatchDrawer();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    SwtDrawer_Shapes startShapesDrawer();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    SwtDrawer_Shapes startShapesDrawer(@Nonnull ShapeType type);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    Batch getBatch();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    ShapeRenderer getShapeRenderer();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    RM getResourceManager();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    CameraHolder getCameraHolder();

    // -------------------------------------------------------------------------------------------------------------------------
    default ISwtBatchSaver batchSave() {
        return this.batchSave(false);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default ISwtBatchSaver batchSave(boolean stopBatch) {
        Batch pBatch = this.getBatch();
        ShapeRenderer sr = this.getShapeRenderer();
        Matrix4 pjm = new Matrix4(pBatch.getProjectionMatrix());
        Matrix4 tmj = new Matrix4(pBatch.getTransformMatrix());
        Matrix4 pm = new Matrix4(sr.getProjectionMatrix());
        Matrix4 ps = new Matrix4(sr.getTransformMatrix());
        boolean restart;
        if (stopBatch && pBatch.isDrawing()) {
            pBatch.end();
            restart = true;
        } else {
            restart = false;
        }
        return () -> {
            pBatch.setTransformMatrix(tmj);
            pBatch.setProjectionMatrix(pjm);
            sr.setProjectionMatrix(pm);
            sr.setTransformMatrix(ps);
            if (restart) {
                pBatch.begin();
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
    Batch setBatch(Batch spriteBatch);

    // -------------------------------------------------------------------------------------------------------------------------
}
