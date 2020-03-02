package de.s2d_advgui.animations;

public abstract class AAnimation extends AAnimationCore {
    // -------------------------------------------------------------------------------------------------------------------------
    protected final float startTime; // msec

    // -------------------------------------------------------------------------------------------------------------------------
    protected final float endTime; // msec

    // -------------------------------------------------------------------------------------------------------------------------
    protected final float effectiveDuration;

    // -------------------------------------------------------------------------------------------------------------------------
    private float ri;

    // -------------------------------------------------------------------------------------------------------------------------
    public AAnimation(float startTime, float endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.effectiveDuration = this.endTime - this.startTime;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final AnimationState calc(long oi) throws Exception {
        if (this.closed)
            return AnimationState.FINISHED;
        float i = oi - this.initTime;
        if (i > this.endTime) {
            if (!this.closed) {
                this.closed = true;
                this.ri = this.effectiveDuration;
                this._calc(this.ri);
            }
            return AnimationState.FINISHED;
        }
        this.ri = i - this.startTime;
        if (this.ri >= 0) {
            this._calc(this.ri);
        }
        return AnimationState.RUNAGAIN;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected float interpol(float src, float dst) {
        return src + (dst - src) / this.effectiveDuration * this.ri;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract void _calc(float i) throws Exception;

    // -------------------------------------------------------------------------------------------------------------------------
    public final float getRi() {
        return this.ri;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
