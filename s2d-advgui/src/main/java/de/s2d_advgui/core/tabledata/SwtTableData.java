package de.s2d_advgui.core.tabledata;

public class SwtTableData {
    // -------------------------------------------------------------------------------------------------------------------------
    public final float[] xpos;
    public final float[] ypos;
    public final float[] columnWidths;
    public final float[] rowHeights;
    public final float[] renews;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTableData(int pColumnCount, int pRowCount) {
        this.xpos = new float[pColumnCount];
        this.ypos = new float[pRowCount];
        this.columnWidths = new float[pColumnCount];
        this.rowHeights = new float[pRowCount];
        this.renews = new float[] { 0, 0 };
    }

    // -------------------------------------------------------------------------------------------------------------------------
}