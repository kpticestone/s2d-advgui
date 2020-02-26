package de.s2d_advgui.core.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.MasterViewport;

public abstract class ASwtStage_750_Rendering<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_725_Calculating<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_750_Rendering(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawKeyboardFocusBorder(Batch batch) {
        batch.setColor(Color.WHITE);
        Actor focus = this.getKeyboardFocus();
        if (focus != null) {
            Vector2 pos = focus.localToStageCoordinates(new Vector2());
            float s = 2;
            float w = focus.getWidth();
            float h = focus.getHeight();
            TextureRegion cyan = this.drawerManager.getResourceManager().getColorTextureRegion(Color.CYAN);
            batch.draw(cyan, pos.x, pos.y - s, w, s);
            batch.draw(cyan, pos.x, pos.y + h, w, s);
            batch.draw(cyan, pos.x - s, pos.y - s, s, h + s * 2);
            batch.draw(cyan, pos.x + w, pos.y - s, s, h + s * 2);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void draw() {
        try {
            this.deployViewportMetrics();
            if (this.getBatch().isDrawing()) {
                log.error("already drawing???");
                this.getBatch().end();
                return;
            }
            super.draw();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final void deployViewportMetrics() {
        MasterViewport vp = this.getViewport();
        vp.setMasterDims(this.stageDimensions, this.getGuiScale());
        getBatch().setProjectionMatrix(vp.getCamera().combined);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
