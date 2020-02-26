package de.s2d_advgui.core.awidget;

import javax.annotation.Nullable;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_900_LayoutManager<ACTOR extends Actor> extends ASwtWidget_300_Traverse<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    protected ASwtLayoutManager layoutmanager = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_900_LayoutManager(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_900_LayoutManager(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void setLayoutManager(ASwtLayoutManager pManager) {
        ISwtWidget<? extends Group> dp = this.getDelegatedParent();
        if (dp != null) {
            dp.setLayoutManager(pManager);
        } else {
            this.layoutmanager = pManager;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
