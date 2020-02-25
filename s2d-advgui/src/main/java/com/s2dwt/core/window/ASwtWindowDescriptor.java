package com.s2dwt.core.window;

import com.leo.commons.singstar.ASingStarDescriptor;
import com.s2dwt.core.stage.ASwtStage;

public abstract class ASwtWindowDescriptor<PT extends ASwtStage<?, ?>, AT extends SwtWindow> extends ASingStarDescriptor<WindowID> {
    public ASwtWindowDescriptor(WindowID pId) {
        super(pId);
    }

    public abstract AT createWindow(PT pContext);
}
