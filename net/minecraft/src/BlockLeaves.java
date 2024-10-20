/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockLeavesBase;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLeaves
extends BlockLeavesBase {
    private int baseIndexInPNG;
    int[] adjacentTreeBlocks;

    protected BlockLeaves(int i1, int i2) {
        super(i1, i2, Material.leaves, false);
        this.baseIndexInPNG = i2;
        this.setTickOnLoad(true);
    }

    @Override
    public int colorMultiplier(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        if ((i5 & 1) == 1) {
            return ColorizerFoliage.getFoliageColorPine();
        }
        if ((i5 & 2) == 2) {
            return ColorizerFoliage.getFoliageColorBirch();
        }
        iBlockAccess1.getWorldChunkManager().func_4069_a(i2, i4, 1, 1);
        double d6 = iBlockAccess1.getWorldChunkManager().temperature[0];
        double d8 = iBlockAccess1.getWorldChunkManager().humidity[0];
        return ColorizerFoliage.getFoliageColor(d6, d8);
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        int b5 = 1;
        int i6 = b5 + 1;
        if (world1.checkChunksExist(i2 - i6, i3 - i6, i4 - i6, i2 + i6, i3 + i6, i4 + i6)) {
            int i7 = -b5;
            while (i7 <= b5) {
                int i8 = -b5;
                while (i8 <= b5) {
                    int i9 = -b5;
                    while (i9 <= b5) {
                        int i10 = world1.getBlockId(i2 + i7, i3 + i8, i4 + i9);
                        if (i10 == Block.leaves.blockID) {
                            int i11 = world1.getBlockMetadata(i2 + i7, i3 + i8, i4 + i9);
                            world1.setBlockMetadata(i2 + i7, i3 + i8, i4 + i9, i11 | 4);
                        }
                        ++i9;
                    }
                    ++i8;
                }
                ++i7;
            }
        }
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        int i6;
        if (!world1.multiplayerWorld && ((i6 = world1.getBlockMetadata(i2, i3, i4)) & 4) != 0) {
            int i12;
            int b7 = 4;
            int i8 = b7 + 1;
            int b9 = 32;
            int i10 = b9 * b9;
            int i11 = b9 / 2;
            if (this.adjacentTreeBlocks == null) {
                this.adjacentTreeBlocks = new int[b9 * b9 * b9];
            }
            if (world1.checkChunksExist(i2 - i8, i3 - i8, i4 - i8, i2 + i8, i3 + i8, i4 + i8)) {
                i12 = -b7;
                while (true) {
                    int i15;
                    int i14;
                    int i13;
                    if (i12 > b7) {
                        for (i12 = 1; i12 <= 4; ++i12) {
                            i13 = -b7;
                            while (i13 <= b7) {
                                i14 = -b7;
                                while (i14 <= b7) {
                                    i15 = -b7;
                                    while (i15 <= b7) {
                                        if (this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + i15 + i11] == i12 - 1) {
                                            if (this.adjacentTreeBlocks[(i13 + i11 - 1) * i10 + (i14 + i11) * b9 + i15 + i11] == -2) {
                                                this.adjacentTreeBlocks[(i13 + i11 - 1) * i10 + (i14 + i11) * b9 + i15 + i11] = i12;
                                            }
                                            if (this.adjacentTreeBlocks[(i13 + i11 + 1) * i10 + (i14 + i11) * b9 + i15 + i11] == -2) {
                                                this.adjacentTreeBlocks[(i13 + i11 + 1) * i10 + (i14 + i11) * b9 + i15 + i11] = i12;
                                            }
                                            if (this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 - 1) * b9 + i15 + i11] == -2) {
                                                this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 - 1) * b9 + i15 + i11] = i12;
                                            }
                                            if (this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 + 1) * b9 + i15 + i11] == -2) {
                                                this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 + 1) * b9 + i15 + i11] = i12;
                                            }
                                            if (this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + (i15 + i11 - 1)] == -2) {
                                                this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + (i15 + i11 - 1)] = i12;
                                            }
                                            if (this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + i15 + i11 + 1] == -2) {
                                                this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + i15 + i11 + 1] = i12;
                                            }
                                        }
                                        ++i15;
                                    }
                                    ++i14;
                                }
                                ++i13;
                            }
                        }
                        break;
                    }
                    i13 = -b7;
                    while (i13 <= b7) {
                        i14 = -b7;
                        while (i14 <= b7) {
                            i15 = world1.getBlockId(i2 + i12, i3 + i13, i4 + i14);
                            this.adjacentTreeBlocks[(i12 + i11) * i10 + (i13 + i11) * b9 + i14 + i11] = i15 == Block.wood.blockID ? 0 : (i15 == Block.leaves.blockID ? -2 : -1);
                            ++i14;
                        }
                        ++i13;
                    }
                    ++i12;
                }
            }
            if ((i12 = this.adjacentTreeBlocks[i11 * i10 + i11 * b9 + i11]) >= 0) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, i6 & 0xFFFFFFFB);
            } else {
                this.removeLeaves(world1, i2, i3, i4);
            }
        }
    }

    private void removeLeaves(World world1, int i2, int i3, int i4) {
        this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
        world1.setBlockWithNotify(i2, i3, i4, 0);
    }

    @Override
    public int quantityDropped(Random random1) {
        return random1.nextInt(16) == 0 ? 1 : 0;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.sapling.blockID;
    }

    @Override
    public boolean isOpaqueCube() {
        return !this.graphicsLevel;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return (i2 & 3) == 1 ? this.blockIndexInTexture + 80 : this.blockIndexInTexture;
    }

    public void setGraphicsLevel(boolean z1) {
        this.graphicsLevel = z1;
        this.blockIndexInTexture = this.baseIndexInPNG + (z1 ? 0 : 1);
    }

    @Override
    public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
        super.onEntityWalking(world1, i2, i3, i4, entity5);
    }
}

