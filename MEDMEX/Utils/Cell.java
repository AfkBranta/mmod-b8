/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import MEDMEX.Utils.Vec3D;

public class Cell {
    public final int x;
    public final int y;
    public final int z;
    public int g = 0;
    public int h = 0;
    public int f = 0;
    public Cell parent;

    public Cell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Cell(Vec3D v) {
        this.x = (int)v.getX();
        this.y = (int)v.getY();
        this.z = (int)v.getZ();
    }

    public boolean equals(Object o) {
        if (o instanceof Cell) {
            Cell c = (Cell)o;
            return c.x == this.x && c.y == this.y && c.z == this.z;
        }
        return false;
    }

    public boolean equalsVec(Vec3D v) {
        return this.x == (int)v.getX() && this.y == (int)v.getY() && this.z == (int)v.getZ();
    }

    public static Cell sum(Cell c1, Cell c2) {
        return new Cell(c1.x + c2.x, c1.y + c2.y, c1.z + c2.z);
    }

    public Vec3D toVec3D() {
        return new Vec3D(this.x, this.y, this.z);
    }

    public int hashCode() {
        return this.x * 31 + this.y * 31 + this.z * 31;
    }

    public String toString() {
        return "Cell(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}

