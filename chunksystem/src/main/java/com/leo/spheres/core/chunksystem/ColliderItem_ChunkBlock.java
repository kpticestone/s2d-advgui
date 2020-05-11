package com.leo.spheres.core.chunksystem;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.leo.spheres.core.base.block.Direction;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.collider.IColliderItem;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Polygon;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RayRay;
import de.s2d_advgui.core.utils.ShapeUtils;

public class ColliderItem_ChunkBlock extends ColliderItem<Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    public static final String DATAKEY_BLOCKSIDE = "BLOCKSIDE"; //$NON-NLS-1$

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    Map<Direction, Ray2D> blockLines;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int atX, atY;

    // -------------------------------------------------------------------------------------------------------------------------
    private final String key;

    // -------------------------------------------------------------------------------------------------------------------------
    public long lastHit = System.currentTimeMillis();

    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_ChunkBlock(int atX, int atY) {
        super(new Rectangle(atX, atY, 1, 1), new Rectangle(atX, atY, 1, 1), false);
        this.key = atX + "_" + atY; //$NON-NLS-1$
        this.atX = atX;
        this.atY = atY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getX() {
        return this.atX;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public int getY() {
        return this.atY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Map<Direction, Ray2D> getBlockLines() {
        if (this.blockLines == null) {
            float l1pax = this.atX;
            float l1pay = this.atY;
            float l1pbx = this.atX + 1;
            float l1pby = this.atY + 1;
            this.blockLines = new EnumMap<>(Direction.class);
            this.blockLines.put(Direction.WEST, new Ray2D(l1pax, l1pay, l1pax, l1pby));
            this.blockLines.put(Direction.NORTH, new Ray2D(l1pax, l1pby, l1pbx, l1pby));
            this.blockLines.put(Direction.SOUTH, new Ray2D(l1pax, l1pay, l1pbx, l1pay));
            this.blockLines.put(Direction.EAST, new Ray2D(l1pbx, l1pay, l1pbx, l1pby));
        }
        return this.blockLines;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getKey() {
        return this.key;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void tick() {
        this.lastHit = System.currentTimeMillis();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(Ray2D pRay, IntersectionContext pContext) {
        for (Entry<Direction, Ray2D> bl : this.getBlockLines().entrySet()) {
            pContext.setData(DATAKEY_BLOCKSIDE, bl.getKey());
            Ray2D v = bl.getValue();
            Intersector_RayRay.getInstance().calculateIntersections(pContext, pRay, v);
            if (pContext.isBreaked()) return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(ShapeRenderer pRenderer) {
        Rectangle r = this.shape;
        pRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public IColliderItem getTransformedItem(Affine2 transform) {
        Polygon asPoly = ShapeUtils.byRect(this.shape);
        return new ColliderItem_Polygon(ShapeUtils.clone2(asPoly, transform));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
