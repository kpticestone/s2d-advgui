package de.s2d_advgui.core.awidget.acc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import de.s2d_advgui.core.rendering.IRend123;

public final class ActorCreatorWidgetGroup implements IActorCreator<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    public final static ActorCreatorWidgetGroup createInstance(boolean pDrawActor) {
        return new ActorCreatorWidgetGroup(pDrawActor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final boolean drawActor;

    // -------------------------------------------------------------------------------------------------------------------------
    private ActorCreatorWidgetGroup(boolean pDrawActor) {
        this.drawActor = pDrawActor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public WidgetGroup createActor(IRend123 pRend) {
        if (this.drawActor) {
            return new WidgetGroup() {
                @Override
                public void draw(Batch batch, float parentAlpha) {
                    pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
                }
            };
        }
        return new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> {
                    // DON
                });
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(WidgetGroup pActor, boolean pEnabled) {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
