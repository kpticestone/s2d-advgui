package de.s2d_advgui.demo;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.badlogic.gdx.graphics.glutils.HdpiMode;
import de.s2d_advgui.core.application.SwtApplicationListener;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawerManager;

public class DemoAppListener extends
        SwtApplicationListener<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>, DemoStage, DemoApp> {
    // -------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 800);
        // config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        // config.setHdpiMode(HdpiMode.Pixels);
        config.useVsync(false);
        // config.useOpenGL3(true, 3, 2);
        config.setHdpiMode(HdpiMode.Logical);
        new Lwjgl3Application(new DemoAppListener(), config); // needs to be in the main thread for osx
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected DemoApp createApplication() throws Exception {
        return new DemoApp(new SwtDrawerManager<>(new DemoResourceManager()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
