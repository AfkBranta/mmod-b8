/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.NBTBase;

public class NBTTagList
extends NBTBase {
    private List tagList = new ArrayList();
    private byte tagType;

    @Override
    void writeTagContents(DataOutput dataOutput1) throws IOException {
        this.tagType = this.tagList.size() > 0 ? ((NBTBase)this.tagList.get(0)).getType() : (byte)1;
        dataOutput1.writeByte(this.tagType);
        dataOutput1.writeInt(this.tagList.size());
        int i2 = 0;
        while (i2 < this.tagList.size()) {
            ((NBTBase)this.tagList.get(i2)).writeTagContents(dataOutput1);
            ++i2;
        }
    }

    @Override
    void readTagContents(DataInput dataInput1) throws IOException {
        this.tagType = dataInput1.readByte();
        int i2 = dataInput1.readInt();
        this.tagList = new ArrayList();
        int i3 = 0;
        while (i3 < i2) {
            NBTBase nBTBase4 = NBTBase.createTagOfType(this.tagType);
            nBTBase4.readTagContents(dataInput1);
            this.tagList.add(nBTBase4);
            ++i3;
        }
    }

    @Override
    public byte getType() {
        return 9;
    }

    public String toString() {
        return this.tagList.size() + " entries of type " + NBTBase.getTagName(this.tagType);
    }

    public void setTag(NBTBase nBTBase1) {
        this.tagType = nBTBase1.getType();
        this.tagList.add(nBTBase1);
    }

    public NBTBase tagAt(int i1) {
        return (NBTBase)this.tagList.get(i1);
    }

    public int tagCount() {
        return this.tagList.size();
    }
}

