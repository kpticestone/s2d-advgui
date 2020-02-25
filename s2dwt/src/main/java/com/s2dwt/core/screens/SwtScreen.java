package com.s2dwt.core.screens;

import javax.annotation.Nonnull;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.awidget.ASwtWidget_ControllerLevel;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;
import com.s2dwt.core.window.ASwtWindowDescriptor;
import com.s2dwt.core.window.SwtWindow;

public abstract class SwtScreen<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtWidget_ControllerLevel<Group> implements Screen {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ASwtStage<RM, DM> stage;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ISwtDrawerManager<RM> drawerManager;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ISwtApplicationController<RM, DM> applicationController;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtScreen(@Nonnull ASwtStage<RM, DM> pStage) {
        super(pStage);
        this.stage = pStage;
        this.stage.clear();
        this.drawerManager = pStage.getDrawerManager();
        this.applicationController = pStage.getApplicationController();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final ASwtStage<RM, DM> getStage() {
        return this.stage;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected final Group createActor() {
        return this.stage.getRoot();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void resize(int w, int h) {
        this.stage.getViewport().update(w, h, true);
        this.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void render(float delta) {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void resume() {
        System.err.println("AMyScreenAdapter.resume()");
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void hide() {
        System.err.println("AMyScreenAdapter.hide()");
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void pause() {
        System.err.println("AMyScreenAdapter.pause()");
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void show() {
        System.err.println("AMyScreenAdapter.show()");
    }

    // -------------------------------------------------------------------------------------------------------------------------
//  public final void addMouseMoveHandler(AInputMouseMoveHandler pInputMouseMoveHandler) {
//      this.inputMouseMoveHandler.add(pInputMouseMoveHandler);
//  }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtWindow createWindow(ASwtWindowDescriptor desc) {
        SwtWindow window = desc.createWindow(this.stage);
        this.addSubWidget(window);
        return window;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
