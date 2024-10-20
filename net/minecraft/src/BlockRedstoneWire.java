/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockRedstoneWire
extends Block {
    private boolean wiresProvidePower = true;
    private Set field_21031_b = new HashSet();

    public BlockRedstoneWire(int i1, int i2) {
        super(i1, i2, Material.circuits);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return this.blockIndexInTexture;
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
        return 5;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2, i3 - 1, i4);
    }

    private void updateAndPropagateCurrentStrength(World world1, int i2, int i3, int i4) {
        this.func_21030_a(world1, i2, i3, i4, i2, i3, i4);
        ArrayList arrayList5 = new ArrayList(this.field_21031_b);
        this.field_21031_b.clear();
        int i6 = 0;
        while (i6 < arrayList5.size()) {
            ChunkPosition chunkPosition7 = (ChunkPosition)arrayList5.get(i6);
            world1.notifyBlocksOfNeighborChange(chunkPosition7.x, chunkPosition7.y, chunkPosition7.z, this.blockID);
            ++i6;
        }
    }

    private void func_21030_a(World world1, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i13;
        int i12;
        int i11;
        int i8 = world1.getBlockMetadata(i2, i3, i4);
        int i9 = 0;
        this.wiresProvidePower = false;
        boolean z10 = world1.isBlockIndirectlyGettingPowered(i2, i3, i4);
        this.wiresProvidePower = true;
        if (z10) {
            i9 = 15;
        } else {
            i11 = 0;
            while (i11 < 4) {
                i12 = i2;
                i13 = i4;
                if (i11 == 0) {
                    i12 = i2 - 1;
                }
                if (i11 == 1) {
                    ++i12;
                }
                if (i11 == 2) {
                    i13 = i4 - 1;
                }
                if (i11 == 3) {
                    ++i13;
                }
                if (i12 != i5 || i3 != i6 || i13 != i7) {
                    i9 = this.getMaxCurrentStrength(world1, i12, i3, i13, i9);
                }
                if (world1.isBlockOpaqueCube(i12, i3, i13) && !world1.isBlockOpaqueCube(i2, i3 + 1, i4)) {
                    if (i12 != i5 || i3 + 1 != i6 || i13 != i7) {
                        i9 = this.getMaxCurrentStrength(world1, i12, i3 + 1, i13, i9);
                    }
                } else if (!(world1.isBlockOpaqueCube(i12, i3, i13) || i12 == i5 && i3 - 1 == i6 && i13 == i7)) {
                    i9 = this.getMaxCurrentStrength(world1, i12, i3 - 1, i13, i9);
                }
                ++i11;
            }
            i9 = i9 > 0 ? --i9 : 0;
        }
        if (i8 != i9) {
            world1.editingBlocks = true;
            world1.setBlockMetadataWithNotify(i2, i3, i4, i9);
            world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
            world1.editingBlocks = false;
            i11 = 0;
            while (i11 < 4) {
                i12 = i2;
                i13 = i4;
                int i14 = i3 - 1;
                if (i11 == 0) {
                    i12 = i2 - 1;
                }
                if (i11 == 1) {
                    ++i12;
                }
                if (i11 == 2) {
                    i13 = i4 - 1;
                }
                if (i11 == 3) {
                    ++i13;
                }
                if (world1.isBlockOpaqueCube(i12, i3, i13)) {
                    i14 += 2;
                }
                boolean z15 = false;
                int i16 = this.getMaxCurrentStrength(world1, i12, i3, i13, -1);
                i9 = world1.getBlockMetadata(i2, i3, i4);
                if (i9 > 0) {
                    --i9;
                }
                if (i16 >= 0 && i16 != i9) {
                    this.func_21030_a(world1, i12, i3, i13, i2, i3, i4);
                }
                i16 = this.getMaxCurrentStrength(world1, i12, i14, i13, -1);
                i9 = world1.getBlockMetadata(i2, i3, i4);
                if (i9 > 0) {
                    --i9;
                }
                if (i16 >= 0 && i16 != i9) {
                    this.func_21030_a(world1, i12, i14, i13, i2, i3, i4);
                }
                ++i11;
            }
            if (i8 == 0 || i9 == 0) {
                this.field_21031_b.add(new ChunkPosition(i2, i3, i4));
                this.field_21031_b.add(new ChunkPosition(i2 - 1, i3, i4));
                this.field_21031_b.add(new ChunkPosition(i2 + 1, i3, i4));
                this.field_21031_b.add(new ChunkPosition(i2, i3 - 1, i4));
                this.field_21031_b.add(new ChunkPosition(i2, i3 + 1, i4));
                this.field_21031_b.add(new ChunkPosition(i2, i3, i4 - 1));
                this.field_21031_b.add(new ChunkPosition(i2, i3, i4 + 1));
            }
        }
    }

    private void notifyWireNeighborsOfNeighborChange(World world1, int i2, int i3, int i4) {
        if (world1.getBlockId(i2, i3, i4) == this.blockID) {
            world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2 - 1, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2 + 1, i3, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 - 1, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3, i4 + 1, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 + 1, i4, this.blockID);
        }
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        super.onBlockAdded(world1, i2, i3, i4);
        if (!world1.multiplayerWorld) {
            this.updateAndPropagateCurrentStrength(world1, i2, i3, i4);
            world1.notifyBlocksOfNeighborChange(i2, i3 + 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            this.notifyWireNeighborsOfNeighborChange(world1, i2 - 1, i3, i4);
            this.notifyWireNeighborsOfNeighborChange(world1, i2 + 1, i3, i4);
            this.notifyWireNeighborsOfNeighborChange(world1, i2, i3, i4 - 1);
            this.notifyWireNeighborsOfNeighborChange(world1, i2, i3, i4 + 1);
            if (world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 - 1, i3 + 1, i4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 - 1, i3 - 1, i4);
            }
            if (world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 + 1, i3 + 1, i4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 + 1, i3 - 1, i4);
            }
            if (world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 + 1, i4 - 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 - 1, i4 - 1);
            }
            if (world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 + 1, i4 + 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 - 1, i4 + 1);
            }
        }
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        super.onBlockRemoval(world1, i2, i3, i4);
        if (!world1.multiplayerWorld) {
            world1.notifyBlocksOfNeighborChange(i2, i3 + 1, i4, this.blockID);
            world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
            this.updateAndPropagateCurrentStrength(world1, i2, i3, i4);
            this.notifyWireNeighborsOfNeighborChange(world1, i2 - 1, i3, i4);
            this.notifyWireNeighborsOfNeighborChange(world1, i2 + 1, i3, i4);
            this.notifyWireNeighborsOfNeighborChange(world1, i2, i3, i4 - 1);
            this.notifyWireNeighborsOfNeighborChange(world1, i2, i3, i4 + 1);
            if (world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 - 1, i3 + 1, i4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 - 1, i3 - 1, i4);
            }
            if (world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 + 1, i3 + 1, i4);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2 + 1, i3 - 1, i4);
            }
            if (world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 + 1, i4 - 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 - 1, i4 - 1);
            }
            if (world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 + 1, i4 + 1);
            } else {
                this.notifyWireNeighborsOfNeighborChange(world1, i2, i3 - 1, i4 + 1);
            }
        }
    }

    private int getMaxCurrentStrength(World world1, int i2, int i3, int i4, int i5) {
        if (world1.getBlockId(i2, i3, i4) != this.blockID) {
            return i5;
        }
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        return i6 > i5 ? i6 : i5;
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (!world1.multiplayerWorld) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            boolean z7 = this.canPlaceBlockAt(world1, i2, i3, i4);
            if (!z7) {
                this.dropBlockAsItem(world1, i2, i3, i4, i6);
                world1.setBlockWithNotify(i2, i3, i4, 0);
            } else {
                this.updateAndPropagateCurrentStrength(world1, i2, i3, i4);
            }
            super.onNeighborBlockChange(world1, i2, i3, i4, i5);
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.redstone.shiftedIndex;
    }

    @Override
    public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
        return !this.wiresProvidePower ? false : this.isPoweringTo(world1, i2, i3, i4, i5);
    }

    @Override
    public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        boolean z9;
        if (!this.wiresProvidePower) {
            return false;
        }
        if (iBlockAccess1.getBlockMetadata(i2, i3, i4) == 0) {
            return false;
        }
        if (i5 == 1) {
            return true;
        }
        boolean z6 = BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2 - 1, i3, i4) || !iBlockAccess1.isBlockOpaqueCube(i2 - 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2 - 1, i3 - 1, i4);
        boolean z7 = BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2 + 1, i3, i4) || !iBlockAccess1.isBlockOpaqueCube(i2 + 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2 + 1, i3 - 1, i4);
        boolean z8 = BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2, i3, i4 - 1) || !iBlockAccess1.isBlockOpaqueCube(i2, i3, i4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2, i3 - 1, i4 - 1);
        boolean bl = z9 = BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2, i3, i4 + 1) || !iBlockAccess1.isBlockOpaqueCube(i2, i3, i4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2, i3 - 1, i4 + 1);
        if (!iBlockAccess1.isBlockOpaqueCube(i2, i3 + 1, i4)) {
            if (iBlockAccess1.isBlockOpaqueCube(i2 - 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2 - 1, i3 + 1, i4)) {
                z6 = true;
            }
            if (iBlockAccess1.isBlockOpaqueCube(i2 + 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2 + 1, i3 + 1, i4)) {
                z7 = true;
            }
            if (iBlockAccess1.isBlockOpaqueCube(i2, i3, i4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2, i3 + 1, i4 - 1)) {
                z8 = true;
            }
            if (iBlockAccess1.isBlockOpaqueCube(i2, i3, i4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(iBlockAccess1, i2, i3 + 1, i4 + 1)) {
                z9 = true;
            }
        }
        return !z8 && !z7 && !z6 && !z9 && i5 >= 2 && i5 <= 5 ? true : (i5 == 2 && z8 && !z6 && !z7 ? true : (i5 == 3 && z9 && !z6 && !z7 ? true : (i5 == 4 && z6 && !z8 && !z9 ? true : i5 == 5 && z7 && !z8 && !z9)));
    }

    @Override
    public boolean canProvidePower() {
        return this.wiresProvidePower;
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if (i6 > 0) {
            double d7 = (double)i2 + 0.5 + ((double)random5.nextFloat() - 0.5) * 0.2;
            double d9 = (float)i3 + 0.0625f;
            double d11 = (double)i4 + 0.5 + ((double)random5.nextFloat() - 0.5) * 0.2;
            float f13 = (float)i6 / 15.0f;
            float f14 = f13 * 0.6f + 0.4f;
            if (i6 == 0) {
                f14 = 0.0f;
            }
            float f15 = f13 * f13 * 0.7f - 0.5f;
            float f16 = f13 * f13 * 0.6f - 0.7f;
            if (f15 < 0.0f) {
                f15 = 0.0f;
            }
            if (f16 < 0.0f) {
                f16 = 0.0f;
            }
            world1.spawnParticle("reddust", d7, d9, d11, f14, f15, f16);
        }
    }

    public static boolean isPowerProviderOrWire(IBlockAccess iBlockAccess0, int i1, int i2, int i3) {
        int i4 = iBlockAccess0.getBlockId(i1, i2, i3);
        return i4 == Block.redstoneWire.blockID ? true : (i4 == 0 ? false : Block.blocksList[i4].canProvidePower());
    }
}

