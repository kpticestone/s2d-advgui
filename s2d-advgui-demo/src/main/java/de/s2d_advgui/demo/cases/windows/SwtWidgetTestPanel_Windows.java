package de.s2d_advgui.demo.cases.windows;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.core.window.SwtWindow;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

final class SwtWidgetTestPanel_Windows extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Windows(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        SwtButton btnModal1 = new SwtButton(this, "open modal");
        btnModal1.setBounds(5, 5, 150, 25);
        btnModal1.addLeftClickListener(() -> {
            SwtWindow majs = new SwtWindow(pParent, "91238921", true, 620, 400);
            SwtTextField txt1 = new SwtTextField(majs);
            txt1.setBounds(15, 15, 250, 25);

            SwtTextField txt2 = new SwtTextField(majs);
            txt2.setBounds(15, 40, 250, 25);
        });

        SwtButton btnModal2 = new SwtButton(this, "open modeless");
        btnModal2.setBounds(5, 35, 150, 25);
        btnModal2.addLeftClickListener(() -> {
            new SwtWindow(pParent, "91238921", false, 640, 200);
        });

        SwtButton btnModal3 = new SwtButton(this, "show message");
        btnModal3.setBounds(5, 65, 150, 25);
        btnModal3.addLeftClickListener(() -> {
            SwtWindow.showMessage(pParent, "message dialog", "message for the dialog");
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}