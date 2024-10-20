/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagLong
extends NBTBase {
    public long longValue;

    public NBTTagLong() {
    }

    public NBTTagLong(long j1) {
        this.longValue = j1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeLong(this.longValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.longValue = dataInput1.readLong();
    }

    @Override
    public byte getType() {
        return 4;
    }

    public String toString() {
        return "" + this.longValue;
    }
}

