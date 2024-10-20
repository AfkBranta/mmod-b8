/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet20NamedEntitySpawn
extends Packet {
    public int entityId;
    public String name;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte rotation;
    public byte pitch;
    public int currentItem;

    public Packet20NamedEntitySpawn() {
    }

    public Packet20NamedEntitySpawn(EntityPlayer entityPlayer1) {
        this.entityId = entityPlayer1.entityId;
        this.name = entityPlayer1.username;
        this.xPosition = MathHelper.floor_double(entityPlayer1.posX * 32.0);
        this.yPosition = MathHelper.floor_double(entityPlayer1.posY * 32.0);
        this.zPosition = MathHelper.floor_double(entityPlayer1.posZ * 32.0);
        this.rotation = (byte)(entityPlayer1.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entityPlayer1.rotationPitch * 256.0f / 360.0f);
        ItemStack itemStack2 = entityPlayer1.inventory.getCurrentItem();
        this.currentItem = itemStack2 == null ? 0 : itemStack2.itemID;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.entityId = dataInputStream1.readInt();
        this.name = dataInputStream1.readUTF();
        this.xPosition = dataInputStream1.readInt();
        this.yPosition = dataInputStream1.readInt();
        this.zPosition = dataInputStream1.readInt();
        this.rotation = dataInputStream1.readByte();
        this.pitch = dataInputStream1.readByte();
        this.currentItem = dataInputStream1.readShort();
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.entityId);
        dataOutputStream1.writeUTF(this.name);
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.writeInt(this.yPosition);
        dataOutputStream1.writeInt(this.zPosition);
        dataOutputStream1.writeByte(this.rotation);
        dataOutputStream1.writeByte(this.pitch);
        dataOutputStream1.writeShort(this.currentItem);
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleNamedEntitySpawn(this);
    }

    @Override
    public int getPacketSize() {
        return 28;
    }
}

