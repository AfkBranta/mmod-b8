/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet16BlockItemSwitch
extends Packet {
    public int id;

    public Packet16BlockItemSwitch() {
    }

    public Packet16BlockItemSwitch(int i1) {
        this.id = i1;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.id = dataInputStream1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeShort(this.id);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleBlockItemSwitch(this);
    }

    @Override
    public int getPacketSize() {
        return 2;
    }
}

