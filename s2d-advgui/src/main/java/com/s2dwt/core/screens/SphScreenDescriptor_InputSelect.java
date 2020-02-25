package com.s2dwt.core.screens;

import javax.annotation.Nonnull;

import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

public class SphScreenDescriptor_InputSelect<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends SwtScreenDescriptor<ASwtStage<RM, DM>, SphScreen_InputSelect<RM, DM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "InputSelect";

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SphScreenDescriptor_InputSelect INSTANCE = new SphScreenDescriptor_InputSelect();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SphScreenDescriptor_InputSelect getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SphScreenDescriptor_InputSelect() {
        super(ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public SphScreen_InputSelect<RM, DM> createScreen(ASwtStage<RM, DM> pStage) {
        return new SphScreen_InputSelect<>(pStage);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
