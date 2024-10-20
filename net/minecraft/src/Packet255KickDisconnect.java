/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet255KickDisconnect
extends Packet {
    public String reason;

    public Packet255KickDisconnect() {
    }

    public Packet255KickDisconnect(String string1) {
        this.reason = string1;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.reason = dataInputStream1.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeUTF(this.reason);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleKickDisconnect(this);
    }

    @Override
    public int getPacketSize() {
        return this.reason.length();
    }
}

