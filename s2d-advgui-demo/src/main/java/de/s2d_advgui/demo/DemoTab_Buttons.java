package de.s2d_advgui.demo;

import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtGroup;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Flow;
import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;

public class DemoTab_Buttons extends SwtTab {
    public DemoTab_Buttons(SwtTabFolder pParent) {
        super(pParent, "buttons");

        int bh = 24;

        SwtGroup grp1 = new SwtGroup(this, "Text-Only");
        {
            SwtButton btn = new SwtButton(grp1);
            btn.setText("TEXT");
            btn.setBounds(0, 0, 0, bh);
            btn.addListener(btnx -> {
                float was = context.getGuiScale();
                was -= .1f;
                if (was <= 1f)
                    was = 1f;
                context.setGuiScale(was);
            });
        }

        SwtGroup grp2 = new SwtGroup(this, "Icon-Only");
        {
            SwtButton btn = new SwtButton(grp2);
            btn.setImage("icons/128/apple.png");
            btn.setBounds(0, 0, 0, bh);
            btn.addListener(btnx -> {
                float was = context.getGuiScale();
                was += .1f;
                if (was > 4f) {
                    was = 4f;
                }
                context.setGuiScale(was);
            });
        }

        SwtGroup grp3 = new SwtGroup(this, "Text+Icon");
        {
            SwtButton btn = new SwtButton(grp3);
            btn.setText("TEXT");
            btn.setImage("icons/128/apple.png");
            btn.setBounds(0, 0, 0, bh);
        }

        SwtGroup grp4 = new SwtGroup(this, "Disabled");
        {
            SwtButton btn = new SwtButton(grp4);
            btn.setText("TEXT");
            btn.setImage("icons/128/apple.png");
            btn.setBounds(0, 0, 0, bh);
            btn.setDisabled();
        }

        this.setLayoutManager(new SwtLayoutManager_Flow(100, 100));
    }

}
