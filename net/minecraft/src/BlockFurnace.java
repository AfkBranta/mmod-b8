/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;

public class BlockFurnace
extends BlockContainer {
    private final boolean isActive;

    protected BlockFurnace(int i1, boolean z2) {
        super(i1, Material.rock);
        this.isActive = z2;
        this.blockIndexInTexture = 45;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.stoneOvenIdle.blockID;
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        super.onBlockAdded(world1, i2, i3, i4);
        this.setDefaultDirection(world1, i2, i3, i4);
    }

    private void setDefaultDirection(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockId(i2, i3, i4 - 1);
        int i6 = world1.getBlockId(i2, i3, i4 + 1);
        int i7 = world1.getBlockId(i2 - 1, i3, i4);
        int i8 = world1.getBlockId(i2 + 1, i3, i4);
        int b9 = 3;
        if (Block.opaqueCubeLookup[i5] && !Block.opaqueCubeLookup[i6]) {
            b9 = 3;
        }
        if (Block.opaqueCubeLookup[i6] && !Block.opaqueCubeLookup[i5]) {
            b9 = 2;
        }
        if (Block.opaqueCubeLookup[i7] && !Block.opaqueCubeLookup[i8]) {
            b9 = 5;
        }
        if (Block.opaqueCubeLookup[i8] && !Block.opaqueCubeLookup[i7]) {
            b9 = 4;
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, b9);
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (i5 == 1) {
            return this.blockIndexInTexture + 17;
        }
        if (i5 == 0) {
            return this.blockIndexInTexture + 17;
        }
        int i6 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        return i5 != i6 ? this.blockIndexInTexture : (this.isActive ? this.blockIndexInTexture + 16 : this.blockIndexInTexture - 1);
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        if (this.isActive) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            float f7 = (float)i2 + 0.5f;
            float f8 = (float)i3 + 0.0f + random5.nextFloat() * 6.0f / 16.0f;
            float f9 = (float)i4 + 0.5f;
            float f10 = 0.52f;
            float f11 = random5.nextFloat() * 0.6f - 0.3f;
            if (i6 == 4) {
                world1.spawnParticle("smoke", f7 - f10, f8, f9 + f11, 0.0, 0.0, 0.0);
                world1.spawnParticle("flame", f7 - f10, f8, f9 + f11, 0.0, 0.0, 0.0);
            } else if (i6 == 5) {
                world1.spawnParticle("smoke", f7 + f10, f8, f9 + f11, 0.0, 0.0, 0.0);
                world1.spawnParticle("flame", f7 + f10, f8, f9 + f11, 0.0, 0.0, 0.0);
            } else if (i6 == 2) {
                world1.spawnParticle("smoke", f7 + f11, f8, f9 - f10, 0.0, 0.0, 0.0);
                world1.spawnParticle("flame", f7 + f11, f8, f9 - f10, 0.0, 0.0, 0.0);
            } else if (i6 == 3) {
                world1.spawnParticle("smoke", f7 + f11, f8, f9 + f10, 0.0, 0.0, 0.0);
                world1.spawnParticle("flame", f7 + f11, f8, f9 + f10, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 1 ? this.blockIndexInTexture + 17 : (i1 == 0 ? this.blockIndexInTexture + 17 : (i1 == 3 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture));
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (world1.multiplayerWorld) {
            return true;
        }
        TileEntityFurnace tileEntityFurnace6 = (TileEntityFurnace)world1.getBlockTileEntity(i2, i3, i4);
        entityPlayer5.displayGUIFurnace(tileEntityFurnace6);
        return true;
    }

    public static void updateFurnaceBlockState(boolean z0, World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockMetadata(i2, i3, i4);
        TileEntity tileEntity6 = world1.getBlockTileEntity(i2, i3, i4);
        if (z0) {
            world1.setBlockWithNotify(i2, i3, i4, Block.stoneOvenActive.blockID);
        } else {
            world1.setBlockWithNotify(i2, i3, i4, Block.stoneOvenIdle.blockID);
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, i5);
        world1.setBlockTileEntity(i2, i3, i4, tileEntity6);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityFurnace();
    }

    @Override
    public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
        int i6 = MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (i6 == 0) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 2);
        }
        if (i6 == 1) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 5);
        }
        if (i6 == 2) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 3);
        }
        if (i6 == 3) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 4);
        }
    }
}

