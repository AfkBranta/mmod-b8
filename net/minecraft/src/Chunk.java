/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.ChunkBlockMap;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NibbleArray;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class Chunk {
    public static boolean isLit;
    public byte[] blocks;
    public boolean isChunkLoaded;
    public World worldObj;
    public NibbleArray data;
    public NibbleArray skylightMap;
    public NibbleArray blocklightMap;
    public byte[] heightMap;
    public int field_1532_i;
    public final int xPosition;
    public final int zPosition;
    public Map chunkTileEntityMap = new HashMap();
    public List[] entities = new List[8];
    public boolean isTerrainPopulated = false;
    public boolean isModified = false;
    public boolean neverSave;
    public boolean hasEntities = false;
    public long lastSaveTime = 0L;

    public Chunk(World world1, int i2, int i3) {
        this.worldObj = world1;
        this.xPosition = i2;
        this.zPosition = i3;
        this.heightMap = new byte[256];
        int i4 = 0;
        while (i4 < this.entities.length) {
            this.entities[i4] = new ArrayList();
            ++i4;
        }
    }

    public Chunk(World world1, byte[] b2, int i3, int i4) {
        this(world1, i3, i4);
        this.blocks = b2;
        this.data = new NibbleArray(b2.length);
        this.skylightMap = new NibbleArray(b2.length);
        this.blocklightMap = new NibbleArray(b2.length);
    }

    public boolean isAtLocation(int i1, int i2) {
        return i1 == this.xPosition && i2 == this.zPosition;
    }

    public int getHeightValue(int i1, int i2) {
        return this.heightMap[i2 << 4 | i1] & 0xFF;
    }

    public void func_1014_a() {
    }

    public void generateHeightMap() {
        int i1 = 127;
        int i2 = 0;
        while (i2 < 16) {
            int i3 = 0;
            while (i3 < 16) {
                int i4 = 127;
                int i5 = i2 << 11 | i3 << 7;
                while (i4 > 0 && Block.lightOpacity[this.blocks[i5 + i4 - 1] & 0xFF] == 0) {
                    --i4;
                }
                this.heightMap[i3 << 4 | i2] = (byte)i4;
                if (i4 < i1) {
                    i1 = i4;
                }
                ++i3;
            }
            ++i2;
        }
        this.field_1532_i = i1;
        this.isModified = true;
    }

    public void func_1024_c() {
        int i3;
        int i1 = 127;
        int i2 = 0;
        while (i2 < 16) {
            i3 = 0;
            while (i3 < 16) {
                int i4 = 127;
                int i5 = i2 << 11 | i3 << 7;
                while (i4 > 0 && Block.lightOpacity[this.blocks[i5 + i4 - 1] & 0xFF] == 0) {
                    --i4;
                }
                this.heightMap[i3 << 4 | i2] = (byte)i4;
                if (i4 < i1) {
                    i1 = i4;
                }
                if (!this.worldObj.worldProvider.field_6478_e) {
                    int i6 = 15;
                    int i7 = 127;
                    do {
                        if ((i6 -= Block.lightOpacity[this.blocks[i5 + i7] & 0xFF]) <= 0) continue;
                        this.skylightMap.setNibble(i2, i7, i3, i6);
                    } while (--i7 > 0 && i6 > 0);
                }
                ++i3;
            }
            ++i2;
        }
        this.field_1532_i = i1;
        i2 = 0;
        while (i2 < 16) {
            i3 = 0;
            while (i3 < 16) {
                this.func_996_c(i2, i3);
                ++i3;
            }
            ++i2;
        }
        this.isModified = true;
    }

    public void func_4143_d() {
    }

    private void func_996_c(int i1, int i2) {
        int i3 = this.getHeightValue(i1, i2);
        int i4 = this.xPosition * 16 + i1;
        int i5 = this.zPosition * 16 + i2;
        this.func_1020_f(i4 - 1, i5, i3);
        this.func_1020_f(i4 + 1, i5, i3);
        this.func_1020_f(i4, i5 - 1, i3);
        this.func_1020_f(i4, i5 + 1, i3);
    }

    private void func_1020_f(int i1, int i2, int i3) {
        int i4 = this.worldObj.getHeightValue(i1, i2);
        if (i4 > i3) {
            this.worldObj.func_616_a(EnumSkyBlock.Sky, i1, i3, i2, i1, i4, i2);
            this.isModified = true;
        } else if (i4 < i3) {
            this.worldObj.func_616_a(EnumSkyBlock.Sky, i1, i4, i2, i1, i3, i2);
            this.isModified = true;
        }
    }

    private void func_1003_g(int i1, int i2, int i3) {
        int i4;
        int i5 = i4 = this.heightMap[i3 << 4 | i1] & 0xFF;
        if (i2 > i4) {
            i5 = i2;
        }
        int i6 = i1 << 11 | i3 << 7;
        while (i5 > 0 && Block.lightOpacity[this.blocks[i6 + i5 - 1] & 0xFF] == 0) {
            --i5;
        }
        if (i5 != i4) {
            int i9;
            int i8;
            int i7;
            this.worldObj.markBlocksDirtyVertical(i1, i3, i5, i4);
            this.heightMap[i3 << 4 | i1] = (byte)i5;
            if (i5 < this.field_1532_i) {
                this.field_1532_i = i5;
            } else {
                i7 = 127;
                i8 = 0;
                while (i8 < 16) {
                    i9 = 0;
                    while (i9 < 16) {
                        if ((this.heightMap[i9 << 4 | i8] & 0xFF) < i7) {
                            i7 = this.heightMap[i9 << 4 | i8] & 0xFF;
                        }
                        ++i9;
                    }
                    ++i8;
                }
                this.field_1532_i = i7;
            }
            i7 = this.xPosition * 16 + i1;
            i8 = this.zPosition * 16 + i3;
            if (i5 < i4) {
                i9 = i5;
                while (i9 < i4) {
                    this.skylightMap.setNibble(i1, i9, i3, 15);
                    ++i9;
                }
            } else {
                this.worldObj.func_616_a(EnumSkyBlock.Sky, i7, i4, i8, i7, i5, i8);
                i9 = i4;
                while (i9 < i5) {
                    this.skylightMap.setNibble(i1, i9, i3, 0);
                    ++i9;
                }
            }
            i9 = 15;
            int i10 = i5;
            while (i5 > 0 && i9 > 0) {
                int i11;
                if ((i11 = Block.lightOpacity[this.getBlockID(i1, --i5, i3)]) == 0) {
                    i11 = 1;
                }
                if ((i9 -= i11) < 0) {
                    i9 = 0;
                }
                this.skylightMap.setNibble(i1, i5, i3, i9);
            }
            while (i5 > 0 && Block.lightOpacity[this.getBlockID(i1, i5 - 1, i3)] == 0) {
                --i5;
            }
            if (i5 != i10) {
                this.worldObj.func_616_a(EnumSkyBlock.Sky, i7 - 1, i5, i8 - 1, i7 + 1, i10, i8 + 1);
            }
            this.isModified = true;
        }
    }

    public int getBlockID(int i1, int i2, int i3) {
        return this.blocks[i1 << 11 | i3 << 7 | i2] & 0xFF;
    }

    public boolean setBlockIDWithMetadata(int i1, int i2, int i3, int i4, int i5) {
        byte b6 = (byte)i4;
        int i7 = this.heightMap[i3 << 4 | i1] & 0xFF;
        int i8 = this.blocks[i1 << 11 | i3 << 7 | i2] & 0xFF;
        if (i8 == i4 && this.data.getNibble(i1, i2, i3) == i5) {
            return false;
        }
        int i9 = this.xPosition * 16 + i1;
        int i10 = this.zPosition * 16 + i3;
        this.blocks[i1 << 11 | i3 << 7 | i2] = (byte)(b6 & 0xFF);
        if (i8 != 0 && !this.worldObj.multiplayerWorld) {
            Block.blocksList[i8].onBlockRemoval(this.worldObj, i9, i2, i10);
        }
        this.data.setNibble(i1, i2, i3, i5);
        if (!this.worldObj.worldProvider.field_6478_e) {
            if (Block.lightOpacity[b6 & 0xFF] != 0) {
                if (i2 >= i7) {
                    this.func_1003_g(i1, i2 + 1, i3);
                }
            } else if (i2 == i7 - 1) {
                this.func_1003_g(i1, i2, i3);
            }
            this.worldObj.func_616_a(EnumSkyBlock.Sky, i9, i2, i10, i9, i2, i10);
        }
        this.worldObj.func_616_a(EnumSkyBlock.Block, i9, i2, i10, i9, i2, i10);
        this.func_996_c(i1, i3);
        this.data.setNibble(i1, i2, i3, i5);
        if (i4 != 0) {
            Block.blocksList[i4].onBlockAdded(this.worldObj, i9, i2, i10);
        }
        this.isModified = true;
        return true;
    }

    public boolean setBlockID(int i1, int i2, int i3, int i4) {
        byte b5 = (byte)i4;
        int i6 = this.heightMap[i3 << 4 | i1] & 0xFF;
        int i7 = this.blocks[i1 << 11 | i3 << 7 | i2] & 0xFF;
        if (i7 == i4) {
            return false;
        }
        int i8 = this.xPosition * 16 + i1;
        int i9 = this.zPosition * 16 + i3;
        this.blocks[i1 << 11 | i3 << 7 | i2] = (byte)(b5 & 0xFF);
        if (i7 != 0) {
            Block.blocksList[i7].onBlockRemoval(this.worldObj, i8, i2, i9);
        }
        this.data.setNibble(i1, i2, i3, 0);
        if (Block.lightOpacity[b5 & 0xFF] != 0) {
            if (i2 >= i6) {
                this.func_1003_g(i1, i2 + 1, i3);
            }
        } else if (i2 == i6 - 1) {
            this.func_1003_g(i1, i2, i3);
        }
        this.worldObj.func_616_a(EnumSkyBlock.Sky, i8, i2, i9, i8, i2, i9);
        this.worldObj.func_616_a(EnumSkyBlock.Block, i8, i2, i9, i8, i2, i9);
        this.func_996_c(i1, i3);
        if (i4 != 0 && !this.worldObj.multiplayerWorld) {
            Block.blocksList[i4].onBlockAdded(this.worldObj, i8, i2, i9);
        }
        this.isModified = true;
        return true;
    }

    public int getBlockMetadata(int i1, int i2, int i3) {
        return this.data.getNibble(i1, i2, i3);
    }

    public void setBlockMetadata(int i1, int i2, int i3, int i4) {
        this.isModified = true;
        this.data.setNibble(i1, i2, i3, i4);
    }

    public int getSavedLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4) {
        return enumSkyBlock1 == EnumSkyBlock.Sky ? this.skylightMap.getNibble(i2, i3, i4) : (enumSkyBlock1 == EnumSkyBlock.Block ? this.blocklightMap.getNibble(i2, i3, i4) : 0);
    }

    public void setLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5) {
        this.isModified = true;
        if (enumSkyBlock1 == EnumSkyBlock.Sky) {
            this.skylightMap.setNibble(i2, i3, i4, i5);
        } else {
            if (enumSkyBlock1 != EnumSkyBlock.Block) {
                return;
            }
            this.blocklightMap.setNibble(i2, i3, i4, i5);
        }
    }

    public int getBlockLightValue(int i1, int i2, int i3, int i4) {
        int i6;
        int i5 = this.skylightMap.getNibble(i1, i2, i3);
        if (i5 > 0) {
            isLit = true;
        }
        if ((i6 = this.blocklightMap.getNibble(i1, i2, i3)) > (i5 -= i4)) {
            i5 = i6;
        }
        return i5;
    }

    public void addEntity(Entity entity1) {
        int i4;
        this.hasEntities = true;
        int i2 = MathHelper.floor_double(entity1.posX / 16.0);
        int i3 = MathHelper.floor_double(entity1.posZ / 16.0);
        if (i2 != this.xPosition || i3 != this.zPosition) {
            System.out.println("Wrong location! " + entity1);
            Thread.dumpStack();
        }
        if ((i4 = MathHelper.floor_double(entity1.posY / 16.0)) < 0) {
            i4 = 0;
        }
        if (i4 >= this.entities.length) {
            i4 = this.entities.length - 1;
        }
        entity1.addedToChunk = true;
        entity1.chunkCoordX = this.xPosition;
        entity1.chunkCoordY = i4;
        entity1.chunkCoordZ = this.zPosition;
        this.entities[i4].add(entity1);
    }

    public void func_1015_b(Entity entity1) {
        this.func_1016_a(entity1, entity1.chunkCoordY);
    }

    public void func_1016_a(Entity entity1, int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 >= this.entities.length) {
            i2 = this.entities.length - 1;
        }
        this.entities[i2].remove(entity1);
    }

    public boolean canBlockSeeTheSky(int i1, int i2, int i3) {
        return i2 >= (this.heightMap[i3 << 4 | i1] & 0xFF);
    }

    public TileEntity getChunkBlockTileEntity(int i1, int i2, int i3) {
        ChunkPosition chunkPosition4 = new ChunkPosition(i1, i2, i3);
        TileEntity tileEntity5 = (TileEntity)this.chunkTileEntityMap.get(chunkPosition4);
        if (tileEntity5 == null) {
            int i6 = this.getBlockID(i1, i2, i3);
            if (!Block.isBlockContainer[i6]) {
                return null;
            }
            BlockContainer blockContainer7 = (BlockContainer)Block.blocksList[i6];
            blockContainer7.onBlockAdded(this.worldObj, this.xPosition * 16 + i1, i2, this.zPosition * 16 + i3);
            tileEntity5 = (TileEntity)this.chunkTileEntityMap.get(chunkPosition4);
        }
        return tileEntity5;
    }

    public void func_1001_a(TileEntity tileEntity1) {
        int i2 = tileEntity1.xCoord - this.xPosition * 16;
        int i3 = tileEntity1.yCoord;
        int i4 = tileEntity1.zCoord - this.zPosition * 16;
        this.setChunkBlockTileEntity(i2, i3, i4, tileEntity1);
    }

    public void setChunkBlockTileEntity(int i1, int i2, int i3, TileEntity tileEntity4) {
        ChunkPosition chunkPosition5 = new ChunkPosition(i1, i2, i3);
        tileEntity4.worldObj = this.worldObj;
        tileEntity4.xCoord = this.xPosition * 16 + i1;
        tileEntity4.yCoord = i2;
        tileEntity4.zCoord = this.zPosition * 16 + i3;
        if (this.getBlockID(i1, i2, i3) != 0 && Block.blocksList[this.getBlockID(i1, i2, i3)] instanceof BlockContainer) {
            if (this.isChunkLoaded) {
                if (this.chunkTileEntityMap.get(chunkPosition5) != null) {
                    this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.get(chunkPosition5));
                }
                this.worldObj.loadedTileEntityList.add(tileEntity4);
            }
            this.chunkTileEntityMap.put(chunkPosition5, tileEntity4);
        } else {
            System.out.println("Attempted to place a tile entity where there was no entity tile!");
        }
    }

    public void removeChunkBlockTileEntity(int i1, int i2, int i3) {
        ChunkPosition chunkPosition4 = new ChunkPosition(i1, i2, i3);
        if (this.isChunkLoaded) {
            this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.remove(chunkPosition4));
        }
    }

    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.loadedTileEntityList.addAll(this.chunkTileEntityMap.values());
        int i1 = 0;
        while (i1 < this.entities.length) {
            this.worldObj.func_636_a(this.entities[i1]);
            ++i1;
        }
    }

    public void onChunkUnload() {
        this.isChunkLoaded = false;
        this.worldObj.loadedTileEntityList.removeAll(this.chunkTileEntityMap.values());
        int i1 = 0;
        while (i1 < this.entities.length) {
            this.worldObj.func_632_b(this.entities[i1]);
            ++i1;
        }
    }

    public void setChunkModified() {
        this.isModified = true;
    }

    public void getEntitiesWithinAABBForEntity(Entity entity1, AxisAlignedBB axisAlignedBB2, List list3) {
        int i4 = MathHelper.floor_double((axisAlignedBB2.minY - 2.0) / 16.0);
        int i5 = MathHelper.floor_double((axisAlignedBB2.maxY + 2.0) / 16.0);
        if (i4 < 0) {
            i4 = 0;
        }
        if (i5 >= this.entities.length) {
            i5 = this.entities.length - 1;
        }
        int i6 = i4;
        while (i6 <= i5) {
            List list7 = this.entities[i6];
            int i8 = 0;
            while (i8 < list7.size()) {
                Entity entity9 = (Entity)list7.get(i8);
                if (entity9 != entity1 && entity9.boundingBox.intersectsWith(axisAlignedBB2)) {
                    list3.add(entity9);
                }
                ++i8;
            }
            ++i6;
        }
    }

    public void getEntitiesOfTypeWithinAAAB(Class class1, AxisAlignedBB axisAlignedBB2, List list3) {
        int i4 = MathHelper.floor_double((axisAlignedBB2.minY - 2.0) / 16.0);
        int i5 = MathHelper.floor_double((axisAlignedBB2.maxY + 2.0) / 16.0);
        if (i4 < 0) {
            i4 = 0;
        }
        if (i5 >= this.entities.length) {
            i5 = this.entities.length - 1;
        }
        int i6 = i4;
        while (i6 <= i5) {
            List list7 = this.entities[i6];
            int i8 = 0;
            while (i8 < list7.size()) {
                Entity entity9 = (Entity)list7.get(i8);
                if (class1.isAssignableFrom(entity9.getClass()) && entity9.boundingBox.intersectsWith(axisAlignedBB2)) {
                    list3.add(entity9);
                }
                ++i8;
            }
            ++i6;
        }
    }

    public boolean needsSaving(boolean z1) {
        if (this.neverSave) {
            return false;
        }
        if (z1 ? this.hasEntities && this.worldObj.getWorldTime() != this.lastSaveTime : this.hasEntities && this.worldObj.getWorldTime() >= this.lastSaveTime + 600L) {
            return true;
        }
        return this.isModified;
    }

    public int setChunkData(byte[] b1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i12;
        int i11;
        int i10;
        int i9 = i2;
        while (i9 < i5) {
            i10 = i4;
            while (i10 < i7) {
                i11 = i9 << 11 | i10 << 7 | i3;
                i12 = i6 - i3;
                System.arraycopy(b1, i8, this.blocks, i11, i12);
                i8 += i12;
                ++i10;
            }
            ++i9;
        }
        this.generateHeightMap();
        i9 = i2;
        while (i9 < i5) {
            i10 = i4;
            while (i10 < i7) {
                i11 = (i9 << 11 | i10 << 7 | i3) >> 1;
                i12 = (i6 - i3) / 2;
                System.arraycopy(b1, i8, this.data.data, i11, i12);
                i8 += i12;
                ++i10;
            }
            ++i9;
        }
        i9 = i2;
        while (i9 < i5) {
            i10 = i4;
            while (i10 < i7) {
                i11 = (i9 << 11 | i10 << 7 | i3) >> 1;
                i12 = (i6 - i3) / 2;
                System.arraycopy(b1, i8, this.blocklightMap.data, i11, i12);
                i8 += i12;
                ++i10;
            }
            ++i9;
        }
        i9 = i2;
        while (i9 < i5) {
            i10 = i4;
            while (i10 < i7) {
                i11 = (i9 << 11 | i10 << 7 | i3) >> 1;
                i12 = (i6 - i3) / 2;
                System.arraycopy(b1, i8, this.skylightMap.data, i11, i12);
                i8 += i12;
                ++i10;
            }
            ++i9;
        }
        return i8;
    }

    public Random func_997_a(long j1) {
        return new Random(this.worldObj.getRandomSeed() + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ j1);
    }

    public boolean func_21167_h() {
        return false;
    }

    public void func_25124_i() {
        ChunkBlockMap.func_26002_a(this.blocks);
    }
}

