package de.s2d_advgui.core.geom.collider;

import java.util.HashMap;
import java.util.Map;

public final class IntersectionContext {
    // -------------------------------------------------------------------------------------------------------------------------
    public final static IntersectionContext OVERLAPCONTEXT = new IntersectionContext(false, new IntersectionListener() {
        @Override
        public void intersects(IntersectionContext pContext, float x, float y) {
            // DON
        }
    });

    // -------------------------------------------------------------------------------------------------------------------------
    private final boolean searchAll;
    private boolean foundOne = false;
    private IntersectionListener listener;
    private ICollider colliderA;
    private ICollider colliderB;
    private IColliderItem colliderItemA;
    private IColliderItem colliderItemB;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<String, Object> datas = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public IntersectionContext(boolean searchAll, IntersectionListener pListener) {
        this.searchAll = searchAll;
        this.listener = pListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean send(float x, float y) {
        this.listener.intersects(this, x, y);
        this.foundOne = true;
        return this.isBreaked();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isBreaked() {
        if (this.searchAll) return false;
        return this.foundOne;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean hasHits() {
        return this.foundOne;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColliderA(ICollider pColliderA) {
        this.colliderA = pColliderA;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColliderB(ICollider pColliderB) {
        this.colliderB = pColliderB;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ICollider getColliderA() {
        return this.colliderA;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ICollider getColliderB() {
        return this.colliderB;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColliderItemA(IColliderItem pColliderA) {
        this.colliderItemA = pColliderA;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColliderItemB(IColliderItem pColliderB) {
        this.colliderItemB = pColliderB;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public IColliderItem getColliderItemA() {
        return this.colliderItemA;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public IColliderItem getColliderItemB() {
        return this.colliderItemB;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setData(String pKey, Object pData) {
        this.datas.put(pKey, pData);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Object getData(String pKey) {
        return this.datas.get(pKey);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
