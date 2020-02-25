package com.s2dwt.core.screens;

import javax.annotation.Nonnull;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

public final class SphScreenDescriptor_Loading<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends SwtScreenDescriptor<ASwtStage<RM, DM>, SphScreen_Loading<RM, DM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "Loading";

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SphScreenDescriptor_Loading INSTANCE = new SphScreenDescriptor_Loading();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SphScreenDescriptor_Loading getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SphScreenDescriptor_Loading() {
        super(ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public SphScreen_Loading<RM, DM> createScreen(ASwtStage<RM, DM> pStage) {
        return new SphScreen_Loading<>(pStage);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
