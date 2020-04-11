package de.s2d_advgui.core.awidget;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.input.ISwtWidgetSelectable;

public abstract class ASwtWidgetSelectable<ACTOR extends Actor> extends ASwtWidgetDisableable<ACTOR>
        implements ISwtWidgetSelectable {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Consumer<Integer>> listeners = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidgetSelectable(ISwtWidget<? extends Group> pParent) {
        super(pParent, true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addListener(Consumer<Integer> pTrigger) {
        this.listeners.add(pTrigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addLeftClickListener(Trigger pTrigger) {
        this.listeners.add(t -> {
            if (t == 0) pTrigger.onTrigger();
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addRightClickListener(Trigger pTrigger) {
        this.listeners.add(t -> {
            if (t == 1) pTrigger.onTrigger();
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void callListeners(int pButtonCode) {
        if (this.isEnabled()) {
            for (Consumer<Integer> a : this.listeners) {
                a.accept(pButtonCode);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected final ACTOR createActor() {
        ACTOR back = this._createActor();
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                    InputEvent ii = (InputEvent) event;
                    switch (ii.getType()) {
                    case touchDown:
                        callListeners(ii.getButton());
                        return true;
                    default:
                        break;
                    }
                }
                return false;
            }
        });
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract ACTOR _createActor();

    // -------------------------------------------------------------------------------------------------------------------------
}
