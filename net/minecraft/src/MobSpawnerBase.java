/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.MobSpawnerDesert;
import net.minecraft.src.MobSpawnerForest;
import net.minecraft.src.MobSpawnerHell;
import net.minecraft.src.MobSpawnerRainforest;
import net.minecraft.src.MobSpawnerSwamp;
import net.minecraft.src.MobSpawnerTaiga;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerBase {
    public static final MobSpawnerBase rainforest = new MobSpawnerRainforest().setColor(588342).setBiomeName("Rainforest").func_4124_a(2094168);
    public static final MobSpawnerBase swampland = new MobSpawnerSwamp().setColor(522674).setBiomeName("Swampland").func_4124_a(9154376);
    public static final MobSpawnerBase seasonalForest = new MobSpawnerBase().setColor(10215459).setBiomeName("Seasonal Forest");
    public static final MobSpawnerBase forest = new MobSpawnerForest().setColor(353825).setBiomeName("Forest").func_4124_a(5159473);
    public static final MobSpawnerBase savanna = new MobSpawnerDesert().setColor(14278691).setBiomeName("Savanna");
    public static final MobSpawnerBase shrubland = new MobSpawnerBase().setColor(10595616).setBiomeName("Shrubland");
    public static final MobSpawnerBase taiga = new MobSpawnerTaiga().setColor(3060051).setBiomeName("Taiga").doesNothingForMobSpawnerBase().func_4124_a(8107825);
    public static final MobSpawnerBase desert = new MobSpawnerDesert().setColor(16421912).setBiomeName("Desert");
    public static final MobSpawnerBase plains = new MobSpawnerDesert().setColor(16767248).setBiomeName("Plains");
    public static final MobSpawnerBase iceDesert = new MobSpawnerDesert().setColor(16772499).setBiomeName("Ice Desert").doesNothingForMobSpawnerBase().func_4124_a(12899129);
    public static final MobSpawnerBase tundra = new MobSpawnerBase().setColor(5762041).setBiomeName("Tundra").doesNothingForMobSpawnerBase().func_4124_a(12899129);
    public static final MobSpawnerBase hell = new MobSpawnerHell().setColor(0xFF0000).setBiomeName("Hell");
    public String biomeName;
    public int color;
    public byte topBlock;
    public byte fillerBlock;
    public int field_6502_q;
    protected List spawnableMonsterList;
    protected List spawnableCreatureList;
    protected List spawnableWaterCreatureList;
    private static MobSpawnerBase[] biomeLookupTable = new MobSpawnerBase[4096];

    static {
        MobSpawnerBase.generateBiomeLookup();
    }

    protected MobSpawnerBase() {
        this.topBlock = (byte)Block.grass.blockID;
        this.fillerBlock = (byte)Block.dirt.blockID;
        this.field_6502_q = 5169201;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 12));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 8));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10));
    }

    public static void generateBiomeLookup() {
        int i0 = 0;
        while (i0 < 64) {
            int i1 = 0;
            while (i1 < 64) {
                MobSpawnerBase.biomeLookupTable[i0 + i1 * 64] = MobSpawnerBase.getBiome((float)i0 / 63.0f, (float)i1 / 63.0f);
                ++i1;
            }
            ++i0;
        }
        MobSpawnerBase.desert.topBlock = MobSpawnerBase.desert.fillerBlock = (byte)Block.sand.blockID;
        MobSpawnerBase.iceDesert.topBlock = MobSpawnerBase.iceDesert.fillerBlock = (byte)Block.sand.blockID;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random1) {
        return random1.nextInt(10) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }

    protected MobSpawnerBase doesNothingForMobSpawnerBase() {
        return this;
    }

    protected MobSpawnerBase setBiomeName(String string1) {
        this.biomeName = string1;
        return this;
    }

    protected MobSpawnerBase func_4124_a(int i1) {
        this.field_6502_q = i1;
        return this;
    }

    protected MobSpawnerBase setColor(int i1) {
        this.color = i1;
        return this;
    }

    public static MobSpawnerBase getBiomeFromLookup(double d0, double d2) {
        int i4 = (int)(d0 * 63.0);
        int i5 = (int)(d2 * 63.0);
        return biomeLookupTable[i4 + i5 * 64];
    }

    public static MobSpawnerBase getBiome(float f0, float f1) {
        return f0 < 0.1f ? tundra : (f1 < 0.2f ? (f0 < 0.5f ? tundra : (f0 < 0.95f ? savanna : desert)) : (f1 > 0.5f && f0 < 0.7f ? swampland : (f0 < 0.5f ? taiga : (f0 < 0.97f ? (f1 < 0.35f ? shrubland : forest) : (f1 < 0.45f ? plains : ((f1 *= f0) < 0.9f ? seasonalForest : rainforest))))));
    }

    public int getSkyColorByTemp(float f1) {
        if ((f1 /= 3.0f) < -1.0f) {
            f1 = -1.0f;
        }
        if (f1 > 1.0f) {
            f1 = 1.0f;
        }
        return Color.getHSBColor(0.62222224f - f1 * 0.05f, 0.5f + f1 * 0.1f, 1.0f).getRGB();
    }

    public List getSpawnableList(EnumCreatureType enumCreatureType1) {
        return enumCreatureType1 == EnumCreatureType.monster ? this.spawnableMonsterList : (enumCreatureType1 == EnumCreatureType.creature ? this.spawnableCreatureList : (enumCreatureType1 == EnumCreatureType.waterCreature ? this.spawnableWaterCreatureList : null));
    }
}

