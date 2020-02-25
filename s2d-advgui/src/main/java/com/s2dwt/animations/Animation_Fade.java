package com.s2dwt.animations;

public class Animation_Fade extends AAnimation {
	private final float from;
	private final float till;
	private final IAnimationListener_Fade listener;

	public Animation_Fade(float startTime, float endTime, float from, float till,
			IAnimationListener_Fade pListener) {
		super(startTime, endTime);
		this.from = from;
		this.till = till;
		this.listener = pListener;
	}

	@Override
	protected void _calc(float i) {
		float cur = interpol(this.from, this.till);
		this.listener.onHit(cur);
	}

}
