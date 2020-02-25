package com.s2dwt.core.layoutmanager;

import com.s2dwt.core.awidget.ISwtWidget;

public abstract class ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void calculate(ISwtWidget<?> pWidget, float width, float height);

    // -------------------------------------------------------------------------------------------------------------------------
}
