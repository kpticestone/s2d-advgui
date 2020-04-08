package de.s2d_advgui.core.tabledata;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ASwtLayoutData;
import de.s2d_advgui.core.awidget.IInternalWidgetDrawer;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.core.rendering.Splitter;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public final class TableDataManager {
    // -------------------------------------------------------------------------------------------------------------------------
    private final int pRowCount;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int pColumnCount;

    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtTableData tableData;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Splitter splitter;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int o1;

    // -------------------------------------------------------------------------------------------------------------------------
    private final ESwtTableMode mode;

    // -------------------------------------------------------------------------------------------------------------------------
    private ISwtCustomCalc customCalc;

    // -------------------------------------------------------------------------------------------------------------------------
    public TableDataManager(AResourceManager pRm, String pTextureResource, ESwtTableMode pMode, int pColumnCount,
            int pRowCount) {
        this.mode = pMode;
        this.tableData = new SwtTableData(pColumnCount, pRowCount);
        this.pRowCount = pRowCount;
        this.pColumnCount = pColumnCount;
        Texture tx = pRm.getTexture(pTextureResource);
        this.splitter = new Splitter(tx);
        this.o1 = this.splitter.o1;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public void setCustomCalc(ISwtCustomCalc customCalc) {
        this.customCalc = customCalc;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTableData getTableData() {
        return this.tableData;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void _drawTable(SwtDrawer_Batch<?> pBatchDrawer, Rectangle pDims) {
        Batch batch = pBatchDrawer.getBatch();
//        batch.setColor(new Color(255,0,0,64));
        float curY = pDims.height;
        for (int row = 0; row < this.tableData.rowHeights.length; row++) {
            float rowH = this.tableData.rowHeights[row];
            curY -= this.splitter.o1;
            float curX = 0f;
            for (int col = 0; col < this.tableData.columnWidths.length; col++) {
                float colW = this.tableData.columnWidths[col];
                batch.draw(
                        row == 0 ? (col == 0 ? this.splitter.trTL : this.splitter.trCrossTop)
                                : (col == 0 ? this.splitter.trCrossLeft : this.splitter.trCrossCenter),
                        pDims.x + curX,
                        pDims.y + curY, this.splitter.o1, this.splitter.o1);
                curX += this.splitter.o1;
                batch.draw(this.splitter.trCrossHor, pDims.x + curX, pDims.y + curY, colW, this.splitter.o1);
                curX += colW;
            }
            batch.draw(row == 0 ? this.splitter.trTR : this.splitter.trCrossRight, pDims.x + curX, pDims.y + curY,
                    this.splitter.o1, this.splitter.o1);
            curY -= rowH;
            curX = 0f;
            for (int col = 0; col < this.tableData.columnWidths.length; col++) {
                float colW = this.tableData.columnWidths[col];
                batch.draw(this.splitter.trCrossVer, pDims.x + curX, pDims.y + curY, this.splitter.o1, rowH);
                curX += this.splitter.o1;
                batch.draw(this.splitter.trC, pDims.x + curX, pDims.y + curY, colW, rowH);
                curX += colW;
            }
            batch.draw(this.splitter.trCL, pDims.x + curX, pDims.y + curY, this.splitter.o1, rowH);
        }
        float curX = 0f;
        curY -= this.splitter.o1;
        for (int col = 0; col < this.tableData.columnWidths.length; col++) {
            float colW = this.tableData.columnWidths[col];
            batch.draw(
                    col == 0 ? this.splitter.trBL : this.splitter.trCrossBotton,
                    pDims.x + curX,
                    pDims.y + curY, this.splitter.o1, this.splitter.o1);
            curX += this.splitter.o1;
            batch.draw(this.splitter.trCrossHor, pDims.x + curX, pDims.y + curY, colW, this.splitter.o1);
            curX += colW;
        }
        batch.draw(this.splitter.trBR, pDims.x + curX, pDims.y + curY, this.splitter.o1, this.splitter.o1);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void _calculate(ISwtWidget<?> pWidget, float width, float height) {
        if (TOldCompatibilityCode.FALSE)
            if (this.tableData.renews[0] == width && this.tableData.renews[1] == height) return;
        this.tableData.renews[0] = width;
        this.tableData.renews[1] = height;

        for (int i = 0; i < this.pColumnCount; i++) {
            this.tableData.columnWidths[i] = 0f;
        }
        for (int i = 0; i < this.pRowCount; i++) {
            this.tableData.rowHeights[i] = 0f;
        }
        if (this.mode == ESwtTableMode.FULLFILL) {
            float restHeight = height - (this.pRowCount + 1) * this.o1;
            float restWidth = width - (this.pColumnCount + 1) * this.o1;
            float oneHeight = restHeight / this.pRowCount;
            float oneWidth = restWidth / this.pColumnCount;
            for (ISwtWidget<?> a : pWidget.getChildren()) {
                ASwtLayoutData ld = a.getSwtLayoutData();
                if (ld instanceof SwtLayoutDataCellPosition) {
                    a.setSize(oneWidth, oneHeight);
                }
            }
        }
        else
            if(this.mode == ESwtTableMode.CUSTOM) {
                this.customCalc.calculate(pWidget, width, height, this.o1);
            }
        for (ISwtWidget<?> a : pWidget.getChildren()) {
            ASwtLayoutData ld = a.getSwtLayoutData();
            if (ld instanceof SwtLayoutDataCellPosition) {
                int co = ((SwtLayoutDataCellPosition) ld).getColumnNr();
                int ro = ((SwtLayoutDataCellPosition) ld).getRowNr();
                this.tableData.columnWidths[co] = Math.max(this.tableData.columnWidths[co], a.getWidth());
                this.tableData.rowHeights[ro] = Math.max(this.tableData.rowHeights[ro], a.getHeight());
            }
        }
        float curX = 0;
        for (int i = 0; i < this.pColumnCount; i++) {
            curX += this.o1;
            this.tableData.xpos[i] = curX;
            curX += this.tableData.columnWidths[i];
        }
        float curY = 0;
        for (int i = 0; i < this.pRowCount; i++) {
            curY += this.o1;
            this.tableData.ypos[i] = curY;
            curY += this.tableData.rowHeights[i];
        }

        for (ISwtWidget<?> a : pWidget.getChildren()) {
            ASwtLayoutData ld = a.getSwtLayoutData();
            if (ld instanceof SwtLayoutDataCellPosition) {
                int co = ((SwtLayoutDataCellPosition) ld).getColumnNr();
                int ro = ((SwtLayoutDataCellPosition) ld).getRowNr();
                float ax = this.tableData.xpos[co];
                float ay = this.tableData.ypos[ro];
                float uw = this.tableData.columnWidths[co];
                float uh = this.tableData.rowHeights[ro];
                a.setBounds(ax, ay, uw, uh);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtLayoutManager getLayoutManager() {
        return new ASwtLayoutManager() {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height) {
                _calculate(pWidget, width, height);
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public IInternalWidgetDrawer getBatchDrawer(Color pColor) {
        return new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                pBatch.setColor(pColor);
                _drawTable(pBatch, pDims);
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
}