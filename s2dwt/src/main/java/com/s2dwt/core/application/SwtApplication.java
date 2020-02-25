package com.s2dwt.core.application;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SphScreenDescriptor_InputSelect;
import com.s2dwt.core.screens.SphScreenDescriptor_Loading;
import com.s2dwt.core.screens.SwtScreen;
import com.s2dwt.core.stage.ASwtStage;

public abstract class SwtApplication<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> extends ASwtApplication_800_Render<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtApplication(DM pDrawerManager) {
        super(pDrawerManager);
        this.registerScreen(SphScreenDescriptor_Loading.getInstance());
        this.registerScreen(SphScreenDescriptor_InputSelect.getInstance());
        SwtScreen<RM, DM> screen = this.activateScreen(-1, SphScreenDescriptor_Loading.ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
