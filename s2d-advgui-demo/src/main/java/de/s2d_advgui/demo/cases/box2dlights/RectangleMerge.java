package de.s2d_advgui.demo.cases.box2dlights;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RectangleMerge {
    public static List<Rectangle> merge(boolean[][] block) {
        int size = block.length;

        List<Rectangle> rects = new ArrayList<>();
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                Rectangle r = searchSquare(block, ix, iy);
                if (r != null) {
                    rects.add(r);
                }
            }
        }
        return rects;
    }

    private static Rectangle searchSquare(boolean[][] block, int sx, int sy) {
        int squareSize = 0;
        A:
        while (sx + squareSize < block.length && sy + squareSize < block.length) {
            for (int cx = sx; cx <= sx + squareSize; cx++) {
                if (!block[cx][sy + squareSize]) {
                    break A;
                }
            }
            for (int cy = sy; cy < sy + squareSize; cy++) {
                if (!block[sx + squareSize][cy]) {
                    break A;
                }
            }
            squareSize++;
        }
        if (squareSize == 0) {
            return null;
        }

        for (int cx = 0; cx < squareSize; cx++) {
            for (int cy = 0; cy < squareSize; cy++) {
                block[sx + cx][sy + cy] = false;
            }
        }
        return new Rectangle(sx, sy, squareSize, squareSize);
    }
}
