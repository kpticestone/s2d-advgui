package com.s2dwt.demo;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.s2dwt.animations.AAnimation;
import com.s2dwt.animations.AnimationManager;
import com.s2dwt.animations.Animation_ChangeBounds;
import com.s2dwt.animations.IAnimationListener_ChangeBounds;
import com.s2dwt.core.basicwidgets.SwtButton;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;

public class DemoTab_Animations extends SwtTab {
	public DemoTab_Animations(SwtTabFolder pParent) {
		super(pParent, "animations");

		RandomXS128 rand = new RandomXS128();

		int[] curPos = { 0 };

		SwtButton btn = new SwtButton(this);
		btn.setText("TEXT");
		btn.setBounds(0, 0, 150, 25);

		Rectangle jj = new Rectangle();
		jj.width = 150;
		jj.height = 25;

		btn.addListener(btnx -> {
			switch (curPos[0]%4) {
			case 0:
				jj.x = 0;
				jj.y = 0;
				jj.width = 250;
				jj.height = 100;
				break;
			case 1:
				jj.x = 600;
				jj.y = 0;
                jj.width = 300;
                jj.height = 120;
				break;
			case 2:
				jj.x = 600;
				jj.y = 500;
                jj.width = 150;
                jj.height = 25;
				break;
			case 3:
				jj.x = 0;
				jj.y = 500;
                jj.width = 100;
                jj.height = 30;
				break;
			}
			curPos[0]++;
			

			Rectangle src = new Rectangle();
			src.x = btn.getX();
			src.y = btn.getY();
			src.width = btn.getWidth();
			src.height = btn.getHeight();

			AAnimation resAni = new Animation_ChangeBounds(0f, 500f, src, jj, new IAnimationListener_ChangeBounds() {
				@Override
				public void onChange(Rectangle pRect) {
					btn.setBounds(pRect.x, pRect.y, pRect.width, pRect.height);
				}
			});

			AnimationManager.getInstance().startAnimation(resAni);

		});
	}

}
