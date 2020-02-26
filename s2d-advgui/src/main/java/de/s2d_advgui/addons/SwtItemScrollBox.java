package de.s2d_advgui.addons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import de.s2d_advgui.core.awidget.ASwtWidget;

public class SwtItemScrollBox extends ASwtWidget<ScrollPane> {
    // -------------------------------------------------------------------------------------------------------------------------
    private SwtItemScrollBox2 content;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox(ASwtWidget<? extends Group> pParent) {
        super(pParent, true);
        this.content = new SwtItemScrollBox2(this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox2 getContent() {
        return this.content;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected ScrollPane createActor() {
        ScrollPaneStyle style = new ScrollPaneStyle();
        style.background = null; // context.getDrawable("ui2/back_red.png");
        style.corner = this.context.getDrawable("ui2/back_red.png");
        style.hScroll = this.context.getDrawable("ui2/back_red.png");
        style.hScrollKnob = this.context.getDrawable("ui/icon.png");
        style.vScroll = this.context.getDrawable("ui2/back_red.png");
        style.vScrollKnob = this.context.getDrawable("ui2/back_red.png");
        ScrollPane scp = new ScrollPane(null, style) //
        // ScrollPane scp = new ScrollPane(null, context.resourceManager.skin) //
        // ScrollPane scp = new ScrollPane(null) //
        {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }

            @Override
            public void addActor(Actor actor) {
                this.setActor(actor);
            }
        };
        scp.setFadeScrollBars(true);
        scp.setScrollbarsVisible(true);
        scp.setForceScroll(false, true);
        scp.setFlickScroll(false);
        return scp;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
