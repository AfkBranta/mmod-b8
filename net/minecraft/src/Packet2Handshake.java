/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet2Handshake
extends Packet {
    public String username;

    public Packet2Handshake() {
    }

    public Packet2Handshake(String string1) {
        this.username = string1;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.username = dataInputStream1.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeUTF(this.username);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleHandshake(this);
    }

    @Override
    public int getPacketSize() {
        return 4 + this.username.length() + 4;
    }
}

