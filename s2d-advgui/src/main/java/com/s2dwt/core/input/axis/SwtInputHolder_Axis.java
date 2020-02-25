package com.s2dwt.core.input.axis;

public final class SwtInputHolder_Axis {
    // -------------------------------------------------------------------------------------------------------------------------
    public final int horAxisCode;
    public final int verAxisCode;
    public ISwtActionRunner_Axis runner;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtInputHolder_Axis(int horAxisCode, int verAxisCode) {
        this.horAxisCode = horAxisCode;
        this.verAxisCode = verAxisCode;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void target(ISwtActionRunner_Axis pRunner) {
        this.runner = pRunner;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
