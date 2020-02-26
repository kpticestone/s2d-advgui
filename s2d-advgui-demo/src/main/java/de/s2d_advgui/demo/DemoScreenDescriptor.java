package de.s2d_advgui.demo;

import javax.annotation.Nonnull;

import de.s2d_advgui.core.screens.SwtScreenDescriptor;

public class DemoScreenDescriptor extends SwtScreenDescriptor<DemoStage, DemoScreen> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static String ID = "demo"; //$NON-NLS-1$

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
