package de.s2d_advgui.demo;

import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;

public class DemoTab_Texts extends SwtTab {
    public DemoTab_Texts(SwtTabFolder pParent) {
        super(pParent, "texts");
        for (int i = 0; i < 10; i++) {
            SwtTextField txtField = new SwtTextField(this);
            txtField.setBounds(20, 60 + i * 30, -40, 24);
        }
    }
}
