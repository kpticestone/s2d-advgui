package de.s2d_advgui.animations;

import java.util.Stack;

public final class AnimationGroup_Sequence extends AAnimationCore {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Stack<AAnimationCore> animations = new Stack<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public AnimationGroup_Sequence() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void add(AAnimationCore pAnimation) {
        this.animations.add(pAnimation);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public AnimationState calc(long oi) throws Exception {
        while (!this.animations.isEmpty()) {
            AAnimationCore fi = this.animations.firstElement();
            fi.lock(-1);
            AnimationState x = fi.calc(oi);
            if (x == AnimationState.RUNAGAIN) return AnimationState.RUNAGAIN;
            fi.callCloseListener();
            if (this.animations.isEmpty()) return AnimationState.FINISHED;
            this.animations.remove(0);
        }
        return AnimationState.FINISHED;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
