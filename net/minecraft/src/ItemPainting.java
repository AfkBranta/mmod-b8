/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemPainting
extends Item {
    public ItemPainting(int i1) {
        super(i1);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        EntityPainting entityPainting9;
        if (i7 == 0) {
            return false;
        }
        if (i7 == 1) {
            return false;
        }
        int b8 = 0;
        if (i7 == 4) {
            b8 = 1;
        }
        if (i7 == 3) {
            b8 = 2;
        }
        if (i7 == 5) {
            b8 = 3;
        }
        if ((entityPainting9 = new EntityPainting(world3, i4, i5, i6, b8)).func_410_i()) {
            if (!world3.multiplayerWorld) {
                world3.entityJoinedWorld(entityPainting9);
            }
            --itemStack1.stackSize;
        }
        return true;
    }
}

