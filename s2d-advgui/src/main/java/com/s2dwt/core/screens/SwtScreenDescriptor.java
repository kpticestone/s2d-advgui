package com.s2dwt.core.screens;

import javax.annotation.Nonnull;

import com.s2dwt.core.stage.ASwtStage;
import com.s2dwt.impcomp.ISingStarAutowire;
import com.s2dwt.impcomp.ISingStarDescriptor;

public abstract class SwtScreenDescriptor<PT extends ASwtStage<?, ?>, PU extends SwtScreen<?, ?>>
        implements ISingStarAutowire, ISingStarDescriptor<String> {
    private String id;

    public SwtScreenDescriptor(@Nonnull String id) {
        this.id = id;
    }

    public abstract PU createScreen(@Nonnull PT pStage) throws Exception;

    @Override
    public String getID() {
        return id;
    }
}
