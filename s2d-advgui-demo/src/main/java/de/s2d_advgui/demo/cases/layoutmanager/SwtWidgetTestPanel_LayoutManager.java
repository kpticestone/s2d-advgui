package de.s2d_advgui.demo.cases.layoutmanager;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Column;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Flow;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_GridByColumns;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Row;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_LayoutManager extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_LayoutManager(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        SwtShowPanel showPanelFlow = new SwtShowPanel(this, "Flow");

        SwtLayoutManager_Flow f1 = new SwtLayoutManager_Flow(100, 50);
        showPanelFlow.setLayoutManager(f1);
        this.attachPanels(showPanelFlow);

        SwtShowPanel showPanelRow = new SwtShowPanel(this, "row");
        SwtLayoutManager_Row f2 = new SwtLayoutManager_Row();
        f2.setAh(25);
        showPanelRow.setLayoutManager(f2);
        this.attachPanels(showPanelRow);

        SwtShowPanel showPanelCol = new SwtShowPanel(this, "col");
        SwtLayoutManager_Column f4 = new SwtLayoutManager_Column();
        f4.setAh(25);
        showPanelCol.setLayoutManager(f4);
        this.attachPanels(showPanelCol);

        SwtShowPanel showPanelGrid = new SwtShowPanel(this, "grid");
        SwtLayoutManager_GridByColumns f3 = new SwtLayoutManager_GridByColumns(3);
        showPanelGrid.setLayoutManager(f3);
        this.attachPanels(showPanelGrid);

        this.setLayoutManager(new ASwtLayoutManager() {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height) {
                showPanelFlow.setBounds(10, 10, width - 20, 200);
                showPanelRow.setBounds(10, 220, width / 2f, 200);
                showPanelCol.setBounds(width / 2f, 220, width / 2f, 200);
                showPanelGrid.setBounds(10, 440, width, 200);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void attachPanels(ISwtWidget<? extends Group> pParent) {
        for (int j = 0; j < 10; j++) {
            SwtPanel panel = new SwtPanel(pParent, true);
            panel.setSize(50 - j, 20 + j);
            SwtLabel lab1 = new SwtLabel(panel);
            lab1.setAlign(Align.center);
            lab1.setBounds(0, 0, 0, 0);
            lab1.setText("- " + j + " -");
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
