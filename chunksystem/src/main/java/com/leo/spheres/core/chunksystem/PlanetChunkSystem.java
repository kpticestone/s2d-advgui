package com.leo.spheres.core.chunksystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.math.Vector2;
import com.leo.commons.geom.Point;
import com.leo.commons.utils.Brush;

public class PlanetChunkSystem extends AChunkSystem {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final Logger log = LoggerFactory.getLogger(PlanetChunkSystem.class);
    // -------------------------------------------------------------------------------------------------------------------------
    public static final int CUSTOM_FIELD_SOURCE = 0;
    public static final int CUSTOM_FIELD_VISIBLE = 1;
    public static final int CUSTOM_FIELD_OUTLINE = 2;
    public static final int CUSTOM_FIELD_LIGHTMAP = 3;
    // -------------------------------------------------------------------------------------------------------------------------
    private final static int CUSTOM_FIELDS = 4;
    // -------------------------------------------------------------------------------------------------------------------------
    private final int quad;
    // -------------------------------------------------------------------------------------------------------------------------
    private short[] allData;
    // -------------------------------------------------------------------------------------------------------------------------
    public boolean calculated = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public PlanetChunkSystem(AChunkSystem other) {
        this(other.planetSize);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public PlanetChunkSystem(int pPlanetSize) {
        super(pPlanetSize);
        this.quad = this.planetSize * this.planetSize;
        this.allData = new short[this.quad * CUSTOM_FIELDS];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.planetSize + ";" + this.allData.length + ";" + this.rev
                + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public short[] save() {
        return this.allData;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void load(short[] x) {
        this.allData = x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final int tr0(int x, int y) {
        return y * this.planetSize + x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final int tr(final int pCustonFieldNr, final int x, final int y) {
        return (pCustonFieldNr * this.quad) + y * this.planetSize + x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected boolean doReset(int x, int y) {
        int o = tr0(x, y);
        boolean back = false;
        for (int i = 0; i < CUSTOM_FIELDS; i++) {
            int p = o + i * this.quad;
            if (this.allData[p] != 0) {
                this.allData[p] = 0;
                back = true;
            }
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doReset(int pCustonFieldNr, int x, int y, short targetId) {
        int o = tr0(x, y);
        boolean back = false;
        int p = o + pCustonFieldNr * this.quad;
        if (this.allData[p] != targetId) {
            this.allData[p] = targetId;
            back = true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void doReset(int fieldType) {
        for (int i = 0; i < quad; i++) {
            allData[i + (fieldType * quad)] = 0;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doSet(int pCustonFieldNr, int pTranslatedX, int pTranslatedY, short pValue) {
        int o = tr(pCustonFieldNr, pTranslatedX, pTranslatedY);
        if (this.allData[o] != pValue) {
            this.allData[o] = pValue;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected short doGet(int pCustonFieldNr, int translatedX, int translatedY) {
        return this.allData[tr(pCustonFieldNr, translatedX, translatedY)];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doUpgrade(int pCustonFieldNr, int pTranslatedX, int pTranslatedY, short pValue) {
        int o = tr(pCustonFieldNr, pTranslatedX, pTranslatedY);
        if (this.allData[o] < pValue) {
            this.allData[o] = pValue;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(int pCustomFieldNr, CoordConsumerShort pCallback) {
        for (int x = 0; x < this.planetSize; x++)
            for (int y = 0; y < this.planetSize; y++) {
                pCallback.accept(x - this.half, y - this.half, this.allData[tr(pCustomFieldNr, x, y)]);
            }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean set(int pCustonFieldNr, int pAbsoluteX, int pAbsoluteY, int pValue) {
        return this.set(pCustonFieldNr, pAbsoluteX, pAbsoluteY, (short) pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void forceFastSet(int pCustonFieldNr, int pAbsoluteX, int pAbsoluteY, short pValue) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return;
        int o = (pCustonFieldNr * this.quad) + translatedY * this.planetSize + translatedX;
        this.allData[o] = pValue;
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * @param pAbsoluteX
     * @param pAbsoluteY
     * @param pValue
     * @return
     */
    public final boolean set(int pCustonFieldNr, int pAbsoluteX, int pAbsoluteY, short pValue) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        if (this.doSet(pCustonFieldNr, translatedX, translatedY, pValue)) {
            if (!this.inImport) {
                if (pCustonFieldNr < CUSTOM_FIELD_OUTLINE) {
                    this.getChunk(translatedX, translatedY).rev = this.rev++;
                }
            }
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean upgrade(int pCustonFieldNr, int pAbsoluteX, int pAbsoluteY, short pValue) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        if (this.doUpgrade(pCustonFieldNr, translatedX, translatedY, pValue)) {
            if (!this.inImport) {
                this.getChunk(translatedX, translatedY).rev = this.rev++;
            }
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final short get(int pCustonFieldNr, int pAbsoluteX, int pAbsoluteY) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return -1;
        return this.doGet(pCustonFieldNr, translatedX, translatedY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void neighbors4(int pCustonFieldNr, int onex, int oney, CoordConsumerShort pCallback) {
        int ax, ay;
        for (Point add : Brush.neighborPoints4) {
            ax = add.x + onex;
            ay = add.y + oney;
            short v = this.get(pCustonFieldNr, ax, ay);
            if (v >= 0) {
                pCallback.accept(ax, ay, v);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void neighbors8(int pCustonFieldNr, int onex, int oney, CoordConsumerShort pCallback) {
        int ax, ay;
        for (Point add : Brush.neighborPoints8) {
            ax = add.x + onex;
            ay = add.y + oney;
            short v = this.get(pCustonFieldNr, ax, ay);
            if (v >= 0) {
                pCallback.accept(ax, ay, v);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public short[] getAll(int x, int y) {
        int rela = this.tr0(x + this.half, y + this.half);
        short[] back = new short[CUSTOM_FIELDS];
        for (int i = 0; i < CUSTOM_FIELDS; i++) {
            back[i] = this.allData[i * this.quad + rela];
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean setAll(int x, int y, short[] type) {
        boolean back = false;
        for (int i = 0; i < CUSTOM_FIELDS; i++) {
            if (this.set(i, x, y, type[i])) {
                back = true;
            }
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(Chunk chunk, int pCustonFieldNr, CoordConsumerShort pCallback) {
        // System.err.println(chunk);
        int atx = (chunk.getX() + getChunkHalf()) * CHUNKSIZE;
        int aty = (chunk.getY() + getChunkHalf()) * CHUNKSIZE;
        // System.err.println("forEach--> " + atx + "," + aty);
        for (int x = 0; x < CHUNKSIZE; x++)
            for (int y = 0; y < CHUNKSIZE; y++) {
                int rx = atx + x;
                int ry = aty + y;

                short ak = this.allData[tr(pCustonFieldNr, rx, ry)];
                if (ak != 0) {
                    pCallback.accept(rx - this.half, ry - this.half, ak);
                }
            }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isBlockExists(Vector2 pProjectilePosition) {
        int blockX = (int) Math.floor(pProjectilePosition.x);
        int blockY = (int) Math.floor(pProjectilePosition.y);
        return this.isBlockExists(blockX, blockY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isBlockExists(int blockX, int blockY) {
        short inside = this.get(CUSTOM_FIELD_SOURCE, blockX, blockY);
        return inside > 0;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public short getBlockGlowId() {
        return 8;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public short getBlockHiddenId() {
        return 9;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void resetChunk(int pCustonFieldNr, Chunk chunk, short pTargetId) {
        int useRev = this.rev++;
        int h = this.planetSize / 2;
        int x = (chunk.getX()) * CHUNKSIZE;
        int y = (chunk.getY()) * CHUNKSIZE;
        for (int cx = 0; cx < CHUNKSIZE; cx++) {
            for (int cy = 0; cy < CHUNKSIZE; cy++) {
                try {
                    if (this.doReset(pCustonFieldNr, x + cx + h, y + cy + h, pTargetId)) {
                        if (pCustonFieldNr < CUSTOM_FIELD_OUTLINE) {
                            chunk.rev = useRev;
                        }
                    }
                } catch (Exception e) {
                    log.error("error resetting chunk {}/{}", cx, cy, e);
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    int stx[] = new int[3000000];
    int sty[] = new int[3000000];

    // -------------------------------------------------------------------------------------------------------------------------
    public void floodOuttaSpace(int x1, int y1) {
        int siz = this.half;
        int f, r, x, y;
        f = r = 0;
        stx[0] = x1;
        sty[0] = y1;
        while (f >= r) {
            x = stx[r];
            y = sty[r++];
            if (x > -siz && y >= -siz
                    && x < siz
                    && y < siz) {
                if (this.get(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, x, y) == 0) {
                    if (this.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, x, y) == 0) {
                        this.forceFastSet(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, x, y, (short)1);
                        stx[f] = x;     sty[f++] = y + 1;
                        stx[f] = x;     sty[f++] = y - 1;
                        stx[f] = x + 1; sty[f++] = y;
                        stx[f] = x - 1; sty[f++] = y;
                        
                        
                        stx[f] = x + 1; sty[f++] = y + 1;
                        stx[f] = x + 1; sty[f++] = y - 1;
                        stx[f] = x - 1; sty[f++] = y + 1;
                        stx[f] = x - 1; sty[f++] = y - 1;
                    } else {
                        this.forceFastSet(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, x, y, (short)2);
                    }
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void copy(int pAbsoluteX, int pAbsoluteY, int pCustomFieldNrSrc, int pCustomFieldNrDst) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return;
        int o = translatedY * this.planetSize + translatedX;
        this.allData[(pCustomFieldNrDst * this.quad) + o] = this.allData[(pCustomFieldNrSrc * this.quad) + o];
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
