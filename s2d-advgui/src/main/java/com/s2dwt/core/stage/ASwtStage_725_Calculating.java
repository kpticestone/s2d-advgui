package com.s2dwt.core.stage;

import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SwtScreen;

public abstract class ASwtStage_725_Calculating<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_600_Events<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_725_Calculating(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
        this.addUpdateHandler(delta -> {
            SwtScreen<RM, DM> sc = this.getScreen();
            if (sc != null) {
                sc.act(delta);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
