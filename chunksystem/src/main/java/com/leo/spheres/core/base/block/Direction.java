package com.leo.spheres.core.base.block;


import com.leo.commons.geom.Point;

import javax.annotation.Nonnull;

/*
 *     1
 *     N
 * 8 W   E 2
 *     S
 *     4
 */
public enum Direction {
    // -------------------------------------------------------------------------------------------------------------------------
    NORTH(0, 2, new Point(0, 1), 180),
    EAST(1, 3, new Point(1, 0), 90),
    SOUTH(2, 0, new Point(0, -1), 0),
    WEST(3, 1, new Point(-1, 0), 270),
    ;
    // -------------------------------------------------------------------------------------------------------------------------
    private final Point modifiers;
    private final int bit;
    private final int ordinal;
    private int rotation;
    private byte index;

    // -------------------------------------------------------------------------------------------------------------------------
    Direction(int pIndex, int ordinal, @Nonnull Point pModifiers, int pRotation) {
        this.index = (byte) pIndex;
        this.modifiers = pModifiers;
        this.bit = (int) Math.pow(2, ordinal);
        this.ordinal = ordinal;
        this.rotation = pRotation;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Direction getForAngle(float angle) {
        if (angle > 315 || angle < 45) {
            return EAST;
        }
        if (angle > 225) {
            return SOUTH;
        }
        if (angle > 135) {
            return WEST;
        }
        return NORTH;
    }

    // -------------------------------------------------------------------------------------------------------------------------


    public static Direction get(float x, float y) {
        if (x == 0 && y == 0) return null;
        return Math.abs(x) > Math.abs(y) ?
                x > 0 ? EAST : WEST :
                y > 0 ? NORTH : SOUTH;
    }

    // -------------------------------------------------------------------------------------------------------------------------

    public static Direction byIndex(int index) {
        if (index == NORTH.index) return NORTH;
        if (index == EAST.index) return EAST;
        if (index == SOUTH.index) return SOUTH;
        if (index == WEST.index) return WEST;
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public byte getIndex() {
        return this.index;
    }

    /**
     * for actions that need to be performed relative to the current direction of the player
     */
    public Point getModifiers() {
        return this.modifiers;
    }

    /**
     * the assigned bit for the direction (we need it for bit-shifting)
     */
    public int getBit() {
        return this.bit;
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * the assigned degree for the direction (for displaying rotated blocks)
     */
    public float getRotation() {
        return this.rotation;
    }

    /**
     * shifts a directing in correlation to this direction.
     * We need this in order to get the relative "down" from the standpoint of the player
     */
    public Direction getRelative(Direction root) {
        if (root == null) return null;
        final int shift = root.ordinal();
        byte bits = (byte) getBit();
        bits = (byte) (((bits << shift) & 0xF) | ((bits << shift) >> 4) & 0xF);

        for (Direction d : Direction.values()) {
            if (d.contains(bits)) return d;
        }
        throw new IllegalStateException("shifting didn't work?");
    }

    public Direction getRelative2(Direction root) {
        if (root == null) return null;
        int n = root.ordinal - ordinal;
        if (n < 0) n += 4;
        if (n == NORTH.ordinal) return NORTH;
        if (n == EAST.ordinal) return EAST;
        if (n == SOUTH.ordinal) return SOUTH;
        if (n == WEST.ordinal) return WEST;
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(byte bits) {
        return (bits & this.bit) == this.bit;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Direction inverse() {
        if (this == EAST) return WEST;
        if (this == WEST) return EAST;
        if (this == SOUTH) return NORTH;
        if (this == NORTH) return SOUTH;
        throw new IllegalStateException("??");
    }
}
