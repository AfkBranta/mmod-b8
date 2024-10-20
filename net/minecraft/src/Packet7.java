/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet7
extends Packet {
    public int playerEntityId;
    public int targetEntity;
    public int isLeftClick;

    public Packet7() {
    }

    public Packet7(int i1, int i2, int i3) {
        this.playerEntityId = i1;
        this.targetEntity = i2;
        this.isLeftClick = i3;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.playerEntityId = dataInputStream1.readInt();
        this.targetEntity = dataInputStream1.readInt();
        this.isLeftClick = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.playerEntityId);
        dataOutputStream1.writeInt(this.targetEntity);
        dataOutputStream1.writeByte(this.isLeftClick);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_6499_a(this);
    }

    @Override
    public int getPacketSize() {
        return 9;
    }
}

