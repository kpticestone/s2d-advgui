package com.s2dwt.demo;

import com.s2dwt.core.basicwidgets.SwtButton;
import com.s2dwt.core.basicwidgets.SwtCheckbox;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;

public class DemoTab_CheckBoxes extends SwtTab {
    public DemoTab_CheckBoxes(SwtTabFolder pParent) {
        super(pParent, "checkboxes");

        int oneH = 24;

        int bh = 10;
        int space = 5;
        // for (int oneH : new int[] { 15, 24, 32, 100 }) {

            SwtCheckbox btn = new SwtCheckbox(this, "A (disabled) abc ddef ghi jkl");
            btn.setBounds(10, bh, -20, oneH);
            btn.setDisabled();

            bh += oneH + space;

            SwtCheckbox btn2 = new SwtCheckbox(this, "B (enabled) abc ddef ghi jkl");
            btn2.setBounds(10, bh, -20, oneH);

            bh += oneH + space;

            SwtCheckbox btn3 = new SwtCheckbox(this, "C");
            btn3.setBounds(10, bh, -20, oneH);

            bh += oneH + space;
            
            
            SwtButton vbb = new SwtButton(this, "contrast");
            vbb.setBounds(10, bh, -20, oneH);
            
            bh += oneH + space;
        // }
    }
}
