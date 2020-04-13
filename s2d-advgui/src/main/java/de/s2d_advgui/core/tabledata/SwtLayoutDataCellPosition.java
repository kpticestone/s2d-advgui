package de.s2d_advgui.core.tabledata;

import de.s2d_advgui.core.layoutmanager.ASwtLayoutData;

public class SwtLayoutDataCellPosition extends ASwtLayoutData {
    // -------------------------------------------------------------------------------------------------------------------------
    private final int columnNr;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int rowNr;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutDataCellPosition(int pColumnNr, int pRowNr) {
        this.columnNr = pColumnNr;
        this.rowNr = pRowNr;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getColumnNr() {
        return this.columnNr;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getRowNr() {
        return this.rowNr;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
