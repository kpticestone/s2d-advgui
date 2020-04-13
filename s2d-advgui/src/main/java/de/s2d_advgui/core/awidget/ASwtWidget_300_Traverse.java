package de.s2d_advgui.core.awidget;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_300_Traverse<ACTOR extends Actor> extends ASwtWidget_299_Hover<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    protected final boolean focusable;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_300_Traverse(ISwtStage<?, ?> pContext) {
        super(pContext);
        this.focusable = false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_300_Traverse(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        super(pBuilder);
        this.focusable = pBuilder.focusable;
        if (this.focusable) {
            this.registerEventHandler(InputEvent.Type.touchDown, (event) -> {
                if (isEnabled()) {
                    if (event.getButton() == 0) {
                        focus();
                    }
                }
                return false;
            });
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isFocusable() {
        return this.focusable;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean focus() {
        return this.context.setKeyboardFocus(this.actor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean isFocused() {
        return this.context.getKeyboardFocus() == this.actor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
