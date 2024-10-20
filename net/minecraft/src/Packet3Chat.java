/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet3Chat
extends Packet {
    public String message;

    public Packet3Chat() {
    }

    public Packet3Chat(String string1) {
        this.message = string1;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.message = dataInputStream1.readUTF();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeUTF(this.message);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleChat(this);
    }

    @Override
    public int getPacketSize() {
        return this.message.length();
    }
}

