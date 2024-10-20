/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Packet30Entity;

public class Packet31RelEntityMove
extends Packet30Entity {
    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        super.readPacketData(dataInputStream1);
        this.xPosition = dataInputStream1.readByte();
        this.yPosition = dataInputStream1.readByte();
        this.zPosition = dataInputStream1.readByte();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        super.writePacketData(dataOutputStream1);
        dataOutputStream1.writeByte(this.xPosition);
        dataOutputStream1.writeByte(this.yPosition);
        dataOutputStream1.writeByte(this.zPosition);
    }

    @Override
    public int getPacketSize() {
        return 7;
    }
}

