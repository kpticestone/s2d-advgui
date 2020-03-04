package de.s2d_advgui.demo.cases.comboboxes;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Info;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public final class SwtWidgetTestCase_ComboBoxes extends ASwtWidgetTestCase {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "ComboBoxes"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SwtWidgetTestCase_ComboBoxes INSTANCE = new SwtWidgetTestCase_ComboBoxes();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SwtWidgetTestCase_ComboBoxes getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtWidgetTestCase_ComboBoxes() {
        super(ID, new Info("comboboxes"));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent) {
        return new SwtWidgetTestPanel_ComboBoxes(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
