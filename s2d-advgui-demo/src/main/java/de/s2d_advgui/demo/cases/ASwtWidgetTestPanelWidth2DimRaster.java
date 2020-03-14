package de.s2d_advgui.demo.cases;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.tabledata.ESwtTableMode;
import de.s2d_advgui.core.tabledata.SwtLayoutDataCellPosition;
import de.s2d_advgui.core.tabledata.SwtTablePanel;

public abstract class ASwtWidgetTestPanelWidth2DimRaster extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidgetTestPanelWidth2DimRaster(ISwtWidget<? extends Group> pParent, ESwtTableMode pMode, int bh) {
        super(pParent);
        this.setClip(true);
        List<ICons> builds = new ArrayList<>();
        this.fillBuilds(builds);
        SwtTablePanel panx = new SwtTablePanel(this, pMode, 2, builds.size());
        panx.setBounds(10, 10, -20, -20);
        int rownr = 0;
        for (ICons c : builds) {
            SwtLabel grp1 = new SwtLabel(panx, c.label);
            grp1.setBounds(0, 0, 300, bh);
            grp1.setAlign(Align.right);
            grp1.setLayoutData(new SwtLayoutDataCellPosition(0, rownr));
            
            ASwtWidget<?> btn1 = c.pBp.create(panx);
            btn1.setBounds(0, 0, 300, bh);
            btn1.setLayoutData(new SwtLayoutDataCellPosition(1, rownr));
            ++rownr;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void fillBuilds(List<ICons> builds);

    // -------------------------------------------------------------------------------------------------------------------------
}
