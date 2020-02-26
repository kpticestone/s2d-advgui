package de.s2d_advgui.animations;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public final class AnimationManager {
	private static AnimationManager INSTANCE;

	public final static AnimationManager getInstance() {
		return INSTANCE;
	}

	public final static void init() {
		INSTANCE = new AnimationManager(true);
	}

	public final static void initWithoutThread() {
		INSTANCE = new AnimationManager(false);
	}

	private final Stack<AAnimation> animations = new Stack<>();

	private AnimationManager(boolean startInternalThread) {
		if (startInternalThread) {
			// alternativ sollte der manager keinen eigenen thread aufmachen,
			// sondern in einer Update-Phase des Nutzsystems aufgerufen werden.
			new Thread(new Runnable() {
				public void run() {
					try {
						while (true) {
							Thread.sleep(100); // TODO zögerlichkeit rausnehmen?
							fuk();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	Set<AAnimation> closeEm = new HashSet<>();

	public void fuk() throws Exception {
		closeEm.clear();
		try {
//			System.err.println("si: " + this.animations.size());
			for (AAnimation a : this.animations) {
				if (a.calc(System.currentTimeMillis())) {
					closeEm.add(a);
				}
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}
		for (AAnimation aa : closeEm) {

			int was = this.animations.size();
			boolean rem = this.animations.remove(aa);
			int now = this.animations.size();
//			System.err.println("w: " + was + " -> " + now);
//			if (rem) {
//				System.err.println("this.." + this.animations.contains(aa));
//			} else {
//				System.err.println("ups");
//			}
			IAnimationListener_Close cl = aa.closeListener;
			if (cl != null) {
				cl.onHit();
			}
		}
	}

	public void startAnimation(AAnimation pAnimation) {
		pAnimation.lock();
//		System.err.println("startAnimation: " + pAnimation);
		this.animations.add(pAnimation);
	}
}
