package de.s2d_advgui.demo;

import java.util.UUID;

import com.badlogic.gdx.utils.Array;
import de.s2d_advgui.core.basicwidgets.SwtComboBox;
import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;

public class DemoTab_ComboBoxes extends SwtTab {
    public DemoTab_ComboBoxes(SwtTabFolder pParent) {
        super(pParent, "combobox");
        for (int i = 0; i < 10; i++) {
            SwtComboBox<String> cmbx = new SwtComboBox<>(this);
            cmbx.setBounds(20, 60 + i * 30, -40, 24);
            Array<String> items = new Array<>();
            for (int j = 0; j < 100; j++) {
                items.add(j + ": " + UUID.randomUUID());
            }
            cmbx.setValues(items);
        }
    }
}
