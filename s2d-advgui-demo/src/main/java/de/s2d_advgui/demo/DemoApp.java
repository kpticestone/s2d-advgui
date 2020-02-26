package de.s2d_advgui.demo;

import com.badlogic.gdx.Gdx;
import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.application.SwtApplication;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;

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
