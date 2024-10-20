/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet29DestroyEntity
extends Packet {
    public int entityId;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.entityId = dataInputStream1.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.entityId);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleDestroyEntity(this);
    }

    @Override
    public int getPacketSize() {
        return 4;
    }
}

