/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagFloat
extends NBTBase {
    public float floatValue;

    public NBTTagFloat() {
    }

    public NBTTagFloat(float f1) {
        this.floatValue = f1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeFloat(this.floatValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.floatValue = dataInput1.readFloat();
    }

    @Override
    public byte getType() {
        return 5;
    }

    public String toString() {
        return "" + this.floatValue;
    }
}

