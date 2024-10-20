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

public class Packet104
extends Packet {
    public int windowId;
    public ItemStack[] itemStack;

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.windowId = dataInputStream1.readByte();
        int s2 = dataInputStream1.readShort();
        this.itemStack = new ItemStack[s2];
        int i3 = 0;
        while (i3 < s2) {
            short s4 = dataInputStream1.readShort();
            if (s4 >= 0) {
                byte b5 = dataInputStream1.readByte();
                short s6 = dataInputStream1.readShort();
                this.itemStack[i3] = new ItemStack(s4, (int)b5, (int)s6);
            }
            ++i3;
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.writeByte(this.windowId);
        dataOutputStream1.writeShort(this.itemStack.length);
        int i2 = 0;
        while (i2 < this.itemStack.length) {
            if (this.itemStack[i2] == null) {
                dataOutputStream1.writeShort(-1);
            } else {
                dataOutputStream1.writeShort((short)this.itemStack[i2].itemID);
                dataOutputStream1.writeByte((byte)this.itemStack[i2].stackSize);
                dataOutputStream1.writeShort((short)this.itemStack[i2].getItemDamage());
            }
            ++i2;
        }
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.func_20094_a(this);
    }

    @Override
    public int getPacketSize() {
        return 3 + this.itemStack.length * 5;
    }
}

