/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class BlockStairs
extends Block {
    private Block modelBlock;

    protected BlockStairs(int i1, Block block2) {
        super(i1, block2.blockIndexInTexture, block2.blockMaterial);
        this.modelBlock = block2;
        this.setHardness(block2.blockHardness);
        this.setResistance(block2.blockResistance / 3.0f);
        this.setStepSound(block2.stepSound);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return super.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
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
    public int getRenderType() {
        return 10;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
    }

    @Override
    public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
        int i7 = world1.getBlockMetadata(i2, i3, i4);
        if (i7 == 0) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            this.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
        } else if (i7 == 1) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            this.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
        } else if (i7 == 2) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            this.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
        } else if (i7 == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
            this.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        this.modelBlock.randomDisplayTick(world1, i2, i3, i4, random5);
    }

    @Override
    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        this.modelBlock.onBlockClicked(world1, i2, i3, i4, entityPlayer5);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
        this.modelBlock.onBlockDestroyedByPlayer(world1, i2, i3, i4, i5);
    }

    @Override
    public float getBlockBrightness(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        return this.modelBlock.getBlockBrightness(iBlockAccess1, i2, i3, i4);
    }

    @Override
    public float getExplosionResistance(Entity entity1) {
        return this.modelBlock.getExplosionResistance(entity1);
    }

    @Override
    public int getRenderBlockPass() {
        return this.modelBlock.getRenderBlockPass();
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return this.modelBlock.idDropped(i1, random2);
    }

    @Override
    public int quantityDropped(Random random1) {
        return this.modelBlock.quantityDropped(random1);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return this.modelBlock.getBlockTextureFromSideAndMetadata(i1, i2);
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return this.modelBlock.getBlockTextureFromSide(i1);
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return this.modelBlock.getBlockTexture(iBlockAccess1, i2, i3, i4, i5);
    }

    @Override
    public int tickRate() {
        return this.modelBlock.tickRate();
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return this.modelBlock.getSelectedBoundingBoxFromPool(world1, i2, i3, i4);
    }

    @Override
    public void velocityToAddToEntity(World world1, int i2, int i3, int i4, Entity entity5, Vec3D vec3D6) {
        this.modelBlock.velocityToAddToEntity(world1, i2, i3, i4, entity5, vec3D6);
    }

    @Override
    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }

    @Override
    public boolean canCollideCheck(int i1, boolean z2) {
        return this.modelBlock.canCollideCheck(i1, z2);
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return this.modelBlock.canPlaceBlockAt(world1, i2, i3, i4);
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        this.onNeighborBlockChange(world1, i2, i3, i4, 0);
        this.modelBlock.onBlockAdded(world1, i2, i3, i4);
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        this.modelBlock.onBlockRemoval(world1, i2, i3, i4);
    }

    @Override
    public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
        this.modelBlock.dropBlockAsItemWithChance(world1, i2, i3, i4, i5, f6);
    }

    @Override
    public void dropBlockAsItem(World world1, int i2, int i3, int i4, int i5) {
        this.modelBlock.dropBlockAsItem(world1, i2, i3, i4, i5);
    }

    @Override
    public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
        this.modelBlock.onEntityWalking(world1, i2, i3, i4, entity5);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        this.modelBlock.updateTick(world1, i2, i3, i4, random5);
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        return this.modelBlock.blockActivated(world1, i2, i3, i4, entityPlayer5);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
        this.modelBlock.onBlockDestroyedByExplosion(world1, i2, i3, i4);
    }

    @Override
    public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
        int i6 = MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (i6 == 0) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 2);
        }
        if (i6 == 1) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 1);
        }
        if (i6 == 2) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 3);
        }
        if (i6 == 3) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 0);
        }
    }
}

