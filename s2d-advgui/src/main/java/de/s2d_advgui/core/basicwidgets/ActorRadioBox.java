package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Pools;

public class ActorRadioBox extends WidgetGroup {

    private ClickListener clickListener;
    boolean isChecked, isDisabled;
    boolean focused;

    public ActorRadioBox() {
        setTouchable(Touchable.enabled);
        addListener(this.clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isDisabled()) return;
                setChecked(true, true);
            }
        });
        addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                ActorRadioBox.this.focused = focused;
            }
        });
    }

    public void setChecked() {
        this.setChecked(true, true);
    }

    void setChecked(boolean checked, boolean fireEvent) {
        this.isChecked = checked;
        if (fireEvent) {
            ChangeEvent changeEvent = Pools.obtain(ChangeEvent.class);
            fire(changeEvent);
            Pools.free(changeEvent);
        }
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
}
