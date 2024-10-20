/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Packet30Entity;

public class Packet32EntityLook
extends Packet30Entity {
    public Packet32EntityLook() {
        this.rotating = true;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        super.readPacketData(dataInputStream1);
        this.yaw = dataInputStream1.readByte();
        this.pitch = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        super.writePacketData(dataOutputStream1);
        dataOutputStream1.writeByte(this.yaw);
        dataOutputStream1.writeByte(this.pitch);
    }

    @Override
    public int getPacketSize() {
        return 6;
    }
}

