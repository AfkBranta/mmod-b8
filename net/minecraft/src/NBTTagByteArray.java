/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.src.NBTBase;

public class NBTTagByteArray
extends NBTBase {
    public byte[] byteArray;

    public NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] b1) {
        this.byteArray = b1;
    }

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        dataOutput1.writeInt(this.byteArray.length);
        dataOutput1.write(this.byteArray);
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        int i2 = dataInput1.readInt();
        this.byteArray = new byte[i2];
        dataInput1.readFully(this.byteArray);
    }

    @Override
    public byte getType() {
        return 7;
    }

    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }
}

