/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet105
extends Packet {
    public int windowId;
    public int progressBar;
    public int progressBarValue;

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20090_a(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.windowId = dataInputStream1.readByte();
        this.progressBar = dataInputStream1.readShort();
        this.progressBarValue = dataInputStream1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeByte(this.windowId);
        dataOutputStream1.writeShort(this.progressBar);
        dataOutputStream1.writeShort(this.progressBarValue);
    }

    @Override
    public int getPacketSize() {
        return 5;
    }
}

