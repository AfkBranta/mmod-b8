/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockPumpkin
extends Block {
    private boolean blockType;

    protected BlockPumpkin(int i1, int i2, boolean z3) {
        super(i1, Material.pumpkin);
        this.blockIndexInTexture = i2;
        this.setTickOnLoad(true);
        this.blockType = z3;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        if (i1 == 1) {
            return this.blockIndexInTexture;
        }
        if (i1 == 0) {
            return this.blockIndexInTexture;
        }
        int i3 = this.blockIndexInTexture + 1 + 16;
        if (this.blockType) {
            ++i3;
        }
        return i2 == 0 && i1 == 2 ? i3 : (i2 == 1 && i1 == 5 ? i3 : (i2 == 2 && i1 == 3 ? i3 : (i2 == 3 && i1 == 4 ? i3 : this.blockIndexInTexture + 16)));
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 1 ? this.blockIndexInTexture : (i1 == 0 ? this.blockIndexInTexture : (i1 == 3 ? this.blockIndexInTexture + 1 + 16 : this.blockIndexInTexture + 16));
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        super.onBlockAdded(world1, i2, i3, i4);
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockId(i2, i3, i4);
        return (i5 == 0 || Block.blocksList[i5].blockMaterial.getIsLiquid()) && world1.isBlockOpaqueCube(i2, i3 - 1, i4);
    }

    @Override
    public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
        int i6 = MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        world1.setBlockMetadataWithNotify(i2, i3, i4, i6);
    }
}

