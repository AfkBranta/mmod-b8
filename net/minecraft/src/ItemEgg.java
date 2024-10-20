/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemEgg
extends Item {
    public ItemEgg(int i1) {
        super(i1);
        this.maxStackSize = 16;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
        --itemStack1.stackSize;
        world2.playSoundAtEntity(entityPlayer3, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world2.multiplayerWorld) {
            world2.entityJoinedWorld(new EntityEgg(world2, entityPlayer3));
        }
        return itemStack1;
    }
}

