/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Packet30Entity;

public class Packet33RelEntityMoveLook
extends Packet30Entity {
    public Packet33RelEntityMoveLook() {
        this.rotating = true;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        super.readPacketData(dataInputStream1);
        this.xPosition = dataInputStream1.readByte();
        this.yPosition = dataInputStream1.readByte();
        this.zPosition = dataInputStream1.readByte();
        this.yaw = dataInputStream1.readByte();
        this.pitch = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        super.writePacketData(dataOutputStream1);
        dataOutputStream1.writeByte(this.xPosition);
        dataOutputStream1.writeByte(this.yPosition);
        dataOutputStream1.writeByte(this.zPosition);
        dataOutputStream1.writeByte(this.yaw);
        dataOutputStream1.writeByte(this.pitch);
    }

    @Override
    public int getPacketSize() {
        return 9;
    }
}

