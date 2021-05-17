package com.leo.spheres.core.chunksystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.commons.ConsumerWithException;
import de.s2d_advgui.commons.TimeTracker;
import de.s2d_advgui.core.utils.MinMax;

public class EntitySet<CE extends IColEnt> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<CE> allEntities = new HashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<CE> nonHittable = new HashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public final int chunkSize;

    // -------------------------------------------------------------------------------------------------------------------------
    class ChunkInfo {
        Set<CE> entities = new HashSet<>();
        long revId;
        int x;
        int y;

        ChunkInfo(int x, int y, long revId) {
            this.x = x;
            this.y = y;
            this.revId = revId;
        }

        void forEach(Consumer<CE> pConsumer) {
            this.entities.forEach(pConsumer);
        }

        public void update(long revId) {
            if (this.revId != revId) {
                this.entities.clear();
                this.revId = revId;
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<Integer, Map<Integer, ChunkInfo>> mep = new HashMap<>();
    private final Map<String, ChunkInfo> allChunks = new HashMap<>();

    private long revId = 0L;

    // -------------------------------------------------------------------------------------------------------------------------
    public EntitySet() {
        this.chunkSize = 100;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void forEach(int chunkX, int chunkY, Consumer<CE> pConsumer) {
        Map<Integer, ChunkInfo> wwe = this.mep.get(chunkX);
        if (wwe != null) {
            ChunkInfo wwf = wwe.get(chunkY);
            if (wwf != null) {
                wwf.forEach(pConsumer);
            }
        }
    }

    Vector2 vector2 = new Vector2();
    int chunkX, chunkY;
    Map<Integer, ChunkInfo> www;
    ChunkInfo wwe;
    Set<String> toRemove = new HashSet<>();

    public int[] stats = {
            0, // new chunks
            0, // chunks removed
            0, // removed y-store
            0, // removed x-store
            0, // av entites per chunks
            0, // active chunks
            0, // time
    };

    // -------------------------------------------------------------------------------------------------------------------------
    public void update() {
        ++this.revId;
        stats[0] = 0;
        stats[1] = 0;
        stats[2] = 0;
        stats[3] = 0;
        long t1 = System.currentTimeMillis();
        for (CE a : this.allEntities) {
            MinMax cb = a.getCollider().getBox();
            // cb.getCenter(vector2);
            vector2.x = cb.getX1() + (cb.getX2() - cb.getX1())/2f;
            vector2.y = cb.getY1() + (cb.getY2() - cb.getY1())/2f;
            chunkX = Math.floorDiv((int) vector2.x, this.chunkSize);
            chunkY = Math.floorDiv((int) vector2.y, this.chunkSize);
            www = mep.get(chunkX);
            if (www == null) {
                www = new HashMap<>();
                mep.put(chunkX, www);
            }
            wwe = www.get(chunkY);
            if (wwe == null) {
                String chunkKey = chunkX + "_" + chunkY;
                ++stats[0];
                wwe = new ChunkInfo(chunkX, chunkY, revId);
                allChunks.put(chunkKey, wwe);
                www.put(chunkY, wwe);
            } else {
                ++stats[1];
                wwe.update(revId);
            }
            wwe.entities.add(a);
        }
        // if( revId % 100 == 0 )
        {
            long all = 0;
            for (Entry<String, ChunkInfo> a : this.allChunks.entrySet()) {
                ChunkInfo ci = a.getValue();
                ci.update(revId);
                all+= ci.entities.size();
                if (ci.entities.isEmpty()) {
                    toRemove.add(a.getKey());
                }
            }
            stats[4] = (int) (all / this.allChunks.size());
            stats[2] = toRemove.size();
            for (String a : toRemove) {
                wwe = this.allChunks.remove(a);
                www = this.mep.get(wwe.x);
                if (www != null) {
                    www.remove(wwe.y);
                    if (www.isEmpty()) {
                        this.mep.remove(wwe.x);
                        ++stats[3];
                    }
                }
            }
            toRemove.clear();
        }
        stats[5] = allChunks.size();
        stats[6] = (int) (System.currentTimeMillis() - t1);
        // System.err.println("add: " + stats[0] + "; upd: " + stats[1] + "; del-x: " + stats[2] + "; del-y: " + stats[3] + "; av cu: " + stats[4] + "; active chunks: " + stats[5]);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int size() {
        return this.allEntities.size() + this.nonHittable.size();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void add(CE ent) {
        if (ent.canHit()) {
            this.allEntities.add(ent);
        } else {
            this.nonHittable.add(ent);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addAll(Collection<CE> newEntities) {
        for (CE e : newEntities) {
            this.add(e);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void removeAll(Collection<CE> removethem) {
        for (CE e : removethem) {
            if (e.canHit()) {
                this.allEntities.remove(e);
            } else {
                this.nonHittable.remove(e);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Iterable<CE> getNonHittable() {
        return this.nonHittable;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Iterable<CE> getHittableEntities() {
        return this.allEntities;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(ConsumerWithException<CE> e) throws Exception {
        for (CE a : this.nonHittable) {
            e.accept(a);
        }
        for (CE a : this.allEntities) {
            e.accept(a);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
