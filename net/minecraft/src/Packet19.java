/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Entity;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet19
extends Packet {
    public int entityId;
    public int state;

    public Packet19() {
    }

    public Packet19(Entity entity1, int i2) {
        this.entityId = entity1.entityId;
        this.state = i2;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.entityId = dataInputStream1.readInt();
        this.state = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.entityId);
        dataOutputStream1.writeByte(this.state);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_21147_a(this);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }
}

