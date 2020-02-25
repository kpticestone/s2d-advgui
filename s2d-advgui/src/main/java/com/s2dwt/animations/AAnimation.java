package com.s2dwt.animations;

public abstract class AAnimation {
	protected final float startTime; // msec
	protected final float endTime; // msec
	protected final float effectiveDuration;

	private float ri;
	private boolean closed = false;
	IAnimationListener_Close closeListener;
	private long initTime = -1;

	public AAnimation(float startTime, float endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.effectiveDuration = this.endTime - this.startTime;
	}

	private final void checkAlreadyInit() {
		if (this.initTime != -1)
			throw new RuntimeException("animation already started and can´t modified");
	}

	public final void setCloseListener(IAnimationListener_Close pListener) {
		checkAlreadyInit();
		this.closeListener = pListener;
	}

	public final boolean calc(long oi) {
		if (this.closed)
			return true;
		float i = oi - this.initTime;
		if (i > this.endTime) {
			if (!this.closed) {
				this.closed = true;
				this.ri = this.effectiveDuration;
				this._calc(ri);
			}
			return true;
		}
		this.ri = i - this.startTime;
		if (ri >= 0) {
			this._calc(ri);
		}
		return false;
	}

	protected float interpol(float src, float dst) {
		return src + (dst - src) / this.effectiveDuration * this.ri;
	}

	protected abstract void _calc(float i);

	public final float getRi() {
		return ri;
	}

	void lock() {
		this.initTime = System.currentTimeMillis();
	}
}
