/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagString
extends NBTBase {
    public String stringValue;

    public NBTTagString() {
    }

    public NBTTagString(String string1) {
        this.stringValue = string1;
        if (string1 == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeUTF(this.stringValue);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.stringValue = dataInput1.readUTF();
    }

    @Override
    public byte getType() {
        return 8;
    }

    public String toString() {
        return this.stringValue;
    }
}

