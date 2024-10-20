/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet50PreChunk
extends Packet {
    public int xPosition;
    public int yPosition;
    public boolean mode;

    public Packet50PreChunk() {
        this.isChunkDataPacket = false;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.xPosition = dataInputStream1.readInt();
        this.yPosition = dataInputStream1.readInt();
        this.mode = dataInputStream1.read() != 0;
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.writeInt(this.yPosition);
        dataOutputStream1.write(this.mode ? 1 : 0);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handlePreChunk(this);
    }

    @Override
    public int getPacketSize() {
        return 9;
    }
}

