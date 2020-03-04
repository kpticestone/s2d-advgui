package de.s2d_advgui.demo.cases.images;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtImage;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.tabledata.SwtLayoutDataCellPosition;
import de.s2d_advgui.core.tabledata.SwtTablePanel;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;
import de.s2d_advgui.demo.cases.ICons;

final class SwtWidgetTestPanel_Images extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Images(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        int bh = 50;
        this.setClip(true);

        List<ICons> builds = new ArrayList<>();
        builds.add(new ICons("fill", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fill);
            return img;
        }));
        builds.add(new ICons("fillX", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fillX);
            return img;
        }));
        builds.add(new ICons("fillY", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fillY);
            return img;
        }));
        builds.add(new ICons("fit", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fit);
            return img;
        }));
        builds.add(new ICons("none", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.none);
            return img;
        }));
        builds.add(new ICons("stretch", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.stretch);
            return img;
        }));
        builds.add(new ICons("stretchX", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.stretchX);
            return img;
        }));
        builds.add(new ICons("stretchY", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.stretchY);
            return img;
        }));


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
            btn1.setClip(true);
            btn1.setLayoutData(new SwtLayoutDataCellPosition(1, rownr));

            ++rownr;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}