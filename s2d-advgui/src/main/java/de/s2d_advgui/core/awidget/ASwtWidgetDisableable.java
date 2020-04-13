package de.s2d_advgui.core.awidget;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ASwtWidgetDisableable<ACTOR extends Actor> extends ASwtWidget<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
//    public ASwtWidgetDisableable(ISwtWidget<? extends Group> pParent, boolean focusable) {
//        super(pParent, focusable);
//        this.addEnabledStateListener((b) -> this.applyDisabledOnActor(!b.booleanValue()));
//    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidgetDisableable(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        super(pBuilder);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void applyDisabledOnActor(boolean b);

    // -------------------------------------------------------------------------------------------------------------------------
}
