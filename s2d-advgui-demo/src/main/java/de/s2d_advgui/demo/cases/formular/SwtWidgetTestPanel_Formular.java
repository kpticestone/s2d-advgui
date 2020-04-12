package de.s2d_advgui.demo.cases.formular;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtForm;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtCheckbox;
import de.s2d_advgui.core.basicwidgets.SwtComboBox;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.basicwidgets.SwtRadioBox;
import de.s2d_advgui.core.basicwidgets.SwtSlider;
import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Formular extends ASwtWidgetTestPanel {

    // -------------------------------------------------------------------------------------------------------------------------
    static class SwtFormLayoutManager extends ASwtLayoutManager {
        // -------------------------------------------------------------------------------------------------------------------------
        private float columnLabelWidth;

        // -------------------------------------------------------------------------------------------------------------------------
        private float columnFieldWidth;

        // -------------------------------------------------------------------------------------------------------------------------
        private float space = 5;

        // -------------------------------------------------------------------------------------------------------------------------
        private float allWidth;

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtFormLayoutManager(float pColumnLabelWidth, float pColumnFieldWidth) {
            this.columnLabelWidth = pColumnLabelWidth;
            this.columnFieldWidth = pColumnFieldWidth;
            this.allWidth = this.columnLabelWidth + this.space + this.columnFieldWidth;
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public void calculate(ISwtWidget<?> pWidget, float width, float height) {
            int aty = 0;
            for (ISwtWidget<?> chx : pWidget.getChildren()) {
                if (chx instanceof SwtFormRow) {
                    SwtFormRow theRow = ((SwtFormRow) chx);
                    float someRowHeight = theRow.getPreferedHeight();
                    chx.setBounds(0, aty, this.allWidth, someRowHeight);
                    theRow.labText.setBounds(0, 0, this.columnLabelWidth, someRowHeight);
                    float atx = this.columnLabelWidth + this.space;
                    for (ISwtWidget<?> chx2 : theRow.getChildren()) {
                        if (chx2 != theRow.labText) {
                            chx2.setBounds(atx, 0, columnFieldWidth, someRowHeight);
                        }
                    }
                    aty+= someRowHeight;
                    aty+= space;
                }
            }
        }
    }

    static class SwtFormRow extends SwtPanel {
        // -------------------------------------------------------------------------------------------------------------------------
        private SwtLabel labText;

        // -------------------------------------------------------------------------------------------------------------------------
        private float preferedHeight;

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtFormRow(ISwtWidget<? extends Group> pParent, String label, float pPreferedHeight) {
            super(pParent);
            this.preferedHeight = pPreferedHeight;
            this.labText = new SwtLabel(this);
            this.labText.setText(label);
            this.labText.setFontScale(.5f);
            this.labText.setFontColor(this.getTheme().getLabelColor());
            this.labText.setAlign(Align.right);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        public float getPreferedHeight() {
            return this.preferedHeight;
        }

        // -------------------------------------------------------------------------------------------------------------------------
    }

    static class SwtForm extends SwtPanel implements ISwtForm<WidgetGroup> {
        // -------------------------------------------------------------------------------------------------------------------------
        private float columnLabelWidth;

        // -------------------------------------------------------------------------------------------------------------------------
        private float columnFieldWidth;

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtForm(ISwtWidget<? extends Group> pParent, float pColumnLabelWidth, float pColumnFieldWidth) {
            super(pParent);
            this.columnLabelWidth = pColumnLabelWidth;
            this.columnFieldWidth = pColumnFieldWidth;
            this.setLayoutManager(new SwtFormLayoutManager(pColumnLabelWidth, pColumnFieldWidth));
        }

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtTextField addTextField(String label) {
            SwtFormRow row = this.addRow(label);
            SwtTextField back = new SwtTextField(row);
            return back;
        }

        // -------------------------------------------------------------------------------------------------------------------------
        private SwtFormRow addRow(String label) {
            SwtFormRow back = new SwtFormRow(this, label, 24f);
            return back;
        }

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtCheckbox addCheckBox(String label) {
            SwtFormRow row = this.addRow(label);
            SwtCheckbox back = new SwtCheckbox(row);
            return back;
        }

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtRadioBox addRadioBox(String groupName, String label) {
            SwtFormRow row = this.addRow(label);
            SwtRadioBox back = new SwtRadioBox(row, groupName);
            return back;
        }

        // -------------------------------------------------------------------------------------------------------------------------
        public <T> SwtComboBox<T> addComboBox(String label) {
            SwtFormRow row = this.addRow(label);
            SwtComboBox<T> back = new SwtComboBox<>(row);
            return back;
        }

        // -------------------------------------------------------------------------------------------------------------------------
        public SwtSlider addSlider(String label) {
            SwtFormRow row = this.addRow(label);
            SwtSlider back = new SwtSlider(row);
            return back;
        }

        // -------------------------------------------------------------------------------------------------------------------------
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Formular(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        SwtForm form = new SwtForm(this, 150, 300);
        form.addTextField("text field a (maxiiiiiiii  loonger longer)");
        form.addTextField("text field b (short)");
        SwtTextField tt = form.addTextField("text field c (longer)");
        tt.setDisabled();
        tt.setText("disabled; not enabled!");
        SwtComboBox<String> box1 = form.addComboBox("box1");
        SwtComboBox<String> box2 = form.addComboBox("box2");
        box2.setValues("A1", "A2", "A3");
        SwtComboBox<String> box3 = form.addComboBox("box3");
        box3.setValues("A1", "A2", "A3");
        box3.setDisabled();
        form.addCheckBox("litte checkbox a");
        form.addRadioBox("A", "litte radiobox a");
        form.addRadioBox("A", "litte radiobox b");
        form.addRadioBox("A", "litte radiobox c").setDisabled();
        form.addCheckBox("litte checkbox b");
        form.addCheckBox("litte checkbox c").setDisabled();
        form.addRadioBox("B", "radiobox 1");
        form.addRadioBox("B", "radiobox 2");
        form.addRadioBox("B", "radiobox 3");
        SwtSlider sliderRed = form.addSlider("red");
        sliderRed.setRange(0, 255);
        sliderRed.setValue(128);
        SwtSlider sliderGreen = form.addSlider("green");
        sliderGreen.setRange(0, 255);
        sliderGreen.setValue(128);
        SwtSlider sliderBlue = form.addSlider("blue");
        sliderBlue.setDisabled();
        sliderBlue.setRange(0, 255);
        sliderBlue.setValue(128);

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
