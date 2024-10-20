/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet70
extends Packet {
    public static final String[] field_25020_a = new String[]{"tile.bed.notValid"};
    public int field_25019_b;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.field_25019_b = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeByte(this.field_25019_b);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_25118_a(this);
    }

    @Override
    public int getPacketSize() {
        return 1;
    }
}

