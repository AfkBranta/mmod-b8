/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet34EntityTeleport
extends Packet {
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte yaw;
    public byte pitch;

    public Packet34EntityTeleport() {
    }

    public Packet34EntityTeleport(Entity entity1) {
        this.entityId = entity1.entityId;
        this.xPosition = MathHelper.floor_double(entity1.posX * 32.0);
        this.yPosition = MathHelper.floor_double(entity1.posY * 32.0);
        this.zPosition = MathHelper.floor_double(entity1.posZ * 32.0);
        this.yaw = (byte)(entity1.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entity1.rotationPitch * 256.0f / 360.0f);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.entityId = dataInputStream1.readInt();
        this.xPosition = dataInputStream1.readInt();
        this.yPosition = dataInputStream1.readInt();
        this.zPosition = dataInputStream1.readInt();
        this.yaw = (byte)dataInputStream1.read();
        this.pitch = (byte)dataInputStream1.read();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.entityId);
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.writeInt(this.yPosition);
        dataOutputStream1.writeInt(this.zPosition);
        dataOutputStream1.write(this.yaw);
        dataOutputStream1.write(this.pitch);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleEntityTeleport(this);
    }

    @Override
    public int getPacketSize() {
        return 34;
    }
}

