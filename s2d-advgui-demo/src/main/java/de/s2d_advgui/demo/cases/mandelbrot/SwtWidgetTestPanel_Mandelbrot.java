package de.s2d_advgui.demo.cases.mandelbrot;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.demo.DemoResourceManager;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Mandelbrot extends ASwtWidgetTestPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Mandelbrot(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();
        SwtDrawerManager<DemoResourceManager> drawerManager = new SwtDrawerManager<>(rm);
        SwtCanvas_Mandelbrot canvas = new SwtCanvas_Mandelbrot(this, drawerManager);
        canvas.setBounds(10, 10, -20, -20);

//        this.addDrawerClipableForeground(new InternalWidgetDrawerBatch() {
//            @Override
//            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle xDims) {
//                pBatch.setColor(new Color(0x00aaff99));
//                Rectangle pDims = new Rectangle(xDims.x + 10, xDims.y + 10, xDims.width - 20, xDims.height - 20);
//            }
//        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
