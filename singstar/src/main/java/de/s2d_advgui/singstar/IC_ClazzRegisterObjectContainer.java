package de.s2d_advgui.singstar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.checkerframework.checker.nullness.qual.NonNull;

import de.s2d_advgui.commons.IC_OrderSupportComparator;
import de.s2d_advgui.commons.TNull;

public final class IC_ClazzRegisterObjectContainer<T extends ASingStarDescriptorString> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private Map<String, ? extends T> map;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private List<T> ordered;

    // -------------------------------------------------------------------------------------------------------------------------
    public IC_ClazzRegisterObjectContainer(@Nonnull Map<String, ? extends T> objectsMap) {
        this.map = objectsMap;
        ArrayList<T> mom = new ArrayList<>();
        for (T x : objectsMap.values()) {
            mom.add(x);
        }
        Collections.sort(mom, IC_OrderSupportComparator.INSTANCE);
        this.ordered = TNull.checkNull(Collections.unmodifiableList(mom));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    public T get(String pId) {
        return this.map.get(pId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @NonNull
    public Collection<T> values() {
        return this.ordered;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
