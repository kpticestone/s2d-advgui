package de.s2d_advgui.core.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

import de.s2d_advgui.commons.SphThread;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

import java.util.Iterator;

public abstract class ASwtApplication_800_Render<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>>
        extends ASwtApplication_310_Events<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtApplication_800_Render(DM pDrawerManager) {
        super(pDrawerManager);
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * Wird nur vom Scene2d-ApplicationListener aufgerufen.
     *
     * @throws Exception
     */
    public final void render() throws Exception {
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
            for (Iterator<STAGE> iterator = this.stages.iterator(); iterator.hasNext(); ) {
                STAGE stage = iterator.next();
                stage.act(delta);
                stage.draw();
                if (stage.isMarkedForDisposal()) {
                    iterator.remove();
                    stage.dispose();
                    updateSplitScreenArrangements(this.stages.size());
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
