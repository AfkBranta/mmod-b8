/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet54
extends Packet {
    public int xLocation;
    public int yLocation;
    public int zLocation;
    public int instrumentType;
    public int pitch;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.xLocation = dataInputStream1.readInt();
        this.yLocation = dataInputStream1.readShort();
        this.zLocation = dataInputStream1.readInt();
        this.instrumentType = dataInputStream1.read();
        this.pitch = dataInputStream1.read();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.xLocation);
        dataOutputStream1.writeShort(this.yLocation);
        dataOutputStream1.writeInt(this.zLocation);
        dataOutputStream1.write(this.instrumentType);
        dataOutputStream1.write(this.pitch);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_21145_a(this);
    }

    @Override
    public int getPacketSize() {
        return 12;
    }
}

