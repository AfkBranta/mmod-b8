/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet6SpawnPosition
extends Packet {
    public int xPosition;
    public int yPosition;
    public int zPosition;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.xPosition = dataInputStream1.readInt();
        this.yPosition = dataInputStream1.readInt();
        this.zPosition = dataInputStream1.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.writeInt(this.yPosition);
        dataOutputStream1.writeInt(this.zPosition);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleSpawnPosition(this);
    }

    @Override
    public int getPacketSize() {
        return 12;
    }
}

