package de.s2d_advgui.demo.cases.canvas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.WidetLayer;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.input.ASwtMouseMoveListenerCanvas;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.utils.ShapeUtils;

public class SwtPanel_DemoScene2b extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPanel_DemoScene2b(ISwtWidget<? extends Group> pParent) {
        super(pParent, false);

        SwtCanvas_DemoScene2 canvas = new SwtCanvas_DemoScene2(this, false);
        canvas.setBounds(0, 0, 0, 0);

        SwtButton bbt = new SwtButton(this);
        bbt.setText("0|0"); //$NON-NLS-1$
        bbt.setBounds(0, 0, 64, 32);
        bbt.setLayoutNegativePositions(false);

        this.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                Rectangle xx = ShapeUtils.explode(pDims, 5);
                CameraHolder ch = canvas.getDrawerManager().getCameraHolder();
                pBatch.drawText("zoom: " + ch.getWantedZoom() + "\nentity: " + canvas.getEntity() + "\n", xx, Align.bottomLeft, .5f, true, Color.WHITE);
            }
        });

        canvas.addMouseMoveHandler(new ASwtMouseMoveListenerCanvas<>(canvas) {
            @Override
            protected boolean onCanvasMouseDown(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY,
                    int pButton) {
                bbt.setText((int) pSceneX + "|" + (int) pSceneY);
                bbt.setPosition(pCanvasX - 32, pCanvasY - 16);
                canvas.getEntity().setPosition(pSceneX, pSceneY);
                return true;
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
