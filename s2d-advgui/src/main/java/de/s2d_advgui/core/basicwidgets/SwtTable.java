package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtTable extends ASwtWidget<Table> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTable(ASwtWidget<? extends Group> pParent) {
        super(pParent, false);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean getIgnoreOnCalcPos() {
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Table createActor() {
        Table back = new Table() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.setBackground(this.context.getDrawable(ATheme.UI_SLOT_TYPE_MINOR_PNG));
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
