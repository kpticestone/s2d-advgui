package de.s2d_advgui.core.tree;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;

final class ActorCreatorTree<PR> implements IActorCreator<ActorTree<PR>> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ActorTree<PR> createActor(IRend123 pRend) {
        return new ActorTree<>(pRend);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(ActorTree<PR> pActor, boolean pEnabled) {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}