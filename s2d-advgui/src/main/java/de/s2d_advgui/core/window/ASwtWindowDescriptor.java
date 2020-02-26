package de.s2d_advgui.core.window;

import de.s2d_advgui.singstar.ASingStarDescriptor;
import de.s2d_advgui.core.stage.ASwtStage;

public abstract class ASwtWindowDescriptor<PT extends ASwtStage<?, ?>, AT extends SwtWindow> extends ASingStarDescriptor<WindowID> {
    public ASwtWindowDescriptor(WindowID pId) {
        super(pId);
    }

    public abstract AT createWindow(PT pContext);
}
