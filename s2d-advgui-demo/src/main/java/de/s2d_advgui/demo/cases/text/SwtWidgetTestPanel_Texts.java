package de.s2d_advgui.demo.cases.text;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanelWidth2DimRaster;
import de.s2d_advgui.demo.cases.ICons;

final class SwtWidgetTestPanel_Texts extends ASwtWidgetTestPanelWidth2DimRaster {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Texts(ISwtWidget<? extends Group> pParent) {
        super(pParent, 50);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void fillBuilds(List<ICons> builds) {
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
    }

    // -------------------------------------------------------------------------------------------------------------------------
}