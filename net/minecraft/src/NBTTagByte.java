/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagByte
extends NBTBase {
    public byte byteValue;

    public NBTTagByte() {
    }

    public NBTTagByte(byte b1) {
        this.byteValue = b1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeByte(this.byteValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.byteValue = dataInput1.readByte();
    }

    @Override
    public byte getType() {
        return 1;
    }

    public String toString() {
        return "" + this.byteValue;
    }
}

