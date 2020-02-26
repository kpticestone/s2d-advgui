package de.s2d_advgui.demo;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.stage.ASwtStage;

public final class DemoStage extends ASwtStage<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    public DemoStage(ISwtApplicationController<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onInit() throws Exception {
        this.applicationController.activateScreen(DemoScreenDescriptor.ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
