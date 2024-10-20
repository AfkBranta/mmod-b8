/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet38
extends Packet {
    public int entityId;
    public byte entityStatus;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.entityId = dataInputStream1.readInt();
        this.entityStatus = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.entityId);
        dataOutputStream1.writeByte(this.entityStatus);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_9447_a(this);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }
}

