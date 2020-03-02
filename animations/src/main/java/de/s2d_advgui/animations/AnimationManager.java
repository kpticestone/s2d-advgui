package de.s2d_advgui.animations;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class AnimationManager {
    // -------------------------------------------------------------------------------------------------------------------------
    private static AnimationManager INSTANCE;

    // -------------------------------------------------------------------------------------------------------------------------
    public final static AnimationManager getInstance() {
        if (INSTANCE == null) throw new RuntimeException("AnimationManager not yet initialized!"); //$NON-NLS-1$
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static void init(int pDelay) {
        if (INSTANCE != null) throw new RuntimeException("AnimationManager already initialized!"); //$NON-NLS-1$
        INSTANCE = new AnimationManager(pDelay);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static void initWithoutThread() {
        if (INSTANCE != null) throw new RuntimeException("AnimationManager already initialized!"); //$NON-NLS-1$
        INSTANCE = new AnimationManager(0);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final Stack<AAnimationCore> animations = new Stack<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<AAnimationCore> closeEm = new HashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private AnimationManager(int pDelay) {
        if (pDelay > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(pDelay);
                            doUpdate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void doUpdate() throws Exception {
        this.closeEm.clear();
        long currentTimeMillis = System.currentTimeMillis();
        try {
            for (AAnimationCore a : this.animations) {
                if (a.calc(currentTimeMillis) == AnimationState.FINISHED) {
                    this.closeEm.add(a);
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        for (AAnimationCore aa : this.closeEm) {
            aa.callCloseListener();
            this.animations.remove(aa);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void startAnimation(AAnimationCore pAnimation) {
        pAnimation.lock(-1);
        this.animations.add(pAnimation);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
