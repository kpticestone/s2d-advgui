package com.s2dwt.core.window;

import com.s2dwt.core.stage.ASwtStage;
import com.s2dwt.impcomp.ASingStarDescriptor;
import com.s2dwt.impcomp.WindowID;

public abstract class ASwtWindowDescriptor<PT extends ASwtStage<?, ?>, AT extends SwtWindow> extends ASingStarDescriptor<WindowID> {
    public ASwtWindowDescriptor(WindowID pId) {
        super(pId);
    }

    public abstract AT createWindow(PT pContext);
}
