package de.s2d_advgui.core.screens;

import javax.annotation.Nonnull;

import de.s2d_advgui.singstar.ISingStarAutowire;
import de.s2d_advgui.singstar.ISingStarDescriptor;
import de.s2d_advgui.core.stage.ASwtStage;

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
