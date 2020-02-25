package com.s2dwt.demo;

import com.badlogic.gdx.Gdx;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.application.SwtApplication;
import com.s2dwt.core.rendering.ISwtDrawerManager;

public class DemoApp extends SwtApplication<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>, DemoStage> {
    // -------------------------------------------------------------------------------------------------------------------------
    public DemoApp(ISwtDrawerManager<DemoResourceManager> pDrawerManager) {
        super(pDrawerManager);
        this.registerScreen(new DemoScreenDescriptor());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected DemoStage _createStage(ISwtApplicationController<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pApplicationController) {
        return new DemoStage(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String getTitle() {
        return "Scene2D Widget Toolkit Demo (" + Gdx.graphics.getFramesPerSecond() + " fps)";
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
