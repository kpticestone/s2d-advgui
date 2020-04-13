package de.s2d_advgui.core.awidget;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;

public final class SwtWidgetBuilder<ACTOR extends Actor> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    final IActorCreator<ACTOR> actorCreator;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    final ISwtWidget<? extends Group> parent;

    // -------------------------------------------------------------------------------------------------------------------------
    final boolean focusable;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetBuilder(@Nonnull ISwtWidget<? extends Group> pParent,
            boolean pFocusable, @Nonnull IActorCreator<ACTOR> pActorCreator) {
        this.actorCreator = pActorCreator;
        this.parent = pParent;
        this.focusable = pFocusable;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public IActorCreator<ACTOR> getActorCreator() {
        return this.actorCreator;
    }

//    // -------------------------------------------------------------------------------------------------------------------------
//    public ACTOR createActor(@Nonnull IRend123 pRend) {
//        return this.actorCreator.createActor(pRend);
//    }
//
    // -------------------------------------------------------------------------------------------------------------------------
}
