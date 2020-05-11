package com.leo.spheres.core.chunksystem;

import static com.leo.spheres.core.chunksystem.AChunkSystem.CHUNKSIZE;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

public class Chunk {
    // -------------------------------------------------------------------------------------------------------------------------
    private final int x;
    private final int y;
    private final String key;
    private final Rectangle rect;

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean outofatmos;

    // -------------------------------------------------------------------------------------------------------------------------
    int rev = 0;

    // -------------------------------------------------------------------------------------------------------------------------
    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;
        this.key = x + "_" + y; //$NON-NLS-1$
        this.rect = new Rectangle(
                x * CHUNKSIZE,
                y * CHUNKSIZE,
                CHUNKSIZE, CHUNKSIZE);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getX() {
        return this.x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getY() {
        return this.y;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getRev() {
        return this.rev;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder back = new StringBuilder(32);
        back.append("Chunk["); //$NON-NLS-1$
        back.append(this.x);
        back.append('|');
        back.append(this.y);
        back.append('|');
        back.append(this.rev);
        back.append(']');
        return back.toString();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Shape2D getRectangle() {
        return this.rect;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getKey() {
        return this.key;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
