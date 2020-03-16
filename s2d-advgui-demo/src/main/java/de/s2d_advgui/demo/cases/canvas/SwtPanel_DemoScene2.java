package de.s2d_advgui.demo.cases.canvas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerShapeRenderer;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;
import de.s2d_advgui.core.utils.RectangleFactory;

public class SwtPanel_DemoScene2 extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    static class JHH {
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        float relWidth;
        float relHeight;
        Rectangle rr = new Rectangle();

        void reset() {
            this.minX = Float.MAX_VALUE;
            this.minY = Float.MAX_VALUE;
            this.maxX = Float.MIN_VALUE;
            this.maxY = Float.MIN_VALUE;
        }

        void add(Vector2 jjj) {
            this.minX = Math.min(this.minX, jjj.x);
            this.minY = Math.min(this.minY, jjj.y);
            this.maxX = Math.max(this.maxX, jjj.x);
            this.maxY = Math.max(this.maxY, jjj.y);
        }

        void end() {
            this.relWidth = this.maxX - this.minX;
            this.relHeight = this.maxY - this.minY;

            this.rr.x = this.minX;
            this.rr.y = this.minY;
            this.rr.width = this.maxX - this.minX;
            this.rr.height = this.maxY - this.minY;
        }

        @Override
        public String toString() {
            return this.minX + "|" + this.minY + "-" + this.maxX + "|" + this.maxY;
        }

        public Rectangle rect() {
            return this.rr;
        }
    }

    JHH curJhh = new JHH();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPanel_DemoScene2(ISwtWidget<? extends Group> pParent) {
        super(pParent, false);

        // this.setClip(true);

        SwtCanvas_DemoScene2 canvas = new SwtCanvas_DemoScene2(this, true);
        canvas.setBounds(0, 0, 0, 0);

        SwtButton bbt = new SwtButton(this);
        bbt.setText("0|0"); //$NON-NLS-1$
        bbt.setBounds(0, 0, 64, 32);
        bbt.setLayoutNegativePositions(false);

        SwtPanel kk = new SwtPanel(this, true);
        kk.setLayoutNegativePositions(false);

        this.addUpdateHandler(new IMyWidgetUpdateHandler() {
            @Override
            public void act(float delta) throws Exception {
                Rectangle e1 = canvas.getEntity();
//                System.err.println("e1: " + e1);
                float x1 = e1.x;
                float y1 = e1.y;
                float x2 = x1 + e1.width;
                float y2 = y1 + e1.height;

                curJhh.reset();
                curJhh.add(canvas.sceneCoordsToCanvasCoords(x1, y1));
                curJhh.add(canvas.sceneCoordsToCanvasCoords(x2, y2));
                curJhh.add(canvas.sceneCoordsToCanvasCoords(x1, y2));
                curJhh.add(canvas.sceneCoordsToCanvasCoords(x2, y1));
                curJhh.end();

                bbt.setText((int) x1 + "|" + (int) y1);
                // bbt.setPosition(minX, canvas.getActor().getHeight() - minY);
                // bbt.setSize(maxX - minX, maxY - minY);
                kk.setPosition(curJhh.minX, canvas.getActor().getHeight() - curJhh.relHeight - curJhh.minY);
                kk.setSize(curJhh.relWidth, curJhh.relHeight);
                bbt.setSize(50, 25);
                bbt.setPosition(curJhh.maxX, canvas.getActor().getHeight() - curJhh.relHeight - curJhh.minY);
            }
        });

        this.addDrawer(WidetLayer.FOREGROUND, false, new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                Rectangle xx = RectangleFactory.explode(pDims, 5);
                CameraHolder ch = canvas.getDrawerManager().getCameraHolder();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("zoom: ");
                stringBuilder.append(ch.getWantedZoom());
                stringBuilder.append("\n");
                stringBuilder.append("entity: ");
                stringBuilder.append(canvas.getEntity());
                stringBuilder.append("\n");
                stringBuilder.append("jhh: ");
                stringBuilder.append(curJhh);
                stringBuilder.append("\n");
                pBatch.drawText(stringBuilder.toString(), xx,
                        Align.bottomLeft, .5f, true, Color.WHITE);
            }
        });

        if (TOldCompatibilityCode.TRUE) {
            this.addDrawer(WidetLayer.FOREGROUND, true, new InternalWidgetDrawerShapeRenderer() {
                @Override
                protected void _drawIt(SwtDrawer_Shapes sd, Vector2 pScreenCoords, Rectangle pDims) {
                    sd.setColor(Color.YELLOW);
                    sd.getRenderer().line(
                            pDims.x, pDims.y,
                            pDims.x + curJhh.minX, pDims.y + curJhh.minY);
                    sd.getRenderer().line(
                            pDims.x + pDims.width, pDims.y + pDims.height,
                            pDims.x + curJhh.maxX, pDims.y + curJhh.maxY);
                    sd.getRenderer().line(
                            pDims.x, pDims.y + pDims.height,
                            pDims.x + curJhh.minX, pDims.y + curJhh.maxY);
                    sd.getRenderer().line(
                            pDims.x + pDims.width, pDims.y,
                            pDims.x + curJhh.maxX, pDims.y + curJhh.minY);

                    sd.setColor(Color.BLACK);

                    sd.getRenderer().line(
                            pDims.x + curJhh.minX,
                            pDims.y - 0,
                            pDims.x + curJhh.minX,
                            pDims.y + pDims.height);

                    sd.getRenderer().line(
                            pDims.x + curJhh.maxX,
                            pDims.y - 0,
                            pDims.x + curJhh.maxX,
                            pDims.y + pDims.height);

                    sd.getRenderer().line(
                            pDims.x - 0,
                            pDims.y + curJhh.minY,
                            pDims.x + pDims.width,
                            pDims.y + curJhh.minY);
                    sd.getRenderer().line(
                            pDims.x - 0,
                            pDims.y + curJhh.maxY,
                            pDims.x + pDims.width,
                            pDims.y + curJhh.maxY);
                }
            });
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
