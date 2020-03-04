package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;

import de.s2d_advgui.core.awidget.ASwtWidget;

public class SwtScrollPanel extends ASwtWidget<ScrollPane> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ScrollPane scp;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtTable table;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtScrollPanel(ASwtWidget<? extends Group> pParent) {
        super(pParent, false);
        this.table = new SwtTable(this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected ScrollPane createActor() {
        ScrollPaneStyle style = new ScrollPaneStyle();
        style.background = null; // context.getDrawable("ui2/back_red.png");
        style.corner = context.getDrawable("ui2/back_red.png");
        style.hScroll = context.getDrawable("ui2/back_red.png");
        style.hScrollKnob = context.getDrawable("ui/icon.png");
        style.vScroll = context.getDrawable("ui2/back_red.png");
        style.vScrollKnob = context.getDrawable("ui2/back_red.png");
        scp = new ScrollPane(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }

            @Override
            public void addActor(Actor actor) {
                Actor was = this.getActor();
                if (was != null) throw new UnsupportedOperationException("actor already set");
                this.setActor(actor);
            }
        };

        return scp;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean getIgnoreOnCalcPos() {
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTable getTable() {
        return table;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
