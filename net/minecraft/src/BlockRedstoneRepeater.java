/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class BlockRedstoneRepeater
extends Block {
    public static final double[] field_22024_a = new double[]{-0.0625, 0.0625, 0.1875, 0.3125};
    private static final int[] field_22023_b = new int[]{1, 2, 3, 4};
    private final boolean field_22025_c;

    protected BlockRedstoneRepeater(int i1, boolean z2) {
        super(i1, 102, Material.circuits);
        this.field_22025_c = z2;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return !world1.isBlockOpaqueCube(i2, i3 - 1, i4) ? false : super.canPlaceBlockAt(world1, i2, i3, i4);
    }

    @Override
    public boolean canBlockStay(World world1, int i2, int i3, int i4) {
        return !world1.isBlockOpaqueCube(i2, i3 - 1, i4) ? false : super.canBlockStay(world1, i2, i3, i4);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        boolean z7 = this.func_22022_g(world1, i2, i3, i4, i6);
        if (this.field_22025_c && !z7) {
            world1.setBlockAndMetadataWithNotify(i2, i3, i4, Block.redstoneRepeaterIdle.blockID, i6);
        } else if (!this.field_22025_c) {
            world1.setBlockAndMetadataWithNotify(i2, i3, i4, Block.redstoneRepeaterActive.blockID, i6);
            if (!z7) {
                int i8 = (i6 & 0xC) >> 2;
                world1.scheduleBlockUpdate(i2, i3, i4, Block.redstoneRepeaterActive.blockID, field_22023_b[i8] * 2);
            }
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return i1 == 0 ? (this.field_22025_c ? 99 : 115) : (i1 == 1 ? (this.field_22025_c ? 147 : 131) : 5);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return i5 != 0 && i5 != 1;
    }

    @Override
    public int getRenderType() {
        return 15;
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return this.getBlockTextureFromSideAndMetadata(i1, 0);
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
        return this.isPoweringTo(world1, i2, i3, i4, i5);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (!this.field_22025_c) {
            return false;
        }
        int i6 = iBlockAccess1.getBlockMetadata(i2, i3, i4) & 3;
        return i6 == 0 && i5 == 3 ? true : (i6 == 1 && i5 == 4 ? true : (i6 == 2 && i5 == 2 ? true : i6 == 3 && i5 == 5));
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (!this.canBlockStay(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        } else {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            boolean z7 = this.func_22022_g(world1, i2, i3, i4, i6);
            int i8 = (i6 & 0xC) >> 2;
            if (this.field_22025_c && !z7) {
                world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, field_22023_b[i8] * 2);
            } else if (!this.field_22025_c && z7) {
                world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, field_22023_b[i8] * 2);
            }
        }
    }

    private boolean func_22022_g(World world1, int i2, int i3, int i4, int i5) {
        int i6 = i5 & 3;
        switch (i6) {
            case 0: {
                return world1.isBlockIndirectlyProvidingPowerTo(i2, i3, i4 + 1, 3);
            }
            case 1: {
                return world1.isBlockIndirectlyProvidingPowerTo(i2 - 1, i3, i4, 4);
            }
            case 2: {
                return world1.isBlockIndirectlyProvidingPowerTo(i2, i3, i4 - 1, 2);
            }
            case 3: {
                return world1.isBlockIndirectlyProvidingPowerTo(i2 + 1, i3, i4, 5);
            }
        }
        return false;
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        int i7 = (i6 & 0xC) >> 2;
        i7 = i7 + 1 << 2 & 0xC;
        world1.setBlockMetadataWithNotify(i2, i3, i4, i7 | i6 & 3);
        return true;
    }

    @Override
    public boolean canProvidePower() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
        int i6 = ((MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) + 2) % 4;
        world1.setBlockMetadataWithNotify(i2, i3, i4, i6);
        boolean z7 = this.func_22022_g(world1, i2, i3, i4, i6);
        if (z7) {
            world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, 1);
        }
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        world1.notifyBlocksOfNeighborChange(i2 + 1, i3, i4, this.blockID);
        world1.notifyBlocksOfNeighborChange(i2 - 1, i3, i4, this.blockID);
        world1.notifyBlocksOfNeighborChange(i2, i3, i4 + 1, this.blockID);
        world1.notifyBlocksOfNeighborChange(i2, i3, i4 - 1, this.blockID);
        world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
        world1.notifyBlocksOfNeighborChange(i2, i3 + 1, i4, this.blockID);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.redstoneRepeater.shiftedIndex;
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        if (this.field_22025_c) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            double d7 = (double)((float)i2 + 0.5f) + (double)(random5.nextFloat() - 0.5f) * 0.2;
            double d9 = (double)((float)i3 + 0.4f) + (double)(random5.nextFloat() - 0.5f) * 0.2;
            double d11 = (double)((float)i4 + 0.5f) + (double)(random5.nextFloat() - 0.5f) * 0.2;
            double d13 = 0.0;
            double d15 = 0.0;
            if (random5.nextInt(2) == 0) {
                switch (i6 & 3) {
                    case 0: {
                        d15 = -0.3125;
                        break;
                    }
                    case 1: {
                        d13 = 0.3125;
                        break;
                    }
                    case 2: {
                        d15 = 0.3125;
                        break;
                    }
                    case 3: {
                        d13 = -0.3125;
                    }
                }
            } else {
                int i17 = (i6 & 0xC) >> 2;
                switch (i6 & 3) {
                    case 0: {
                        d15 = field_22024_a[i17];
                        break;
                    }
                    case 1: {
                        d13 = -field_22024_a[i17];
                        break;
                    }
                    case 2: {
                        d15 = -field_22024_a[i17];
                        break;
                    }
                    case 3: {
                        d13 = field_22024_a[i17];
                    }
                }
            }
            world1.spawnParticle("reddust", d7 + d13, d9, d11 + d15, 0.0, 0.0, 0.0);
        }
    }
}

