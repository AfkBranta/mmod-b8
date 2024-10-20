/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagShort
extends NBTBase {
    public short shortValue;

    public NBTTagShort() {
    }

    public NBTTagShort(short s1) {
        this.shortValue = s1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeShort(this.shortValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.shortValue = dataInput1.readShort();
    }

    @Override
    public byte getType() {
        return 2;
    }

    public String toString() {
        return "" + this.shortValue;
    }
}

