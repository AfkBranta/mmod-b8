/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class ItemDoor
extends Item {
    private Material field_321_a;

    public ItemDoor(int i1, Material material2) {
        super(i1);
        this.field_321_a = material2;
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        if (i7 != 1) {
            return false;
        }
        Block block8 = this.field_321_a == Material.wood ? Block.doorWood : Block.doorSteel;
        if (!block8.canPlaceBlockAt(world3, i4, ++i5, i6)) {
            return false;
        }
        int i9 = MathHelper.floor_double((double)((entityPlayer2.rotationYaw + 180.0f) * 4.0f / 360.0f) - 0.5) & 3;
        int b10 = 0;
        int b11 = 0;
        if (i9 == 0) {
            b11 = 1;
        }
        if (i9 == 1) {
            b10 = -1;
        }
        if (i9 == 2) {
            b11 = -1;
        }
        if (i9 == 3) {
            b10 = 1;
        }
        int i12 = (world3.isBlockOpaqueCube(i4 - b10, i5, i6 - b11) ? 1 : 0) + (world3.isBlockOpaqueCube(i4 - b10, i5 + 1, i6 - b11) ? 1 : 0);
        int i13 = (world3.isBlockOpaqueCube(i4 + b10, i5, i6 + b11) ? 1 : 0) + (world3.isBlockOpaqueCube(i4 + b10, i5 + 1, i6 + b11) ? 1 : 0);
        boolean z14 = world3.getBlockId(i4 - b10, i5, i6 - b11) == block8.blockID || world3.getBlockId(i4 - b10, i5 + 1, i6 - b11) == block8.blockID;
        boolean z15 = world3.getBlockId(i4 + b10, i5, i6 + b11) == block8.blockID || world3.getBlockId(i4 + b10, i5 + 1, i6 + b11) == block8.blockID;
        boolean z16 = false;
        if (z14 && !z15) {
            z16 = true;
        } else if (i13 > i12) {
            z16 = true;
        }
        if (z16) {
            i9 = i9 - 1 & 3;
            i9 += 4;
        }
        world3.setBlockWithNotify(i4, i5, i6, block8.blockID);
        world3.setBlockMetadataWithNotify(i4, i5, i6, i9);
        world3.setBlockWithNotify(i4, i5 + 1, i6, block8.blockID);
        world3.setBlockMetadataWithNotify(i4, i5 + 1, i6, i9 + 8);
        --itemStack1.stackSize;
        return true;
    }
}

