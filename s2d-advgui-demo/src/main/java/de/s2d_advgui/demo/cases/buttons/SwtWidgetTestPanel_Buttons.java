package de.s2d_advgui.demo.cases.buttons;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtCheckbox;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.tabledata.SwtLayoutDataCellPosition;
import de.s2d_advgui.core.tabledata.SwtTablePanel;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;
import de.s2d_advgui.demo.cases.ICons;

public class SwtWidgetTestPanel_Buttons extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Buttons(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        int bh = 30;
        this.setClip(true);

        List<ICons> builds = new ArrayList<>();
        builds.add(new ICons("Text-Only", (x) -> {
            SwtButton btn1 = new SwtButton(x);
            btn1.setText("TEXT");
            return btn1;
        }));
        builds.add(new ICons("Icon-Only", (x) -> {
            SwtButton btn1 = new SwtButton(x);
            btn1.setImage("icons/128/apple.png");
            return btn1;
        }));
        builds.add(new ICons("Text+Icon", (x) -> {
            SwtButton btn1 = new SwtButton(x);
            btn1.setText("TEXT");
            btn1.setImage("icons/128/apple.png");
            return btn1;
        }));
        builds.add(new ICons("Disabled", (x) -> {
            SwtButton btn1 = new SwtButton(x);
            btn1.setText("TEXT");
            btn1.setImage("icons/128/apple.png");
            btn1.setDisabled();
            return btn1;
        }));
        builds.add(new ICons("checkbox", (x) -> {
            SwtCheckbox btn1 = new SwtCheckbox(x, "check me");
            return btn1;
        }));
        builds.add(new ICons("checkbox disabled", (x) -> {
            SwtCheckbox btn1 = new SwtCheckbox(x, "check me - not");
            btn1.setDisabled();
            return btn1;
        }));
//        builds.add(new ICons("checkbox disabled", (x) -> {
//            SwtRadioBox btn1 = new SwtRadioBox(x, "check me - not");
//            btn1.setDisabled();
//            return btn1;
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
