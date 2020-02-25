package com.s2dwt.core.stage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.awidget.ISwtWidget;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;

public abstract class ASwtStage_300_Traverse<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_200_Widgets<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final List<ISwtWidget<?>> orderedList = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_300_Traverse(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
        this.addUpdateHandler(delta -> {
            calculateTabOrderList();
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final void calculateTabOrderList() {
        this.orderedList.clear();
        this.currentScreen.calculateTabOrderList(this.orderedList);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean traverseTabNext() {
        Actor actor = this.getKeyboardFocus();
        ISwtWidget<?> widget = this.actorMappings.get(actor);
        int idx = this.orderedList.indexOf(widget);
        int nidx = idx + 1 < this.orderedList.size() ? idx + 1 : 0;
        ISwtWidget<?> jd = this.orderedList.get(nidx);
        jd.focus();
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean traverseTabPrev() {
        Actor actor = this.getKeyboardFocus();
        ISwtWidget<?> widget = this.actorMappings.get(actor);
        int idx = this.orderedList.indexOf(widget);
        ISwtWidget<?> jd = this.orderedList.get(idx - 1 >= 0 ? idx - 1 : this.orderedList.size() - 1);
        jd.focus();
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Actor getKeyboardFocus() {
        return super.getKeyboardFocus();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean setKeyboardFocus(@Nullable Actor actor) {
        return super.setKeyboardFocus(actor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
