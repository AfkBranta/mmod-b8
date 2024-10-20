/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet22Collect
extends Packet {
    public int collectedEntityId;
    public int collectorEntityId;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.collectedEntityId = dataInputStream1.readInt();
        this.collectorEntityId = dataInputStream1.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.collectedEntityId);
        dataOutputStream1.writeInt(this.collectorEntityId);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleCollect(this);
    }

    @Override
    public int getPacketSize() {
        return 8;
    }
}

