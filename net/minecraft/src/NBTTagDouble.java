/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagDouble
extends NBTBase {
    public double doubleValue;

    public NBTTagDouble() {
    }

    public NBTTagDouble(double d1) {
        this.doubleValue = d1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeDouble(this.doubleValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.doubleValue = dataInput1.readDouble();
    }

    @Override
    public byte getType() {
        return 6;
    }

    public String toString() {
        return "" + this.doubleValue;
    }
}

