package com.s2dwt.demo;

import com.s2dwt.core.basicwidgets.SwtButton;
import com.s2dwt.core.basicwidgets.SwtGroup;
import com.s2dwt.core.basicwidgets.SwtLabel;
import com.s2dwt.core.basicwidgets.SwtPanel;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.screens.SwtScreen;
import com.s2dwt.core.stage.ASwtStage;
import com.s2dwt.core.tabfolder.SwtTabFolder;

public class DemoScreen extends SwtScreen<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    public DemoScreen(ASwtStage<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pStage) {
        super(pStage);
        SwtPanel panel1 = new SwtPanel(this, true);
        panel1.setBounds(10, 10, -20, 35);

        SwtLabel title = new SwtLabel(panel1, "Scene2D Widget Toolkit ÖÄÜ#+!§$");
        title.setBounds(20, 5, -140, -10);

        SwtLabel zoom = new SwtLabel(panel1);
        zoom.setBounds(-75, 5, 50, 25);
        zoom.setText("1");
        {
            SwtButton btn = new SwtButton(panel1);
            btn.setText("-");
            btn.setBounds(-100, 5, 25, 25);
            btn.addListener(btnx -> {
                float was = context.getGuiScale();
                was -= .1f;
                if (was <= 1f)
                    was = 1f;
                zoom.setText(String.valueOf(was));
                context.setGuiScale(was);
            });
        }
        {
            SwtButton btn = new SwtButton(panel1);
            btn.setText("+");
            btn.setBounds(-25, 5, 25, 25);
            btn.addListener(btnx -> {
                float was = context.getGuiScale();
                was += .1f;
                if (was > 4f) {
                    was = 4f;
                }
                zoom.setText(String.valueOf(was));
                context.setGuiScale(was);
            });
        }

        SwtPanel panel2 = new SwtPanel(this, true);
        panel2.setBounds(10, 55, -20, -65);

        SwtTabFolder folderA = new SwtTabFolder(panel2);

        DemoTab_Canvas tabCanvas = new DemoTab_Canvas(folderA);
        DemoTab_ComboBoxes tabCom = new DemoTab_ComboBoxes(folderA);
        DemoTab_Tree tabTree = new DemoTab_Tree(folderA);
        DemoTab_Buttons tabButtons = new DemoTab_Buttons(folderA);
        DemoTab_Texts tabTexts = new DemoTab_Texts(folderA);
        DemoTab_Images tabImages = new DemoTab_Images(folderA);
        DemoTab_CheckBoxes tabCheckBoxes = new DemoTab_CheckBoxes(folderA);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
