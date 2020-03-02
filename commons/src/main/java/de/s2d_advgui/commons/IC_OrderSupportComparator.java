package de.s2d_advgui.commons;

import java.util.Comparator;

import javax.annotation.Nonnull;

public final class IC_OrderSupportComparator implements Comparator<Object> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static IC_OrderSupportComparator INSTANCE = new IC_OrderSupportComparator();

    // -------------------------------------------------------------------------------------------------------------------------
    private IC_OrderSupportComparator() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("all")
    public int compare(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1 == o2) return 0;

        if (o1 instanceof IC_OrderSupport && o2 instanceof IC_OrderSupport) {
            String s1 = ((IC_OrderSupport) o1).getOrderId();
            String s2 = ((IC_OrderSupport) o2).getOrderId();
            if (s1 == null && s2 == null) return 0;
            if (s1 == null) return -1;
            if (s2 == null) return 2;
            if (s1 == s2) return 0;
            return s1.compareTo(s2);
        }
        return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
