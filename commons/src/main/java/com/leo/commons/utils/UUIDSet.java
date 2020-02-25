package com.leo.commons.utils;

import javax.annotation.Nonnull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class UUIDSet implements Iterable<UUID> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static UUIDSet EMPTY = new UUIDSet().mute();
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Set<UUID> internal;

    // -------------------------------------------------------------------------------------------------------------------------
    public UUIDSet() {
        this.internal = new HashSet<>();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private UUIDSet(Set<UUID> internal) {
        this.internal = Collections.unmodifiableSet(internal);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static UUIDSet getEmpty() {
        return EMPTY;
    }

    public static void main(String[] args) {
        UUIDSet uus = new UUIDSet();
        uus.add(new UUID("a"));
        uus.add(new UUID("b"));
        uus.add(new UUID("c"));
        uus.add(new UUID("d"));
        uus.add(new UUID("e"));
        System.err.println("uus: " + uus);
        for (int i = 0; i < 100; i++) {
            System.err.println(uus.random());
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clear() {
        this.internal.clear();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public UUIDSet mute() {
        return new UUIDSet(this.internal);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Iterator<UUID> iterator() {
        return this.internal.iterator();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean remove(@Nonnull UUID uuid) {
        return this.internal.remove(uuid);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean add(@Nonnull UUIDSupport uuid) {
        return this.internal.add(TNull.checkNull(uuid).getUID());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean add(UUID uuid) {
        return this.internal.add(uuid);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(@Nonnull UUID childUid) {
        return this.internal.contains(childUid);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int size() {
        return this.internal.size();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isEmpty() {
        return this.internal.isEmpty();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(UUIDConsumer pCallback) {
        this.internal.forEach(someUid ->
        {
            if (someUid != null) {
                pCallback.accept(someUid);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "UUIDSet" + this.internal + "";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public UUID random() {
        int mm = (int) (Math.random() * this.internal.size());
        Iterator<UUID> it = this.internal.iterator();
        int cnt = 0;
        while (it.hasNext()) {
            UUID cur = it.next();
            if (++cnt > mm) return cur;
        }
        return null;
    }

    public static UUIDSet parse(Collection<String> strings) {
        UUIDSet uuidSet = new UUIDSet();
        for (String string : strings) {
            uuidSet.add(new UUID(string));
        }
        return uuidSet;
    }
    // -------------------------------------------------------------------------------------------------------------------------
}
