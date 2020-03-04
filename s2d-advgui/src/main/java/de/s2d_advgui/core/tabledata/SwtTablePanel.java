package de.s2d_advgui.core.tabledata;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public class SwtTablePanel extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTablePanel(ISwtWidget<? extends Group> pParent, int pColumnCount, int pRowCount) {
        super(pParent, false);
        AResourceManager rm = pParent.getContext().getResourceManager();
        TableDataManager layout = new TableDataManager(rm, "borders/white-round-5.png",
                pColumnCount, pRowCount);
        
        this.addDrawerBackground(layout.getBatchDrawer(Color.GREEN));
        this.setLayoutManager(layout.getLayoutManager());
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
