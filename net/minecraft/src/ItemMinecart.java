/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemMinecart
extends Item {
    public int minecartType;

    public ItemMinecart(int i1, int i2) {
        super(i1);
        this.maxStackSize = 1;
        this.minecartType = i2;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        int i8 = world3.getBlockId(i4, i5, i6);
        if (i8 == Block.minecartTrack.blockID) {
            if (!world3.multiplayerWorld) {
                world3.entityJoinedWorld(new EntityMinecart(world3, (float)i4 + 0.5f, (float)i5 + 0.5f, (float)i6 + 0.5f, this.minecartType));
            }
            --itemStack1.stackSize;
            return true;
        }
        return false;
    }
}

