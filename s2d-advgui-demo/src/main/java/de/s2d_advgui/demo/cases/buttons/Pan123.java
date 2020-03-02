package de.s2d_advgui.demo.cases.buttons;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.demo.ASwtWidgetTestPanel;

public class Pan123 extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public Pan123(ISwtWidget<? extends Group> pParent) {
        super(pParent);
//        SwtPanel buttons = new SwtPanel(this, false);
//        buttons.setClip(true);
//        buttons.setBounds(10, 10, -20, -20);
        int bh = 30;
        this.setClip(true);
        
        SwtTablePanel panx = new SwtTablePanel(this, 2, 10);
        panx.setBounds(10, 10, -20, -20);
        
        SwtLabel grp1 = new SwtLabel(panx, "Text-Only");
        grp1.setBounds(0, 0, 150, bh);
        grp1.setLayoutData(new SwtLayoutDataCellPosition(0, 0));
        SwtButton btn1 = new SwtButton(panx);
        btn1.setText("TEXT");
        btn1.setBounds(0, 0, 150, bh);
        btn1.setLayoutData(new SwtLayoutDataCellPosition(1, 0));

        SwtLabel grp2 = new SwtLabel(panx, "Icon-Only");
        grp2.setBounds(0, 0, 150, bh);
        grp2.setLayoutData(new SwtLayoutDataCellPosition(0, 1));
        SwtButton btn2 = new SwtButton(panx);
        btn2.setImage("icons/128/apple.png");
        btn2.setBounds(0, 0, 150, bh);
        btn2.setLayoutData(new SwtLayoutDataCellPosition(1, 1));

        SwtLabel grp3 = new SwtLabel(panx, "Text+Icon");
        grp3.setBounds(0, 0, 150, bh);
        grp3.setLayoutData(new SwtLayoutDataCellPosition(0, 2));
        SwtButton btn3 = new SwtButton(panx);
        btn3.setText("TEXT");
        btn3.setImage("icons/128/apple.png");
        btn3.setBounds(0, 0, 150, bh);
        btn3.setLayoutData(new SwtLayoutDataCellPosition(1, 2));

        SwtLabel grp4 = new SwtLabel(panx, "Disabled");
        grp4.setBounds(0, 0, 150, bh);
        grp4.setLayoutData(new SwtLayoutDataCellPosition(0, 3));
        SwtButton btn4 = new SwtButton(panx);
        btn4.setText("TEXT");
        btn4.setImage("icons/128/apple.png");
        btn4.setBounds(0, 0, 150, bh);
        btn4.setDisabled();
        btn4.setLayoutData(new SwtLayoutDataCellPosition(1, 3));
        
        // buttons.setLayoutManager(new SwtLayoutManager_Flow(320, 200));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
