/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class ChunkCoordinates
implements Comparable {
    public int x;
    public int y;
    public int z;

    public ChunkCoordinates() {
    }

    public ChunkCoordinates(int i1, int i2, int i3) {
        this.x = i1;
        this.y = i2;
        this.z = i3;
    }

    public ChunkCoordinates(ChunkCoordinates chunkCoordinates1) {
        this.x = chunkCoordinates1.x;
        this.y = chunkCoordinates1.y;
        this.z = chunkCoordinates1.z;
    }

    public boolean equals(Object object1) {
        if (!(object1 instanceof ChunkCoordinates)) {
            return false;
        }
        ChunkCoordinates chunkCoordinates2 = (ChunkCoordinates)object1;
        return this.x == chunkCoordinates2.x && this.y == chunkCoordinates2.y && this.z == chunkCoordinates2.z;
    }

    public int hashCode() {
        return this.x + this.z << 8 + this.y << 16;
    }

    public int func_22393_a(ChunkCoordinates chunkCoordinates1) {
        return this.y == chunkCoordinates1.y ? (this.z == chunkCoordinates1.z ? this.x - chunkCoordinates1.x : this.z - chunkCoordinates1.z) : this.y - chunkCoordinates1.y;
    }

    public int compareTo(Object object1) {
        return this.func_22393_a((ChunkCoordinates)object1);
    }
}

