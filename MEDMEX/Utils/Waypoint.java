/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import MEDMEX.Utils.Vec3D;

public class Waypoint {
    Vec3D pos;
    String name;
    int dimension;
    boolean showing;

    public int getDimension() {
        return this.dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public Waypoint(Vec3D pos, String name, int dimension) {
        this.pos = pos;
        this.name = name;
    }

    public Vec3D getPos() {
        return this.pos;
    }

    public void setPos(Vec3D pos) {
        this.pos = pos;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowing() {
        return this.showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public String toString() {
        return String.valueOf(this.pos.getX()) + ":" + this.pos.getY() + ":" + this.pos.getZ() + ":" + this.getName() + ":" + this.getDimension();
    }
}

