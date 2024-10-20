/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet4UpdateTime
extends Packet {
    public long time;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.time = dataInputStream1.readLong();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeLong(this.time);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleUpdateTime(this);
    }

    @Override
    public int getPacketSize() {
        return 8;
    }
}

