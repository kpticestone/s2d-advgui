package de.s2d_advgui.core.resourcemanager;

import de.s2d_advgui.commons.Trigger;

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