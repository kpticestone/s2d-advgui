package com.s2dwt.demo;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.s2dwt.core.application.SwtApplicationListener;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.rendering.SwtDrawerManager;

public class DemoAppListener extends SwtApplicationListener<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>, DemoStage, DemoApp>
{
    // -------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] arg)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 800);
        // config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        // config.setHdpiMode(HdpiMode.Pixels);
        config.useVsync(true);
        // config.useOpenGL3(true, 3, 2);
        new Lwjgl3Application(new DemoAppListener(), config); // needs to be in the main thread for osx
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected DemoApp createApplication() throws Exception
    {
        return new DemoApp(new SwtDrawerManager<>(new DemoResourceManager()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
