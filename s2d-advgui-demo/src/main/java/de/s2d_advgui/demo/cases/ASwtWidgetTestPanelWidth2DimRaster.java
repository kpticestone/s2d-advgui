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
        this(pParent, pMode, bh, 1);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidgetTestPanelWidth2DimRaster(ISwtWidget<? extends Group> pParent, ESwtTableMode pMode, int bh,
            int wii) {
        super(pParent);
        this.setClip(true);
        List<ICons> builds = new ArrayList<>();
        this.fillBuilds(builds);
        SwtTablePanel panx = new SwtTablePanel(this, pMode, wii*2, (int)Math.ceil(builds.size()/(float)wii));
        panx.setBounds(10, 10, -20, -20);
        int rownr = 0;
        int colnr = 0;
        for (ICons c : builds) {
            // for (int i = 0; i < wii; i++)
            {
                SwtLabel grp1 = new SwtLabel(panx, c.label);
                grp1.setBounds(0, 0, 300, bh);
                grp1.setAlign(Align.right);
                grp1.setLayoutData(new SwtLayoutDataCellPosition(colnr++, rownr));

                ASwtWidget<?> btn1 = c.pBp.create(panx);
                btn1.setBounds(0, 0, 300, bh);
                btn1.setLayoutData(new SwtLayoutDataCellPosition(colnr++, rownr));
            }
            if (colnr >= wii*2) {
                colnr = 0;
                ++rownr;
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void fillBuilds(List<ICons> builds);

    // -------------------------------------------------------------------------------------------------------------------------
}
