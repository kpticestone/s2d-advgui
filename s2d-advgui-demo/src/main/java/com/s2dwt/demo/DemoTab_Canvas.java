package com.s2dwt.demo;

import com.s2dwt.core.canvas.SwtCanvas;
import com.s2dwt.core.layoutmanager.SwtLayoutManager_Flow;
import com.s2dwt.core.rendering.SwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;

public class DemoTab_Canvas extends SwtTab {
    public DemoTab_Canvas(SwtTabFolder pParent) {
        super(pParent, "canvas");

        for (int i = 0; i < 4; i++) {
            ASwtStage<?, ?> a = (DemoStage) this.context;
            SwtDrawerManager<AResourceManager> b = new SwtDrawerManager<>(this.context.getResourceManager());
            DemoCanvasScene pRenderer = new DemoCanvasScene(a, b);
            SwtCanvas<AResourceManager, SwtDrawerManager<AResourceManager>> canvas = new SwtCanvas<>(this, pRenderer);
            // canvas.setBounds(10, 10, -20, -20);
            canvas.addDrawerBackground((batch, pScreenCoords, dims) -> {
                batch.setColor(0.6f, 0.6f, 0.8f, .75f);
                batch.draw(context.getTextureRegion("ui/minimap.png"), dims.x + 10, dims.y + 10, dims.width - 20,
                        dims.height - 20);
            });
            this.addUpdateHandler(pRenderer);
        }

        this.setLayoutManager(new SwtLayoutManager_Flow(200, 200));
    }
}
