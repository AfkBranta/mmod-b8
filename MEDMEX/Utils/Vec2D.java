/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

public class Vec2D {
    public double x;
    public double y;

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equalsVec(Vec2D vec) {
        return this.x == vec.x && this.y == vec.y;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }
}

