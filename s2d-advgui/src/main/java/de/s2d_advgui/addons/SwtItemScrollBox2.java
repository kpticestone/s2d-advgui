package de.s2d_advgui.addons;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.ActorCreatorWidgetGroup;

public class SwtItemScrollBox2 extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox2(ASwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, ActorCreatorWidgetGroup.createInstance(true)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
