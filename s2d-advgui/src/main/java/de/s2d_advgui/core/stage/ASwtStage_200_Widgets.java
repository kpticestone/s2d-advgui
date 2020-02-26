package de.s2d_advgui.core.stage;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public abstract class ASwtStage_200_Widgets<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_050_Disposing<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    final Map<Actor, ISwtWidget<?>> actorMappings = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_200_Widgets(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public <XT extends Actor> void registerMapping(XT pActor, ISwtWidget<XT> pWidget) {
        this.actorMappings.put(pActor, pWidget);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
