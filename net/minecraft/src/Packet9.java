/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet9
extends Packet {
    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_9448_a(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) {
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) {
    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}

