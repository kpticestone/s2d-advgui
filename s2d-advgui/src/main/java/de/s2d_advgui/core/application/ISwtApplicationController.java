package de.s2d_advgui.core.application;

import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.SwtScreen;

public interface ISwtApplicationController<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtScreen<RM, DM> activateScreen(String id) throws Exception;

    // -------------------------------------------------------------------------------------------------------------------------
    DM getDrawerManager();

    // -------------------------------------------------------------------------------------------------------------------------
    // SwtWindow activateWindow(String id);

    // -------------------------------------------------------------------------------------------------------------------------
}
