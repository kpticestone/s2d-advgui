package com.s2dwt.demo;

import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.stage.ASwtStage;

public final class DemoStage extends ASwtStage<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    public DemoStage(ISwtApplicationController<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onInit() {
        this.applicationController.activateScreen(DemoScreenDescriptor.ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
