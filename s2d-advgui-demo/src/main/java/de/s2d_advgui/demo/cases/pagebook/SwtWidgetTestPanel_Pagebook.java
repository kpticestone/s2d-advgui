package de.s2d_advgui.demo.cases.pagebook;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.core.pagebook.SwtPagebook;
import de.s2d_advgui.core.pagebook.SwtPagebookItem;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Pagebook extends ASwtWidgetTestPanel {

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Pagebook(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        SwtPagebook pagebook = new SwtPagebook(this);
        pagebook.setBounds(10, 50, -20, -60);
        for (int i = 0; i < 10; i++) {
            SwtPagebookItem item = pagebook.addItem("bla-" + i);
            int ay = 10;
            for (int j = 0; j < 5; j++) {
                SwtTextField txt = new SwtTextField(item);
                txt.setText("text " + j + " on pbitem " + i);
                txt.setBounds(10+i*10, ay+i*10, 150, 25);
                ay += 25;
            }
        }
        int ax = 0;
        for (int i = 0; i < 10; i++) {
            SwtButton btn = new SwtButton(this);
            btn.setBounds(ax, 0, 50, 25);
            ax+= 50;
            btn.setText("" + i);
            final int useI = i;
            btn.addLeftClickListener(() -> {
                pagebook.setSelectedItem("bla-" + useI);
            });
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
