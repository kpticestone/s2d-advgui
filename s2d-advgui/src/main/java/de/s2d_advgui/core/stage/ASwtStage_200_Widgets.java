package de.s2d_advgui.core.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.badlogic.gdx.scenes.scene2d.Actor;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public abstract class ASwtStage_200_Widgets<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>>
        extends ASwtStage_050_Disposing<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    final Map<Actor, ISwtWidget<?>> actorMappings = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    final List<Consumer<ISwtWidget<?>>> widgetCreationListeners = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_200_Widgets(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public <XT extends Actor> void registerMapping(XT pActor, ISwtWidget<XT> pWidget) {
        this.actorMappings.put(pActor, pWidget);
        this.bumbRevision();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addWidgetCreationListener(Consumer<ISwtWidget<?>> l) {
        this.widgetCreationListeners.add(l);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onWidgetCreation(ISwtWidget<?> widget) {
        this.widgetCreationListeners.forEach(c -> c.accept(widget));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @Nullable
    public ISwtWidget<?> getRegisteredActorMapping(Actor actor) {
        return this.actorMappings.get(actor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void unregisterMapping(Actor actor) {
        this.actorMappings.remove(actor);
        this.bumbRevision();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
