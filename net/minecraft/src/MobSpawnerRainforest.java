/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class MobSpawnerRainforest
extends MobSpawnerBase {
    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random random1) {
        return random1.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees();
    }
}

