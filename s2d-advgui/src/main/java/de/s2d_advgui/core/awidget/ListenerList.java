package de.s2d_advgui.core.awidget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

public class ListenerList<E> implements Collection<E> {
    private Collection<E> internal = new ArrayList<>();
    private Consumer<E> onAddConsumer;

    public ListenerList(Consumer<E> onAddConsumer) {
        this.onAddConsumer = onAddConsumer;
    }

    @Override
    public int size() {return internal.size();}

    @Override
    public boolean isEmpty() {return internal.isEmpty();}

    @Override
    public boolean contains(Object o) {return internal.contains(o);}

    @Nonnull
    @Override
    public Iterator<E> iterator() {return internal.iterator();}

    @Nonnull
    @Override
    public Object[] toArray() {return internal.toArray();}

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {return internal.toArray(a);}

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {return internal.toArray(generator);}

    @Override
    public boolean add(E e) {
        onAddConsumer.accept(e);
        return internal.add(e);}

    @Override
    public boolean remove(Object o) {return internal.remove(o);}

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {return internal.containsAll(c);}

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {return internal.addAll(c);}

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {return internal.removeAll(c);}

    @Override
    public boolean removeIf(Predicate<? super E> filter) {return internal.removeIf(filter);}

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {return internal.retainAll(c);}

    @Override
    public void clear() {
        internal.clear();
    }
}
