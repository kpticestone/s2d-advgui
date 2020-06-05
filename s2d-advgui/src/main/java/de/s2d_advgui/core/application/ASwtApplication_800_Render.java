package de.s2d_advgui.core.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

import java.util.ArrayList;

public abstract class ASwtApplication_800_Render<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>>
        extends ASwtApplication_310_Events<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final LwjglSync lwjglSync = new LwjglSync();

    // -------------------------------------------------------------------------------------------------------------------------
    public int fpsLimit = -1;

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
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL30.GL_BLEND);

        Gdx.graphics.setTitle(this.getTitle());

        float delta = Gdx.graphics.getDeltaTime();
        if (delta > 0.2f) {
            log.error("OUCH: {}", delta); //$NON-NLS-1$
            delta = 0.2f;
        }

        this.handleInput();

        if (this.stages.isEmpty()) {
            this.zero.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            this.zero.act(delta);
            this.zero.draw();
        } else {
            this.updateSplitScreenArrangements(this.stages.size());
            this.updateScreenSplit();
            for (STAGE stage : new ArrayList<>(this.stages)) {
                stage.act(delta);
                stage.draw();
                stage.executeQueuedCommands();
            }
        }

        if (fpsLimit > 0) {
            lwjglSync.sync(fpsLimit);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
