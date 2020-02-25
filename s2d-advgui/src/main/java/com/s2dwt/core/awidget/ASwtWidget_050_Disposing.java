package com.s2dwt.core.awidget;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.s2dwt.core.stage.ISwtStage;
import com.s2dwt.impcomp.Trigger;

public abstract class ASwtWidget_050_Disposing<ACTOR extends Actor> extends ASwtWidget_000_Ground<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Trigger> disposeListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_050_Disposing(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_050_Disposing(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDisposeListener(@Nonnull Trigger pTrigger) {
        this.disposeListener.add(pTrigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void dispose() {
        for (Trigger a : this.disposeListener) {
            a.onTrigger();
        }
        Collection<ISwtWidget<?>> into = new HashSet<>();
        this.children.removeAll(into);
        for (ISwtWidget<?> a : into) {
            a.dispose();
        }
        this.actor.remove();
        ISwtWidget<? extends Group> pa = this.parent;
        if (pa != null) {
            this.parent.getChildren().remove(this);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void disposeChildren() {
        for (ISwtWidget<?> a : new HashSet<>(this.children)) {
            a.dispose();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
