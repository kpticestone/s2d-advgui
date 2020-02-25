package com.s2dwt.demo;

import com.badlogic.gdx.Gdx;
import com.s2dwt.animations.AnimationManager;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.application.SwtApplication;
import com.s2dwt.core.awidget.IMyWidgetUpdateHandler;
import com.s2dwt.core.rendering.ISwtDrawerManager;

public class DemoApp extends SwtApplication<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>, DemoStage> {
	// -------------------------------------------------------------------------------------------------------------------------
	public DemoApp(ISwtDrawerManager<DemoResourceManager> pDrawerManager) throws Exception {
		super(pDrawerManager);
		this.registerScreen(new DemoScreenDescriptor());
	}

	// -------------------------------------------------------------------------------------------------------------------------
	@Override
	protected DemoStage _createStage(
			ISwtApplicationController<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pApplicationController) {
		DemoStage stage = new DemoStage(pApplicationController);
		AnimationManager.initWithoutThread();
		stage.addUpdateHandler(new IMyWidgetUpdateHandler() {
			@Override
			public void act(float delta) {
				try {
//					long t1 = System.currentTimeMillis();
					AnimationManager.getInstance().fuk();
//					System.err.println("anim cost: " + (System.currentTimeMillis()-t1) + "msec");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return stage;
	}

	// -------------------------------------------------------------------------------------------------------------------------
	@Override
	protected String getTitle() {
		return "Scene2D Widget Toolkit Demo (" + Gdx.graphics.getFramesPerSecond() + " fps)";
	}

	// -------------------------------------------------------------------------------------------------------------------------
}
