package com.s2dwt.core.input.axis;

import java.util.ArrayList;
import java.util.List;

public class ASwtInputRegister_Axis {
    // -------------------------------------------------------------------------------------------------------------------------
    private final List<SwtInputHolder_Axis> actions = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtInputRegister_Axis() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtInputHolder_Axis bind(int horAxisCode, int verAxisCode) {
        SwtInputHolder_Axis back = new SwtInputHolder_Axis(horAxisCode, verAxisCode);
        this.actions.add(back);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final List<SwtInputHolder_Axis> getActions() {
        return this.actions;
    }

    // -------------------------------------------------------------------------------------------------------------------------

}
