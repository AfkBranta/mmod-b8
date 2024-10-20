/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;

public class ChunkCache
implements IBlockAccess {
    private int chunkX;
    private int chunkZ;
    private Chunk[][] chunkArray;
    private World worldObj;

    public ChunkCache(World world1, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.worldObj = world1;
        this.chunkX = i2 >> 4;
        this.chunkZ = i4 >> 4;
        int i8 = i5 >> 4;
        int i9 = i7 >> 4;
        this.chunkArray = new Chunk[i8 - this.chunkX + 1][i9 - this.chunkZ + 1];
        int i10 = this.chunkX;
        while (i10 <= i8) {
            int i11 = this.chunkZ;
            while (i11 <= i9) {
                this.chunkArray[i10 - this.chunkX][i11 - this.chunkZ] = world1.getChunkFromChunkCoords(i10, i11);
                ++i11;
            }
            ++i10;
        }
    }

    @Override
    public int getBlockId(int i1, int i2, int i3) {
        if (i2 < 0) {
            return 0;
        }
        if (i2 >= 128) {
            return 0;
        }
        int i4 = (i1 >> 4) - this.chunkX;
        int i5 = (i3 >> 4) - this.chunkZ;
        if (i4 >= 0 && i4 < this.chunkArray.length && i5 >= 0 && i5 < this.chunkArray[i4].length) {
            Chunk chunk6 = this.chunkArray[i4][i5];
            return chunk6 == null ? 0 : chunk6.getBlockID(i1 & 0xF, i2, i3 & 0xF);
        }
        return 0;
    }

    @Override
    public TileEntity getBlockTileEntity(int i1, int i2, int i3) {
        int i4 = (i1 >> 4) - this.chunkX;
        int i5 = (i3 >> 4) - this.chunkZ;
        return this.chunkArray[i4][i5].getChunkBlockTileEntity(i1 & 0xF, i2, i3 & 0xF);
    }

    @Override
    public float getLightBrightness(int i1, int i2, int i3) {
        return this.worldObj.worldProvider.lightBrightnessTable[this.getLightValue(i1, i2, i3)];
    }

    public int getLightValue(int i1, int i2, int i3) {
        return this.func_716_a(i1, i2, i3, true);
    }

    public int func_716_a(int i1, int i2, int i3, boolean z4) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            int i5;
            if (z4 && ((i5 = this.getBlockId(i1, i2, i3)) == Block.stairSingle.blockID || i5 == Block.tilledField.blockID)) {
                int i6 = this.func_716_a(i1, i2 + 1, i3, false);
                int i7 = this.func_716_a(i1 + 1, i2, i3, false);
                int i8 = this.func_716_a(i1 - 1, i2, i3, false);
                int i9 = this.func_716_a(i1, i2, i3 + 1, false);
                int i10 = this.func_716_a(i1, i2, i3 - 1, false);
                if (i7 > i6) {
                    i6 = i7;
                }
                if (i8 > i6) {
                    i6 = i8;
                }
                if (i9 > i6) {
                    i6 = i9;
                }
                if (i10 > i6) {
                    i6 = i10;
                }
                return i6;
            }
            if (i2 < 0) {
                return 0;
            }
            if (i2 >= 128) {
                i5 = 15 - this.worldObj.skylightSubtracted;
                if (i5 < 0) {
                    i5 = 0;
                }
                return i5;
            }
            i5 = (i1 >> 4) - this.chunkX;
            int i6 = (i3 >> 4) - this.chunkZ;
            return this.chunkArray[i5][i6].getBlockLightValue(i1 & 0xF, i2, i3 & 0xF, this.worldObj.skylightSubtracted);
        }
        return 15;
    }

    @Override
    public int getBlockMetadata(int i1, int i2, int i3) {
        if (i2 < 0) {
            return 0;
        }
        if (i2 >= 128) {
            return 0;
        }
        int i4 = (i1 >> 4) - this.chunkX;
        int i5 = (i3 >> 4) - this.chunkZ;
        return this.chunkArray[i4][i5].getBlockMetadata(i1 & 0xF, i2, i3 & 0xF);
    }

    @Override
    public Material getBlockMaterial(int i1, int i2, int i3) {
        int i4 = this.getBlockId(i1, i2, i3);
        return i4 == 0 ? Material.air : Block.blocksList[i4].blockMaterial;
    }

    @Override
    public boolean isBlockOpaqueCube(int i1, int i2, int i3) {
        Block block4 = Block.blocksList[this.getBlockId(i1, i2, i3)];
        return block4 == null ? false : block4.isOpaqueCube();
    }

    @Override
    public WorldChunkManager getWorldChunkManager() {
        return this.worldObj.getWorldChunkManager();
    }
}

