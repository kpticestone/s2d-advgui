package com.s2dwt.core.application;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SwtScreen;
import com.s2dwt.core.window.SwtWindow;

public interface ISwtApplicationController<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtScreen<RM, DM> activateScreen(String id);

    // -------------------------------------------------------------------------------------------------------------------------
    DM getDrawerManager();

    // -------------------------------------------------------------------------------------------------------------------------
    // SwtWindow activateWindow(String id);

    // -------------------------------------------------------------------------------------------------------------------------
}
