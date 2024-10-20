/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet1Login
extends Packet {
    public int protocolVersion;
    public String username;
    public String password;
    public long mapSeed;
    public byte dimension;

    public Packet1Login() {
    }

    public Packet1Login(String string1, String string2, int i3) {
        this.username = string1;
        this.password = string2;
        this.protocolVersion = i3;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.protocolVersion = dataInputStream1.readInt();
        this.username = dataInputStream1.readUTF();
        this.password = dataInputStream1.readUTF();
        this.mapSeed = dataInputStream1.readLong();
        this.dimension = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.protocolVersion);
        dataOutputStream1.writeUTF(this.username);
        dataOutputStream1.writeUTF(this.password);
        dataOutputStream1.writeLong(this.mapSeed);
        dataOutputStream1.writeByte(this.dimension);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleLogin(this);
    }

    @Override
    public int getPacketSize() {
        return 4 + this.username.length() + this.password.length() + 4 + 5;
    }
}

