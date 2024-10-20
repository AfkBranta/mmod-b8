/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockJukeBox
extends Block {
    protected BlockJukeBox(int i1, int i2) {
        super(i1, i2, Material.wood);
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return this.blockIndexInTexture + (i1 == 1 ? 1 : 0);
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if (i6 > 0) {
            this.ejectRecord(world1, i2, i3, i4, i6);
            return true;
        }
        return false;
    }

    public void ejectRecord(World world1, int i2, int i3, int i4, int i5) {
        world1.playRecord(null, i2, i3, i4);
        world1.setBlockMetadataWithNotify(i2, i3, i4, 0);
        int i6 = Item.record13.shiftedIndex + i5 - 1;
        float f7 = 0.7f;
        double d8 = (double)(world1.rand.nextFloat() * f7) + (double)(1.0f - f7) * 0.5;
        double d10 = (double)(world1.rand.nextFloat() * f7) + (double)(1.0f - f7) * 0.2 + 0.6;
        double d12 = (double)(world1.rand.nextFloat() * f7) + (double)(1.0f - f7) * 0.5;
        EntityItem entityItem14 = new EntityItem(world1, (double)i2 + d8, (double)i3 + d10, (double)i4 + d12, new ItemStack(i6, 1, 0));
        entityItem14.delayBeforeCanPickup = 10;
        world1.entityJoinedWorld(entityItem14);
    }

    @Override
    public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
        if (!world1.multiplayerWorld) {
            if (i5 > 0) {
                this.ejectRecord(world1, i2, i3, i4, i5);
            }
            super.dropBlockAsItemWithChance(world1, i2, i3, i4, i5, f6);
        }
    }
}

