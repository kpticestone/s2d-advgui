package de.s2d_advgui.core.tabfolder;

import de.s2d_advgui.core.basicwidgets.SwtPanel;

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
