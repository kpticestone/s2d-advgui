package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import de.s2d_advgui.commons.TNull;
import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.stage.ISwtStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ASwtWidget_000_Ground<ACTOR extends Actor> implements ISwtWidget<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final Logger log = LoggerFactory.getLogger(ASwtWidget_000_Ground.class);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ISwtStage<?, ?> context;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    protected final ISwtWidget<? extends Group> parent;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ACTOR actor;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final SwtArrayList<ISwtWidget<?>> children = new SwtArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean visible = true;

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean enabled = true;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Set<IMyWidgetUpdateHandler> updateHandler = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private ISwtWidget<? extends Group> delegatedParent;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private Map<String, Object> data = null;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<InputEvent.Type, Set<ISwtInputEventCaller>> eventMappings = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private Set<ISwtChangeEventCaller> eventChangeEventCallers;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Consumer<Boolean>> enabledStateListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    final IActorCreator<ACTOR> ac;

    // -------------------------------------------------------------------------------------------------------------------------
    static ISwtWidget<? extends Group> resolveDelegatedParent(@Nonnull ISwtWidget<? extends Group> pOrgParent) {
        ISwtWidget<? extends Group> x1 = TNull.checkNull(pOrgParent).getDelegatedParent();
        if (x1 != null) return x1;
        return pOrgParent;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public ASwtWidget_000_Ground(@Nonnull ISwtStage<?, ?> pContext) {
        this.context = TNull.checkNull(pContext);
        this.parent = null;
        this.actor = (ACTOR) pContext.getRoot();
        this.ac = null;
        pContext.registerMapping(this.actor, this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_000_Ground(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        this.parent = resolveDelegatedParent(pBuilder.parent);
        this.context = TNull.checkNull(this.parent.getContext());
        this.ac = pBuilder.getActorCreator();
        this.actor = ac.createActor(new IRend123() {
            @Override
            public void doRender(Batch batch, float parentAlpha, Trigger pOrg) {
                _internalDrawWidget(batch, parentAlpha, pOrg);
            }

            @Override
            public AResourceManager getResourceManager() {
                return pBuilder.parent.getResourceManager();
            }
        });
        this.actor.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                // if (!isEnabled()) return false;
                if (event instanceof ChangeEvent) {
                    Set<ISwtChangeEventCaller> ec = ASwtWidget_000_Ground.this.eventChangeEventCallers;
                    if (ec != null) {
                        for (ISwtChangeEventCaller a : ec) {
                            // System.err.println("handle change '" + event + "' on '" + a + "'");
                            a.call((ChangeEvent) event);
                        }
                    }
                } else if (event instanceof InputEvent) {
                    InputEvent input = (InputEvent) event;
                    Type ti = input.getType();
                    Set<ISwtInputEventCaller> li = ASwtWidget_000_Ground.this.eventMappings.get(ti);
                    if (li != null) {
                        for (ISwtInputEventCaller c : li) {
                            if (c.call(input)) {
                                if (ti == Type.touchDown && input.getTouchFocus()) {
                                    event.getStage().addTouchFocus(this,
                                            input.getListenerActor(), input.getTarget(),
                                            input.getPointer(), input.getButton());// 65 66
                                }
                                // System.err.println("handle input '" + event + "' on '" + c + "'");
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });
        this.context.registerMapping(this.actor, this);
        Group paractor = this.parent.getActor();
        if (this.getIgnoreOnCalcPos() && paractor instanceof Table) {
            ((Table) paractor).add(this.actor).row();
        } else {
            paractor.addActor(this.actor);
        }

        ((ASwtWidget_000_Ground<?>) this.parent).children.add(this);
        this.context.onWidgetCreation(this);

        this.parent.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    abstract void _internalDrawWidget(Batch batch, float parentAlpha, Trigger pOrg);

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void registerEventHandler(InputEvent.Type pInputEventType, ISwtInputEventCaller pCaller) {
        Set<ISwtInputEventCaller> as = this.eventMappings.computeIfAbsent(pInputEventType,
                s -> new LinkedHashSet<>());
        as.add(pCaller);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void registerChangeEventHandler(ISwtChangeEventCaller pCaller) {
        Set<ISwtChangeEventCaller> em = this.eventChangeEventCallers;
        if (em == null) {
            em = this.eventChangeEventCallers = new LinkedHashSet<>();
        }
        em.add(pCaller);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final AResourceManager getResourceManager() {
        return this.context.getResourceManager();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final ATheme getTheme() {
        return this.context.getResourceManager().getTheme();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected final void setDelegatedParent(ISwtWidget<? extends Group> pWidget) {
        this.delegatedParent = pWidget;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final ISwtWidget<? extends Group> getDelegatedParent() {
        return this.delegatedParent;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final ISwtStage<?, ?> getContext() {
        return this.context;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final ACTOR getActor() {
        return this.actor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final Collection<ISwtWidget<?>> getChildren() {
        return this.children;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void bringToFront(ISwtWidget<?> someWidget) {
        if (this.children.bringToFront(someWidget)) {
            int cnt = 0;
            for (ISwtWidget<?> a : this.children) {
                a.getActor().setZIndex(cnt++);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setVisible(boolean b) {
        if (this.visible != b) {
            this.context.bumbRevision();
            this.visible = b;
            this.actor.setVisible(b);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isVisible() {
        if (!this.visible) return false;
        if (this.parent == null) return true;
        return this.parent.isVisible();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addEnabledStateListener(Consumer<Boolean> pListener) {
        this.enabledStateListener.add(pListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean setEnabled(boolean b) {
        if (this.enabled != b) {
            this.enabled = b;
            this.context.bumbRevision();
            for (Consumer<Boolean> a : this.enabledStateListener) {
                a.accept(b);
            }
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isEnabled() {
        if (!this.enabled) return false;
        if (this.parent == null) return true;
        return this.parent.isEnabled();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addUpdateHandler(IMyWidgetUpdateHandler pUpdateHandler) {
        this.updateHandler.add(pUpdateHandler);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void act(float delta) {
        this.recalc();
        for (IMyWidgetUpdateHandler a : this.updateHandler) {
            try {
                a.act(delta);
            } catch (Exception e) {
                log.error("unable to draw '{}'", a, e); //$NON-NLS-1$
            }
        }
        for (ISwtWidget<?> a : this.children) {
            try {
                a.act(delta);
            } catch (Exception e) {
                log.error("unable to draw '{}'", a, e); //$NON-NLS-1$
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    abstract boolean recalc();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    @Override
    public final ISwtWidget<? extends Group> getParent() {
        return this.parent;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void setData(String key, Object data) {
        Map<String, Object> d = this.data;
        if (d == null) {
            d = this.data = new HashMap<>();
        }
        d.put(key, data);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final Object getData(String key) {
        Map<String, Object> d = this.data;
        if (d == null) return null;
        return d.get(key);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final <T> T computeDataIfNotExists(String key, Supplier<T> any) {
        Map<String, Object> d = this.data;
        if (d == null) {
            d = this.data = new HashMap<>();
        }
        Object back = d.get(key);
        if (back == null) {
            back = any.get();
            d.put(key, back);
        }
        return (T) back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    @Override
    public final <T extends ISwtWidget<?>> T getNextParent(Class<T> pClz) {
        if (pClz.isAssignableFrom(this.getClass())) {
            return pClz.cast(this);
        }
        if (this.parent != null) {
            return this.parent.getNextParent(pClz);
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
