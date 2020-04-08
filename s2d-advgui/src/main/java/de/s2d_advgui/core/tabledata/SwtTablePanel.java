package de.s2d_advgui.core.tabledata;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtTablePanel extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTablePanel(ISwtWidget<? extends Group> pParent, ESwtTableMode pMode, int pColumnCount, int pRowCount) {
        super(pParent, false);
        AResourceManager rm = this.getResourceManager();
        TableDataManager layout = new TableDataManager(rm, ATheme.BORDERS_WHITE_ROUND_5_PNG, pMode,
                pColumnCount, pRowCount);
        this.addDrawerBackground(layout.getBatchDrawer(Color.GREEN));
        this.setLayoutManager(layout.getLayoutManager());
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
