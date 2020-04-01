package de.s2d_advgui.demo.cases.mandelbrot;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtCheckbox;
import de.s2d_advgui.core.basicwidgets.SwtSlider;
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
                stepsField.setText("" + canvas.steps);
            }
        });
        stepsField.addEnterListener(() -> {
            canvas.steps = Integer.parseInt(stepsField.getText());
        });

        Supplier<Float> jsj = () -> {
            return (float)(1f/canvas.zoom)*0.00005f;
        };

        SwtSlider slsl = new SwtSlider(this);
        slsl.setBounds(25, -75, -50, 25);
        slsl.setRange(1, 1000);
        slsl.addListener(new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                float vv = slsl.getValue() / 10f;
                System.err.println("setZoom: " + vv);
                canvas.zoom = vv;
//                canvas.steps = (int) vv * 10;
            }
        });

        SwtButton btnTop = new SwtButton(this);
        btnTop.setText("^");
        btnTop.setBounds(-75, 25, 25, 25);
        btnTop.addLeftClickListener(new Trigger() {
            @Override
            public void onTrigger() {
                canvas.useTop += jsj.get();
            }
        });

        SwtButton btnRight = new SwtButton(this);
        btnRight.setText(">");
        btnRight.setBounds(-50, 50, 25, 25);
        btnRight.addLeftClickListener(new Trigger() {
            @Override
            public void onTrigger() {
                canvas.useLeft += jsj.get();
            }
        });

        SwtButton btnBottom = new SwtButton(this);
        btnBottom.setText("v");
        btnBottom.setBounds(-75, 75, 25, 25);
        btnBottom.addLeftClickListener(new Trigger() {
            @Override
            public void onTrigger() {
                canvas.useTop -= jsj.get();
            }
        });

        SwtButton btnLeft = new SwtButton(this);
        btnLeft.setText("<");
        btnLeft.setBounds(-100, 50, 25, 25);
        btnLeft.addLeftClickListener(new Trigger() {
            @Override
            public void onTrigger() {
                canvas.useLeft -= jsj.get();
            }
        });

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
