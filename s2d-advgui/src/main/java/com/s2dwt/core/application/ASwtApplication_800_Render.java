package com.s2dwt.core.application;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.leo.commons.utils.SphThread;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

public abstract class ASwtApplication_800_Render<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> extends ASwtApplication_310_Events<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtApplication_800_Render(DM pDrawerManager) {
        super(pDrawerManager);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * Wird nur vom Scene2d-ApplicationListener aufgerufen.
     */
    public final void render() {
        float delta = Gdx.graphics.getDeltaTime();
        if (delta < 0.005F) {
            // Chill out!
            SphThread.sleep(100);
            return;
        }
        if (delta > 0.2f) {
            log.error("OUCH: {}", delta); //$NON-NLS-1$
            delta = 0.2f;
        }

        this.handleInput();

        Gdx.graphics.setTitle(this.getTitle());
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        if (this.stages.isEmpty()) {
            this.zero.setBounds(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
            this.zero.act(delta);
            this.zero.draw();
        } else {
            this.updateSplitScreenArrangements(this.stages.size());
            this.updateScreenSplit();
            for (STAGE stage : this.stages) {
                stage.act(delta);
                stage.draw();
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
