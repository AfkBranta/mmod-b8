/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet52MultiBlockChange
extends Packet {
    public int xPosition;
    public int zPosition;
    public short[] coordinateArray;
    public byte[] typeArray;
    public byte[] metadataArray;
    public int size;

    public Packet52MultiBlockChange() {
        this.isChunkDataPacket = true;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.xPosition = dataInputStream1.readInt();
        this.zPosition = dataInputStream1.readInt();
        this.size = dataInputStream1.readShort() & 0xFFFF;
        this.coordinateArray = new short[this.size];
        this.typeArray = new byte[this.size];
        this.metadataArray = new byte[this.size];
        int i2 = 0;
        while (i2 < this.size) {
            this.coordinateArray[i2] = dataInputStream1.readShort();
            ++i2;
        }
        dataInputStream1.readFully(this.typeArray);
        dataInputStream1.readFully(this.metadataArray);
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.writeInt(this.zPosition);
        dataOutputStream1.writeShort((short)this.size);
        int i2 = 0;
        while (i2 < this.size) {
            dataOutputStream1.writeShort(this.coordinateArray[i2]);
            ++i2;
        }
        dataOutputStream1.write(this.typeArray);
        dataOutputStream1.write(this.metadataArray);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleMultiBlockChange(this);
    }

    @Override
    public int getPacketSize() {
        return 10 + this.size * 4;
    }
}

