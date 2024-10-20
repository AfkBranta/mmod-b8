/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import MEDMEX.Utils.Vec3D;

public class BlockPos {
    Vec3D pos;
    int blockid;

    public BlockPos(Vec3D pos, int blockid) {
        this.pos = pos;
        this.blockid = blockid;
    }

    public Vec3D getPos() {
        return this.pos;
    }

    public void setPos(Vec3D pos) {
        this.pos = pos;
    }

    public int getBlockid() {
        return this.blockid;
    }

    public void setBlockid(int blockid) {
        this.blockid = blockid;
    }
}

