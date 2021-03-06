package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;

public class SwtLabel extends ASwtWidget<Label> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLabel(ISwtWidget<? extends Group> pParent) {
        super(pParent, false);
        this.setColor(Color.WHITE);
        this.setFontScale(1f);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLabel(ISwtWidget<? extends Group> pParent, String pText) {
        this(pParent);
        this.setText(pText);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Label createActor() {
        LabelStyle style = new LabelStyle();
        style.font = this.context.getResourceManager().getFont(1f, true);
        // style.fontColor = this.context.getColor_Label();
        Label back = new Label(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.setWrap(false);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setWrap(boolean wrap) {
        this.actor.setWrap(wrap);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColor(Color c) {
        this.actor.setColor(c);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String string) {
        this.actor.setText(string);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setAlign(int align) {
        this.actor.setAlignment(align);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setFontScale(float scale) {
        //LabelStyle style = this.actor.getStyle();
        //style.font = this.context.getResourceManager().getFont(scale, true);
        this.actor.setFontScale(scale);
        this.actor.layout();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setFontColor(Color red) {
        this.actor.getStyle().fontColor = red;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clearText() {
        this.setText(null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getText() {
        return this.actor.getText().toString();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean hasText() {
        return this.actor.getText().length > 0;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}