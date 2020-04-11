package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class ASwtWidgetDisableable<ACTOR extends Actor> extends ASwtWidget<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
//    public ASwtWidgetDisableable(ISwtStage<?, ?> pContext) {
//        super(pContext);
//    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidgetDisableable(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
        this.addEnabledStateListener((b) -> this.applyDisabledOnActor(!b.booleanValue()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void applyDisabledOnActor(boolean b);

    // -------------------------------------------------------------------------------------------------------------------------
}
