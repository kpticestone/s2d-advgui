package de.s2d_advgui.core.awidget;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_050_Disposing<ACTOR extends Actor> extends ASwtWidget_000_Ground<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Trigger> disposeListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_050_Disposing(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public ASwtWidget_050_Disposing(ISwtWidget<? extends Group> pParent) {
        super(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_050_Disposing(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        super(pBuilder);
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
        this.context.unregisterMapping(this.actor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void disposeChildren() {
        for (ISwtWidget<?> a : new HashSet<>(this.children)) {
            a.dispose();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
