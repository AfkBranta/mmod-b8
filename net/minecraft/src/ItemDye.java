/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.BlockCrops;
import net.minecraft.src.BlockSapling;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemDye
extends Item {
    public static final String[] dyeColors = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};

    public ItemDye(int i1) {
        super(i1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getIconIndex(ItemStack itemStack1) {
        int i2 = itemStack1.getItemDamage();
        return this.iconIndex + i2 % 8 * 16 + i2 / 8;
    }

    @Override
    public String getItemNameIS(ItemStack itemStack1) {
        return String.valueOf(super.getItemName()) + "." + dyeColors[itemStack1.getItemDamage()];
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        if (itemStack1.getItemDamage() == 15) {
            int i8 = world3.getBlockId(i4, i5, i6);
            if (i8 == Block.sapling.blockID) {
                ((BlockSapling)Block.sapling).growTree(world3, i4, i5, i6, world3.rand);
                --itemStack1.stackSize;
                return true;
            }
            if (i8 == Block.crops.blockID) {
                ((BlockCrops)Block.crops).fertilize(world3, i4, i5, i6);
                --itemStack1.stackSize;
                return true;
            }
        }
        return false;
    }

    @Override
    public void saddleEntity(ItemStack itemStack1, EntityLiving entityLiving2) {
        if (entityLiving2 instanceof EntitySheep) {
            EntitySheep entitySheep3 = (EntitySheep)entityLiving2;
            int i4 = BlockCloth.func_21034_c(itemStack1.getItemDamage());
            if (!entitySheep3.getSheared() && entitySheep3.getFleeceColor() != i4) {
                entitySheep3.setFleeceColor(i4);
                --itemStack1.stackSize;
            }
        }
    }
}

