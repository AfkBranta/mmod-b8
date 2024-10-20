/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet0KeepAlive
extends Packet {
    @Override
    public void processPacket(NetHandler netHandler1) {
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}

