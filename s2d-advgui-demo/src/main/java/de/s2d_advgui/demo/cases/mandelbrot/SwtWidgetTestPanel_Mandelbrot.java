package de.s2d_advgui.demo.cases.mandelbrot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtCheckbox;
import de.s2d_advgui.core.basicwidgets.SwtTextField;
import de.s2d_advgui.core.rendering.SwtDrawerManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.utils.RectangleFactory;
import de.s2d_advgui.demo.DemoResourceManager;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanel;

public class SwtWidgetTestPanel_Mandelbrot extends ASwtWidgetTestPanel {
    private SwtTextField txtField;
    private SwtTextField stepsField;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Mandelbrot(ISwtWidget<? extends Group> pParent) {
        super(pParent);

        SwtCheckbox autozoom = new SwtCheckbox(this, "autozoom");
        autozoom.setBounds(0, 0, 200, 25);

        txtField = new SwtTextField(this);
        txtField.setBounds(200, 0, 200, 25);

        stepsField = new SwtTextField(this);
        stepsField.setBounds(400, 0, 200, 25);

        DemoResourceManager rm = (DemoResourceManager) this.getContext().getResourceManager();
        SwtDrawerManager<DemoResourceManager> drawerManager = new SwtDrawerManager<>(rm);
        SwtCanvas_Mandelbrot canvas = new SwtCanvas_Mandelbrot(this, drawerManager);
        canvas.setBounds(0, 25, 0, -25);

        this.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                String pStrg = "zoom: " + canvas.zoom + "\n";
                pStrg += "x: " + canvas.useLeft + "\n";
                pStrg += "y: " + canvas.useTop + "\n";
                pBatch.drawText(pStrg, RectangleFactory.explode(pDims, 20), Align.bottom, 1f, true, Color.CYAN);

            }
        });

        txtField.setText("" + canvas.zoom);
        stepsField.setText("" + canvas.steps);

        autozoom.addLeftClickListener(() -> {
            canvas.autozoom = autozoom.isChecked();
        });
        txtField.addEnterListener(() -> {
            canvas.zoom = Double.parseDouble(txtField.getText());
        });
        this.addUpdateHandler((x) -> {
            if (canvas.autozoom) {
                txtField.setText("" + canvas.zoom);
                stepsField.setText(""+canvas.steps);
            }
        });
        stepsField.addEnterListener(() -> {
            canvas.steps = Integer.parseInt(stepsField.getText());
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
