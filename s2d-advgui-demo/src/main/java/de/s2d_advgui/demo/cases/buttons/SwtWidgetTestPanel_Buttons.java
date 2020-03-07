package de.s2d_advgui.demo.cases.buttons;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtCheckbox;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanelWidth2DimRaster;
import de.s2d_advgui.demo.cases.ICons;

public class SwtWidgetTestPanel_Buttons extends ASwtWidgetTestPanelWidth2DimRaster {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Buttons(ISwtWidget<? extends Group> pParent) {
        super(pParent, 30);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void fillBuilds(List<ICons> builds) {
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
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
