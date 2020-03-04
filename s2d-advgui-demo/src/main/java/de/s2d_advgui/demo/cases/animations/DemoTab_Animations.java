package de.s2d_advgui.demo.cases.animations;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.animations.AAnimation;
import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.geom.animations.IAnimationListener_ChangeBounds;
import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;

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
