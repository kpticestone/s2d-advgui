package com.s2dwt.core.stage;

import com.leo.commons.utils.Trigger;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SwtScreen;
import com.s2dwt.core.window.ASwtWindowDescriptor;
import com.s2dwt.core.window.SwtWindow;
import com.s2dwt.core.window.WindowID;

import java.util.HashMap;
import java.util.Map;

public abstract class ASwtStage<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_750_Rendering<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    private Trigger oldHook;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void registerHook(Trigger object) {
        Trigger ojk = this.oldHook;
        if (ojk != null) {
            ojk.onTrigger();
        }
        this.oldHook = object;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void releaseHook() {
        this.registerHook(null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void pause() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void resume() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void onInit();

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean isControllerMode() {
        return this.controller != null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Map<WindowID, ASwtWindowDescriptor<?, ?>> windowDescriptors = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public void registerWindowDescriptor(ASwtWindowDescriptor<?, ?> desc)
    {
        this.windowDescriptors.put(desc.getID(), desc);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public SwtWindow activateWindow(WindowID id) {
        SwtScreen<RM, DM> sc = this.currentScreen;
        if (sc != null) {
            ASwtWindowDescriptor<?, ?> desc = this.windowDescriptors.get(id);
            return sc.createWindow(desc);
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
