package com.s2dwt.core.screens;

import com.leo.commons.singstar.ISingStarAutowire;
import com.leo.commons.singstar.ISingStarDescriptor;
import com.s2dwt.core.stage.ASwtStage;

import javax.annotation.Nonnull;

public abstract class SwtScreenDescriptor<PT extends ASwtStage<?, ?>, PU extends SwtScreen<?, ?>>
        implements ISingStarAutowire, ISingStarDescriptor<String> {
    private String id;

    public SwtScreenDescriptor(@Nonnull String id) {
        this.id = id;
    }

    public abstract PU createScreen(@Nonnull PT pStage);

    @Override
    public String getID() {
        return id;
    }
}
