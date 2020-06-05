package de.s2d_advgui.demo;

import com.badlogic.gdx.Gdx;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.application.SwtApplication;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;

public class DemoApp extends SwtApplication<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>, DemoStage> {
    // -------------------------------------------------------------------------------------------------------------------------
    public DemoApp(ISwtDrawerManager<DemoResourceManager> pDrawerManager) throws Exception {
        super(pDrawerManager);
        fpsLimit = 144;
        this.registerScreen(DemoScreenDescriptor.getInstance());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected DemoStage _createStage(
            ISwtApplicationController<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pApplicationController) {
        return new DemoStage(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String getTitle() {
        return "Scene2D Widget Toolkit Demo (" + Gdx.graphics.getFramesPerSecond() + " fps)";
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
