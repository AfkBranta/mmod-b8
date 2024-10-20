/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPig;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemSaddle
extends Item {
    public ItemSaddle(int i1) {
        super(i1);
        this.maxStackSize = 1;
    }

    @Override
    public void saddleEntity(ItemStack itemStack1, EntityLiving entityLiving2) {
        EntityPig entityPig3;
        if (entityLiving2 instanceof EntityPig && !(entityPig3 = (EntityPig)entityLiving2).getSaddled()) {
            entityPig3.setSaddled(true);
            --itemStack1.stackSize;
        }
    }

    @Override
    public boolean hitEntity(ItemStack itemStack1, EntityLiving entityLiving2, EntityLiving entityLiving3) {
        this.saddleEntity(itemStack1, entityLiving2);
        return true;
    }
}

