package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;

final class ActorCreatorRadioBox implements IActorCreator<ActorRadioBox> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ActorRadioBox createActor(IRend123 pRend) {
        ActorRadioBox back = new ActorRadioBox() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> {
                    // DON
                });
            }
        };
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(ActorRadioBox pActor, boolean pEnabled) {
        pActor.setDisabled(!pEnabled);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}