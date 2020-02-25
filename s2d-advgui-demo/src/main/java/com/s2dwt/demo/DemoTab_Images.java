package com.s2dwt.demo;

import com.s2dwt.core.basicwidgets.SwtImage;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;

public class DemoTab_Images extends SwtTab {
    public DemoTab_Images(SwtTabFolder pParent) {
        super(pParent, "images");
        SwtImage img = new SwtImage(this);
        img.setImage("ui/1.jpg");
        img.setBounds(120, 55, -130, -65);
    }
}
