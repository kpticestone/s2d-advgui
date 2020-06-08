package com.leo.spheres.core.chunksystem;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.geom.collider.IColliderItem;
import de.s2d_advgui.core.geom.collider.IntersectionFactory;
import de.s2d_advgui.core.utils.MinMax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.leo.spheres.core.chunksystem.PlanetChunkSystem.CUSTOM_FIELD_VISIBLE;

public class Collider_Planet implements ICollider {
    private static final Logger log = LoggerFactory.getLogger(Collider_Planet.class);

    // -------------------------------------------------------------------------------------------------------------------------
    private PlanetChunkSystem chunkSystem;

    // -------------------------------------------------------------------------------------------------------------------------
    Map<String, Collider_Chunk> chunks = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final MinMax box;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Set<IRegionOfInterest> regs = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider_Planet(PlanetChunkSystem chunkSystem) {
        this.chunkSystem = chunkSystem;
        MinMax minmax = new MinMax();
        Collider_Chunk newChunkCollider = new Collider_Chunk();
        chunkSystem.forEachChunk((chunk) -> {
            chunkSystem.forEach(chunk, CUSTOM_FIELD_VISIBLE, (x, y, t) -> {
                ColliderItem_ChunkBlock ch = newChunkCollider.getBlockCollider(x, y);
                minmax.append(ch.getBox());
            });
        });
        this.box = minmax;
        this.update();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addRegionOfInterest(@Nonnull IRegionOfInterest pRegionOfInterest) {
        this.regs.add(pRegionOfInterest);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void update() {
        int surroundSize = 2;
        Set<Rectangle> rects = new HashSet<>();
        Set<IRegionOfInterest> removes = new HashSet<>();
        for (IRegionOfInterest ri : this.regs) {
            Vector2 pnt = ri.getEntityColliderOnPlanet();
            if (pnt == null) {
                removes.add(ri);
            } else {
                Rectangle rect = new Rectangle(pnt.x - surroundSize - 1, pnt.y - surroundSize - 1, surroundSize * 2 + 1,
                        surroundSize * 2 + 1);
                rects.add(rect);
            }
        }
        this.regs.removeAll(removes);

        Set<String> usedChunkKeys = new HashSet<>(this.chunks.keySet());
        for (String al : usedChunkKeys) {
            Collider_Chunk chch = this.chunks.get(al);
            for (ColliderItem_ChunkBlock a : new HashSet<>(chch.getMap().values())) {
                short ix = this.chunkSystem.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, a.getX(), a.getY());
                chch.removeKey(a, ix == 0);
            }
        }

        Consumer<Chunk> pChunk = chunk -> {
            boolean hits = false;
            AX: for (Rectangle r : rects) {
                if (IntersectionFactory.getInstance().calculateOverlapping(chunk.getRectangle(), r)) {
                    hits = true;
                    break AX;
                }
            }
            if (hits) {
                String chunkKey = chunk.getKey();
                usedChunkKeys.remove(chunkKey);
                Collider_Chunk colChunk = this.chunks.computeIfAbsent(chunkKey, k -> new Collider_Chunk());
                Map<String, ColliderItem_ChunkBlock> existingKeys = new HashMap<>(colChunk.getMap());
                this.chunkSystem.forEach(chunk, PlanetChunkSystem.CUSTOM_FIELD_SOURCE, (x, y, t) -> {
                    boolean hits2 = false;
                    AY: for (Rectangle r : rects) {
                        if (r.contains(x, y)) {
                            hits2 = true;
                            break AY;
                        }
                    }
                    if (hits2) {
                        ColliderItem_ChunkBlock col = colChunk.getBlockCollider(x, y);
                        existingKeys.remove(col.getKey());
                    }
                });
            }
        };
        this.chunkSystem.forEachChunk(pChunk);

        for (String al : usedChunkKeys) {
            Collider_Chunk chch = this.chunks.get(al);
            if (chch.isEmpty()) {
                log.debug("remove chunk-infos");
                this.chunks.remove(al);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_ChunkBlock getBlockCollider(int atX, int atY) {
        boolean ex = this.chunkSystem.isBlockExists(atX, atY);
        if (ex) {
            Chunk chunk = this.chunkSystem.getChunk(atX, atY);
            if (chunk != null) {
                String chunkKey = chunk.getKey();
                Collider_Chunk colChunk = this.chunks.computeIfAbsent(chunkKey, k -> new Collider_Chunk());
                return colChunk.getBlockCollider(atX, atY);
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isEmpty() {
        this.update();
        return this.chunks.isEmpty();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public MinMax getBox() {
        this.update();
        return this.box;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Collection<? extends IColliderItem> getItems() {
        this.update();
        Collection<IColliderItem> back = new HashSet<>();
        for (Collider_Chunk a : this.chunks.values()) {
            for (ColliderItem_ChunkBlock b : a.getItems()) {
                back.add(b);
            }
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ICollider getTransformed(Affine2 transform) {
        return ICollider.super.getTransformed(transform);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean hits(ICollider pA, ICollider pB) {
        return ICollider.super.hits(pA, pB);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public float getDistance(ICollider pOtherCollider) {
        return ICollider.super.getDistance(pOtherCollider);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean hits(ICollider pOther) {
        return ICollider.super.hits(pOther);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(Vector2 pPoint) {
        return ICollider.super.contains(pPoint);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(float x, float y) {
        return ICollider.super.contains(x, y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void forEach(Consumer<IColliderItem> pConsumer) {
        ICollider.super.forEach(pConsumer);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
