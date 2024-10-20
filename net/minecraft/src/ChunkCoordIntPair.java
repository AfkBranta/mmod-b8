/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class ChunkCoordIntPair {
    public final int chunkXPos;
    public final int chunkZPos;

    public ChunkCoordIntPair(int i1, int i2) {
        this.chunkXPos = i1;
        this.chunkZPos = i2;
    }

    public static int func_22011_a(int i0, int i1) {
        return (i0 < 0 ? Integer.MIN_VALUE : 0) | (i0 & Short.MAX_VALUE) << 16 | (i1 < 0 ? 32768 : 0) | i1 & Short.MAX_VALUE;
    }

    public int hashCode() {
        return ChunkCoordIntPair.func_22011_a(this.chunkXPos, this.chunkZPos);
    }

    public boolean equals(Object object1) {
        ChunkCoordIntPair chunkCoordIntPair2 = (ChunkCoordIntPair)object1;
        return chunkCoordIntPair2.chunkXPos == this.chunkXPos && chunkCoordIntPair2.chunkZPos == this.chunkZPos;
    }
}

