/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMobType;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockPressurePlate
extends Block {
    private EnumMobType triggerMobType;

    protected BlockPressurePlate(int i1, int i2, EnumMobType enumMobType3) {
        super(i1, i2, Material.rock);
        this.triggerMobType = enumMobType3;
        this.setTickOnLoad(true);
        float f4 = 0.0625f;
        this.setBlockBounds(f4, 0.0f, f4, 1.0f - f4, 0.03125f, 1.0f - f4);
    }

    @Override
    public int tickRate() {
        return 20;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2, i3 - 1, i4);
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        boolean z6 = false;
        if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
            z6 = true;
        }
        if (z6) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (!world1.multiplayerWorld && world1.getBlockMetadata(i2, i3, i4) != 0) {
            this.setStateIfMobInteractsWithPlate(world1, i2, i3, i4);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
        if (!world1.multiplayerWorld && world1.getBlockMetadata(i2, i3, i4) != 1) {
            this.setStateIfMobInteractsWithPlate(world1, i2, i3, i4);
        }
    }

    private void setStateIfMobInteractsWithPlate(World world1, int i2, int i3, int i4) {
        boolean z5 = world1.getBlockMetadata(i2, i3, i4) == 1;
        boolean z6 = false;
        float f7 = 0.125f;
        List list8 = null;
        if (this.triggerMobType == EnumMobType.everything) {
            list8 = world1.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f7, i3, (float)i4 + f7, (float)(i2 + 1) - f7, (double)i3 + 0.25, (float)(i4 + 1) - f7));
        }
        if (this.triggerMobType == EnumMobType.mobs) {
            list8 = world1.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f7, i3, (float)i4 + f7, (float)(i2 + 1) - f7, (double)i3 + 0.25, (float)(i4 + 1) - f7));
        }
        if (this.triggerMobType == EnumMobType.players) {
            list8 = world1.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f7, i3, (float)i4 + f7, (float)(i2 + 1) - f7, (double)i3 + 0.25, (float)(i4 + 1) - f7));
        }
        if (list8.size() > 0) {
            z6 = true;
        }
        if (z6 && !z5) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 1);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
            world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.1, (double)i4 + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!z6 && z5) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 0);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
            world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.1, (double)i4 + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (z6) {
            world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
        }
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockMetadata(i2, i3, i4);
        if (i5 > 0) {
            world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
        }
        super.onBlockRemoval(world1, i2, i3, i4);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        boolean z5 = iBlockAccess1.getBlockMetadata(i2, i3, i4) == 1;
        float f6 = 0.0625f;
        if (z5) {
            this.setBlockBounds(f6, 0.0f, f6, 1.0f - f6, 0.03125f, 1.0f - f6);
        } else {
            this.setBlockBounds(f6, 0.0f, f6, 1.0f - f6, 0.0625f, 1.0f - f6);
        }
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return iBlockAccess1.getBlockMetadata(i2, i3, i4) > 0;
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
        return world1.getBlockMetadata(i2, i3, i4) == 0 ? false : i5 == 1;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float f1 = 0.5f;
        float f2 = 0.125f;
        float f3 = 0.5f;
        this.setBlockBounds(0.5f - f1, 0.5f - f2, 0.5f - f3, 0.5f + f1, 0.5f + f2, 0.5f + f3);
    }
}

