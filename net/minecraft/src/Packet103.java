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

public class Packet103
extends Packet {
    public int windowId;
    public int itemSlot;
    public ItemStack myItemStack;

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20088_a(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.windowId = dataInputStream1.readByte();
        this.itemSlot = dataInputStream1.readShort();
        short s2 = dataInputStream1.readShort();
        if (s2 >= 0) {
            byte b3 = dataInputStream1.readByte();
            short s4 = dataInputStream1.readShort();
            this.myItemStack = new ItemStack(s2, (int)b3, (int)s4);
        } else {
            this.myItemStack = null;
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeByte(this.windowId);
        dataOutputStream1.writeShort(this.itemSlot);
        if (this.myItemStack == null) {
            dataOutputStream1.writeShort(-1);
        } else {
            dataOutputStream1.writeShort(this.myItemStack.itemID);
            dataOutputStream1.writeByte(this.myItemStack.stackSize);
            dataOutputStream1.writeShort(this.myItemStack.getItemDamage());
        }
    }

    @Override
    public int getPacketSize() {
        return 8;
    }
}

