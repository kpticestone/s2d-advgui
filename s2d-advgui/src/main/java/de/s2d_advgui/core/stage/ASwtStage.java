package de.s2d_advgui.core.stage;

import java.util.HashMap;
import java.util.Map;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.SwtScreen;
import de.s2d_advgui.core.window.ASwtWindowDescriptor;
import de.s2d_advgui.core.window.SwtWindow;
import de.s2d_advgui.core.window.WindowID;
import de.s2d_advgui.commons.Trigger;

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
    public abstract void onInit() throws Exception;

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
