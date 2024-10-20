/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class ItemHoe
extends Item {
    public ItemHoe(int i1, EnumToolMaterial enumToolMaterial2) {
        super(i1);
        this.maxStackSize = 1;
        this.setMaxDamage(enumToolMaterial2.getMaxUses());
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        int i8 = world3.getBlockId(i4, i5, i6);
        Material material9 = world3.getBlockMaterial(i4, i5 + 1, i6);
        if ((material9.isSolid() || i8 != Block.grass.blockID) && i8 != Block.dirt.blockID) {
            return false;
        }
        Block block10 = Block.tilledField;
        world3.playSoundEffect((float)i4 + 0.5f, (float)i5 + 0.5f, (float)i6 + 0.5f, block10.stepSound.func_1145_d(), (block10.stepSound.func_1147_b() + 1.0f) / 2.0f, block10.stepSound.func_1144_c() * 0.8f);
        if (world3.multiplayerWorld) {
            return true;
        }
        world3.setBlockWithNotify(i4, i5, i6, block10.blockID);
        itemStack1.func_25190_a(1, entityPlayer2);
        if (world3.rand.nextInt(8) == 0 && i8 == Block.grass.blockID) {
            int b11 = 1;
            int i12 = 0;
            while (i12 < b11) {
                float f13 = 0.7f;
                float f14 = world3.rand.nextFloat() * f13 + (1.0f - f13) * 0.5f;
                float f15 = 1.2f;
                float f16 = world3.rand.nextFloat() * f13 + (1.0f - f13) * 0.5f;
                EntityItem entityItem17 = new EntityItem(world3, (float)i4 + f14, (float)i5 + f15, (float)i6 + f16, new ItemStack(Item.seeds));
                entityItem17.delayBeforeCanPickup = 10;
                world3.entityJoinedWorld(entityItem17);
                ++i12;
            }
        }
        return true;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

