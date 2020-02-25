package com.s2dwt.core.rendering;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.s2dwt.core.camera.CameraHolder;
import com.s2dwt.core.resourcemanager.AResourceManager;

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
        Batch pBatch = this.getBatch();
        ShapeRenderer sr = this.getShapeRenderer();
        Matrix4 pjm = new Matrix4(pBatch.getProjectionMatrix());
        Matrix4 tmj = new Matrix4(pBatch.getTransformMatrix());
        Matrix4 pm = new Matrix4(sr.getProjectionMatrix());
        Matrix4 ps = new Matrix4(sr.getTransformMatrix());
        return () -> {
            pBatch.setTransformMatrix(tmj);
            pBatch.setProjectionMatrix(pjm);
            sr.setProjectionMatrix(pm);
            sr.setTransformMatrix(ps);
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
    ISwtDrawerManager<RM> cloneDrawerWithOtherBatch(Batch batch, ShapeRenderer pShapeRenderer);

    // -------------------------------------------------------------------------------------------------------------------------
}
