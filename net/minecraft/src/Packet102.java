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

public class Packet102
extends Packet {
    public int window_Id;
    public int inventorySlot;
    public int mouseClick;
    public short action;
    public ItemStack itemStack;

    public Packet102() {
    }

    public Packet102(int i1, int i2, int i3, ItemStack itemStack4, short s5) {
        this.window_Id = i1;
        this.inventorySlot = i2;
        this.mouseClick = i3;
        this.itemStack = itemStack4;
        this.action = s5;
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20091_a(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.window_Id = dataInputStream1.readByte();
        this.inventorySlot = dataInputStream1.readShort();
        this.mouseClick = dataInputStream1.readByte();
        this.action = dataInputStream1.readShort();
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
        dataOutputStream1.writeByte(this.window_Id);
        dataOutputStream1.writeShort(this.inventorySlot);
        dataOutputStream1.writeByte(this.mouseClick);
        dataOutputStream1.writeShort(this.action);
        if (this.itemStack == null) {
            dataOutputStream1.writeShort(-1);
        } else {
            dataOutputStream1.writeShort(this.itemStack.itemID);
            dataOutputStream1.writeByte(this.itemStack.stackSize);
            dataOutputStream1.writeShort(this.itemStack.getItemDamage());
        }
    }

    @Override
    public int getPacketSize() {
        return 11;
    }
}

