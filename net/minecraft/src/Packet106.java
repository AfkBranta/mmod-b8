/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet106
extends Packet {
    public int windowId;
    public short field_20028_b;
    public boolean field_20030_c;

    public Packet106() {
    }

    public Packet106(int i1, short s2, boolean z3) {
        this.windowId = i1;
        this.field_20028_b = s2;
        this.field_20030_c = z3;
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20089_a(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.windowId = dataInputStream1.readByte();
        this.field_20028_b = dataInputStream1.readShort();
        this.field_20030_c = dataInputStream1.readByte() != 0;
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeByte(this.windowId);
        dataOutputStream1.writeShort(this.field_20028_b);
        dataOutputStream1.writeByte(this.field_20030_c ? 1 : 0);
    }

    @Override
    public int getPacketSize() {
        return 4;
    }
}

