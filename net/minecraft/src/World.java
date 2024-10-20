/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Modules.Visual.Fullbright;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluids;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.Explosion;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MetadataChunkBlock;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NextTickListEntry;
import net.minecraft.src.PathEntity;
import net.minecraft.src.Pathfinder;
import net.minecraft.src.SpawnerAnimals;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class World
implements IBlockAccess {
    public boolean scheduledUpdatesAreImmediate = false;
    private List field_1051_z = new ArrayList();
    public List loadedEntityList = new ArrayList();
    private List unloadedEntityList = new ArrayList();
    private TreeSet scheduledTickTreeSet = new TreeSet();
    private Set scheduledTickSet = new HashSet();
    public List loadedTileEntityList = new ArrayList();
    public List playerEntities = new ArrayList();
    private long field_1019_F = 0xFFFFFFL;
    public int skylightSubtracted = 0;
    protected int field_9437_g = new Random().nextInt();
    protected int field_9436_h = 1013904223;
    public boolean editingBlocks = false;
    private long lockTimestamp = System.currentTimeMillis();
    protected int autosavePeriod = 40;
    public int difficultySetting;
    public Random rand = new Random();
    public boolean isNewWorld = false;
    public final WorldProvider worldProvider;
    protected List worldAccesses = new ArrayList();
    protected IChunkProvider chunkProvider;
    protected final ISaveHandler saveHandler;
    protected WorldInfo worldInfo;
    public boolean findingSpawnPoint;
    private boolean allPlayersSleeping;
    private ArrayList field_9428_I = new ArrayList();
    private int field_4204_J = 0;
    private boolean spawnHostileMobs = true;
    private boolean spawnPeacefulMobs = true;
    static int field_9429_y = 0;
    public Set field_9427_K = new HashSet();
    private int field_9426_L = this.rand.nextInt(12000);
    private List field_1012_M = new ArrayList();
    public boolean multiplayerWorld = false;

    @Override
    public WorldChunkManager getWorldChunkManager() {
        return this.worldProvider.worldChunkMgr;
    }

    public World(ISaveHandler iSaveHandler1, String string2, WorldProvider worldProvider3, long j4) {
        this.saveHandler = iSaveHandler1;
        this.worldInfo = new WorldInfo(j4, string2);
        this.worldProvider = worldProvider3;
        worldProvider3.registerWorld(this);
        this.chunkProvider = this.getChunkProvider();
        this.calculateInitialSkylight();
    }

    public World(World world1, WorldProvider worldProvider2) {
        this.lockTimestamp = world1.lockTimestamp;
        this.saveHandler = world1.saveHandler;
        this.worldInfo = new WorldInfo(world1.worldInfo);
        this.worldProvider = worldProvider2;
        worldProvider2.registerWorld(this);
        this.chunkProvider = this.getChunkProvider();
        this.calculateInitialSkylight();
    }

    public World(ISaveHandler iSaveHandler1, String string2, long j3) {
        this(iSaveHandler1, string2, j3, null);
    }

    public World(ISaveHandler iSaveHandler1, String string2, long j3, WorldProvider worldProvider5) {
        this.saveHandler = iSaveHandler1;
        this.worldInfo = iSaveHandler1.loadWorldInfo();
        boolean bl = this.isNewWorld = this.worldInfo == null;
        this.worldProvider = worldProvider5 != null ? worldProvider5 : (this.worldInfo != null && this.worldInfo.getDimension() == -1 ? new WorldProviderHell() : new WorldProvider());
        boolean z6 = false;
        if (this.worldInfo == null) {
            this.worldInfo = new WorldInfo(j3, string2);
            z6 = true;
        } else {
            this.worldInfo.setWorldName(string2);
        }
        this.worldProvider.registerWorld(this);
        this.chunkProvider = this.getChunkProvider();
        if (z6) {
            this.func_25098_c();
        }
        this.calculateInitialSkylight();
    }

    protected IChunkProvider getChunkProvider() {
        IChunkLoader iChunkLoader1 = this.saveHandler.getChunkLoader(this.worldProvider);
        return new ChunkProviderLoadOrGenerate(this, iChunkLoader1, this.worldProvider.getChunkProvider());
    }

    protected void func_25098_c() {
        this.findingSpawnPoint = true;
        int i1 = 0;
        int b2 = 64;
        int i3 = 0;
        while (!this.worldProvider.canCoordinateBeSpawn(i1, i3)) {
            i1 += this.rand.nextInt(64) - this.rand.nextInt(64);
            i3 += this.rand.nextInt(64) - this.rand.nextInt(64);
        }
        this.worldInfo.setSpawn(i1, b2, i3);
        this.findingSpawnPoint = false;
    }

    public void setSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }
        int i1 = this.worldInfo.getSpawnX();
        int i2 = this.worldInfo.getSpawnZ();
        while (this.getFirstUncoveredBlock(i1, i2) == 0) {
            i1 += this.rand.nextInt(8) - this.rand.nextInt(8);
            i2 += this.rand.nextInt(8) - this.rand.nextInt(8);
        }
        this.worldInfo.setSpawnX(i1);
        this.worldInfo.setSpawnZ(i2);
    }

    public int getFirstUncoveredBlock(int i1, int i2) {
        int i3 = 63;
        while (!this.isAirBlock(i1, i3 + 1, i2)) {
            ++i3;
        }
        return this.getBlockId(i1, i3, i2);
    }

    public void func_6464_c() {
    }

    public void spawnPlayerWithLoadedChunks(EntityPlayer entityPlayer1) {
        try {
            NBTTagCompound nBTTagCompound2 = this.worldInfo.getPlayerNBTTagCompound();
            if (nBTTagCompound2 != null) {
                entityPlayer1.readFromNBT(nBTTagCompound2);
                this.worldInfo.setPlayerNBTTagCompound(null);
            }
            if (this.chunkProvider instanceof ChunkProviderLoadOrGenerate) {
                ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate3 = (ChunkProviderLoadOrGenerate)this.chunkProvider;
                int i4 = MathHelper.floor_float((int)entityPlayer1.posX) >> 4;
                int i5 = MathHelper.floor_float((int)entityPlayer1.posZ) >> 4;
                chunkProviderLoadOrGenerate3.setCurrentChunkOver(i4, i5);
            }
            this.entityJoinedWorld(entityPlayer1);
        }
        catch (Exception exception6) {
            exception6.printStackTrace();
        }
    }

    public void saveWorld(boolean z1, IProgressUpdate iProgressUpdate2) {
        if (this.chunkProvider.func_536_b()) {
            if (iProgressUpdate2 != null) {
                iProgressUpdate2.func_594_b("Saving level");
            }
            this.saveLevel();
            if (iProgressUpdate2 != null) {
                iProgressUpdate2.displayLoadingString("Saving chunks");
            }
            this.chunkProvider.saveChunks(z1, iProgressUpdate2);
        }
    }

    private void saveLevel() {
        this.checkSessionLock();
        this.saveHandler.saveWorldInfoAndPlayer(this.worldInfo, this.playerEntities);
    }

    public boolean func_650_a(int i1) {
        if (!this.chunkProvider.func_536_b()) {
            return true;
        }
        if (i1 == 0) {
            this.saveLevel();
        }
        return this.chunkProvider.saveChunks(false, null);
    }

    @Override
    public int getBlockId(int i1, int i2, int i3) {
        return i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000 ? (i2 < 0 ? 0 : (i2 >= 128 ? 0 : this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4).getBlockID(i1 & 0xF, i2, i3 & 0xF))) : 0;
    }

    public boolean isAirBlock(int i1, int i2, int i3) {
        return this.getBlockId(i1, i2, i3) == 0;
    }

    public boolean blockExists(int i1, int i2, int i3) {
        return i2 >= 0 && i2 < 128 ? this.chunkExists(i1 >> 4, i3 >> 4) : false;
    }

    public boolean doChunksNearChunkExist(int i1, int i2, int i3, int i4) {
        return this.checkChunksExist(i1 - i4, i2 - i4, i3 - i4, i1 + i4, i2 + i4, i3 + i4);
    }

    public boolean checkChunksExist(int i1, int i2, int i3, int i4, int i5, int i6) {
        if (i5 >= 0 && i2 < 128) {
            i1 >>= 4;
            i2 >>= 4;
            i3 >>= 4;
            i4 >>= 4;
            i5 >>= 4;
            i6 >>= 4;
            int i7 = i1;
            while (i7 <= i4) {
                int i8 = i3;
                while (i8 <= i6) {
                    if (!this.chunkExists(i7, i8)) {
                        return false;
                    }
                    ++i8;
                }
                ++i7;
            }
            return true;
        }
        return false;
    }

    private boolean chunkExists(int i1, int i2) {
        return this.chunkProvider.chunkExists(i1, i2);
    }

    public Chunk getChunkFromBlockCoords(int i1, int i2) {
        return this.getChunkFromChunkCoords(i1 >> 4, i2 >> 4);
    }

    public Chunk getChunkFromChunkCoords(int i1, int i2) {
        return this.chunkProvider.provideChunk(i1, i2);
    }

    public boolean setBlockAndMetadata(int i1, int i2, int i3, int i4, int i5) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            if (i2 < 0) {
                return false;
            }
            if (i2 >= 128) {
                return false;
            }
            Chunk chunk6 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
            return chunk6.setBlockIDWithMetadata(i1 & 0xF, i2, i3 & 0xF, i4, i5);
        }
        return false;
    }

    public boolean setBlock(int i1, int i2, int i3, int i4) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            if (i2 < 0) {
                return false;
            }
            if (i2 >= 128) {
                return false;
            }
            Chunk chunk5 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
            return chunk5.setBlockID(i1 & 0xF, i2, i3 & 0xF, i4);
        }
        return false;
    }

    @Override
    public Material getBlockMaterial(int i1, int i2, int i3) {
        int i4 = this.getBlockId(i1, i2, i3);
        return i4 == 0 ? Material.air : Block.blocksList[i4].blockMaterial;
    }

    @Override
    public int getBlockMetadata(int i1, int i2, int i3) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            if (i2 < 0) {
                return 0;
            }
            if (i2 >= 128) {
                return 0;
            }
            Chunk chunk4 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
            return chunk4.getBlockMetadata(i1 &= 0xF, i2, i3 &= 0xF);
        }
        return 0;
    }

    public void setBlockMetadataWithNotify(int i1, int i2, int i3, int i4) {
        if (this.setBlockMetadata(i1, i2, i3, i4)) {
            this.notifyBlockChange(i1, i2, i3, this.getBlockId(i1, i2, i3));
        }
    }

    public boolean setBlockMetadata(int i1, int i2, int i3, int i4) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            if (i2 < 0) {
                return false;
            }
            if (i2 >= 128) {
                return false;
            }
            Chunk chunk5 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
            chunk5.setBlockMetadata(i1 &= 0xF, i2, i3 &= 0xF, i4);
            return true;
        }
        return false;
    }

    public boolean setBlockWithNotify(int i1, int i2, int i3, int i4) {
        if (this.setBlock(i1, i2, i3, i4)) {
            this.notifyBlockChange(i1, i2, i3, i4);
            return true;
        }
        return false;
    }

    public boolean setBlockAndMetadataWithNotify(int i1, int i2, int i3, int i4, int i5) {
        if (this.setBlockAndMetadata(i1, i2, i3, i4, i5)) {
            this.notifyBlockChange(i1, i2, i3, i4);
            return true;
        }
        return false;
    }

    public void markBlockNeedsUpdate(int i1, int i2, int i3) {
        int i4 = 0;
        while (i4 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i4)).markBlockAndNeighborsNeedsUpdate(i1, i2, i3);
            ++i4;
        }
    }

    protected void notifyBlockChange(int i1, int i2, int i3, int i4) {
        this.markBlockNeedsUpdate(i1, i2, i3);
        this.notifyBlocksOfNeighborChange(i1, i2, i3, i4);
    }

    public void markBlocksDirtyVertical(int i1, int i2, int i3, int i4) {
        if (i3 > i4) {
            int i5 = i4;
            i4 = i3;
            i3 = i5;
        }
        this.markBlocksDirty(i1, i3, i2, i1, i4, i2);
    }

    public void markBlockAsNeedsUpdate(int i1, int i2, int i3) {
        int i4 = 0;
        while (i4 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i4)).markBlockRangeNeedsUpdate(i1, i2, i3, i1, i2, i3);
            ++i4;
        }
    }

    public void markBlocksDirty(int i1, int i2, int i3, int i4, int i5, int i6) {
        int i7 = 0;
        while (i7 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i7)).markBlockRangeNeedsUpdate(i1, i2, i3, i4, i5, i6);
            ++i7;
        }
    }

    public void notifyBlocksOfNeighborChange(int i1, int i2, int i3, int i4) {
        this.notifyBlockOfNeighborChange(i1 - 1, i2, i3, i4);
        this.notifyBlockOfNeighborChange(i1 + 1, i2, i3, i4);
        this.notifyBlockOfNeighborChange(i1, i2 - 1, i3, i4);
        this.notifyBlockOfNeighborChange(i1, i2 + 1, i3, i4);
        this.notifyBlockOfNeighborChange(i1, i2, i3 - 1, i4);
        this.notifyBlockOfNeighborChange(i1, i2, i3 + 1, i4);
    }

    private void notifyBlockOfNeighborChange(int i1, int i2, int i3, int i4) {
        Block block5;
        if (!this.editingBlocks && !this.multiplayerWorld && (block5 = Block.blocksList[this.getBlockId(i1, i2, i3)]) != null) {
            block5.onNeighborBlockChange(this, i1, i2, i3, i4);
        }
    }

    public boolean canBlockSeeTheSky(int i1, int i2, int i3) {
        return this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4).canBlockSeeTheSky(i1 & 0xF, i2, i3 & 0xF);
    }

    public int getBlockLightValue(int i1, int i2, int i3) {
        return this.getBlockLightValue_do(i1, i2, i3, true);
    }

    public int getBlockLightValue_do(int i1, int i2, int i3, boolean z4) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            int i5;
            if (z4 && ((i5 = this.getBlockId(i1, i2, i3)) == Block.stairSingle.blockID || i5 == Block.tilledField.blockID)) {
                int i6 = this.getBlockLightValue_do(i1, i2 + 1, i3, false);
                int i7 = this.getBlockLightValue_do(i1 + 1, i2, i3, false);
                int i8 = this.getBlockLightValue_do(i1 - 1, i2, i3, false);
                int i9 = this.getBlockLightValue_do(i1, i2, i3 + 1, false);
                int i10 = this.getBlockLightValue_do(i1, i2, i3 - 1, false);
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
                i5 = 15 - this.skylightSubtracted;
                if (i5 < 0) {
                    i5 = 0;
                }
                return i5;
            }
            Chunk chunk11 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
            return chunk11.getBlockLightValue(i1 &= 0xF, i2, i3 &= 0xF, this.skylightSubtracted);
        }
        return 15;
    }

    public boolean canExistingBlockSeeTheSky(int i1, int i2, int i3) {
        if (i1 >= -32000000 && i3 >= -32000000 && i1 < 32000000 && i3 <= 32000000) {
            if (i2 < 0) {
                return false;
            }
            if (i2 >= 128) {
                return true;
            }
            if (!this.chunkExists(i1 >> 4, i3 >> 4)) {
                return false;
            }
            Chunk chunk4 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
            return chunk4.canBlockSeeTheSky(i1 &= 0xF, i2, i3 &= 0xF);
        }
        return false;
    }

    public int getHeightValue(int i1, int i2) {
        if (i1 >= -32000000 && i2 >= -32000000 && i1 < 32000000 && i2 <= 32000000) {
            if (!this.chunkExists(i1 >> 4, i2 >> 4)) {
                return 0;
            }
            Chunk chunk3 = this.getChunkFromChunkCoords(i1 >> 4, i2 >> 4);
            return chunk3.getHeightValue(i1 & 0xF, i2 & 0xF);
        }
        return 0;
    }

    public void neighborLightPropagationChanged(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5) {
        if ((!this.worldProvider.field_6478_e || enumSkyBlock1 != EnumSkyBlock.Sky) && this.blockExists(i2, i3, i4)) {
            int i6;
            if (enumSkyBlock1 == EnumSkyBlock.Sky) {
                if (this.canExistingBlockSeeTheSky(i2, i3, i4)) {
                    i5 = 15;
                }
            } else if (enumSkyBlock1 == EnumSkyBlock.Block && Block.lightValue[i6 = this.getBlockId(i2, i3, i4)] > i5) {
                i5 = Block.lightValue[i6];
            }
            if (this.getSavedLightValue(enumSkyBlock1, i2, i3, i4) != i5) {
                this.func_616_a(enumSkyBlock1, i2, i3, i4, i2, i3, i4);
            }
        }
    }

    public int getSavedLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4) {
        if (i3 >= 0 && i3 < 128 && i2 >= -32000000 && i4 >= -32000000 && i2 < 32000000 && i4 <= 32000000) {
            int i5 = i2 >> 4;
            int i6 = i4 >> 4;
            if (!this.chunkExists(i5, i6)) {
                return 0;
            }
            Chunk chunk7 = this.getChunkFromChunkCoords(i5, i6);
            return chunk7.getSavedLightValue(enumSkyBlock1, i2 & 0xF, i3, i4 & 0xF);
        }
        return enumSkyBlock1.field_1722_c;
    }

    public void setLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5) {
        if (i2 >= -32000000 && i4 >= -32000000 && i2 < 32000000 && i4 <= 32000000 && i3 >= 0 && i3 < 128 && this.chunkExists(i2 >> 4, i4 >> 4)) {
            Chunk chunk6 = this.getChunkFromChunkCoords(i2 >> 4, i4 >> 4);
            chunk6.setLightValue(enumSkyBlock1, i2 & 0xF, i3, i4 & 0xF, i5);
            int i7 = 0;
            while (i7 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(i7)).markBlockAndNeighborsNeedsUpdate(i2, i3, i4);
                ++i7;
            }
        }
    }

    @Override
    public float getLightBrightness(int i1, int i2, int i3) {
        if (Fullbright.instance.isEnabled) {
            return 1.0f;
        }
        return this.worldProvider.lightBrightnessTable[this.getBlockLightValue(i1, i2, i3)];
    }

    public boolean isDaytime() {
        return this.skylightSubtracted < 4;
    }

    public MovingObjectPosition rayTraceBlocks(Vec3D vec3D1, Vec3D vec3D2) {
        return this.rayTraceBlocks_do(vec3D1, vec3D2, false);
    }

    public MovingObjectPosition rayTraceBlocks_do(Vec3D vec3D1, Vec3D vec3D2, boolean z3) {
        if (!(Double.isNaN(vec3D1.xCoord) || Double.isNaN(vec3D1.yCoord) || Double.isNaN(vec3D1.zCoord))) {
            if (!(Double.isNaN(vec3D2.xCoord) || Double.isNaN(vec3D2.yCoord) || Double.isNaN(vec3D2.zCoord))) {
                int i4 = MathHelper.floor_double(vec3D2.xCoord);
                int i5 = MathHelper.floor_double(vec3D2.yCoord);
                int i6 = MathHelper.floor_double(vec3D2.zCoord);
                int i7 = MathHelper.floor_double(vec3D1.xCoord);
                int i8 = MathHelper.floor_double(vec3D1.yCoord);
                int i9 = MathHelper.floor_double(vec3D1.zCoord);
                int i10 = 200;
                while (i10-- >= 0) {
                    MovingObjectPosition movingObjectPosition34;
                    int b35;
                    if (Double.isNaN(vec3D1.xCoord) || Double.isNaN(vec3D1.yCoord) || Double.isNaN(vec3D1.zCoord)) {
                        return null;
                    }
                    if (i7 == i4 && i8 == i5 && i9 == i6) {
                        return null;
                    }
                    double d11 = 999.0;
                    double d13 = 999.0;
                    double d15 = 999.0;
                    if (i4 > i7) {
                        d11 = (double)i7 + 1.0;
                    }
                    if (i4 < i7) {
                        d11 = (double)i7 + 0.0;
                    }
                    if (i5 > i8) {
                        d13 = (double)i8 + 1.0;
                    }
                    if (i5 < i8) {
                        d13 = (double)i8 + 0.0;
                    }
                    if (i6 > i9) {
                        d15 = (double)i9 + 1.0;
                    }
                    if (i6 < i9) {
                        d15 = (double)i9 + 0.0;
                    }
                    double d17 = 999.0;
                    double d19 = 999.0;
                    double d21 = 999.0;
                    double d23 = vec3D2.xCoord - vec3D1.xCoord;
                    double d25 = vec3D2.yCoord - vec3D1.yCoord;
                    double d27 = vec3D2.zCoord - vec3D1.zCoord;
                    if (d11 != 999.0) {
                        d17 = (d11 - vec3D1.xCoord) / d23;
                    }
                    if (d13 != 999.0) {
                        d19 = (d13 - vec3D1.yCoord) / d25;
                    }
                    if (d15 != 999.0) {
                        d21 = (d15 - vec3D1.zCoord) / d27;
                    }
                    boolean z29 = false;
                    if (d17 < d19 && d17 < d21) {
                        b35 = i4 > i7 ? 4 : 5;
                        vec3D1.xCoord = d11;
                        vec3D1.yCoord += d25 * d17;
                        vec3D1.zCoord += d27 * d17;
                    } else if (d19 < d21) {
                        b35 = i5 > i8 ? 0 : 1;
                        vec3D1.xCoord += d23 * d19;
                        vec3D1.yCoord = d13;
                        vec3D1.zCoord += d27 * d19;
                    } else {
                        b35 = i6 > i9 ? 2 : 3;
                        vec3D1.xCoord += d23 * d21;
                        vec3D1.yCoord += d25 * d21;
                        vec3D1.zCoord = d15;
                    }
                    Vec3D vec3D30 = Vec3D.createVector(vec3D1.xCoord, vec3D1.yCoord, vec3D1.zCoord);
                    vec3D30.xCoord = MathHelper.floor_double(vec3D1.xCoord);
                    i7 = (int)vec3D30.xCoord;
                    if (b35 == 5) {
                        --i7;
                        vec3D30.xCoord += 1.0;
                    }
                    vec3D30.yCoord = MathHelper.floor_double(vec3D1.yCoord);
                    i8 = (int)vec3D30.yCoord;
                    if (b35 == 1) {
                        --i8;
                        vec3D30.yCoord += 1.0;
                    }
                    vec3D30.zCoord = MathHelper.floor_double(vec3D1.zCoord);
                    i9 = (int)vec3D30.zCoord;
                    if (b35 == 3) {
                        --i9;
                        vec3D30.zCoord += 1.0;
                    }
                    int i31 = this.getBlockId(i7, i8, i9);
                    int i32 = this.getBlockMetadata(i7, i8, i9);
                    Block block33 = Block.blocksList[i31];
                    if (i31 <= 0 || !block33.canCollideCheck(i32, z3) || (movingObjectPosition34 = block33.collisionRayTrace(this, i7, i8, i9, vec3D1, vec3D2)) == null) continue;
                    return movingObjectPosition34;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public void playSoundAtEntity(Entity entity1, String string2, float f3, float f4) {
        int i5 = 0;
        while (i5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i5)).playSound(string2, entity1.posX, entity1.posY - (double)entity1.yOffset, entity1.posZ, f3, f4);
            ++i5;
        }
    }

    public void playSoundEffect(double d1, double d3, double d5, String string7, float f8, float f9) {
        int i10 = 0;
        while (i10 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i10)).playSound(string7, d1, d3, d5, f8, f9);
            ++i10;
        }
    }

    public void playRecord(String string1, int i2, int i3, int i4) {
        int i5 = 0;
        while (i5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i5)).playRecord(string1, i2, i3, i4);
            ++i5;
        }
    }

    public void spawnParticle(String string1, double d2, double d4, double d6, double d8, double d10, double d12) {
        int i14 = 0;
        while (i14 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i14)).spawnParticle(string1, d2, d4, d6, d8, d10, d12);
            ++i14;
        }
    }

    public boolean entityJoinedWorld(Entity entity1) {
        int i2 = MathHelper.floor_double(entity1.posX / 16.0);
        int i3 = MathHelper.floor_double(entity1.posZ / 16.0);
        boolean z4 = false;
        if (entity1 instanceof EntityPlayer) {
            z4 = true;
        }
        if (!z4 && !this.chunkExists(i2, i3)) {
            return false;
        }
        if (entity1 instanceof EntityPlayer) {
            EntityPlayer entityPlayer5 = (EntityPlayer)entity1;
            this.playerEntities.add(entityPlayer5);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(i2, i3).addEntity(entity1);
        this.loadedEntityList.add(entity1);
        this.obtainEntitySkin(entity1);
        return true;
    }

    protected void obtainEntitySkin(Entity entity1) {
        int i2 = 0;
        while (i2 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i2)).obtainEntitySkin(entity1);
            ++i2;
        }
    }

    protected void releaseEntitySkin(Entity entity1) {
        int i2 = 0;
        while (i2 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i2)).releaseEntitySkin(entity1);
            ++i2;
        }
    }

    public void setEntityDead(Entity entity1) {
        if (entity1.riddenByEntity != null) {
            entity1.riddenByEntity.mountEntity(null);
        }
        if (entity1.ridingEntity != null) {
            entity1.mountEntity(null);
        }
        entity1.setEntityDead();
        if (entity1 instanceof EntityPlayer) {
            this.playerEntities.remove((EntityPlayer)entity1);
            this.updateAllPlayersSleepingFlag();
        }
    }

    public void addWorldAccess(IWorldAccess iWorldAccess1) {
        this.worldAccesses.add(iWorldAccess1);
    }

    public void removeWorldAccess(IWorldAccess iWorldAccess1) {
        this.worldAccesses.remove(iWorldAccess1);
    }

    public List getCollidingBoundingBoxes(Entity entity1, AxisAlignedBB axisAlignedBB2) {
        this.field_9428_I.clear();
        int i3 = MathHelper.floor_double(axisAlignedBB2.minX);
        int i4 = MathHelper.floor_double(axisAlignedBB2.maxX + 1.0);
        int i5 = MathHelper.floor_double(axisAlignedBB2.minY);
        int i6 = MathHelper.floor_double(axisAlignedBB2.maxY + 1.0);
        int i7 = MathHelper.floor_double(axisAlignedBB2.minZ);
        int i8 = MathHelper.floor_double(axisAlignedBB2.maxZ + 1.0);
        int i9 = i3;
        while (i9 < i4) {
            int i10 = i7;
            while (i10 < i8) {
                if (this.blockExists(i9, 64, i10)) {
                    int i11 = i5 - 1;
                    while (i11 < i6) {
                        Block block12 = Block.blocksList[this.getBlockId(i9, i11, i10)];
                        if (block12 != null) {
                            block12.getCollidingBoundingBoxes(this, i9, i11, i10, axisAlignedBB2, this.field_9428_I);
                        }
                        ++i11;
                    }
                }
                ++i10;
            }
            ++i9;
        }
        double d14 = 0.25;
        List list15 = this.getEntitiesWithinAABBExcludingEntity(entity1, axisAlignedBB2.expand(d14, d14, d14));
        int i16 = 0;
        while (i16 < list15.size()) {
            AxisAlignedBB axisAlignedBB13 = ((Entity)list15.get(i16)).getBoundingBox();
            if (axisAlignedBB13 != null && axisAlignedBB13.intersectsWith(axisAlignedBB2)) {
                this.field_9428_I.add(axisAlignedBB13);
            }
            if ((axisAlignedBB13 = entity1.getCollisionBox((Entity)list15.get(i16))) != null && axisAlignedBB13.intersectsWith(axisAlignedBB2)) {
                this.field_9428_I.add(axisAlignedBB13);
            }
            ++i16;
        }
        return this.field_9428_I;
    }

    public int calculateSkylightSubtracted(float f1) {
        float f2 = this.getCelestialAngle(f1);
        float f3 = 1.0f - (MathHelper.cos(f2 * (float)Math.PI * 2.0f) * 2.0f + 0.5f);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        return (int)(f3 * 11.0f);
    }

    public Vec3D func_4079_a(Entity entity1, float f2) {
        float f3 = this.getCelestialAngle(f2);
        float f4 = MathHelper.cos(f3 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        int i5 = MathHelper.floor_double(entity1.posX);
        int i6 = MathHelper.floor_double(entity1.posZ);
        float f7 = (float)this.getWorldChunkManager().func_4072_b(i5, i6);
        int i8 = this.getWorldChunkManager().func_4073_a(i5, i6).getSkyColorByTemp(f7);
        float f9 = (float)(i8 >> 16 & 0xFF) / 255.0f;
        float f10 = (float)(i8 >> 8 & 0xFF) / 255.0f;
        float f11 = (float)(i8 & 0xFF) / 255.0f;
        return Vec3D.createVector(f9 *= f4, f10 *= f4, f11 *= f4);
    }

    public float getCelestialAngle(float f1) {
        return this.worldProvider.calculateCelestialAngle(this.worldInfo.getWorldTime(), f1);
    }

    public Vec3D func_628_d(float f1) {
        float f2 = this.getCelestialAngle(f1);
        float f3 = MathHelper.cos(f2 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        float f4 = (float)(this.field_1019_F >> 16 & 0xFFL) / 255.0f;
        float f5 = (float)(this.field_1019_F >> 8 & 0xFFL) / 255.0f;
        float f6 = (float)(this.field_1019_F & 0xFFL) / 255.0f;
        return Vec3D.createVector(f4 *= f3 * 0.9f + 0.1f, f5 *= f3 * 0.9f + 0.1f, f6 *= f3 * 0.85f + 0.15f);
    }

    public Vec3D getFogColor(float f1) {
        float f2 = this.getCelestialAngle(f1);
        return this.worldProvider.func_4096_a(f2, f1);
    }

    public int findTopSolidBlock(int i1, int i2) {
        Chunk chunk3 = this.getChunkFromBlockCoords(i1, i2);
        int i4 = 127;
        while (this.getBlockMaterial(i1, i4, i2).getIsSolid() && i4 > 0) {
            --i4;
        }
        i1 &= 0xF;
        i2 &= 0xF;
        while (i4 > 0) {
            int i5 = chunk3.getBlockID(i1, i4, i2);
            if (i5 != 0 && (Block.blocksList[i5].blockMaterial.getIsSolid() || Block.blocksList[i5].blockMaterial.getIsLiquid())) {
                return i4 + 1;
            }
            --i4;
        }
        return -1;
    }

    public int func_696_e(int i1, int i2) {
        return this.getChunkFromBlockCoords(i1, i2).getHeightValue(i1 & 0xF, i2 & 0xF);
    }

    public float getStarBrightness(float f1) {
        float f2 = this.getCelestialAngle(f1);
        float f3 = 1.0f - (MathHelper.cos(f2 * (float)Math.PI * 2.0f) * 2.0f + 0.75f);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        return f3 * f3 * 0.5f;
    }

    public void scheduleBlockUpdate(int i1, int i2, int i3, int i4, int i5) {
        NextTickListEntry nextTickListEntry6 = new NextTickListEntry(i1, i2, i3, i4);
        int b7 = 8;
        if (this.scheduledUpdatesAreImmediate) {
            int i8;
            if (this.checkChunksExist(nextTickListEntry6.xCoord - b7, nextTickListEntry6.yCoord - b7, nextTickListEntry6.zCoord - b7, nextTickListEntry6.xCoord + b7, nextTickListEntry6.yCoord + b7, nextTickListEntry6.zCoord + b7) && (i8 = this.getBlockId(nextTickListEntry6.xCoord, nextTickListEntry6.yCoord, nextTickListEntry6.zCoord)) == nextTickListEntry6.blockID && i8 > 0) {
                Block.blocksList[i8].updateTick(this, nextTickListEntry6.xCoord, nextTickListEntry6.yCoord, nextTickListEntry6.zCoord, this.rand);
            }
        } else if (this.checkChunksExist(i1 - b7, i2 - b7, i3 - b7, i1 + b7, i2 + b7, i3 + b7)) {
            if (i4 > 0) {
                nextTickListEntry6.setScheduledTime((long)i5 + this.worldInfo.getWorldTime());
            }
            if (!this.scheduledTickSet.contains(nextTickListEntry6)) {
                this.scheduledTickSet.add(nextTickListEntry6);
                this.scheduledTickTreeSet.add(nextTickListEntry6);
            }
        }
    }

    public void func_633_c() {
        int i4;
        int i3;
        Entity entity2;
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int i1 = 0;
        while (i1 < this.unloadedEntityList.size()) {
            entity2 = (Entity)this.unloadedEntityList.get(i1);
            i3 = entity2.chunkCoordX;
            i4 = entity2.chunkCoordZ;
            if (entity2.addedToChunk && this.chunkExists(i3, i4)) {
                this.getChunkFromChunkCoords(i3, i4).func_1015_b(entity2);
            }
            ++i1;
        }
        i1 = 0;
        while (i1 < this.unloadedEntityList.size()) {
            this.releaseEntitySkin((Entity)this.unloadedEntityList.get(i1));
            ++i1;
        }
        this.unloadedEntityList.clear();
        i1 = 0;
        while (i1 < this.loadedEntityList.size()) {
            block12: {
                block11: {
                    entity2 = (Entity)this.loadedEntityList.get(i1);
                    if (entity2.ridingEntity == null) break block11;
                    if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) break block12;
                    entity2.ridingEntity.riddenByEntity = null;
                    entity2.ridingEntity = null;
                }
                if (!entity2.isDead) {
                    this.updateEntity(entity2);
                }
                if (entity2.isDead) {
                    i3 = entity2.chunkCoordX;
                    i4 = entity2.chunkCoordZ;
                    if (entity2.addedToChunk && this.chunkExists(i3, i4)) {
                        this.getChunkFromChunkCoords(i3, i4).func_1015_b(entity2);
                    }
                    this.loadedEntityList.remove(i1--);
                    this.releaseEntitySkin(entity2);
                }
            }
            ++i1;
        }
        i1 = 0;
        while (i1 < this.loadedTileEntityList.size()) {
            TileEntity tileEntity5 = (TileEntity)this.loadedTileEntityList.get(i1);
            tileEntity5.updateEntity();
            ++i1;
        }
    }

    public void updateEntity(Entity entity1) {
        this.updateEntityWithOptionalForce(entity1, true);
    }

    public void updateEntityWithOptionalForce(Entity entity1, boolean z2) {
        int i3 = MathHelper.floor_double(entity1.posX);
        int i4 = MathHelper.floor_double(entity1.posZ);
        int b5 = 32;
        if (!z2 || this.checkChunksExist(i3 - b5, 0, i4 - b5, i3 + b5, 128, i4 + b5)) {
            entity1.lastTickPosX = entity1.posX;
            entity1.lastTickPosY = entity1.posY;
            entity1.lastTickPosZ = entity1.posZ;
            entity1.prevRotationYaw = entity1.rotationYaw;
            entity1.prevRotationPitch = entity1.rotationPitch;
            if (z2 && entity1.addedToChunk) {
                if (entity1.ridingEntity != null) {
                    entity1.updateRidden();
                } else {
                    entity1.onUpdate();
                }
            }
            if (Double.isNaN(entity1.posX) || Double.isInfinite(entity1.posX)) {
                entity1.posX = entity1.lastTickPosX;
            }
            if (Double.isNaN(entity1.posY) || Double.isInfinite(entity1.posY)) {
                entity1.posY = entity1.lastTickPosY;
            }
            if (Double.isNaN(entity1.posZ) || Double.isInfinite(entity1.posZ)) {
                entity1.posZ = entity1.lastTickPosZ;
            }
            if (Double.isNaN(entity1.rotationPitch) || Double.isInfinite(entity1.rotationPitch)) {
                entity1.rotationPitch = entity1.prevRotationPitch;
            }
            if (Double.isNaN(entity1.rotationYaw) || Double.isInfinite(entity1.rotationYaw)) {
                entity1.rotationYaw = entity1.prevRotationYaw;
            }
            int i6 = MathHelper.floor_double(entity1.posX / 16.0);
            int i7 = MathHelper.floor_double(entity1.posY / 16.0);
            int i8 = MathHelper.floor_double(entity1.posZ / 16.0);
            if (!entity1.addedToChunk || entity1.chunkCoordX != i6 || entity1.chunkCoordY != i7 || entity1.chunkCoordZ != i8) {
                if (entity1.addedToChunk && this.chunkExists(entity1.chunkCoordX, entity1.chunkCoordZ)) {
                    this.getChunkFromChunkCoords(entity1.chunkCoordX, entity1.chunkCoordZ).func_1016_a(entity1, entity1.chunkCoordY);
                }
                if (this.chunkExists(i6, i8)) {
                    entity1.addedToChunk = true;
                    this.getChunkFromChunkCoords(i6, i8).addEntity(entity1);
                } else {
                    entity1.addedToChunk = false;
                }
            }
            if (z2 && entity1.addedToChunk && entity1.riddenByEntity != null) {
                if (!entity1.riddenByEntity.isDead && entity1.riddenByEntity.ridingEntity == entity1) {
                    this.updateEntity(entity1.riddenByEntity);
                } else {
                    entity1.riddenByEntity.ridingEntity = null;
                    entity1.riddenByEntity = null;
                }
            }
        }
    }

    public boolean checkIfAABBIsClear(AxisAlignedBB axisAlignedBB1) {
        List list2 = this.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB1);
        int i3 = 0;
        while (i3 < list2.size()) {
            Entity entity4 = (Entity)list2.get(i3);
            if (!entity4.isDead && entity4.preventEntitySpawning) {
                return false;
            }
            ++i3;
        }
        return true;
    }

    public boolean getIsAnyLiquid(AxisAlignedBB axisAlignedBB1) {
        int i2 = MathHelper.floor_double(axisAlignedBB1.minX);
        int i3 = MathHelper.floor_double(axisAlignedBB1.maxX + 1.0);
        int i4 = MathHelper.floor_double(axisAlignedBB1.minY);
        int i5 = MathHelper.floor_double(axisAlignedBB1.maxY + 1.0);
        int i6 = MathHelper.floor_double(axisAlignedBB1.minZ);
        int i7 = MathHelper.floor_double(axisAlignedBB1.maxZ + 1.0);
        if (axisAlignedBB1.minX < 0.0) {
            --i2;
        }
        if (axisAlignedBB1.minY < 0.0) {
            --i4;
        }
        if (axisAlignedBB1.minZ < 0.0) {
            --i6;
        }
        int i8 = i2;
        while (i8 < i3) {
            int i9 = i4;
            while (i9 < i5) {
                int i10 = i6;
                while (i10 < i7) {
                    Block block11 = Block.blocksList[this.getBlockId(i8, i9, i10)];
                    if (block11 != null && block11.blockMaterial.getIsLiquid()) {
                        return true;
                    }
                    ++i10;
                }
                ++i9;
            }
            ++i8;
        }
        return false;
    }

    public boolean isBoundingBoxBurning(AxisAlignedBB axisAlignedBB1) {
        int i7;
        int i2 = MathHelper.floor_double(axisAlignedBB1.minX);
        int i3 = MathHelper.floor_double(axisAlignedBB1.maxX + 1.0);
        int i4 = MathHelper.floor_double(axisAlignedBB1.minY);
        int i5 = MathHelper.floor_double(axisAlignedBB1.maxY + 1.0);
        int i6 = MathHelper.floor_double(axisAlignedBB1.minZ);
        if (this.checkChunksExist(i2, i4, i6, i3, i5, i7 = MathHelper.floor_double(axisAlignedBB1.maxZ + 1.0))) {
            int i8 = i2;
            while (i8 < i3) {
                int i9 = i4;
                while (i9 < i5) {
                    int i10 = i6;
                    while (i10 < i7) {
                        int i11 = this.getBlockId(i8, i9, i10);
                        if (i11 == Block.fire.blockID || i11 == Block.lavaMoving.blockID || i11 == Block.lavaStill.blockID) {
                            return true;
                        }
                        ++i10;
                    }
                    ++i9;
                }
                ++i8;
            }
        }
        return false;
    }

    public boolean handleMaterialAcceleration(AxisAlignedBB axisAlignedBB1, Material material2, Entity entity3) {
        int i9;
        int i4 = MathHelper.floor_double(axisAlignedBB1.minX);
        int i5 = MathHelper.floor_double(axisAlignedBB1.maxX + 1.0);
        int i6 = MathHelper.floor_double(axisAlignedBB1.minY);
        int i7 = MathHelper.floor_double(axisAlignedBB1.maxY + 1.0);
        int i8 = MathHelper.floor_double(axisAlignedBB1.minZ);
        if (!this.checkChunksExist(i4, i6, i8, i5, i7, i9 = MathHelper.floor_double(axisAlignedBB1.maxZ + 1.0))) {
            return false;
        }
        boolean z10 = false;
        Vec3D vec3D11 = Vec3D.createVector(0.0, 0.0, 0.0);
        int i12 = i4;
        while (i12 < i5) {
            int i13 = i6;
            while (i13 < i7) {
                int i14 = i8;
                while (i14 < i9) {
                    double d16;
                    Block block15 = Block.blocksList[this.getBlockId(i12, i13, i14)];
                    if (block15 != null && block15.blockMaterial == material2 && (double)i7 >= (d16 = (double)((float)(i13 + 1) - BlockFluids.getPercentAir(this.getBlockMetadata(i12, i13, i14))))) {
                        z10 = true;
                        block15.velocityToAddToEntity(this, i12, i13, i14, entity3, vec3D11);
                    }
                    ++i14;
                }
                ++i13;
            }
            ++i12;
        }
        if (vec3D11.lengthVector() > 0.0) {
            vec3D11 = vec3D11.normalize();
            double d18 = 0.004;
            entity3.motionX += vec3D11.xCoord * d18;
            entity3.motionY += vec3D11.yCoord * d18;
            entity3.motionZ += vec3D11.zCoord * d18;
        }
        return z10;
    }

    public boolean isMaterialInBB(AxisAlignedBB axisAlignedBB1, Material material2) {
        int i3 = MathHelper.floor_double(axisAlignedBB1.minX);
        int i4 = MathHelper.floor_double(axisAlignedBB1.maxX + 1.0);
        int i5 = MathHelper.floor_double(axisAlignedBB1.minY);
        int i6 = MathHelper.floor_double(axisAlignedBB1.maxY + 1.0);
        int i7 = MathHelper.floor_double(axisAlignedBB1.minZ);
        int i8 = MathHelper.floor_double(axisAlignedBB1.maxZ + 1.0);
        int i9 = i3;
        while (i9 < i4) {
            int i10 = i5;
            while (i10 < i6) {
                int i11 = i7;
                while (i11 < i8) {
                    Block block12 = Block.blocksList[this.getBlockId(i9, i10, i11)];
                    if (block12 != null && block12.blockMaterial == material2) {
                        return true;
                    }
                    ++i11;
                }
                ++i10;
            }
            ++i9;
        }
        return false;
    }

    public boolean isAABBInMaterial(AxisAlignedBB axisAlignedBB1, Material material2) {
        int i3 = MathHelper.floor_double(axisAlignedBB1.minX);
        int i4 = MathHelper.floor_double(axisAlignedBB1.maxX + 1.0);
        int i5 = MathHelper.floor_double(axisAlignedBB1.minY);
        int i6 = MathHelper.floor_double(axisAlignedBB1.maxY + 1.0);
        int i7 = MathHelper.floor_double(axisAlignedBB1.minZ);
        int i8 = MathHelper.floor_double(axisAlignedBB1.maxZ + 1.0);
        int i9 = i3;
        while (i9 < i4) {
            int i10 = i5;
            while (i10 < i6) {
                int i11 = i7;
                while (i11 < i8) {
                    Block block12 = Block.blocksList[this.getBlockId(i9, i10, i11)];
                    if (block12 != null && block12.blockMaterial == material2) {
                        int i13 = this.getBlockMetadata(i9, i10, i11);
                        double d14 = i10 + 1;
                        if (i13 < 8) {
                            d14 = (double)(i10 + 1) - (double)i13 / 8.0;
                        }
                        if (d14 >= axisAlignedBB1.minY) {
                            return true;
                        }
                    }
                    ++i11;
                }
                ++i10;
            }
            ++i9;
        }
        return false;
    }

    public Explosion createExplosion(Entity entity1, double d2, double d4, double d6, float f8) {
        return this.newExplosion(entity1, d2, d4, d6, f8, false);
    }

    public Explosion newExplosion(Entity entity1, double d2, double d4, double d6, float f8, boolean z9) {
        Explosion explosion10 = new Explosion(this, entity1, d2, d4, d6, f8);
        explosion10.field_12257_a = z9;
        explosion10.doExplosionA();
        explosion10.doExplosionB();
        return explosion10;
    }

    public float func_675_a(Vec3D vec3D1, AxisAlignedBB axisAlignedBB2) {
        double d3 = 1.0 / ((axisAlignedBB2.maxX - axisAlignedBB2.minX) * 2.0 + 1.0);
        double d5 = 1.0 / ((axisAlignedBB2.maxY - axisAlignedBB2.minY) * 2.0 + 1.0);
        double d7 = 1.0 / ((axisAlignedBB2.maxZ - axisAlignedBB2.minZ) * 2.0 + 1.0);
        int i9 = 0;
        int i10 = 0;
        float f11 = 0.0f;
        while (f11 <= 1.0f) {
            float f12 = 0.0f;
            while (f12 <= 1.0f) {
                float f13 = 0.0f;
                while (f13 <= 1.0f) {
                    double d14 = axisAlignedBB2.minX + (axisAlignedBB2.maxX - axisAlignedBB2.minX) * (double)f11;
                    double d16 = axisAlignedBB2.minY + (axisAlignedBB2.maxY - axisAlignedBB2.minY) * (double)f12;
                    double d18 = axisAlignedBB2.minZ + (axisAlignedBB2.maxZ - axisAlignedBB2.minZ) * (double)f13;
                    if (this.rayTraceBlocks(Vec3D.createVector(d14, d16, d18), vec3D1) == null) {
                        ++i9;
                    }
                    ++i10;
                    f13 = (float)((double)f13 + d7);
                }
                f12 = (float)((double)f12 + d5);
            }
            f11 = (float)((double)f11 + d3);
        }
        return (float)i9 / (float)i10;
    }

    public void onBlockHit(int i1, int i2, int i3, int i4) {
        if (i4 == 0) {
            --i2;
        }
        if (i4 == 1) {
            ++i2;
        }
        if (i4 == 2) {
            --i3;
        }
        if (i4 == 3) {
            ++i3;
        }
        if (i4 == 4) {
            --i1;
        }
        if (i4 == 5) {
            ++i1;
        }
        if (this.getBlockId(i1, i2, i3) == Block.fire.blockID) {
            this.playSoundEffect((float)i1 + 0.5f, (float)i2 + 0.5f, (float)i3 + 0.5f, "random.fizz", 0.5f, 2.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8f);
            this.setBlockWithNotify(i1, i2, i3, 0);
        }
    }

    public Entity func_4085_a(Class class1) {
        return null;
    }

    public String func_687_d() {
        return "All: " + this.loadedEntityList.size();
    }

    public String func_21119_g() {
        return this.chunkProvider.toString();
    }

    @Override
    public TileEntity getBlockTileEntity(int i1, int i2, int i3) {
        Chunk chunk4 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
        return chunk4 != null ? chunk4.getChunkBlockTileEntity(i1 & 0xF, i2, i3 & 0xF) : null;
    }

    public void setBlockTileEntity(int i1, int i2, int i3, TileEntity tileEntity4) {
        Chunk chunk5 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
        if (chunk5 != null) {
            chunk5.setChunkBlockTileEntity(i1 & 0xF, i2, i3 & 0xF, tileEntity4);
        }
    }

    public void removeBlockTileEntity(int i1, int i2, int i3) {
        Chunk chunk4 = this.getChunkFromChunkCoords(i1 >> 4, i3 >> 4);
        if (chunk4 != null) {
            chunk4.removeChunkBlockTileEntity(i1 & 0xF, i2, i3 & 0xF);
        }
    }

    @Override
    public boolean isBlockOpaqueCube(int i1, int i2, int i3) {
        Block block4 = Block.blocksList[this.getBlockId(i1, i2, i3)];
        return block4 == null ? false : block4.isOpaqueCube();
    }

    public void func_651_a(IProgressUpdate iProgressUpdate1) {
        this.saveWorld(true, iProgressUpdate1);
    }

    public boolean func_6465_g() {
        boolean z2;
        if (this.field_4204_J >= 50) {
            return false;
        }
        ++this.field_4204_J;
        try {
            int i1 = 500;
            while (this.field_1051_z.size() > 0) {
                if (--i1 <= 0) {
                    boolean z22;
                    boolean bl = z22 = true;
                    return bl;
                }
                ((MetadataChunkBlock)this.field_1051_z.remove(this.field_1051_z.size() - 1)).func_4127_a(this);
            }
            z2 = false;
        }
        finally {
            --this.field_4204_J;
        }
        return z2;
    }

    public void func_616_a(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.func_627_a(enumSkyBlock1, i2, i3, i4, i5, i6, i7, true);
    }

    public void func_627_a(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5, int i6, int i7, boolean z8) {
        if (!this.worldProvider.field_6478_e || enumSkyBlock1 != EnumSkyBlock.Sky) {
            if (++field_9429_y == 50) {
                --field_9429_y;
            } else {
                int i9 = (i5 + i2) / 2;
                int i10 = (i7 + i4) / 2;
                if (!this.blockExists(i9, 64, i10)) {
                    --field_9429_y;
                } else if (!this.getChunkFromBlockCoords(i9, i10).func_21167_h()) {
                    int i12;
                    int i11 = this.field_1051_z.size();
                    if (z8) {
                        i12 = 5;
                        if (i12 > i11) {
                            i12 = i11;
                        }
                        int i13 = 0;
                        while (i13 < i12) {
                            MetadataChunkBlock metadataChunkBlock14 = (MetadataChunkBlock)this.field_1051_z.get(this.field_1051_z.size() - i13 - 1);
                            if (metadataChunkBlock14.field_1299_a == enumSkyBlock1 && metadataChunkBlock14.func_866_a(i2, i3, i4, i5, i6, i7)) {
                                --field_9429_y;
                                return;
                            }
                            ++i13;
                        }
                    }
                    this.field_1051_z.add(new MetadataChunkBlock(enumSkyBlock1, i2, i3, i4, i5, i6, i7));
                    i12 = 1000000;
                    if (this.field_1051_z.size() > 1000000) {
                        System.out.println("More than " + i12 + " updates, aborting lighting updates");
                        this.field_1051_z.clear();
                    }
                    --field_9429_y;
                }
            }
        }
    }

    public void calculateInitialSkylight() {
        int i1 = this.calculateSkylightSubtracted(1.0f);
        if (i1 != this.skylightSubtracted) {
            this.skylightSubtracted = i1;
        }
    }

    public void setAllowedMobSpawns(boolean z1, boolean z2) {
        this.spawnHostileMobs = z1;
        this.spawnPeacefulMobs = z2;
    }

    public void tick() {
        long j2;
        if (this.isAllPlayersFullyAsleep()) {
            boolean z1 = false;
            if (this.spawnHostileMobs && this.difficultySetting >= 1) {
                z1 = SpawnerAnimals.performSleepSpawning(this, this.playerEntities);
            }
            if (!z1) {
                j2 = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(j2 - j2 % 24000L);
                this.wakeUpAllPlayers();
            }
        }
        SpawnerAnimals.performSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs);
        this.chunkProvider.func_532_a();
        int i4 = this.calculateSkylightSubtracted(1.0f);
        if (i4 != this.skylightSubtracted) {
            this.skylightSubtracted = i4;
            int i5 = 0;
            while (i5 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(i5)).updateAllRenderers();
                ++i5;
            }
        }
        if ((j2 = this.worldInfo.getWorldTime() + 1L) % (long)this.autosavePeriod == 0L) {
            this.saveWorld(false, null);
        }
        this.worldInfo.setWorldTime(j2);
        this.TickUpdates(false);
        this.updateBlocksAndPlayCaveSounds();
    }

    protected void updateBlocksAndPlayCaveSounds() {
        int i7;
        int i6;
        int i4;
        int i3;
        this.field_9427_K.clear();
        int i1 = 0;
        while (i1 < this.playerEntities.size()) {
            EntityPlayer entityPlayer2 = (EntityPlayer)this.playerEntities.get(i1);
            i3 = MathHelper.floor_double(entityPlayer2.posX / 16.0);
            i4 = MathHelper.floor_double(entityPlayer2.posZ / 16.0);
            int b5 = 9;
            i6 = -b5;
            while (i6 <= b5) {
                i7 = -b5;
                while (i7 <= b5) {
                    this.field_9427_K.add(new ChunkCoordIntPair(i6 + i3, i7 + i4));
                    ++i7;
                }
                ++i6;
            }
            ++i1;
        }
        if (this.field_9426_L > 0) {
            --this.field_9426_L;
        }
        for (ChunkCoordIntPair chunkCoordIntPair13 : this.field_9427_K) {
            int i10;
            int i9;
            int i8;
            i3 = chunkCoordIntPair13.chunkXPos * 16;
            i4 = chunkCoordIntPair13.chunkZPos * 16;
            Chunk chunk14 = this.getChunkFromChunkCoords(chunkCoordIntPair13.chunkXPos, chunkCoordIntPair13.chunkZPos);
            if (this.field_9426_L == 0) {
                EntityPlayer entityPlayer11;
                this.field_9437_g = this.field_9437_g * 3 + this.field_9436_h;
                i6 = this.field_9437_g >> 2;
                i7 = i6 & 0xF;
                i8 = i6 >> 8 & 0xF;
                i9 = i6 >> 16 & 0x7F;
                i10 = chunk14.getBlockID(i7, i9, i8);
                if (i10 == 0 && this.getBlockLightValue(i7 += i3, i9, i8 += i4) <= this.rand.nextInt(8) && this.getSavedLightValue(EnumSkyBlock.Sky, i7, i9, i8) <= 0 && (entityPlayer11 = this.getClosestPlayer((double)i7 + 0.5, (double)i9 + 0.5, (double)i8 + 0.5, 8.0)) != null && entityPlayer11.getDistanceSq((double)i7 + 0.5, (double)i9 + 0.5, (double)i8 + 0.5) > 4.0) {
                    this.playSoundEffect((double)i7 + 0.5, (double)i9 + 0.5, (double)i8 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.field_9426_L = this.rand.nextInt(12000) + 6000;
                }
            }
            i6 = 0;
            while (i6 < 80) {
                this.field_9437_g = this.field_9437_g * 3 + this.field_9436_h;
                i7 = this.field_9437_g >> 2;
                i8 = i7 & 0xF;
                i9 = i7 >> 8 & 0xF;
                i10 = i7 >> 16 & 0x7F;
                int i15 = chunk14.blocks[i8 << 11 | i9 << 7 | i10] & 0xFF;
                if (Block.tickOnLoad[i15]) {
                    Block.blocksList[i15].updateTick(this, i8 + i3, i10, i9 + i4, this.rand);
                }
                ++i6;
            }
        }
    }

    public boolean TickUpdates(boolean z1) {
        int i2 = this.scheduledTickTreeSet.size();
        if (i2 != this.scheduledTickSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (i2 > 1000) {
            i2 = 1000;
        }
        int i3 = 0;
        while (i3 < i2) {
            int i6;
            NextTickListEntry nextTickListEntry4 = (NextTickListEntry)this.scheduledTickTreeSet.first();
            if (!z1 && nextTickListEntry4.scheduledTime > this.worldInfo.getWorldTime()) break;
            this.scheduledTickTreeSet.remove(nextTickListEntry4);
            this.scheduledTickSet.remove(nextTickListEntry4);
            int b5 = 8;
            if (this.checkChunksExist(nextTickListEntry4.xCoord - b5, nextTickListEntry4.yCoord - b5, nextTickListEntry4.zCoord - b5, nextTickListEntry4.xCoord + b5, nextTickListEntry4.yCoord + b5, nextTickListEntry4.zCoord + b5) && (i6 = this.getBlockId(nextTickListEntry4.xCoord, nextTickListEntry4.yCoord, nextTickListEntry4.zCoord)) == nextTickListEntry4.blockID && i6 > 0) {
                Block.blocksList[i6].updateTick(this, nextTickListEntry4.xCoord, nextTickListEntry4.yCoord, nextTickListEntry4.zCoord, this.rand);
            }
            ++i3;
        }
        return this.scheduledTickTreeSet.size() != 0;
    }

    public void randomDisplayUpdates(int i1, int i2, int i3) {
        int b4 = 16;
        Random random5 = new Random();
        int i6 = 0;
        while (i6 < 1000) {
            int i9;
            int i8;
            int i7 = i1 + this.rand.nextInt(b4) - this.rand.nextInt(b4);
            int i10 = this.getBlockId(i7, i8 = i2 + this.rand.nextInt(b4) - this.rand.nextInt(b4), i9 = i3 + this.rand.nextInt(b4) - this.rand.nextInt(b4));
            if (i10 > 0) {
                Block.blocksList[i10].randomDisplayTick(this, i7, i8, i9, random5);
            }
            ++i6;
        }
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity entity1, AxisAlignedBB axisAlignedBB2) {
        this.field_1012_M.clear();
        int i3 = MathHelper.floor_double((axisAlignedBB2.minX - 2.0) / 16.0);
        int i4 = MathHelper.floor_double((axisAlignedBB2.maxX + 2.0) / 16.0);
        int i5 = MathHelper.floor_double((axisAlignedBB2.minZ - 2.0) / 16.0);
        int i6 = MathHelper.floor_double((axisAlignedBB2.maxZ + 2.0) / 16.0);
        int i7 = i3;
        while (i7 <= i4) {
            int i8 = i5;
            while (i8 <= i6) {
                if (this.chunkExists(i7, i8)) {
                    this.getChunkFromChunkCoords(i7, i8).getEntitiesWithinAABBForEntity(entity1, axisAlignedBB2, this.field_1012_M);
                }
                ++i8;
            }
            ++i7;
        }
        return this.field_1012_M;
    }

    public List getEntitiesWithinAABB(Class class1, AxisAlignedBB axisAlignedBB2) {
        int i3 = MathHelper.floor_double((axisAlignedBB2.minX - 2.0) / 16.0);
        int i4 = MathHelper.floor_double((axisAlignedBB2.maxX + 2.0) / 16.0);
        int i5 = MathHelper.floor_double((axisAlignedBB2.minZ - 2.0) / 16.0);
        int i6 = MathHelper.floor_double((axisAlignedBB2.maxZ + 2.0) / 16.0);
        ArrayList arrayList7 = new ArrayList();
        int i8 = i3;
        while (i8 <= i4) {
            int i9 = i5;
            while (i9 <= i6) {
                if (this.chunkExists(i8, i9)) {
                    this.getChunkFromChunkCoords(i8, i9).getEntitiesOfTypeWithinAAAB(class1, axisAlignedBB2, arrayList7);
                }
                ++i9;
            }
            ++i8;
        }
        return arrayList7;
    }

    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }

    public void func_698_b(int i1, int i2, int i3, TileEntity tileEntity4) {
        if (this.blockExists(i1, i2, i3)) {
            this.getChunkFromBlockCoords(i1, i3).setChunkModified();
        }
        int i5 = 0;
        while (i5 < this.worldAccesses.size()) {
            ((IWorldAccess)this.worldAccesses.get(i5)).doNothingWithTileEntity(i1, i2, i3, tileEntity4);
            ++i5;
        }
    }

    public int countEntities(Class class1) {
        int i2 = 0;
        int i3 = 0;
        while (i3 < this.loadedEntityList.size()) {
            Entity entity4 = (Entity)this.loadedEntityList.get(i3);
            if (class1.isAssignableFrom(entity4.getClass())) {
                ++i2;
            }
            ++i3;
        }
        return i2;
    }

    public void func_636_a(List list1) {
        this.loadedEntityList.addAll(list1);
        int i2 = 0;
        while (i2 < list1.size()) {
            this.obtainEntitySkin((Entity)list1.get(i2));
            ++i2;
        }
    }

    public void func_632_b(List list1) {
        this.unloadedEntityList.addAll(list1);
    }

    public void func_656_j() {
        while (this.chunkProvider.func_532_a()) {
        }
    }

    public boolean canBlockBePlacedAt(int i1, int i2, int i3, int i4, boolean z5) {
        int i6 = this.getBlockId(i2, i3, i4);
        Block block7 = Block.blocksList[i6];
        Block block8 = Block.blocksList[i1];
        AxisAlignedBB axisAlignedBB9 = block8.getCollisionBoundingBoxFromPool(this, i2, i3, i4);
        if (z5) {
            axisAlignedBB9 = null;
        }
        return axisAlignedBB9 != null && !this.checkIfAABBIsClear(axisAlignedBB9) ? false : (block7 != Block.waterMoving && block7 != Block.waterStill && block7 != Block.lavaMoving && block7 != Block.lavaStill && block7 != Block.fire && block7 != Block.snow ? i1 > 0 && block7 == null && block8.canPlaceBlockAt(this, i2, i3, i4) : true);
    }

    public PathEntity getPathToEntity(Entity entity1, Entity entity2, float f3) {
        int i4 = MathHelper.floor_double(entity1.posX);
        int i5 = MathHelper.floor_double(entity1.posY);
        int i6 = MathHelper.floor_double(entity1.posZ);
        int i7 = (int)(f3 + 16.0f);
        int i8 = i4 - i7;
        int i9 = i5 - i7;
        int i10 = i6 - i7;
        int i11 = i4 + i7;
        int i12 = i5 + i7;
        int i13 = i6 + i7;
        ChunkCache chunkCache14 = new ChunkCache(this, i8, i9, i10, i11, i12, i13);
        return new Pathfinder(chunkCache14).createEntityPathTo(entity1, entity2, f3);
    }

    public PathEntity getEntityPathToXYZ(Entity entity1, int i2, int i3, int i4, float f5) {
        int i6 = MathHelper.floor_double(entity1.posX);
        int i7 = MathHelper.floor_double(entity1.posY);
        int i8 = MathHelper.floor_double(entity1.posZ);
        int i9 = (int)(f5 + 8.0f);
        int i10 = i6 - i9;
        int i11 = i7 - i9;
        int i12 = i8 - i9;
        int i13 = i6 + i9;
        int i14 = i7 + i9;
        int i15 = i8 + i9;
        ChunkCache chunkCache16 = new ChunkCache(this, i10, i11, i12, i13, i14, i15);
        return new Pathfinder(chunkCache16).createEntityPathTo(entity1, i2, i3, i4, f5);
    }

    public boolean isBlockProvidingPowerTo(int i1, int i2, int i3, int i4) {
        int i5 = this.getBlockId(i1, i2, i3);
        return i5 == 0 ? false : Block.blocksList[i5].isIndirectlyPoweringTo(this, i1, i2, i3, i4);
    }

    public boolean isBlockGettingPowered(int i1, int i2, int i3) {
        return this.isBlockProvidingPowerTo(i1, i2 - 1, i3, 0) ? true : (this.isBlockProvidingPowerTo(i1, i2 + 1, i3, 1) ? true : (this.isBlockProvidingPowerTo(i1, i2, i3 - 1, 2) ? true : (this.isBlockProvidingPowerTo(i1, i2, i3 + 1, 3) ? true : (this.isBlockProvidingPowerTo(i1 - 1, i2, i3, 4) ? true : this.isBlockProvidingPowerTo(i1 + 1, i2, i3, 5)))));
    }

    public boolean isBlockIndirectlyProvidingPowerTo(int i1, int i2, int i3, int i4) {
        if (this.isBlockOpaqueCube(i1, i2, i3)) {
            return this.isBlockGettingPowered(i1, i2, i3);
        }
        int i5 = this.getBlockId(i1, i2, i3);
        return i5 == 0 ? false : Block.blocksList[i5].isPoweringTo(this, i1, i2, i3, i4);
    }

    public boolean isBlockIndirectlyGettingPowered(int i1, int i2, int i3) {
        return this.isBlockIndirectlyProvidingPowerTo(i1, i2 - 1, i3, 0) ? true : (this.isBlockIndirectlyProvidingPowerTo(i1, i2 + 1, i3, 1) ? true : (this.isBlockIndirectlyProvidingPowerTo(i1, i2, i3 - 1, 2) ? true : (this.isBlockIndirectlyProvidingPowerTo(i1, i2, i3 + 1, 3) ? true : (this.isBlockIndirectlyProvidingPowerTo(i1 - 1, i2, i3, 4) ? true : this.isBlockIndirectlyProvidingPowerTo(i1 + 1, i2, i3, 5)))));
    }

    public EntityPlayer getClosestPlayerToEntity(Entity entity1, double d2) {
        return this.getClosestPlayer(entity1.posX, entity1.posY, entity1.posZ, d2);
    }

    public EntityPlayer getClosestPlayer(double d1, double d3, double d5, double d7) {
        double d9 = -1.0;
        EntityPlayer entityPlayer11 = null;
        int i12 = 0;
        while (i12 < this.playerEntities.size()) {
            EntityPlayer entityPlayer13 = (EntityPlayer)this.playerEntities.get(i12);
            double d14 = entityPlayer13.getDistanceSq(d1, d3, d5);
            if ((d7 < 0.0 || d14 < d7 * d7) && (d9 == -1.0 || d14 < d9)) {
                d9 = d14;
                entityPlayer11 = entityPlayer13;
            }
            ++i12;
        }
        return entityPlayer11;
    }

    public EntityPlayer getPlayerEntityByName(String string1) {
        int i2 = 0;
        while (i2 < this.playerEntities.size()) {
            if (string1.equals(((EntityPlayer)this.playerEntities.get((int)i2)).username)) {
                return (EntityPlayer)this.playerEntities.get(i2);
            }
            ++i2;
        }
        return null;
    }

    public void setChunkData(int i1, int i2, int i3, int i4, int i5, int i6, byte[] b7) {
        int i8 = i1 >> 4;
        int i9 = i3 >> 4;
        int i10 = i1 + i4 - 1 >> 4;
        int i11 = i3 + i6 - 1 >> 4;
        int i12 = 0;
        int i13 = i2;
        int i14 = i2 + i5;
        if (i2 < 0) {
            i13 = 0;
        }
        if (i14 > 128) {
            i14 = 128;
        }
        int i15 = i8;
        while (i15 <= i10) {
            int i16 = i1 - i15 * 16;
            int i17 = i1 + i4 - i15 * 16;
            if (i16 < 0) {
                i16 = 0;
            }
            if (i17 > 16) {
                i17 = 16;
            }
            int i18 = i9;
            while (i18 <= i11) {
                int i19 = i3 - i18 * 16;
                int i20 = i3 + i6 - i18 * 16;
                if (i19 < 0) {
                    i19 = 0;
                }
                if (i20 > 16) {
                    i20 = 16;
                }
                i12 = this.getChunkFromChunkCoords(i15, i18).setChunkData(b7, i16, i13, i19, i17, i14, i20, i12);
                this.markBlocksDirty(i15 * 16 + i16, i13, i18 * 16 + i19, i15 * 16 + i17, i14, i18 * 16 + i20);
                ++i18;
            }
            ++i15;
        }
    }

    public void sendQuittingDisconnectingPacket() {
    }

    public void checkSessionLock() {
        this.saveHandler.func_22150_b();
    }

    public void setWorldTime(long j1) {
        this.worldInfo.setWorldTime(j1);
    }

    public long getRandomSeed() {
        return this.worldInfo.getRandomSeed();
    }

    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }

    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
    }

    public void setSpawnPoint(ChunkCoordinates chunkCoordinates1) {
        this.worldInfo.setSpawn(chunkCoordinates1.x, chunkCoordinates1.y, chunkCoordinates1.z);
    }

    public void joinEntityInSurroundings(Entity entity1) {
        int i2 = MathHelper.floor_double(entity1.posX / 16.0);
        int i3 = MathHelper.floor_double(entity1.posZ / 16.0);
        int b4 = 2;
        int i5 = i2 - b4;
        while (i5 <= i2 + b4) {
            int i6 = i3 - b4;
            while (i6 <= i3 + b4) {
                this.getChunkFromChunkCoords(i5, i6);
                ++i6;
            }
            ++i5;
        }
        if (!this.loadedEntityList.contains(entity1)) {
            this.loadedEntityList.add(entity1);
        }
    }

    public boolean func_6466_a(EntityPlayer entityPlayer1, int i2, int i3, int i4) {
        return true;
    }

    public void func_9425_a(Entity entity1, byte b2) {
    }

    public void updateEntityList() {
        int i4;
        int i3;
        Entity entity2;
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int i1 = 0;
        while (i1 < this.unloadedEntityList.size()) {
            entity2 = (Entity)this.unloadedEntityList.get(i1);
            i3 = entity2.chunkCoordX;
            i4 = entity2.chunkCoordZ;
            if (entity2.addedToChunk && this.chunkExists(i3, i4)) {
                this.getChunkFromChunkCoords(i3, i4).func_1015_b(entity2);
            }
            ++i1;
        }
        i1 = 0;
        while (i1 < this.unloadedEntityList.size()) {
            this.releaseEntitySkin((Entity)this.unloadedEntityList.get(i1));
            ++i1;
        }
        this.unloadedEntityList.clear();
        i1 = 0;
        while (i1 < this.loadedEntityList.size()) {
            block10: {
                block9: {
                    entity2 = (Entity)this.loadedEntityList.get(i1);
                    if (entity2.ridingEntity == null) break block9;
                    if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) break block10;
                    entity2.ridingEntity.riddenByEntity = null;
                    entity2.ridingEntity = null;
                }
                if (entity2.isDead) {
                    i3 = entity2.chunkCoordX;
                    i4 = entity2.chunkCoordZ;
                    if (entity2.addedToChunk && this.chunkExists(i3, i4)) {
                        this.getChunkFromChunkCoords(i3, i4).func_1015_b(entity2);
                    }
                    this.loadedEntityList.remove(i1--);
                    this.releaseEntitySkin(entity2);
                }
            }
            ++i1;
        }
    }

    public IChunkProvider getIChunkProvider() {
        return this.chunkProvider;
    }

    public void playNoteAt(int i1, int i2, int i3, int i4, int i5) {
        int i6 = this.getBlockId(i1, i2, i3);
        if (i6 > 0) {
            Block.blocksList[i6].playBlock(this, i1, i2, i3, i4, i5);
        }
    }

    public WorldInfo func_22144_v() {
        return this.worldInfo;
    }

    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = !this.playerEntities.isEmpty();
        for (EntityPlayer entityPlayer2 : this.playerEntities) {
            if (entityPlayer2.isPlayerSleeping()) continue;
            this.allPlayersSleeping = false;
            break;
        }
    }

    protected void wakeUpAllPlayers() {
        this.allPlayersSleeping = false;
        for (EntityPlayer entityPlayer2 : this.playerEntities) {
            if (!entityPlayer2.isPlayerSleeping()) continue;
            entityPlayer2.wakeUpPlayer(false, false, true);
        }
    }

    public boolean isAllPlayersFullyAsleep() {
        if (this.allPlayersSleeping && !this.multiplayerWorld) {
            EntityPlayer entityPlayer2;
            Iterator iterator1 = this.playerEntities.iterator();
            do {
                if (iterator1.hasNext()) continue;
                return true;
            } while ((entityPlayer2 = (EntityPlayer)iterator1.next()).isPlayerFullyAsleep());
            return false;
        }
        return false;
    }
}

