package de.s2d_advgui.core.awidget;

import java.util.ArrayList;

public class SwtArrayList<E> extends ArrayList<E> {
    // -------------------------------------------------------------------------------------------------------------------------
    public boolean bringToEnd(E pEntry) {
        int wason = this.indexOf(pEntry);
        if (wason > 0) {
            this.remove(wason);
            this.add(0, pEntry);
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean bringToFront(E pEntry) {
        int wason = this.indexOf(pEntry);
        if (wason > 0) {
            this.remove(wason);
            this.add(pEntry);
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
