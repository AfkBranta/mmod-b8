/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLever
extends Block {
    protected BlockLever(int i1, int i2) {
        super(i1, i2, Material.circuits);
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
    public int getRenderType() {
        return 12;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2 - 1, i3, i4) ? true : (world1.isBlockOpaqueCube(i2 + 1, i3, i4) ? true : (world1.isBlockOpaqueCube(i2, i3, i4 - 1) ? true : (world1.isBlockOpaqueCube(i2, i3, i4 + 1) ? true : world1.isBlockOpaqueCube(i2, i3 - 1, i4))));
    }

    @Override
    public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        int i7 = i6 & 8;
        i6 &= 7;
        if (i5 == 1 && world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
            i6 = 5 + world1.rand.nextInt(2);
        }
        if (i5 == 2 && world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
            i6 = 4;
        }
        if (i5 == 3 && world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
            i6 = 3;
        }
        if (i5 == 4 && world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
            i6 = 2;
        }
        if (i5 == 5 && world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
            i6 = 1;
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, i6 + i7);
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (this.checkIfAttachedToBlock(world1, i2, i3, i4)) {
            int i6 = world1.getBlockMetadata(i2, i3, i4) & 7;
            boolean z7 = false;
            if (!world1.isBlockOpaqueCube(i2 - 1, i3, i4) && i6 == 1) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2 + 1, i3, i4) && i6 == 2) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3, i4 - 1) && i6 == 3) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3, i4 + 1) && i6 == 4) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4) && i6 == 5) {
                z7 = true;
            }
            if (z7) {
                this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
        }
    }

    private boolean checkIfAttachedToBlock(World world1, int i2, int i3, int i4) {
        if (!this.canPlaceBlockAt(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
            return false;
        }
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4) & 7;
        float f6 = 0.1875f;
        if (i5 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - f6, f6 * 2.0f, 0.8f, 0.5f + f6);
        } else if (i5 == 2) {
            this.setBlockBounds(1.0f - f6 * 2.0f, 0.2f, 0.5f - f6, 1.0f, 0.8f, 0.5f + f6);
        } else if (i5 == 3) {
            this.setBlockBounds(0.5f - f6, 0.2f, 0.0f, 0.5f + f6, 0.8f, f6 * 2.0f);
        } else if (i5 == 4) {
            this.setBlockBounds(0.5f - f6, 0.2f, 1.0f - f6 * 2.0f, 0.5f + f6, 0.8f, 1.0f);
        } else {
            f6 = 0.25f;
            this.setBlockBounds(0.5f - f6, 0.0f, 0.5f - f6, 0.5f + f6, 0.6f, 0.5f + f6);
        }
    }

    @Override
    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        this.blockActivated(world1, i2, i3, i4, entityPlayer5);
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (world1.multiplayerWorld) {
            return true;
        }
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        int i7 = i6 & 7;
        int i8 = 8 - (i6 & 8);
        world1.setBlockMetadataWithNotify(i2, i3, i4, i7 + i8);
        world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
        world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.click", 0.3f, i8 > 0 ? 0.6f : 0.5f);
        world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
        if (i7 == 1) {
            world1.notifyBlocksOfNeighborChange(i2 - 1, i3, i4, this.blockID);
        } else if (i7 == 2) {
            world1.notifyBlocksOfNeighborChange(i2 + 1, i3, i4, this.blockID);
        } else if (i7 == 3) {
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 - 1, this.blockID);
        } else if (i7 == 4) {
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 + 1, this.blockID);
        } else {
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
        }
        return true;
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockMetadata(i2, i3, i4);
        if ((i5 & 8) > 0) {
            world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
            int i6 = i5 & 7;
            if (i6 == 1) {
                world1.notifyBlocksOfNeighborChange(i2 - 1, i3, i4, this.blockID);
            } else if (i6 == 2) {
                world1.notifyBlocksOfNeighborChange(i2 + 1, i3, i4, this.blockID);
            } else if (i6 == 3) {
                world1.notifyBlocksOfNeighborChange(i2, i3, i4 - 1, this.blockID);
            } else if (i6 == 4) {
                world1.notifyBlocksOfNeighborChange(i2, i3, i4 + 1, this.blockID);
            } else {
                world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            }
        }
        super.onBlockRemoval(world1, i2, i3, i4);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return (iBlockAccess1.getBlockMetadata(i2, i3, i4) & 8) > 0;
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if ((i6 & 8) == 0) {
            return false;
        }
        int i7 = i6 & 7;
        return i7 == 5 && i5 == 1 ? true : (i7 == 4 && i5 == 2 ? true : (i7 == 3 && i5 == 3 ? true : (i7 == 2 && i5 == 4 ? true : i7 == 1 && i5 == 5)));
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }
}

