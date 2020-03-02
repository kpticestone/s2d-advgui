package de.s2d_advgui.core.screens;

import javax.annotation.Nonnull;

import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

public final class SphScreenDescriptor_Loading<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends SwtScreenDescriptor<ASwtStage<RM, DM>, SphScreen_Loading<RM, DM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "Loading";

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SphScreenDescriptor_Loading INSTANCE = new SphScreenDescriptor_Loading<>();

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
