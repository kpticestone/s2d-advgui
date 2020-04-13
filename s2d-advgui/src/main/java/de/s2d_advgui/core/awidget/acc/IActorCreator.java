package de.s2d_advgui.core.awidget.acc;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;

import de.s2d_advgui.core.rendering.IRend123;

public interface IActorCreator<ACTOR extends Actor> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    ACTOR createActor(@Nonnull IRend123 pRend);

    // -------------------------------------------------------------------------------------------------------------------------
    void setEnabledOnActor(ACTOR pActor, boolean pEnabled);

    // -------------------------------------------------------------------------------------------------------------------------
}
