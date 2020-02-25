package com.s2dwt.core.resourcemanager;

import com.s2dwt.impcomp.Trigger;

public final class ResourceLoader {
    // -------------------------------------------------------------------------------------------------------------------------
    private final String title;
    private final Trigger trigger;

    // -------------------------------------------------------------------------------------------------------------------------
    public ResourceLoader(String pTitle, Trigger pTrigger) {
        this.title = pTitle;
        this.trigger = pTrigger;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getTitle() {
        return this.title;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void doLoad() {
        this.trigger.onTrigger();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}