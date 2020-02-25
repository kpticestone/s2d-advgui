package com.leo.commons.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class UUIDMap<DT> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<UUID, DT> internal = new ConcurrentHashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public void put(@Nonnull UUID pUid, @Nonnull DT pVal) {
        this.internal.put(TNull.checkNull(pUid), TNull.checkNull(pVal));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void put(@Nonnull UUIDSupport pUid, @Nonnull DT pVal) {
        this.internal.put(TNull.checkNull(pUid.getUID()), TNull.checkNull(pVal));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public DT get(@Nonnull UUID pUid) {
        return this.internal.get(TNull.checkNull(pUid));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public DT get(@Nonnull UUIDSupport pUid) {
        return this.internal.get(TNull.checkNull(pUid.getUID()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(@Nonnull BiConsumerWithException<UUID, DT> asd) {
        for (Entry<UUID, DT> en : this.internal.entrySet()) {
            asd.accept(en.getKey(), en.getValue());
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public DT computeIfAbsent(@Nonnull UUID pUid, @Nonnull Function<UUID, DT> mappingFunction) {
        return this.internal.computeIfAbsent(TNull.checkNull(pUid), mappingFunction);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public DT computeIfAbsent(@Nonnull UUIDSupport pUid, @Nonnull Function<UUID, DT> mappingFunction) {
        return this.internal.computeIfAbsent(TNull.checkNull(pUid.getUID()), mappingFunction);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @SuppressWarnings("null")
    public Collection<DT> values() {
        return this.internal.values();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean containsKey(@Nullable UUID pUid) {
        if (pUid == null) return false;
        return this.internal.containsKey(pUid);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean containsKey(@Nullable UUIDSupport pUid) {
        if (pUid == null) return false;
        return this.internal.containsKey(pUid.getUID());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clear() {
        this.internal.clear();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    public DT remove(@Nullable UUID pUid) {
        if (pUid == null) return null;
        return this.internal.remove(pUid);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    public DT remove(@Nullable UUIDSupport pUid) {
        if (pUid == null) return null;
        return this.internal.remove(pUid.getUID());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Set<Entry<UUID, DT>> entrySet() {
        return this.internal.entrySet();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "UUIDMAP" + this.internal; //$NON-NLS-1$
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
