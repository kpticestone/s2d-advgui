package de.s2d_advgui.core.dnd;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

import de.s2d_advgui.core.awidget.ISwtWidget;

public final class DragAndDropHelper implements IDragAndDropHelper {
    // -------------------------------------------------------------------------------------------------------------------------
    private final IDragAndDropHandler dndHandler;

    // -------------------------------------------------------------------------------------------------------------------------
    boolean inDrag = false;
    float dragX;
    float dragY;

    // -------------------------------------------------------------------------------------------------------------------------
    public final static IDragAndDropHelper attach(IDragAndDropHandler pDndHandler, ISwtWidget<?> pWidget) {
        return new DragAndDropHelper(pDndHandler, pWidget);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private DragAndDropHelper(IDragAndDropHandler pDndHandler, ISwtWidget<?> pWidget) {
        this.dndHandler = pDndHandler;
        pWidget.registerEventHandler(InputEvent.Type.touchDown, this::doDragStart);
        pWidget.registerEventHandler(InputEvent.Type.touchDragged, this::doDrag);
        pWidget.registerEventHandler(InputEvent.Type.touchUp, this::doDragStop);
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isInDrag() {
        return this.inDrag;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean doDragStart(InputEvent event) {
        this.inDrag = true;
        this.dragX = event.getStageX();
        this.dragY = event.getStageY();
        this.dndHandler.onDragStart();
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean doDrag(InputEvent event) {
        if (this.inDrag) {
            float lx = event.getStageX() - this.dragX;
            float ly = event.getStageY() - this.dragY;
            this.dndHandler.onDrag(lx, ly);
            this.dragX = event.getStageX();
            this.dragY = event.getStageY();
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean doDragStop(InputEvent event) {
        this.inDrag = false;
        this.dndHandler.onDragStop();
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}