package de.s2d_advgui.demo.cases.buttons;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Info;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.demo.ASwtWidgetTestCase;
import de.s2d_advgui.demo.ASwtWidgetTestPanel;

public final class SwtWidgetTestCase_Buttons extends ASwtWidgetTestCase {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "Buttons"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SwtWidgetTestCase_Buttons INSTANCE = new SwtWidgetTestCase_Buttons();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SwtWidgetTestCase_Buttons getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtWidgetTestCase_Buttons() {
        super(ID, new Info("buttons"));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent) {
        return new Pan123(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
