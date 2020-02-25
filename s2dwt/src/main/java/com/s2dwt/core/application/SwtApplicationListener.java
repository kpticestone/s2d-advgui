package com.s2dwt.core.application;

import com.badlogic.gdx.ApplicationListener;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

public abstract class SwtApplicationListener<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>, APP extends SwtApplication<RM, DM, STAGE>> implements ApplicationListener {
    // -------------------------------------------------------------------------------------------------------------------------
    private APP application;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtApplicationListener() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract APP createApplication();

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void create() {
        this.application = this.createApplication();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public APP getApplication() {
        return this.application;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void render() {
        this.application.render();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void pause() {
        this.application.pause();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void resume() {
        this.application.resume();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void resize(int width, int height) {
        this.application.resize(width, height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void dispose() {
        this.application.dispose();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
