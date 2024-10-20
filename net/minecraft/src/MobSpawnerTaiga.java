/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenTaiga1;
import net.minecraft.src.WorldGenTaiga2;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerTaiga
extends MobSpawnerBase {
    public MobSpawnerTaiga() {
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 2));
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random random1) {
        return random1.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2();
    }
}

