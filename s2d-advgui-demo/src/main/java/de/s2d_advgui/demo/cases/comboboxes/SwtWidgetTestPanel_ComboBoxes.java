package de.s2d_advgui.demo.cases.comboboxes;

import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtComboBox;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanelWidth2DimRaster;
import de.s2d_advgui.demo.cases.ICons;

final class SwtWidgetTestPanel_ComboBoxes extends ASwtWidgetTestPanelWidth2DimRaster {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_ComboBoxes(ISwtWidget<? extends Group> pParent) {
        super(pParent, 50);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void fillBuilds(List<ICons> builds) {
        builds.add(new ICons("regular no presel", (x) -> {
            SwtComboBox<String> cmbx = new SwtComboBox<>(x);
            Array<String> items = new Array<>();
            for (int j = 0; j < 100; j++) {
                items.add(j + ": " + UUID.randomUUID());
            }
            cmbx.setValues(items);
            return cmbx;
        }));
        builds.add(new ICons("regular presel", (x) -> {
            SwtComboBox<String> cmbx = new SwtComboBox<>(x);
            Array<String> items = new Array<>();
            for (int j = 0; j < 100; j++) {
                items.add(j + ": " + UUID.randomUUID());
            }
            cmbx.setValues(items);
            cmbx.setSelection(items.get(0));
            return cmbx;
        }));
        builds.add(new ICons("disabled", (x) -> {
            SwtComboBox<String> cmbx = new SwtComboBox<>(x);
            Array<String> items = new Array<>();
            for (int j = 0; j < 100; j++) {
                items.add(j + ": " + UUID.randomUUID());
            }
            cmbx.setValues(items);
            cmbx.setDisabled();
            return cmbx;
        }));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}