/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet101
extends Packet {
    public int windowId;

    public Packet101() {
    }

    public Packet101(int i1) {
        this.windowId = i1;
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20092_a(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.windowId = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeByte(this.windowId);
    }

    @Override
    public int getPacketSize() {
        return 1;
    }
}

