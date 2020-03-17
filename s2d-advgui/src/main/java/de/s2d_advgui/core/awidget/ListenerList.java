package de.s2d_advgui.core.awidget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

public class ListenerList<E> implements Collection<E> {
    // -------------------------------------------------------------------------------------------------------------------------
    private List<E> internal = new ArrayList<>();
    private Consumer<E> onAddConsumer;

    // -------------------------------------------------------------------------------------------------------------------------
    public ListenerList(Consumer<E> onAddConsumer) {
        this.onAddConsumer = onAddConsumer;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public int size() {
        return this.internal.size();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isEmpty() {
        return this.internal.isEmpty();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(Object o) {
        return this.internal.contains(o);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return this.internal.iterator();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public Object[] toArray() {
        return this.internal.toArray();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return this.internal.toArray(a);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return this.internal.toArray(generator);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean add(E e) {
        this.onAddConsumer.accept(e);
        return this.internal.add(e);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean remove(Object o) {
        return this.internal.remove(o);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return this.internal.containsAll(c);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        return this.internal.addAll(c);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return this.internal.removeAll(c);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return this.internal.removeIf(filter);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return this.internal.retainAll(c);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void clear() {
        this.internal.clear();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean bringToEnd(E pEntry) {
        int wason = this.internal.indexOf(pEntry);
        if (wason > 0) {
            this.internal.remove(wason);
            this.internal.add(0, pEntry);
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean bringToFront(E pEntry) {
        int wason = this.internal.indexOf(pEntry);
        if (wason > 0) {
            this.internal.remove(wason);
            this.internal.add(pEntry);
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
