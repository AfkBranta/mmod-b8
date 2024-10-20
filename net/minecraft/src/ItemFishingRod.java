/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFishingRod
extends Item {
    public ItemFishingRod(int i1) {
        super(i1);
        this.setMaxDamage(64);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
        if (entityPlayer3.fishEntity != null) {
            int i4 = entityPlayer3.fishEntity.catchFish();
            itemStack1.func_25190_a(i4, entityPlayer3);
            entityPlayer3.swingItem();
        } else {
            world2.playSoundAtEntity(entityPlayer3, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world2.multiplayerWorld) {
                world2.entityJoinedWorld(new EntityFish(world2, entityPlayer3));
            }
            entityPlayer3.swingItem();
        }
        return itemStack1;
    }
}

