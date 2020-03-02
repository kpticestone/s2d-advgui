package de.s2d_advgui.animations;

public abstract class AAnimationCore {
    // -------------------------------------------------------------------------------------------------------------------------
    boolean closed = false;

    // -------------------------------------------------------------------------------------------------------------------------
    private IAnimationListener_Close closeListener;

    // -------------------------------------------------------------------------------------------------------------------------
    long initTime = -1;

    // -------------------------------------------------------------------------------------------------------------------------
    private final void checkAlreadyInit() {
        if (this.initTime != -1)
            throw new RuntimeException("animation already started and can´t modified"); //$NON-NLS-1$
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void setCloseListener(IAnimationListener_Close pListener) {
        checkAlreadyInit();
        this.closeListener = pListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    long lock(long useLockTime) {
        if (this.initTime == -1) {
            this.initTime = useLockTime == -1 ? System.currentTimeMillis() : useLockTime;
        }
        return this.initTime;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract AnimationState calc(long oi) throws Exception;

    // -------------------------------------------------------------------------------------------------------------------------
    void callCloseListener() throws Exception {
        IAnimationListener_Close cl = this.closeListener;
        if (cl != null) {
            cl.onAnimationClosed();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
