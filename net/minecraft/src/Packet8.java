/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet8
extends Packet {
    public int healthMP;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.healthMP = dataInputStream1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeShort(this.healthMP);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleHealth(this);
    }

    @Override
    public int getPacketSize() {
        return 2;
    }
}

