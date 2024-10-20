/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagInt
extends NBTBase {
    public int intValue;

    public NBTTagInt() {
    }

    public NBTTagInt(int i1) {
        this.intValue = i1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeInt(this.intValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.intValue = dataInput1.readInt();
    }

    @Override
    public byte getType() {
        return 3;
    }

    public String toString() {
        return "" + this.intValue;
    }
}

