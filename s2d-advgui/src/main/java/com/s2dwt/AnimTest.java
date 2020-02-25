package com.s2dwt;

import com.badlogic.gdx.math.Rectangle;
import com.s2dwt.animations.AnimationManager;
import com.s2dwt.animations.Animation_ChangeBounds;
import com.s2dwt.animations.Animation_Fade;

public class AnimTest {
	public static void main(String[] args) {
		try {
			Animation_ChangeBounds animBounds = new Animation_ChangeBounds(1000f, 5000f,
					new Rectangle(750, 20, 150, 150), new Rectangle(150, 150, 300, 300), calc -> {
						System.err.println(calc);
					});
			animBounds.setCloseListener(() -> {
				System.err.println("dead.");
				Animation_Fade anns = new Animation_Fade(0, 1000, 25, 75, (cur) -> {
					System.err.println("fade: " + cur);
				});
				anns.setCloseListener(() -> {
					System.err.println("dead2.");
					System.exit(123);
				});
				AnimationManager.getInstance().startAnimation(anns);
			});

			AnimationManager.getInstance().startAnimation(animBounds);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
