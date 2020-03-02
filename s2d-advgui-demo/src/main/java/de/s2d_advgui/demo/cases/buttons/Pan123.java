package de.s2d_advgui.demo.cases.buttons;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtGroup;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Flow;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Row;
import de.s2d_advgui.demo.ASwtWidgetTestPanel;

public class Pan123 extends ASwtWidgetTestPanel {
    public Pan123(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        SwtPanel buttons = new SwtPanel(this, false);
        buttons.setClip(true);
        buttons.setBounds(10, 10, -20, -20);
        int bh = 18;
        SwtGroup grp1 = new SwtGroup(buttons, "Text-Only");
        {
            SwtButton btn = new SwtButton(grp1);
            btn.setText("TEXT");
            btn.setBounds(0, 0, 0, bh);
            btn.addListener(btnx -> {
            });
        }

        SwtGroup grp2 = new SwtGroup(buttons, "Icon-Only");
        {
            SwtButton btn = new SwtButton(grp2);
            btn.setImage("icons/128/apple.png");
            btn.setBounds(0, 0, 0, bh);
            btn.addListener(btnx -> {
            });
        }

        SwtGroup grp3 = new SwtGroup(buttons, "Text+Icon");
        {
            SwtButton btn = new SwtButton(grp3);
            btn.setText("TEXT");
            btn.setImage("icons/128/apple.png");
            btn.setBounds(0, 0, 0, bh);
        }

        SwtGroup grp4 = new SwtGroup(buttons, "Disabled");
        {
            SwtButton btn = new SwtButton(grp4);
            btn.setText("TEXT");
            btn.setImage("icons/128/apple.png");
            btn.setBounds(0, 0, 0, bh);
            btn.setDisabled();
        }
        
        buttons.setLayoutManager(new SwtLayoutManager_Flow(320, 200));
    }

}
