package com.leo.spheres.core.chunksystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leo.commons.geom.Point;

import java.io.Closeable;
import java.util.function.Consumer;

public abstract class AChunkSystem {
    private static final Logger log = LoggerFactory.getLogger(AChunkSystem.class);

    // -------------------------------------------------------------------------------------------------------------------------
    public final static int CHUNKSIZE = 64;

    // -------------------------------------------------------------------------------------------------------------------------
    protected final int planetSize;
    // -------------------------------------------------------------------------------------------------------------------------
    protected final int half;
    // -------------------------------------------------------------------------------------------------------------------------
    private final Chunk[][] chunks;
    // -------------------------------------------------------------------------------------------------------------------------
    private final int chunkcountmax;
    // -------------------------------------------------------------------------------------------------------------------------
    private final int chunkcount;
    // -------------------------------------------------------------------------------------------------------------------------
    private final int chunkHalf;
    // -------------------------------------------------------------------------------------------------------------------------
    private final CircleHolder circleHolder;
    // -------------------------------------------------------------------------------------------------------------------------
    protected int rev;
    // -------------------------------------------------------------------------------------------------------------------------
    boolean inImport = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public AChunkSystem(int pPlanetSize) {
        this.planetSize = pPlanetSize;
        this.half = (int) Math.ceil(this.planetSize / 2f);
        this.circleHolder = CircleHolder.getInstance(half);
        this.chunkcount = (int) Math.ceil((float) this.planetSize / (float) CHUNKSIZE);
        this.chunkHalf = (int) Math.ceil(this.chunkcount / 2f);
        this.chunkcountmax = this.chunkcount * this.chunkcount;
        this.chunks = new Chunk[this.chunkcount][this.chunkcount];
        for (int x = 0; x < this.chunkcount; x++) {
            for (int y = 0; y < this.chunkcount; y++) {
                this.chunks[x][y] = new Chunk(x - this.chunkHalf, y - this.chunkHalf);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static final int calcBlockMapperSize(float outlineRadius) {
        float nokkel = Math.max(outlineRadius, AChunkSystem.CHUNKSIZE);
        double oaps = Math.ceil((nokkel * 2 / AChunkSystem.CHUNKSIZE));
        int blockMapperSize = (int) Math.ceil(oaps * AChunkSystem.CHUNKSIZE);
        int papa = (blockMapperSize / AChunkSystem.CHUNKSIZE) % 2;
        if (papa == 1) blockMapperSize += AChunkSystem.CHUNKSIZE;
        return blockMapperSize;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final int getPlanetSize() {
        return this.planetSize;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getHalf() {
        return this.half;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getChunkHalf() {
        return this.chunkHalf;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final int getChunkCount() {
        return this.chunkcount;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final int getChunkCountMax() {
        return this.chunkcountmax;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final int getRev() {
        return this.rev;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final Chunk getChunk(int pAbsoluteX, int pAbsoluteY) {
        int ox = pAbsoluteX / CHUNKSIZE;
        int oy = pAbsoluteY / CHUNKSIZE;
        if (ox < 0 || oy < 0 || ox > this.chunkcount || oy > this.chunkcount) return null;
        return this.chunks[ox][oy];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final Chunk getNChunk(int pChunkX, int pChunkY) {
        return this.chunks[pChunkX][pChunkY];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void getChunksOver(int cur, Consumer<Chunk> pChunk) {
        for (int x = 0; x < this.chunkcount; x++) {
            for (int y = 0; y < this.chunkcount; y++) {
                Chunk ch = this.chunks[x][y];
                if (ch.rev > cur) {
                    pChunk.accept(ch);
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void reset() {
        int useRev = this.rev++;
        for (int x = 0; x < this.chunkcount; x++) {
            for (int y = 0; y < this.chunkcount; y++) {
                this.resetChunk(x, y, useRev);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void resetChunk(Chunk chunk) {
        int useRev = this.rev++;
        int h = this.planetSize / 2;
        int x = (chunk.getX()) * CHUNKSIZE;
        int y = (chunk.getY()) * CHUNKSIZE;
        for (int cx = 0; cx < CHUNKSIZE; cx++) {
            for (int cy = 0; cy < CHUNKSIZE; cy++) {
                try {
                    if (this.doReset(x + cx + h, y + cy + h)) {
                        chunk.rev = useRev;
                    }
                } catch (Exception e) {
                    log.error("error resetting chunk {}/{}", cx, cy, e);
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void resetChunk(int x, int y, int useRev) {
        Chunk ch = this.chunks[x][y];
        for (int cx = 0; cx < CHUNKSIZE; cx++) {
            for (int cy = 0; cy < CHUNKSIZE; cy++) {
                if (this.doReset(x * CHUNKSIZE + cx, y * CHUNKSIZE + cy)) {
                    ch.rev = useRev;
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEachChunk(Consumer<Chunk> pChunkConsumer) {
        for (int x = 0; x < this.chunkcount; x++) {
            for (int y = 0; y < this.chunkcount; y++) {
                pChunkConsumer.accept(this.chunks[x][y]);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract boolean doReset(int x, int y);

    // -------------------------------------------------------------------------------------------------------------------------
    public final Closeable init() {
        this.inImport = true;
        return () -> AChunkSystem.this.inImport = false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean inCircle(int pX, int pY) {
        return circleHolder.inCircle(pX, pY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(Point p) {
        int translatedX = p.x + this.half;
        int translatedY = p.y + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(int x, int y) {
        int translatedX = x + this.half;
        int translatedY = y + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
