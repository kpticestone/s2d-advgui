package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

final class ActorCreatorScrollPanel implements IActorCreator<ScrollPane> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ScrollPane createActor(IRend123 pRend) {
        AResourceManager rm = pRend.getResourceManager();
        ScrollPaneStyle style = new ScrollPaneStyle();
        style.background = null; // context.getDrawable("ui2/back_red.png");
        style.corner = rm.getDrawable("ui2/back_red.png");
        style.hScroll = rm.getDrawable("ui2/back_red.png");
        style.hScrollKnob = rm.getDrawable("ui/icon.png");
        style.vScroll = rm.getDrawable("ui2/back_red.png");
        style.vScrollKnob = rm.getDrawable("ui2/back_red.png");
        ScrollPane scp = new ScrollPane(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
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
    public void setEnabledOnActor(ScrollPane pActor, boolean pEnabled) {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}