package de.s2d_advgui.demo.cases.canvas;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.input.ASwtMouseMoveListenerCanvas;

public class SwtPanel_DemoScene2 extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPanel_DemoScene2(ISwtWidget<? extends Group> pParent) {
        super(pParent, false);

        SwtCanvas_DemoScene2 canvas = new SwtCanvas_DemoScene2(this);
        canvas.setBounds(0, 0, 0, 0);

        SwtButton bbt = new SwtButton(this);
        bbt.setText("0|0"); //$NON-NLS-1$
        bbt.setBounds(0, 0, 64, 32);
        bbt.setLayoutNegativePositions(false);

        canvas.addMouseMoveHandler(new ASwtMouseMoveListenerCanvas<>(canvas) {
            @Override
            protected boolean onCanvasMouseDown(float pSceneX, float pSceneY, float pCanvasX, float pCanvasY,
                    int pButton) {
                bbt.setText((int) pSceneX + "|" + (int) pSceneY);
                bbt.setPosition(pCanvasX - 32, pCanvasY - 16);
                return true;
            }
        });

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
