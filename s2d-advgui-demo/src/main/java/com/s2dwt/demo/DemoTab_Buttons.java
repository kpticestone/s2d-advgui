package com.s2dwt.demo;

import com.s2dwt.core.basicwidgets.SwtButton;
import com.s2dwt.core.basicwidgets.SwtGroup;
import com.s2dwt.core.layoutmanager.SwtLayoutManager_Flow;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;

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
            btn.setImage("ui/icon.png");
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
            btn.setImage("ui/icon.png");
            btn.setBounds(0, 0, 0, bh);
        }

        SwtGroup grp4 = new SwtGroup(this, "Disabled");
        {
            SwtButton btn = new SwtButton(grp4);
            btn.setText("TEXT");
            btn.setImage("ui/icon.png");
            btn.setBounds(0, 0, 0, bh);
            btn.setDisabled();
        }

        this.setLayoutManager(new SwtLayoutManager_Flow(100, 100));
    }

}
