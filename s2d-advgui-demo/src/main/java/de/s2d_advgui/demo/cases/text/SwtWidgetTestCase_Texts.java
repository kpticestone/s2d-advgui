package de.s2d_advgui.demo.cases.text;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Info;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public final class SwtWidgetTestCase_Texts extends ASwtWidgetTestCase {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "Texts"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SwtWidgetTestCase_Texts INSTANCE = new SwtWidgetTestCase_Texts();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SwtWidgetTestCase_Texts getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtWidgetTestCase_Texts() {
        super(ID, new Info("texts"));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent) {
        return new SwtWidgetTestPanel_Texts(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
