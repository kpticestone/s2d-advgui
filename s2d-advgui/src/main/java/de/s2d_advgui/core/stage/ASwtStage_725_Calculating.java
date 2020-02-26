package de.s2d_advgui.core.stage;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.SwtScreen;

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
