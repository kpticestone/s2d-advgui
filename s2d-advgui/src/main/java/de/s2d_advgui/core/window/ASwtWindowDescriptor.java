package de.s2d_advgui.core.window;

import javax.annotation.Nonnull;

import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.singstar.ASingStarDescriptor;

public abstract class ASwtWindowDescriptor<PT extends ASwtStage<?, ?>, AT extends SwtWindow>
        extends ASingStarDescriptor<WindowID> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWindowDescriptor(@Nonnull WindowID pId) {
        super(pId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract AT createWindow(@Nonnull PT pContext);

    // -------------------------------------------------------------------------------------------------------------------------
}
