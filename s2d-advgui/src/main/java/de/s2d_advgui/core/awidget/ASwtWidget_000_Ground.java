package de.s2d_advgui.core.awidget;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.s2d_advgui.commons.TNull;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_000_Ground<ACTOR extends Actor> implements ISwtWidget<ACTOR> {
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
        pContext.registerMapping(this.actor, this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_000_Ground(@Nonnull ISwtWidget<? extends Group> pOrgParent, boolean focusable) {
        this.parent = resolveDelegatedParent(pOrgParent);
        this.context = TNull.checkNull(this.parent.getContext());
        this.actor = this.createActor();
        this.context.registerMapping(this.actor, this);
        Group paractor = this.parent.getActor();
        if (this.getIgnoreOnCalcPos() && paractor instanceof Table) {
            ((Table) paractor).add(this.actor).row();
        } else {
            paractor.addActor(this.actor);
        }

        (((ASwtWidget_000_Ground<?>) this.parent)).children.add(this);
        context.onWidgetCreation(this);

        this.parent.calcPositions();
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
    protected final void setDelegatedParent(ISwtWidget<? extends Group> pa) {
        this.delegatedParent = pa;
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
    protected abstract ACTOR createActor();

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
    private final Set<Consumer<Boolean>> enabledStateListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addEnabledStateListener(Consumer<Boolean> pListener) {
        this.enabledStateListener.add(pListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean setEnabled(boolean b) {
        if (this.enabled != b) {
            this.enabled = b;
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
                log.error("unable to draw '{}'", a, e);
            }
        }
        for (ISwtWidget<?> a : this.children) {
            try {
                a.act(delta);
            } catch (Exception e) {
                log.error("unable to draw '{}'", a, e);
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
