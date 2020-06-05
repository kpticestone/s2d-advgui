package com.leo.spheres.core.chunksystem;

import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.utils.MinMax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Collider_Chunk implements ICollider {
    private static final Logger log = LoggerFactory.getLogger(Collider_Chunk.class);

    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<String, ColliderItem_ChunkBlock> blocks = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final MinMax minmax = new MinMax();

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider_Chunk() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isEmpty() {
        return this.blocks.isEmpty();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public MinMax getBox() {
        return this.minmax;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Collection<ColliderItem_ChunkBlock> getItems() {
        return this.blocks.values();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void reset() {
        this.blocks.clear();
        this.minmax.reset();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_ChunkBlock getBlockCollider(int atX, int atY) {
        String key = atX + "_" + atY; //$NON-NLS-1$
        ColliderItem_ChunkBlock back = this.blocks.computeIfAbsent(key, k -> {
            ColliderItem_ChunkBlock ch = new ColliderItem_ChunkBlock(atX, atY);
            this.minmax.append(ch.getBox());
            return ch;
        });
        back.tick();
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void removeKey(ColliderItem_ChunkBlock bl, boolean b) {
        if (b) {
            log.debug("remove " + bl.getKey() + " cause removed block");
            this.blocks.remove(bl.getKey());
        } else {
            long cur = System.currentTimeMillis();
            if (cur - bl.lastHit > 5000) {
                log.debug("remove " + bl.getKey() + " cause older than 1");
                this.blocks.remove(bl.getKey());
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Map<String, ColliderItem_ChunkBlock> getMap() {
        return this.blocks;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
