package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import de.s2d_advgui.core.geom.collider.ICollider;

public abstract class AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    private final String title;
    private final int results;
    private final String description;

    // -------------------------------------------------------------------------------------------------------------------------
    public AColliderTest(String title, int results) {
        this(title, results, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public AColliderTest(String title, int results, String description) {
        this.title = title;
        this.results = results;
        this.description = description;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public final String getTitle() {
        return this.title;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public final int getResults() {
        return this.results;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public final String getDescription() {
        return this.description;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void createCollider(int ciX, int ciY, Collection<ICollider> cols) {
        this.createCollider((float) ciX, (float) ciY, cols);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void createCollider(float ciX, float ciY, Collection<ICollider> cols);

    // -------------------------------------------------------------------------------------------------------------------------
}
