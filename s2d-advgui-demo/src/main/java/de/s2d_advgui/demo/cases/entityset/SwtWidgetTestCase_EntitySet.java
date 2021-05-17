package de.s2d_advgui.demo.cases.entityset;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Info;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestCase_EntitySet extends ASwtWidgetTestCase {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "EntitySet"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SwtWidgetTestCase_EntitySet INSTANCE = new SwtWidgetTestCase_EntitySet();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SwtWidgetTestCase_EntitySet getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtWidgetTestCase_EntitySet() {
        super(ID, new Info("EntitySet"));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent) {
        return new SwtWidgetTestPanel_EntitySet(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
