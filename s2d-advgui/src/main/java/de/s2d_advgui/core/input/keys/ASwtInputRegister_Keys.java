package de.s2d_advgui.core.input.keys;

import java.util.ArrayList;
import java.util.List;

public class ASwtInputRegister_Keys {
    // -------------------------------------------------------------------------------------------------------------------------
    private final List<SwtInputHolder_Keys> actions = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtInputRegister_Keys() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtInputHolder_Keys bind(int... keyCodes) {
        SwtInputHolder_Keys back = new SwtInputHolder_Keys(keyCodes);
        this.actions.add(back);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtInputHolder_Keys buttons(int... buttons) {
        SwtInputHolder_Keys back = new SwtInputHolder_Keys(new int[0]);
        back.buttons(buttons);
        this.actions.add(back);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final List<SwtInputHolder_Keys> getActions() {
        return this.actions;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
