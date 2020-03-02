package de.s2d_advgui.animations;

import java.util.ArrayList;
import java.util.List;

public final class AnimationGroup_Parallel extends AAnimationCore {
    // -------------------------------------------------------------------------------------------------------------------------
    private final List<AAnimationCore> animations = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public AnimationGroup_Parallel() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void add(AAnimationCore pAnimation) {
        this.animations.add(pAnimation);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    final long lock(long useLockTime) {
        long iuseLockTime = useLockTime == -1 ? System.currentTimeMillis() : useLockTime;
        for (AAnimationCore c : this.animations) {
            c.lock(iuseLockTime);
        }
        return super.lock(iuseLockTime);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public AnimationState calc(long oi) throws Exception {
        AnimationState back = AnimationState.FINISHED;
        for (AAnimationCore a : this.animations) {
            if (a.calc(oi) == AnimationState.RUNAGAIN) {
                back = AnimationState.RUNAGAIN;
            }
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    void callCloseListener() throws Exception {
        for (AAnimationCore a : this.animations) {
            a.callCloseListener();
        }
        super.callCloseListener();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
