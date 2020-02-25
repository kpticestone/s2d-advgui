package com.s2dwt.core.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

import javax.annotation.Nonnull;

public abstract class ASwtApplication_300_Input<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> extends ASwtApplication_150_ScreenStages<RM, DM, STAGE> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final SwtApplicationInputDispatcher inputBroadCaster;

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
    public final SwtApplicationInputDispatcher getInputBroadCaster() {
        return this.inputBroadCaster;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    final void handleInput() {
        if (this.inputBroadCaster.hasKeyboardActivity()) {
            STAGE someStage = this.createStage();
            someStage.addDisposeListener(inputBroadCaster::detachKeyboard);
            this.inputBroadCaster.attach(someStage);
        }
        this.inputBroadCaster.fetchUnassignedControllerActivity(controller -> {
            STAGE someStage = this.createStage();
            ControllerListener controllerListener = someStage.getControllerListener(controller);
            someStage.addDisposeListener(() -> inputBroadCaster.detach(controllerListener));
            this.inputBroadCaster.attach(controller, controllerListener);
        });
    }
    // -------------------------------------------------------------------------------------------------------------------------
}
