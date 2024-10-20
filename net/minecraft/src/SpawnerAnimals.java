/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.src.BlockBed;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.Pathfinder;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.World;

public final class SpawnerAnimals {
    private static Set eligibleChunksForSpawning = new HashSet();
    protected static final Class[] nightSpawnEntities = new Class[]{EntitySpider.class, EntityZombie.class, EntitySkeleton.class};

    protected static ChunkPosition getRandomSpawningPointInChunk(World world0, int i1, int i2) {
        int i3 = i1 + world0.rand.nextInt(16);
        int i4 = world0.rand.nextInt(128);
        int i5 = i2 + world0.rand.nextInt(16);
        return new ChunkPosition(i3, i4, i5);
    }

    public static final int performSpawning(World world0, boolean z1, boolean z2) {
        int i6;
        if (!z1 && !z2) {
            return 0;
        }
        eligibleChunksForSpawning.clear();
        int i3 = 0;
        while (i3 < world0.playerEntities.size()) {
            EntityPlayer entityPlayer4 = (EntityPlayer)world0.playerEntities.get(i3);
            int i5 = MathHelper.floor_double(entityPlayer4.posX / 16.0);
            i6 = MathHelper.floor_double(entityPlayer4.posZ / 16.0);
            int b7 = 8;
            int i8 = -b7;
            while (i8 <= b7) {
                int i9 = -b7;
                while (i9 <= b7) {
                    eligibleChunksForSpawning.add(new ChunkCoordIntPair(i8 + i5, i9 + i6));
                    ++i9;
                }
                ++i8;
            }
            ++i3;
        }
        i3 = 0;
        ChunkCoordinates chunkCoordinates35 = world0.getSpawnPoint();
        EnumCreatureType[] enumCreatureType36 = EnumCreatureType.values();
        i6 = enumCreatureType36.length;
        int i37 = 0;
        while (i37 < i6) {
            EnumCreatureType enumCreatureType38 = enumCreatureType36[i37];
            if ((!enumCreatureType38.getPeacefulCreature() || z2) && (enumCreatureType38.getPeacefulCreature() || z1) && world0.countEntities(enumCreatureType38.getCreatureClass()) <= enumCreatureType38.getMaxNumberOfCreature() * eligibleChunksForSpawning.size() / 256) {
                block6: for (ChunkCoordIntPair chunkCoordIntPair10 : eligibleChunksForSpawning) {
                    SpawnListEntry spawnListEntry152;
                    MobSpawnerBase mobSpawnerBase11 = world0.getWorldChunkManager().func_4074_a(chunkCoordIntPair10);
                    List list12 = mobSpawnerBase11.getSpawnableList(enumCreatureType38);
                    if (list12 == null || list12.isEmpty()) continue;
                    int i13 = 0;
                    for (SpawnListEntry spawnListEntry152 : list12) {
                        i13 += spawnListEntry152.field_25211_b;
                    }
                    int i40 = world0.rand.nextInt(i13);
                    spawnListEntry152 = (SpawnListEntry)list12.get(0);
                    for (SpawnListEntry spawnListEntry17 : list12) {
                        if ((i40 -= spawnListEntry17.field_25211_b) >= 0) continue;
                        spawnListEntry152 = spawnListEntry17;
                        break;
                    }
                    ChunkPosition chunkPosition41 = SpawnerAnimals.getRandomSpawningPointInChunk(world0, chunkCoordIntPair10.chunkXPos * 16, chunkCoordIntPair10.chunkZPos * 16);
                    int i42 = chunkPosition41.x;
                    int i18 = chunkPosition41.y;
                    int i19 = chunkPosition41.z;
                    if (world0.isBlockOpaqueCube(i42, i18, i19) || world0.getBlockMaterial(i42, i18, i19) != enumCreatureType38.getCreatureMaterial()) continue;
                    int i20 = 0;
                    int i21 = 0;
                    while (i21 < 3) {
                        int i22 = i42;
                        int i23 = i18;
                        int i24 = i19;
                        int b25 = 6;
                        int i26 = 0;
                        while (i26 < 4) {
                            float f32;
                            float f31;
                            float f30;
                            float f33;
                            float f29;
                            float f28;
                            float f27;
                            if (SpawnerAnimals.canCreatureTypeSpawnAtLocation(enumCreatureType38, world0, i22 += world0.rand.nextInt(b25) - world0.rand.nextInt(b25), i23 += world0.rand.nextInt(1) - world0.rand.nextInt(1), i24 += world0.rand.nextInt(b25) - world0.rand.nextInt(b25)) && world0.getClosestPlayer(f27 = (float)i22 + 0.5f, f28 = (float)i23, f29 = (float)i24 + 0.5f, 24.0) == null && (f33 = (f30 = f27 - (float)chunkCoordinates35.x) * f30 + (f31 = f28 - (float)chunkCoordinates35.y) * f31 + (f32 = f29 - (float)chunkCoordinates35.z) * f32) >= 576.0f) {
                                EntityLiving entityLiving43;
                                try {
                                    entityLiving43 = (EntityLiving)spawnListEntry152.field_25212_a.getConstructor(World.class).newInstance(world0);
                                }
                                catch (Exception exception34) {
                                    exception34.printStackTrace();
                                    return i3;
                                }
                                entityLiving43.setLocationAndAngles(f27, f28, f29, world0.rand.nextFloat() * 360.0f, 0.0f);
                                if (entityLiving43.getCanSpawnHere()) {
                                    world0.entityJoinedWorld(entityLiving43);
                                    SpawnerAnimals.creatureSpecificInit(entityLiving43, world0, f27, f28, f29);
                                    if (++i20 >= entityLiving43.getMaxSpawnedInChunk()) continue block6;
                                }
                                i3 += i20;
                            }
                            ++i26;
                        }
                        ++i21;
                    }
                }
            }
            ++i37;
        }
        return i3;
    }

