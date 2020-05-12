package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RectangleMerger {
    public static List<Rectangle> merge(boolean[][] blocks) {
        int size = blocks.length;

        List<Rectangle> rects = new ArrayList<>();
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                Rectangle r = searchSquare(blocks, ix, iy);
                if (r != null) {
                    rects.add(r);
                }
            }
        }
        return rects;
    }

    private static Rectangle searchSquare(boolean[][] block, int sx, int sy) {
        if (!block[sx][sy]) {
            return null;
        }

        int width = 1;
        int height = 1;
        boolean continueY = true;
        boolean continueX = true;
        while ((continueY || continueX) && sx + width < block.length && sy + height < block.length) {
            if (continueY) {
                for (int cx = sx; cx <= sx + width; cx++) {
                    if (!block[cx][sy + height]) {
                        continueY = false;
                        break;
                    }
                }
            }
            if (continueX) {
                for (int cy = sy; cy < sy + height; cy++) {
                    if (!block[sx + width][cy]) {
                        continueX = false;
                        break;
                    }
                }
            }
            if (continueX) width++;
            if (continueY) height++;
        }

        for (int cx = 0; cx < width; cx++) {
            for (int cy = 0; cy < height; cy++) {
                block[sx + cx][sy + cy] = false;
            }
        }
        return new Rectangle(sx, sy, width, height);
    }
}
