package de.s2d_advgui.singstar;

import java.util.Optional;

public interface ISingStarCollection<T> extends Iterable<T> {
    int size();
    Optional<T> get(Object id);
}
