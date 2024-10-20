/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet130
extends Packet {
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public String[] signLines;

    public Packet130() {
        this.isChunkDataPacket = true;
    }

    public Packet130(int i1, int i2, int i3, String[] string4) {
        this.isChunkDataPacket = true;
        this.xPosition = i1;
        this.yPosition = i2;
        this.zPosition = i3;
        this.signLines = string4;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.xPosition = dataInputStream1.readInt();
        this.yPosition = dataInputStream1.readShort();
        this.zPosition = dataInputStream1.readInt();
        this.signLines = new String[4];
        int i2 = 0;
        while (i2 < 4) {
            this.signLines[i2] = dataInputStream1.readUTF();
            ++i2;
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.writeShort(this.yPosition);
        dataOutputStream1.writeInt(this.zPosition);
        int i2 = 0;
        while (i2 < 4) {
            dataOutputStream1.writeUTF(this.signLines[i2]);
            ++i2;
        }
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20093_a(this);
    }

    @Override
    public int getPacketSize() {
        int i1 = 0;
        int i2 = 0;
        while (i2 < 4) {
            i1 += this.signLines[i2].length();
            ++i2;
        }
        return i1;
    }
}

