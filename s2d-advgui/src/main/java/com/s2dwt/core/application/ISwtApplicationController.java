package com.s2dwt.core.application;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SwtScreen;

public interface ISwtApplicationController<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtScreen<RM, DM> activateScreen(String id) throws Exception;

    // -------------------------------------------------------------------------------------------------------------------------
    DM getDrawerManager();

    // -------------------------------------------------------------------------------------------------------------------------
    // SwtWindow activateWindow(String id);

    // -------------------------------------------------------------------------------------------------------------------------
}
