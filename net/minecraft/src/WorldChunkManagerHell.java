/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Arrays;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.WorldChunkManager;

public class WorldChunkManagerHell
extends WorldChunkManager {
    private MobSpawnerBase field_4201_e;
    private double field_4200_f;
    private double field_4199_g;

    public WorldChunkManagerHell(MobSpawnerBase mobSpawnerBase1, double d2, double d4) {
        this.field_4201_e = mobSpawnerBase1;
        this.field_4200_f = d2;
        this.field_4199_g = d4;
    }

    @Override
    public MobSpawnerBase func_4074_a(ChunkCoordIntPair chunkCoordIntPair1) {
        return this.field_4201_e;
    }

    @Override
    public MobSpawnerBase func_4073_a(int i1, int i2) {
        return this.field_4201_e;
    }

    @Override
    public double func_4072_b(int i1, int i2) {
        return this.field_4200_f;
    }

    @Override
    public MobSpawnerBase[] func_4069_a(int i1, int i2, int i3, int i4) {
        this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, i1, i2, i3, i4);
        return this.field_4195_d;
    }

    @Override
    public double[] getTemperatures(double[] d1, int i2, int i3, int i4, int i5) {
        if (d1 == null || d1.length < i4 * i5) {
            d1 = new double[i4 * i5];
        }
        Arrays.fill(d1, 0, i4 * i5, this.field_4200_f);
        return d1;
    }

    @Override
    public MobSpawnerBase[] loadBlockGeneratorData(MobSpawnerBase[] mobSpawnerBase1, int i2, int i3, int i4, int i5) {
        if (mobSpawnerBase1 == null || mobSpawnerBase1.length < i4 * i5) {
            mobSpawnerBase1 = new MobSpawnerBase[i4 * i5];
            this.temperature = new double[i4 * i5];
            this.humidity = new double[i4 * i5];
        }
        Arrays.fill(mobSpawnerBase1, 0, i4 * i5, this.field_4201_e);
        Arrays.fill(this.humidity, 0, i4 * i5, this.field_4199_g);
        Arrays.fill(this.temperature, 0, i4 * i5, this.field_4200_f);
        return mobSpawnerBase1;
    }
}

