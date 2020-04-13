package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;

final class ActorCreatorSlider implements IActorCreator<Slider> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Slider createActor(IRend123 pRend) {
        AResourceManager rm = pRend.getResourceManager();
        TextureRegion rh = rm.getColorTextureRegion(Color.YELLOW);
        SliderStyle style = new SliderStyle();
        style.background = rm.getDrawable(ATheme.ICONS_128_ANGEL_PNG);
        style.background.setMinHeight(25);
        style.background.setMinWidth(100);
        style.knob = rm.getDrawable(ATheme.ICONS_128_COW_PNG);
        style.knob.setMinHeight(25);
        style.knob.setMinWidth(25);
        style.knobBefore = rm.getDrawable(ATheme.ICONS_128_APPLICATION_PNG);
        style.knobBefore.setMinHeight(25);
        Slider back = new Slider(0, 10, 1, false, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> {
                    // DON
                });
            }
        };
        back.setTouchable(Touchable.enabled);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(Slider pActor, boolean pEnabled) {
        pActor.setDisabled(!pEnabled);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}