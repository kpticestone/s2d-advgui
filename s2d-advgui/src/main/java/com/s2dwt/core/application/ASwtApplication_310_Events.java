package com.s2dwt.core.application;

import java.util.ArrayList;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

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
