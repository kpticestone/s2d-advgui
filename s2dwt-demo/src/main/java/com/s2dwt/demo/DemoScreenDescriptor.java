package com.s2dwt.demo;

import javax.annotation.Nonnull;

import com.s2dwt.core.screens.SwtScreenDescriptor;

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
