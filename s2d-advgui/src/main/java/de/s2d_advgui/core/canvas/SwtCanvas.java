package de.s2d_advgui.core.canvas;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.camera.CameraHolder;
import de.s2d_advgui.core.input.ISwtMouseMoveListener;
import de.s2d_advgui.core.input.ISwtScrollListener;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.rendering.ISwtBatchSaver;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.MasterViewport;
import de.s2d_advgui.core.screens.SlaveViewport;
import de.s2d_advgui.core.utils.CalcUtils;

public abstract class SwtCanvas<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>>
        extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private ISwtScrollListener scrollListener;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private Set<ISwtMouseMoveListener> mouseMoveHandler = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private ASwtInputRegister_Keys inputHandler;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final DM drawerManager;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtCanvas(@Nonnull ISwtWidget<? extends Group> pParent, @Nonnull DM pDrawerManager) {
        super(pParent, true);
        this.drawerManager = pDrawerManager;
        this.addDrawerClipableMiddle(this::drawIt);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected void drawIt(ISwtDrawerManager<?> pDrawerManager, Vector2 pScreenCoords, Rectangle pDims) {
        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        Batch pBatch = pDrawerManager.getBatch();
        try (ISwtBatchSaver saver = this.drawerManager.batchSave()) {
            pBatch.end();
            MasterViewport vp = this.context.getViewport();
            Rectangle rDims = this.context.getStageDimensions();
            float gs = this.context.getGuiScale();
            cameraHolder.setViewport(pScreenCoords.x, (rDims.height * gs) - (pScreenCoords.y), pDims.width * gs,
                    pDims.height * gs);
            try (SlaveViewport slaveViewPort = vp.runSlaveViewport(cameraHolder)) {
                OrthographicCamera cam = cameraHolder.getCamera();
                cam.zoom = CalcUtils.approachExponential(cam.zoom, cameraHolder.getWantedZoom(),
                        Gdx.graphics.getDeltaTime());
                cam.update();
                this._drawIt(cameraHolder.getLastDims());
            }
        } finally {
            pBatch.begin();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected void _drawIt(Rectangle dimensions) {
        // DON
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
    public void coord123(Vector2 in, Vector2 out) {
        this.drawerManager.getCameraHolder().unproject(in, out);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final Vector2 decodeCoords(InputEvent pInputEvent) {
        Vector2 voo = new Vector2(pInputEvent.getStageX(), pInputEvent.getStageY());
        Vector2 rii = new Vector2();
        this.coord123(voo, rii);
        return rii;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setScrollListener(ISwtScrollListener pScrollListener) {
        this.scrollListener = pScrollListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addMouseMoveHandler(ISwtMouseMoveListener pHandler) {
        this.mouseMoveHandler.add(pHandler);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setInputHandler(ASwtInputRegister_Keys pInputHandler) {
        this.inputHandler = pInputHandler;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    boolean handleInput(WidgetGroup pWidget, InputEvent pInputEvent) {
        if (!this.isEnabled()) return false;
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
                for (ISwtMouseMoveListener a : this.mouseMoveHandler) {
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
                for (ISwtMouseMoveListener a : this.mouseMoveHandler) {
                    if (a.onMouseUp(rii.x, rii.y, pInputEvent.getButton())) {
                        return true;
                    }
                }
            }
            break;
        case touchDown:
            if (this.mouseMoveHandler != null) {
                Vector2 rii = decodeCoords(pInputEvent);
                for (ISwtMouseMoveListener a : this.mouseMoveHandler) {
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
    /**
     * convert coordinates from scene into coordinates of the swtcanvas
     * @param pX
     * @param pY
     * @return
     */
    public final Vector2 sceneCoordsToCanvasCoords(float pX, float pY) {
        Rectangle sd = this.getContext().getStageDimensions();
        CameraHolder cameraHolder = this.drawerManager.getCameraHolder();
        Rectangle ld = cameraHolder.getLastDims();
        Vector3 ooo = cameraHolder.getCamera().project(new Vector3(pX, pY, 0), ld.x, ld.y,
                ld.width, ld.height);
        Vector2 dke = this.getActor().screenToLocalCoordinates(new Vector2(ooo.x, sd.height - ooo.y));
        return dke;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