    private static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType enumCreatureType0, World world1, int i2, int i3, int i4) {
        return enumCreatureType0.getCreatureMaterial() == Material.water ? world1.getBlockMaterial(i2, i3, i4).getIsLiquid() && !world1.isBlockOpaqueCube(i2, i3 + 1, i4) : world1.isBlockOpaqueCube(i2, i3 - 1, i4) && !world1.isBlockOpaqueCube(i2, i3, i4) && !world1.getBlockMaterial(i2, i3, i4).getIsLiquid() && !world1.isBlockOpaqueCube(i2, i3 + 1, i4);
    }

    private static void creatureSpecificInit(EntityLiving entityLiving0, World world1, float f2, float f3, float f4) {
        if (entityLiving0 instanceof EntitySpider && world1.rand.nextInt(100) == 0) {
            EntitySkeleton entitySkeleton5 = new EntitySkeleton(world1);
            entitySkeleton5.setLocationAndAngles(f2, f3, f4, entityLiving0.rotationYaw, 0.0f);
            world1.entityJoinedWorld(entitySkeleton5);
            entitySkeleton5.mountEntity(entityLiving0);
        } else if (entityLiving0 instanceof EntitySheep) {
            ((EntitySheep)entityLiving0).setFleeceColor(EntitySheep.getRandomFleeceColor(world1.rand));
        }
    }

    /*
     * Unable to fully structure code
     */
    public static boolean performSleepSpawning(World world0, List list1) {
        z2 = false;
        pathfinder3 = new Pathfinder(world0);
        iterator4 = list1.iterator();
        block2: while (true) {
            if (!iterator4.hasNext()) {
                return z2;
            }
            entityPlayer5 = (EntityPlayer)iterator4.next();
            class6 = SpawnerAnimals.nightSpawnEntities;
            if (class6 == null || class6.length == 0) continue;
            z7 = false;
            i8 = 0;
            while (true) {
                if (i8 < 20 && !z7) ** break;
                continue block2;
                i9 = MathHelper.floor_double(entityPlayer5.posX) + world0.rand.nextInt(32) - world0.rand.nextInt(32);
                i10 = MathHelper.floor_double(entityPlayer5.posZ) + world0.rand.nextInt(32) - world0.rand.nextInt(32);
                i11 = MathHelper.floor_double(entityPlayer5.posY) + world0.rand.nextInt(16) - world0.rand.nextInt(16);
                if (i11 < 1) {
                    i11 = 1;
                } else if (i11 > 128) {
                    i11 = 128;
                }
                i12 = world0.rand.nextInt(class6.length);
                i13 = i11;
                while (i13 > 2 && !world0.isBlockOpaqueCube(i9, i13 - 1, i10)) {
                    --i13;
                }
                while (!SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, world0, i9, i13, i10) && i13 < i11 + 16 && i13 < 128) {
                    ++i13;
                }
                if (i13 < i11 + 16 && i13 < 128) {
                    f14 = (float)i9 + 0.5f;
                    f15 = i13;
                    f16 = (float)i10 + 0.5f;
                    try {
                        entityLiving17 = (EntityLiving)class6[i12].getConstructor(new Class[]{World.class}).newInstance(new Object[]{world0});
                    }
                    catch (Exception exception21) {
                        exception21.printStackTrace();
                        return z2;
                    }
                    entityLiving17.setLocationAndAngles(f14, f15, f16, world0.rand.nextFloat() * 360.0f, 0.0f);
                    if (entityLiving17.getCanSpawnHere() && (pathEntity18 = pathfinder3.createEntityPathTo(entityLiving17, entityPlayer5, 32.0f)) != null && pathEntity18.pathLength > 1) {
                        pathPoint19 = pathEntity18.func_22328_c();
                        if (Math.abs((double)pathPoint19.xCoord - entityPlayer5.posX) < 1.5 && Math.abs((double)pathPoint19.zCoord - entityPlayer5.posZ) < 1.5 && Math.abs((double)pathPoint19.yCoord - entityPlayer5.posY) < 1.5) {
                            chunkCoordinates20 = BlockBed.getNearestEmptyChunkCoordinates(world0, MathHelper.floor_double(entityPlayer5.posX), MathHelper.floor_double(entityPlayer5.posY), MathHelper.floor_double(entityPlayer5.posZ), 1);
                            if (chunkCoordinates20 == null) {
                                chunkCoordinates20 = new ChunkCoordinates(i9, i13 + 1, i10);
                            }
                            entityLiving17.setLocationAndAngles((float)chunkCoordinates20.x + 0.5f, chunkCoordinates20.y, (float)chunkCoordinates20.z + 0.5f, 0.0f, 0.0f);
                            world0.entityJoinedWorld(entityLiving17);
                            SpawnerAnimals.creatureSpecificInit(entityLiving17, world0, (float)chunkCoordinates20.x + 0.5f, chunkCoordinates20.y, (float)chunkCoordinates20.z + 0.5f);
                            entityPlayer5.wakeUpPlayer(true, false, false);
                            entityLiving17.playLivingSound();
                            z2 = true;
                            z7 = true;
                        }
                    }
                }
                ++i8;
            }
            break;
        }
    }
}

