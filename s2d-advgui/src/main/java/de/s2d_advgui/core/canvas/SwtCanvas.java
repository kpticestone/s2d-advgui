package de.s2d_advgui.core.canvas;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.input.ASwtMouseMoveListener;
import de.s2d_advgui.core.input.ISwtScrollListener;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public final class SwtCanvas<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private ISwtScrollListener scrollListener;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private Set<ASwtMouseMoveListener> mouseMoveHandler = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ICanvasRenderer<RM, DM> renderer;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private ASwtInputRegister_Keys inputHandler;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas(@Nonnull ASwtWidget<? extends Group> pParent, @Nonnull ICanvasRenderer<RM, DM> pRenderer) {
        super(pParent, true);
        this.renderer = pRenderer;
        this.renderer.canvas = this;
        this.addDrawerClipableMiddle(pRenderer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup createActor() {
        WidgetGroup back = new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                    return handleInput(back, (InputEvent) event);
                }
                return false;
            }
        });
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final Vector2 decodeCoords(InputEvent pInputEvent) {
        Vector2 voo = new Vector2(pInputEvent.getStageX(), pInputEvent.getStageY());
        Vector2 rii = new Vector2();
        this.renderer.coord123(voo, rii);
        return rii;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setScrollListener(ISwtScrollListener pScrollListener) {
        this.scrollListener = pScrollListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addMouseMoveHandler(ASwtMouseMoveListener pHandler) {
        this.mouseMoveHandler.add(pHandler);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setInputHandler(ASwtInputRegister_Keys pInputHandler) {
        this.inputHandler = pInputHandler;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    boolean handleInput(WidgetGroup pWidget, InputEvent pInputEvent) {
        switch (pInputEvent.getType()) {
            case exit:
                this.context.setScrollFocus(null);
                this.context.setKeyboardFocus(null);
                return true;
            case touchDragged:
            case mouseMoved:
                this.context.setScrollFocus(pWidget);
                this.context.setKeyboardFocus(pWidget);
                if (this.mouseMoveHandler != null) {
                    Vector2 rii = decodeCoords(pInputEvent);
                    for (ASwtMouseMoveListener a : this.mouseMoveHandler) {
                        if (a.onMouseMove(rii.x, rii.y, 0)) {
                            return true;
                        }
                    }
                }
                return false; // nachfolgende Events sollen trotzdem noch gelten.
            case scrolled:
                if (this.scrollListener != null) {
                    return this.scrollListener.onScroll(pInputEvent.getScrollAmount());
                }
                break;
            case touchUp:
                if (this.mouseMoveHandler != null) {
                    Vector2 rii = decodeCoords(pInputEvent);
                    for (ASwtMouseMoveListener a : this.mouseMoveHandler) {
                        if (a.onMouseUp(rii.x, rii.y, pInputEvent.getButton())) {
                            return true;
                        }
                    }
                }
                break;
            case touchDown:
                if (this.mouseMoveHandler != null) {
                    Vector2 rii = decodeCoords(pInputEvent);
                    for (ASwtMouseMoveListener a : this.mouseMoveHandler) {
                        if (a.onMouseDown(rii.x, rii.y, pInputEvent.getButton())) {
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
