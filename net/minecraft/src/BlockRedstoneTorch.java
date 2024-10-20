/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockTorch;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RedstoneUpdateInfo;
import net.minecraft.src.World;

public class BlockRedstoneTorch
extends BlockTorch {
    private boolean torchActive = false;
    private static List torchUpdates = new ArrayList();

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return i1 == 1 ? Block.redstoneWire.getBlockTextureFromSideAndMetadata(i1, i2) : super.getBlockTextureFromSideAndMetadata(i1, i2);
    }

    private boolean checkForBurnout(World world1, int i2, int i3, int i4, boolean z5) {
        if (z5) {
            torchUpdates.add(new RedstoneUpdateInfo(i2, i3, i4, world1.getWorldTime()));
        }
        int i6 = 0;
        int i7 = 0;
        while (i7 < torchUpdates.size()) {
            RedstoneUpdateInfo redstoneUpdateInfo8 = (RedstoneUpdateInfo)torchUpdates.get(i7);
            if (redstoneUpdateInfo8.x == i2 && redstoneUpdateInfo8.y == i3 && redstoneUpdateInfo8.z == i4 && ++i6 >= 8) {
                return true;
            }
            ++i7;
        }
        return false;
    }

    protected BlockRedstoneTorch(int i1, int i2, boolean z3) {
        super(i1, i2);
        this.torchActive = z3;
        this.setTickOnLoad(true);
    }

    @Override
    public int tickRate() {
        return 2;
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        if (world1.getBlockMetadata(i2, i3, i4) == 0) {
            super.onBlockAdded(world1, i2, i3, i4);
        }
        if (this.torchActive) {
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 + 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2 - 1, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2 + 1, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 - 1, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 + 1, this.blockID);
        }
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        if (this.torchActive) {
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 + 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2 - 1, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2 + 1, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 - 1, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 + 1, this.blockID);
        }
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (!this.torchActive) {
            return false;
        }
        int i6 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        return i6 == 5 && i5 == 1 ? false : (i6 == 3 && i5 == 3 ? false : (i6 == 4 && i5 == 2 ? false : (i6 == 1 && i5 == 5 ? false : i6 != 2 || i5 != 4)));
    }

    private boolean func_22026_h(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockMetadata(i2, i3, i4);
        return i5 == 5 && world1.isBlockIndirectlyProvidingPowerTo(i2, i3 - 1, i4, 0) ? true : (i5 == 3 && world1.isBlockIndirectlyProvidingPowerTo(i2, i3, i4 - 1, 2) ? true : (i5 == 4 && world1.isBlockIndirectlyProvidingPowerTo(i2, i3, i4 + 1, 3) ? true : (i5 == 1 && world1.isBlockIndirectlyProvidingPowerTo(i2 - 1, i3, i4, 4) ? true : i5 == 2 && world1.isBlockIndirectlyProvidingPowerTo(i2 + 1, i3, i4, 5))));
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        boolean z6 = this.func_22026_h(world1, i2, i3, i4);
        while (torchUpdates.size() > 0 && world1.getWorldTime() - ((RedstoneUpdateInfo)BlockRedstoneTorch.torchUpdates.get((int)0)).updateTime > 100L) {
            torchUpdates.remove(0);
        }
        if (this.torchActive) {
            if (z6) {
                world1.setBlockAndMetadataWithNotify(i2, i3, i4, Block.torchRedstoneIdle.blockID, world1.getBlockMetadata(i2, i3, i4));
                if (this.checkForBurnout(world1, i2, i3, i4, true)) {
                    world1.playSoundEffect((float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f, "random.fizz", 0.5f, 2.6f + (world1.rand.nextFloat() - world1.rand.nextFloat()) * 0.8f);
                    int i7 = 0;
                    while (i7 < 5) {
                        double d8 = (double)i2 + random5.nextDouble() * 0.6 + 0.2;
                        double d10 = (double)i3 + random5.nextDouble() * 0.6 + 0.2;
                        double d12 = (double)i4 + random5.nextDouble() * 0.6 + 0.2;
                        world1.spawnParticle("smoke", d8, d10, d12, 0.0, 0.0, 0.0);
                        ++i7;
                    }
                }
            }
        } else if (!z6 && !this.checkForBurnout(world1, i2, i3, i4, false)) {
            world1.setBlockAndMetadataWithNotify(i2, i3, i4, Block.torchRedstoneActive.blockID, world1.getBlockMetadata(i2, i3, i4));
        }
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        super.onNeighborBlockChange(world1, i2, i3, i4, i5);
        world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
        return i5 == 0 ? this.isPoweringTo(world1, i2, i3, i4, i5) : false;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.torchRedstoneActive.blockID;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        if (this.torchActive) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            double d7 = (double)((float)i2 + 0.5f) + (double)(random5.nextFloat() - 0.5f) * 0.2;
            double d9 = (double)((float)i3 + 0.7f) + (double)(random5.nextFloat() - 0.5f) * 0.2;
            double d11 = (double)((float)i4 + 0.5f) + (double)(random5.nextFloat() - 0.5f) * 0.2;
            double d13 = 0.22f;
            double d15 = 0.27f;
            if (i6 == 1) {
                world1.spawnParticle("reddust", d7 - d15, d9 + d13, d11, 0.0, 0.0, 0.0);
            } else if (i6 == 2) {
                world1.spawnParticle("reddust", d7 + d15, d9 + d13, d11, 0.0, 0.0, 0.0);
            } else if (i6 == 3) {
                world1.spawnParticle("reddust", d7, d9 + d13, d11 - d15, 0.0, 0.0, 0.0);
            } else if (i6 == 4) {
                world1.spawnParticle("reddust", d7, d9 + d13, d11 + d15, 0.0, 0.0, 0.0);
            } else {
                world1.spawnParticle("reddust", d7, d9, d11, 0.0, 0.0, 0.0);
            }
        }
    }
}

