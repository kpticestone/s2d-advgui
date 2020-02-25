package com.s2dwt.demo;

import com.s2dwt.core.basicwidgets.SwtTextField;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;

public class DemoTab_Texts extends SwtTab {
    public DemoTab_Texts(SwtTabFolder pParent) {
        super(pParent, "texts");
        for (int i = 0; i < 10; i++) {
            SwtTextField txtField = new SwtTextField(this);
            txtField.setBounds(20, 60 + i * 30, -40, 24);
        }
    }
}
