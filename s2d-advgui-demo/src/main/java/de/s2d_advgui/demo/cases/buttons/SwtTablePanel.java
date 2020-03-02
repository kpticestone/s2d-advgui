package de.s2d_advgui.demo.cases.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ASwtLayoutData;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public class SwtTablePanel extends SwtPanel {

    public static class Splitter {
        public final TextureRegion trTL;
        public final TextureRegion trT;
        public final TextureRegion trCrossTop;
        public final TextureRegion trTR;

        public final TextureRegion trCL;
        public final TextureRegion trC;
        public final TextureRegion trCrossVer;
        public final TextureRegion trCR;

        public final TextureRegion trCrossLeft;
        public final TextureRegion trCrossHor;
        public final TextureRegion trCrossCenter;
        public final TextureRegion trCrossRight;

        public final TextureRegion trBL;
        public final TextureRegion trB;
        public final TextureRegion trCrossBotton;
        public final TextureRegion trBR;

        public final int o1;
        public final int o2;
        public final int o3;
        private final Texture tx;

        public Splitter(Texture tx) {
            this.tx = tx;
            this.o1 = tx.getWidth() / 4;
            this.o2 = this.o1 * 2;
            this.o3 = this.o1 * 3;

            this.trTL = load(0, 0);
            this.trT = load(1, 0);
            this.trCrossTop = load(2, 0);
            this.trTR = load(3, 0);

            this.trCL = load(0, 1);
            this.trC = load(1, 1);
            this.trCrossVer = load(2, 1);
            this.trCR = load(3, 1);

            this.trCrossLeft = load(0, 2);
            this.trCrossHor = load(1, 2);
            this.trCrossCenter = load(2, 2);
            this.trCrossRight = load(3, 2);

            this.trBL = load(0, 3);
            this.trB = load(1, 3);
            this.trCrossBotton = load(2, 3);
            this.trBR = load(3, 3);
        }

        private TextureRegion load(int i, int j) {
            return new TextureRegion(this.tx, i * this.o1, j * this.o1, this.o1, this.o1);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTablePanel(ISwtWidget<? extends Group> pParent, int pColumnCount, int pRowCount) {
        super(pParent, false);

        float[] xpos = new float[pColumnCount];
        float[] ypos = new float[pRowCount];
        float[] columnWidths = new float[pColumnCount];
        float[] rowHeights = new float[pRowCount];
        float[] renew = new float[] { 0, 0 };

        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                AResourceManager rm = pBatch.getResourceManager();
                Texture tx = rm.getTexture("borders/white-round-5.png");
                Splitter splitter = new Splitter(tx);
                Batch batch = pBatch.getBatch();
                batch.setColor(1f, 1f, 1f, .5f);
                float curY = pDims.height;
                for (int row = 0; row < rowHeights.length; row++) {
                    float rowH = rowHeights[row];
                    curY -= splitter.o1;
                    float curX = 0f;
                    for (int col = 0; col < columnWidths.length; col++) {
                        float colW = columnWidths[col];
                        batch.draw(
                                row == 0 ? (col == 0 ? splitter.trTL : splitter.trCrossTop)
                                        : (col == 0 ? splitter.trCrossLeft : splitter.trCrossCenter),
                                pDims.x + curX,
                                pDims.y + curY, splitter.o1, splitter.o1);
                        curX += splitter.o1;
                        batch.draw(splitter.trCrossHor, pDims.x + curX, pDims.y + curY, colW, splitter.o1);
                        curX += colW;
                    }
                    batch.draw(row == 0 ? splitter.trTR : splitter.trCrossRight, pDims.x + curX, pDims.y + curY,
                            splitter.o1, splitter.o1);
                    curY -= rowH;
                    curX = 0f;
                    for (int col = 0; col < columnWidths.length; col++) {
                        float colW = columnWidths[col];
                        batch.draw(splitter.trCrossVer, pDims.x + curX, pDims.y + curY, splitter.o1, rowH);
                        curX += splitter.o1;
                        batch.draw(splitter.trC, pDims.x + curX, pDims.y + curY, colW, rowH);
                        curX += colW;
                    }
                    batch.draw(splitter.trCL, pDims.x + curX, pDims.y + curY, splitter.o1, rowH);
                }
                float curX = 0f;
                curY -= splitter.o1;
                for (int col = 0; col < columnWidths.length; col++) {
                    float colW = columnWidths[col];
                    batch.draw(
                            col == 0 ? splitter.trBL : splitter.trCrossBotton,
                            pDims.x + curX,
                            pDims.y + curY, splitter.o1, splitter.o1);
                    curX += splitter.o1;
                    batch.draw(splitter.trCrossHor, pDims.x + curX, pDims.y + curY, colW, splitter.o1);
                    curX += colW;
                }
                batch.draw(splitter.trBR, pDims.x + curX, pDims.y + curY, splitter.o1, splitter.o1);
            }
        });

        this.setLayoutManager(new ASwtLayoutManager() {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height) {
                if (renew[0] == width && renew[1] == height) return;

                renew[0] = width;
                renew[1] = height;

                for (int i = 0; i < pColumnCount; i++) {
                    columnWidths[i] = 0f;
                }
                for (int i = 0; i < pRowCount; i++) {
                    rowHeights[i] = 0f;
                }
                for (ISwtWidget<?> a : pWidget.getChildren()) {
                    ASwtLayoutData ld = a.getSwtLayoutData();
                    if (ld instanceof SwtLayoutDataCellPosition) {
                        int co = ((SwtLayoutDataCellPosition) ld).getColumnNr();
                        int ro = ((SwtLayoutDataCellPosition) ld).getRowNr();
                        columnWidths[co] = Math.max(columnWidths[co], a.getWidth());
                        rowHeights[ro] = Math.max(rowHeights[ro], a.getHeight());
                    }
                }
                float curX = 0;
                for (int i = 0; i < pColumnCount; i++) {
                    curX += 5;
                    xpos[i] = curX;
                    curX += columnWidths[i];
                }
                float curY = 0;
                for (int i = 0; i < pRowCount; i++) {
                    curY += 5;
                    ypos[i] = curY;
                    curY += rowHeights[i];
                }

                for (ISwtWidget<?> a : pWidget.getChildren()) {
                    ASwtLayoutData ld = a.getSwtLayoutData();
                    if (ld instanceof SwtLayoutDataCellPosition) {
                        int co = ((SwtLayoutDataCellPosition) ld).getColumnNr();
                        int ro = ((SwtLayoutDataCellPosition) ld).getRowNr();
                        float ax = xpos[co];
                        float ay = ypos[ro];
                        float uw = columnWidths[co];
                        float uh = rowHeights[ro];
                        a.setBounds(ax, ay, uw, uh);
                    }
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
