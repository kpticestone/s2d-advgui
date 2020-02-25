package com.s2dwt.demo;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.leo.commons.utils.TOldCompatibilityCode;
import com.s2dwt.animations.AnimationManager;
import com.s2dwt.animations.Animation_ChangeBounds;
import com.s2dwt.animations.IAnimationListener_ChangeBounds;
import com.s2dwt.core.awidget.IMyWidgetUpdateHandler;
import com.s2dwt.core.canvas.ICanvasRenderer;
import com.s2dwt.core.rendering.SwtDrawerManager;
import com.s2dwt.core.rendering.SwtDrawer_Batch;
import com.s2dwt.core.rendering.SwtDrawer_Shapes;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

public class DemoCanvasScene extends ICanvasRenderer<AResourceManager, SwtDrawerManager<AResourceManager>>
		implements IMyWidgetUpdateHandler {

	private double time = 0;

	public DemoCanvasScene(ASwtStage<?, ?> stage, SwtDrawerManager<AResourceManager> pDrawerManager) {
		super(stage, pDrawerManager);
		this.newAnim();
	}

	@Override
	public void act(float delta) {
		time += delta;
	}

	@Override
	protected void _drawIt(Rectangle dimensions) {
		this.cameraHolder.setWantedZoom(1f);
		this.cameraHolder.getCamera().update();

		Batch bb = this.drawerManager.getBatch();
		bb.setProjectionMatrix(this.cameraHolder.getCamera().combined);
		ShapeRenderer sr = this.drawerManager.getShapeRenderer();
		sr.setProjectionMatrix(this.cameraHolder.getCamera().combined);

		if (TOldCompatibilityCode.FALSE) {
			try (SwtDrawer_Shapes sd = this.drawerManager.startShapesDrawer()) {
				sd.drawCross(0, 0, 100);
				sd.rect(-dimensions.width / 2f + 1, -dimensions.height / 2f + 1, dimensions.width - 2,
						dimensions.height - 2);
			}
		}
		AResourceManager rm = this.drawerManager.getResourceManager();
		try (SwtDrawer_Batch<AResourceManager> bd = this.drawerManager.startBatchDrawer()) {
//			System.err.println("cur: " + cur);
			bd.draw(rm.getTextureRegion("icons/128/convertible_blue.png"), cur.x, cur.y, cur.width, cur.height);
		}
	}

	Rectangle cur = new Rectangle(0, 0, 192, 108);
	Rectangle src = null;
	Rectangle dst = null;

	void newAnim() {
		RandomXS128 rand = new RandomXS128();
		src = new Rectangle(cur);
		dst = new Rectangle(cur.x + rand.nextInt(100) - 50, cur.y + rand.nextInt(100) - 50,
				cur.width + rand.nextInt(100) - 50, cur.height + rand.nextInt(100) - 50);

		int jj = 100;
		if (dst.x > jj)
			dst.x = jj;
		if (dst.y > jj)
			dst.y = jj;
		if (dst.x < -jj)
			dst.x = -jj;
		if (dst.y < -jj)
			dst.y = -jj;
		if (dst.width < 100)
			dst.width = 100;
		if (dst.height < 100)
			dst.height = 100;
		if (dst.width > 300)
			dst.width = 300;
		if (dst.height > 300)
			dst.height = 300;
//		System.err.println("dst: " + dst);

		Animation_ChangeBounds back = new Animation_ChangeBounds(0f, 1000f, src, dst,
				new IAnimationListener_ChangeBounds() {
					@Override
					public void onChange(Rectangle pRect) {
						cur.set(pRect);
					}
				});
		back.setCloseListener(() -> {
			newAnim();
		});
		AnimationManager.getInstance().startAnimation(back);
	}

}
