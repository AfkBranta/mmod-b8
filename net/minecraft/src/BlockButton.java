/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockButton
extends Block {
    protected BlockButton(int i1, int i2) {
        super(i1, i2, Material.circuits);
        this.setTickOnLoad(true);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public int tickRate() {
        return 20;
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
        return world1.isBlockOpaqueCube(i2 - 1, i3, i4) ? true : (world1.isBlockOpaqueCube(i2 + 1, i3, i4) ? true : (world1.isBlockOpaqueCube(i2, i3, i4 - 1) ? true : world1.isBlockOpaqueCube(i2, i3, i4 + 1)));
    }

    @Override
    public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        int i7 = i6 & 8;
        i6 &= 7;
        i6 = i5 == 2 && world1.isBlockOpaqueCube(i2, i3, i4 + 1) ? 4 : (i5 == 3 && world1.isBlockOpaqueCube(i2, i3, i4 - 1) ? 3 : (i5 == 4 && world1.isBlockOpaqueCube(i2 + 1, i3, i4) ? 2 : (i5 == 5 && world1.isBlockOpaqueCube(i2 - 1, i3, i4) ? 1 : this.getOrientation(world1, i2, i3, i4))));
        world1.setBlockMetadataWithNotify(i2, i3, i4, i6 + i7);
    }

    private int getOrientation(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2 - 1, i3, i4) ? 1 : (world1.isBlockOpaqueCube(i2 + 1, i3, i4) ? 2 : (world1.isBlockOpaqueCube(i2, i3, i4 - 1) ? 3 : (world1.isBlockOpaqueCube(i2, i3, i4 + 1) ? 4 : 1)));
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (this.func_305_h(world1, i2, i3, i4)) {
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
            if (z7) {
                this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
        }
    }

    private boolean func_305_h(World world1, int i2, int i3, int i4) {
        if (!this.canPlaceBlockAt(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
            return false;
        }
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        int i6 = i5 & 7;
        boolean z7 = (i5 & 8) > 0;
        float f8 = 0.375f;
        float f9 = 0.625f;
        float f10 = 0.1875f;
        float f11 = 0.125f;
        if (z7) {
            f11 = 0.0625f;
        }
        if (i6 == 1) {
            this.setBlockBounds(0.0f, f8, 0.5f - f10, f11, f9, 0.5f + f10);
        } else if (i6 == 2) {
            this.setBlockBounds(1.0f - f11, f8, 0.5f - f10, 1.0f, f9, 0.5f + f10);
        } else if (i6 == 3) {
            this.setBlockBounds(0.5f - f10, f8, 0.0f, 0.5f + f10, f9, f11);
        } else if (i6 == 4) {
            this.setBlockBounds(0.5f - f10, f8, 1.0f - f11, 0.5f + f10, f9, 1.0f);
        }
    }

    @Override
    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        this.blockActivated(world1, i2, i3, i4, entityPlayer5);
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        int i7 = i6 & 7;
        int i8 = 8 - (i6 & 8);
        if (i8 == 0) {
            return true;
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, i7 + i8);
        world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
        world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.click", 0.3f, 0.6f);
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
        world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
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

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        int i6;
        if (!world1.multiplayerWorld && ((i6 = world1.getBlockMetadata(i2, i3, i4)) & 8) != 0) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, i6 & 7);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
            int i7 = i6 & 7;
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
            world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.click", 0.3f, 0.5f);
            world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
        }
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float f1 = 0.1875f;
        float f2 = 0.125f;
        float f3 = 0.125f;
        this.setBlockBounds(0.5f - f1, 0.5f - f2, 0.5f - f3, 0.5f + f1, 0.5f + f2, 0.5f + f3);
    }
}

