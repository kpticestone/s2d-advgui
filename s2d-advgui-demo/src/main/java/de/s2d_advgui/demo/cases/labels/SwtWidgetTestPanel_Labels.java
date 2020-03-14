package de.s2d_advgui.demo.cases.labels;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.tabledata.ESwtTableMode;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanelWidth2DimRaster;
import de.s2d_advgui.demo.cases.ICons;

final class SwtWidgetTestPanel_Labels extends ASwtWidgetTestPanelWidth2DimRaster {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Labels(ISwtWidget<? extends Group> pParent) {
        super(pParent, ESwtTableMode.FULLFILL, 50);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void fillBuilds(List<ICons> builds) {
        builds.add(new ICons("top left", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("top left");
            back.setAlign(Align.topLeft);
            return back;
        }));
        builds.add(new ICons("top", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("top");
            back.setAlign(Align.top);
            return back;
        }));
        builds.add(new ICons("top right", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("top right");
            back.setAlign(Align.topRight);
            return back;
        }));
        builds.add(new ICons("left", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("left");
            back.setAlign(Align.left);
            return back;
        }));
        builds.add(new ICons("center", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("center");
            back.setAlign(Align.center);
            return back;
        }));
        builds.add(new ICons("right", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("right");
            back.setAlign(Align.right);
            return back;
        }));
        builds.add(new ICons("bottom left", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("bottom left");
            back.setAlign(Align.bottomLeft);
            return back;
        }));
        builds.add(new ICons("bottom .5f", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("bottom");
            back.setAlign(Align.bottom);
            back.setFontScale(0.5f);
            return back;
        }));
        builds.add(new ICons("bottom right blue", (x) -> {
            SwtLabel back = new SwtLabel(x);
            back.setText("bottom right");
            back.setColor(Color.BLUE);
            back.setAlign(Align.bottomRight);
            return back;
        }));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}