package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;

public class SwtScrollPanel extends ASwtWidget<ScrollPane> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ScrollPane scp;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtTable table;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtScrollPanel(ASwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, false, new ActorCreatorScrollPanel()));
        this.scp = this.actor;
        this.table = new SwtTable(this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean getIgnoreOnCalcPos() {
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTable getTable() {
        return this.table;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
