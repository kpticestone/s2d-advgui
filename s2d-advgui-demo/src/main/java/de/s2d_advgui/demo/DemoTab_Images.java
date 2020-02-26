package de.s2d_advgui.demo;

import de.s2d_advgui.core.basicwidgets.SwtImage;
import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;

public class DemoTab_Images extends SwtTab {
    public DemoTab_Images(SwtTabFolder pParent) {
        super(pParent, "images");
        SwtImage img = new SwtImage(this);
        img.setImage("icons/128/workplace.png");
        img.setBounds(120, 55, -130, -65);
    }
}
