package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtSlider extends ASwtWidgetSelectable<Slider> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtSlider(ASwtWidget<? extends Group> pParent) {
        super(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void applyDisabledOnActor(boolean b) {
        this.actor.setDisabled(b);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Slider _createActor() {
        SliderStyle style = new SliderStyle();
        style.background = this.context.getDrawable(ATheme.ICONS_128_ANGEL_PNG);
        style.knob = this.context.getDrawable(ATheme.ICONS_128_COW_PNG);
        style.knobBefore = this.context.getDrawable(ATheme.ICONS_128_APPLICATION_PNG);
        Slider back = new Slider(0, 10, 1, false, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeEvent) {
                    callListeners(0);
                    return true;
                }
                return false;
            }
        });
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setRange(float min, float max) {
        this.actor.setRange(min, max);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMax() {
        return this.actor.getMaxValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMin() {
        return this.actor.getMinValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValue(float value) {
        this.actor.setValue(value);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getValue() {
        return this.actor.getValue();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
