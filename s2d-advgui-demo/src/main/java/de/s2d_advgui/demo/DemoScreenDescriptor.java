package de.s2d_advgui.demo;

import javax.annotation.Nonnull;

import de.s2d_advgui.core.screens.SwtScreenDescriptor;

public final class DemoScreenDescriptor extends SwtScreenDescriptor<DemoStage, DemoScreen> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "demo"; //$NON-NLS-1$
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static DemoScreenDescriptor INSTANCE = new DemoScreenDescriptor();
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static DemoScreenDescriptor getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoScreenDescriptor() {
        super(ID);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public DemoScreen createScreen(DemoStage pStage) {
        return new DemoScreen(pStage);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
