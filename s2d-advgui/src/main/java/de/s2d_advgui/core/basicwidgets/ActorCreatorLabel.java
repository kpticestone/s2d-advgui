package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;

final class ActorCreatorLabel implements IActorCreator<Label> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Label createActor(IRend123 pRend) {
        LabelStyle style = new LabelStyle();
        style.font = pRend.getResourceManager().getFont(1f, true);
        Label back = new Label(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.setWrap(false);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(Label pActor, boolean pEnabled) {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}