/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileEntityNote
extends TileEntity {
    public byte note = 0;
    public boolean previousRedstoneState = false;

    @Override
    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        nBTTagCompound1.setByte("note", this.note);
    }

    @Override
    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);
        this.note = nBTTagCompound1.getByte("note");
        if (this.note < 0) {
            this.note = 0;
        }
        if (this.note > 24) {
            this.note = (byte)24;
        }
    }

    public void changePitch() {
        this.note = (byte)((this.note + 1) % 25);
        this.onInventoryChanged();
    }

    public void triggerNote(World world1, int i2, int i3, int i4) {
        if (world1.getBlockMaterial(i2, i3 + 1, i4) == Material.air) {
            Material material5 = world1.getBlockMaterial(i2, i3 - 1, i4);
            int b6 = 0;
            if (material5 == Material.rock) {
                b6 = 1;
            }
            if (material5 == Material.sand) {
                b6 = 2;
            }
            if (material5 == Material.glass) {
                b6 = 3;
            }
            if (material5 == Material.wood) {
                b6 = 4;
            }
            world1.playNoteAt(i2, i3, i4, b6, this.note);
        }
    }
}

