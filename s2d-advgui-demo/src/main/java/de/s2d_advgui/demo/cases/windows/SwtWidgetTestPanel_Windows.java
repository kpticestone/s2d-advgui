package de.s2d_advgui.demo.cases.windows;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.window.SwtWindow;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

final class SwtWidgetTestPanel_Windows extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Windows(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        SwtButton btnModal1 = new SwtButton(this, "open modal");
        btnModal1.setBounds(5, 5, 150, 25);
        btnModal1.addLeftClickListener(() -> {
            new SwtWindow(pParent, "91238921", 620, 400);
            
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}