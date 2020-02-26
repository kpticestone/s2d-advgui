package de.s2d_advgui.core.stage;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.commons.Trigger;

public abstract class ASwtStage_050_Disposing<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_010_QuickDraw<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Trigger> disposeListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_050_Disposing(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void dispose() {
        for (Trigger a : this.disposeListener) {
            a.onTrigger();
        }
        super.dispose();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addDisposeListener(@Nonnull Trigger pTrigger) {
        this.disposeListener.add(pTrigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
