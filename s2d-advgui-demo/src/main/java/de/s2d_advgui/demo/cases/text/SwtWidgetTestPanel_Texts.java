package de.s2d_advgui.demo.cases.text;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.core.tabledata.SwtLayoutDataCellPosition;
import de.s2d_advgui.core.tabledata.SwtTablePanel;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;
import de.s2d_advgui.demo.cases.ICons;

final class SwtWidgetTestPanel_Texts extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Texts(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        int bh = 50;
        this.setClip(true);

        List<ICons> builds = new ArrayList<>();
        builds.add(new ICons("regular", (x) -> {
            SwtTextField back = new SwtTextField(x);
            back.setText("textfield");
            return back;
        }));
        builds.add(new ICons("password", (x) -> {
            SwtTextField back = new SwtTextField(x);
            back.setText("password");
            back.setPassword(true);
            return back;
        }));
//        builds.add(new ICons("password", (x) -> {
//            SwtTextField back = new SwtTextField(x);
//            back.setText("password");
//            return back;
//        }));

        SwtTablePanel panx = new SwtTablePanel(this, 2, builds.size());
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
}