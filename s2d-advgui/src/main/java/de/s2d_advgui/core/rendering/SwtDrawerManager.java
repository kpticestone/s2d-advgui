package de.s2d_advgui.core.rendering;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public class SwtDrawerManager<RM extends AResourceManager> implements ISwtDrawerManager<RM> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final RM resourceManager;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final Batch batch;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ShapeRenderer shapeRenderer;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final CameraHolder cameraHolder = new CameraHolder();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtDrawerManager(RM pResourceManager) {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.resourceManager = pResourceManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtDrawerManager(@Nonnull Batch pBatch, @Nonnull ShapeRenderer pShapeRenderer, @Nonnull RM pResourceManager) {
        this.batch = pBatch;
        this.shapeRenderer = pShapeRenderer;
        this.resourceManager = pResourceManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtDrawerManager(@Nonnull ISwtDrawerManager<RM> pOther) {
        this.batch = pOther.getBatch();
        this.shapeRenderer = pOther.getShapeRenderer();
        this.resourceManager = pOther.getResourceManager();
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public SwtDrawerManager<RM> cloneDrawerWithOtherBatch(Batch batch, ShapeRenderer pShapeRenderer)
    {
        return new SwtDrawerManager<RM>(batch, pShapeRenderer, this.resourceManager);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final CameraHolder getCameraHolder() {
        return this.cameraHolder;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final SwtDrawer_Batch<RM> startBatchDrawer() {
        this.batch.setColor(Color.WHITE);
        return new SwtDrawer_Batch<>(this.resourceManager, this.batch);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final SwtDrawer_Shapes startShapesDrawer() {
        return new SwtDrawer_Shapes(this.shapeRenderer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final SwtDrawer_Shapes startShapesDrawer(@Nonnull ShapeType type) {
        return new SwtDrawer_Shapes(this.shapeRenderer, type);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final Batch getBatch() {
        return this.batch;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final RM getResourceManager() {
        return this.resourceManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
