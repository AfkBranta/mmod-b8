/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemSeeds
extends Item {
    private int field_318_a;

    public ItemSeeds(int i1, int i2) {
        super(i1);
        this.field_318_a = i2;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        if (i7 != 1) {
            return false;
        }
        int i8 = world3.getBlockId(i4, i5, i6);
        if (i8 == Block.tilledField.blockID && world3.isAirBlock(i4, i5 + 1, i6)) {
            world3.setBlockWithNotify(i4, i5 + 1, i6, this.field_318_a);
            --itemStack1.stackSize;
            return true;
        }
        return false;
    }
}

