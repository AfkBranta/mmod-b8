/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemBow
extends Item {
    public ItemBow(int i1) {
        super(i1);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
        if (entityPlayer3.inventory.consumeInventoryItem(Item.arrow.shiftedIndex)) {
            world2.playSoundAtEntity(entityPlayer3, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world2.multiplayerWorld) {
                world2.entityJoinedWorld(new EntityArrow(world2, entityPlayer3));
            }
        }
        return itemStack1;
    }
}

