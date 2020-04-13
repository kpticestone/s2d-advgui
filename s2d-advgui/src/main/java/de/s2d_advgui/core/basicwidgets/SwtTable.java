package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;

public class SwtTable extends ASwtWidget<Table> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTable(ASwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, false, new ActorCreatorTable()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean getIgnoreOnCalcPos() {
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
