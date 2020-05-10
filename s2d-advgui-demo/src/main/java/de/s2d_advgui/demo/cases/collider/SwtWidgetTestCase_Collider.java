package de.s2d_advgui.demo.cases.collider;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Info;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestCase_Collider extends ASwtWidgetTestCase {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "Collider"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SwtWidgetTestCase_Collider INSTANCE = new SwtWidgetTestCase_Collider();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SwtWidgetTestCase_Collider getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtWidgetTestCase_Collider() {
        super(ID, new Info("collider"));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent) {
        return new SwtWidgetTestPanel_Collider(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
