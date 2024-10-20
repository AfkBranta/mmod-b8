/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import MEDMEX.Utils.Vec3D;

public class BlockPlacement {
    Vec3D pos;
    int side;

    public BlockPlacement(Vec3D pos, int side) {
        this.pos = pos;
        this.side = side;
    }

    public Vec3D getPos() {
        return this.pos;
    }

    public void setPos(Vec3D pos) {
        this.pos = pos;
    }

    public int getSide() {
        return this.side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public int getX() {
        return (int)this.pos.getX();
    }

    public int getY() {
        return (int)this.pos.getY();
    }

    public int getZ() {
        return (int)this.pos.getZ();
    }
}

