package de.s2d_advgui.demo.cases.canvas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.canvas.SwtCanvas;
import de.s2d_advgui.core.layoutmanager.SwtLayoutManager_Flow;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;
import de.s2d_advgui.demo.DemoStage;

public class DemoTab_Canvas extends SwtTab
{
    public DemoTab_Canvas(SwtTabFolder pParent)
    {
        super(pParent, "canvas");

        for (int i = 0; i < 4; i++)
        {
            ASwtStage<?, ?> a = (DemoStage)this.context;
            SwtDrawerManager<AResourceManager> b = new SwtDrawerManager<>(this.context.getResourceManager());
            DemoCanvasScene pRenderer = new DemoCanvasScene(a, b);
            SwtCanvas<AResourceManager, SwtDrawerManager<AResourceManager>> canvas = new SwtCanvas<>(this, pRenderer);
            // canvas.setBounds(10, 10, -20, -20);
            canvas.addDrawerBackground(new InternalWidgetDrawerBatch()
            {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims)
                {
                    batch.setColor(0.6f, 0.6f, 0.8f, .75f);
                    batch.draw(context.getTextureRegion("icons/128/cow.png"), dims.x + 10, dims.y + 10, dims.width - 20, dims.height - 20);
                }
            });
            this.addDrawerClipableForeground(new InternalWidgetDrawerBatch()
            {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims)
                {
                    pBatch.setColor(new Color(0x00aaff99));
                    pBatch.drawBorder("borders/white-round-5.png", pDims);
                    
                    pBatch.drawText("TL1\nTL2", pDims, Align.topLeft, .5f, true, Color.CYAN);
                    pBatch.drawText("T1\nT2", pDims, Align.top, .5f, true, Color.CYAN);
                    pBatch.drawText("TR1\nTR2", pDims, Align.topRight, .5f, true, Color.CYAN);
                    pBatch.drawText("L1\nL2", pDims, Align.left, .5f, true, Color.CYAN);
                    pBatch.drawText("C1\nC2", pDims, Align.center, .5f, true, Color.CYAN);
                    pBatch.drawText("R1\nR2", pDims, Align.right, .5f, true, Color.CYAN);
                    pBatch.drawText("BL1\nBL2", pDims, Align.bottomLeft, .5f, true, Color.CYAN);
                    pBatch.drawText("B1\nB2", pDims, Align.bottom, .5f, true, Color.CYAN);
                    pBatch.drawText("BR1\nBR2", pDims, Align.bottomRight, .5f, true, Color.CYAN);
                    
                }
            });
            this.addUpdateHandler(pRenderer);
        }

        this.setLayoutManager(new SwtLayoutManager_Flow(200, 200));
    }
}
