package com.s2dwt.core.stage;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.impcomp.Trigger;

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
