package de.s2d_advgui.core.awidget;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_300_Traverse<ACTOR extends Actor> extends ASwtWidget_200_Coordinates<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    protected final boolean focusable;

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean hovered = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_300_Traverse(ISwtStage<?, ?> pContext) {
        super(pContext);
        this.focusable = false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isHovered() {
        return this.hovered;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_300_Traverse(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(pParent, focusable);
        this.focusable = focusable;
        if (focusable) {
            this.actor.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    if (isEnabled()) {
                        if (event instanceof InputEvent) {
                            InputEvent ii = (InputEvent) event;
                            switch (ii.getType()) {
                            case enter:
                                ASwtWidget_300_Traverse.this.hovered = true;
                                return true;
                            case exit:
                                ASwtWidget_300_Traverse.this.hovered = false;
                                return true;
                            default:
                                break;
                            }
                        }
                    }
                    return false;
                }
            });
            this.actor.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (isEnabled()) {
                        if (button == 0) {
                            focus();
                        }
                    }
                    return false;
                }
            });
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateTabOrderList(List<ISwtWidget<?>> orderedList) {
        if (this.isVisible()) {
            if (this.isFocusable()) {
                orderedList.add(this);
            }
            for (ISwtWidget<?> child : this.children) {
                child.calculateTabOrderList(orderedList);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean isFocusable() {
        return this.focusable && this.enabled && this.visible;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean focus() {
        return this.context.setKeyboardFocus(this.actor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean isFocused() {
        return this.context.getKeyboardFocus() == this.actor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
