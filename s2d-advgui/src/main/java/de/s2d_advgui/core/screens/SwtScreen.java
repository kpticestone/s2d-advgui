package de.s2d_advgui.core.screens;

import javax.annotation.Nonnull;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.awidget.ASwtWidget_ControllerLevel;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.core.window.ASwtWindowDescriptor;
import de.s2d_advgui.core.window.SwtWindow;

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
    protected final Group __createActor() {
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
        return window;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
