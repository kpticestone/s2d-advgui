package com.s2dwt.core.tabfolder;

import com.s2dwt.core.basicwidgets.SwtPanel;

public class SwtTab extends SwtPanel implements ISwtTab{
    // -------------------------------------------------------------------------------------------------------------------------
    private final String tabId;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTab(SwtTabFolder pParent, String tabId) {
        super(pParent, false);
        this.tabId = tabId;
        pParent.setTabMapping(tabId, this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final String getTabId() {
        return this.tabId;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
