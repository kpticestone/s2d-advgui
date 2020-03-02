package de.s2d_advgui.core.application;

import javax.annotation.Nonnull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;

import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

public abstract class ASwtApplication_300_Input<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>>
        extends ASwtApplication_150_ScreenStages<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final SwtApplicationInputDispatcher inputBroadCaster;

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean allowInputRegister = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtApplication_300_Input(DM pDrawerManager) {
        super(pDrawerManager);
        Controllers.getControllers(); // in order to initialize the Controllers right away
        if (Gdx.input.getInputProcessor() != null) {
            throw new IllegalStateException("inputProcessor already exists. Did you create multiple GameHolders?"); //$NON-NLS-1$
        }
        this.inputBroadCaster = new SwtApplicationInputDispatcher();
        Gdx.input.setInputProcessor(this.inputBroadCaster);
        Controllers.addListener(this.inputBroadCaster);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    final void registerLoadReady() {
        this.allowInputRegister = true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtApplicationInputDispatcher getInputBroadCaster() {
        return this.inputBroadCaster;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    final void handleInput() throws Exception {
        if (!this.allowInputRegister) return;
        if (this.inputBroadCaster.hasKeyboardActivity()) {
            STAGE someStage = this.createStage();
            someStage.addDisposeListener(inputBroadCaster::detachKeyboard);
            this.inputBroadCaster.attach(someStage);
        }
        this.inputBroadCaster.fetchUnassignedControllerActivity(controller -> {
            try {
                STAGE someStage = this.createStage();
                ControllerListener controllerListener = someStage.getControllerListener(controller);
                someStage.addDisposeListener(() -> inputBroadCaster.detach(controllerListener));
                this.inputBroadCaster.attach(controller, controllerListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    // -------------------------------------------------------------------------------------------------------------------------
}
