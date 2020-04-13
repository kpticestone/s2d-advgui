package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;

final class ActorCreatorProgressBar implements IActorCreator<ProgressBar> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ProgressBar createActor(IRend123 pRend) {
        ProgressBarStyle style = new ProgressBarStyle();
        // style.knobBefore = new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.GREEN,
        // 15, 15));
        // style.knob = null; // context.getDrawable("ui/icon.png");
        // style.knobAfter = null; // new
        // TextureRegionDrawable(context.resourceManager.getColorTextureRegion(Color.BLACK));
        ProgressBar back = new ProgressBar(0, 100, 1, false, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.setValue(25);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(ProgressBar pActor, boolean pEnabled) {
        pActor.setDisabled(!pEnabled);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}