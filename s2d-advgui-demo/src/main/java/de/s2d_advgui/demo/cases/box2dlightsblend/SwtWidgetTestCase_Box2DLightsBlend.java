package de.s2d_advgui.demo.cases.box2dlightsblend;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Info;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestCase_Box2DLightsBlend extends ASwtWidgetTestCase {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "Box2DLightsBlend"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SwtWidgetTestCase_Box2DLightsBlend INSTANCE = new SwtWidgetTestCase_Box2DLightsBlend();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SwtWidgetTestCase_Box2DLightsBlend getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtWidgetTestCase_Box2DLightsBlend() {
        super(ID, new Info("Box2DLightsBlend"));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent) {
        return new SwtWidgetTestPanel_Box2DLightsBlend(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
