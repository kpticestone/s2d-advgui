package de.s2d_advgui.core.application;

import java.util.ArrayList;

import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

public abstract class ASwtApplication_310_Events<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> extends ASwtApplication_300_Input<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtApplication_310_Events(DM pDrawerManager) {
        super(pDrawerManager);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void pause() {
        for (STAGE stage : this.stages) {
            stage.pause();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void resume() {
        for (STAGE stage : this.stages) {
            stage.resume();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void dispose() {
        for (STAGE stage : new ArrayList<>(this.stages)) {
            stage.dispose();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void resize(int pWidth, int pHeight) {
        if (this.width != pWidth || this.height != pHeight) {
            this.width = pWidth;
            this.height = pHeight;
            updateScreenSplit();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
