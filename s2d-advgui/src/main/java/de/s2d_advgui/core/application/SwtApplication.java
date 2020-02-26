package de.s2d_advgui.core.application;

import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.SphScreenDescriptor_InputSelect;
import de.s2d_advgui.core.screens.SphScreenDescriptor_Loading;
import de.s2d_advgui.core.screens.SwtScreen;
import de.s2d_advgui.core.stage.ASwtStage;

public abstract class SwtApplication<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> extends ASwtApplication_800_Render<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtApplication(DM pDrawerManager) throws Exception {
        super(pDrawerManager);
        this.registerScreen(SphScreenDescriptor_Loading.getInstance());
        this.registerScreen(SphScreenDescriptor_InputSelect.getInstance());
        SwtScreen<RM, DM> screen = this.activateScreen(-1, SphScreenDescriptor_Loading.ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
