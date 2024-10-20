/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet15Place
extends Packet {
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int direction;
    public ItemStack itemStack;

    public Packet15Place() {
    }

    public Packet15Place(int i1, int i2, int i3, int i4, ItemStack itemStack5) {
        this.xPosition = i1;
        this.yPosition = i2;
        this.zPosition = i3;
        this.direction = i4;
        this.itemStack = itemStack5;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.xPosition = dataInputStream1.readInt();
        this.yPosition = dataInputStream1.read();
        this.zPosition = dataInputStream1.readInt();
        this.direction = dataInputStream1.read();
        short s2 = dataInputStream1.readShort();
        if (s2 >= 0) {
            byte b3 = dataInputStream1.readByte();
            short s4 = dataInputStream1.readShort();
            this.itemStack = new ItemStack(s2, (int)b3, (int)s4);
        } else {
            this.itemStack = null;
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeInt(this.xPosition);
        dataOutputStream1.write(this.yPosition);
        dataOutputStream1.writeInt(this.zPosition);
        dataOutputStream1.write(this.direction);
        if (this.itemStack == null) {
            dataOutputStream1.writeShort(-1);
        } else {
            dataOutputStream1.writeShort(this.itemStack.itemID);
            dataOutputStream1.writeByte(this.itemStack.stackSize);
            dataOutputStream1.writeShort(this.itemStack.getItemDamage());
        }
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handlePlace(this);
    }

    @Override
    public int getPacketSize() {
        return 15;
    }
}

