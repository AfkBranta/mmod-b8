/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFlintAndSteel
extends Item {
    public ItemFlintAndSteel(int i1) {
        super(i1);
        this.maxStackSize = 1;
        this.setMaxDamage(64);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        int i8;
        if (i7 == 0) {
            --i5;
        }
        if (i7 == 1) {
            ++i5;
        }
        if (i7 == 2) {
            --i6;
        }
        if (i7 == 3) {
            ++i6;
        }
        if (i7 == 4) {
            --i4;
        }
        if (i7 == 5) {
            ++i4;
        }
        if ((i8 = world3.getBlockId(i4, i5, i6)) == 0) {
            world3.playSoundEffect((double)i4 + 0.5, (double)i5 + 0.5, (double)i6 + 0.5, "fire.ignite", 1.0f, itemRand.nextFloat() * 0.4f + 0.8f);
            world3.setBlockWithNotify(i4, i5, i6, Block.fire.blockID);
        }
        itemStack1.func_25190_a(1, entityPlayer2);
        return true;
    }
}

