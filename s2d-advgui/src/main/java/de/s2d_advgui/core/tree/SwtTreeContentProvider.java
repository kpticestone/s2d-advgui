package de.s2d_advgui.core.tree;

import java.util.List;

public interface SwtTreeContentProvider<AW> {
    // -------------------------------------------------------------------------------------------------------------------------
    String getLabel(AW item);

    // -------------------------------------------------------------------------------------------------------------------------
    default String getIcon(AW item) {
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    List<AW> getChildren(AW pItem);

    // -------------------------------------------------------------------------------------------------------------------------
}