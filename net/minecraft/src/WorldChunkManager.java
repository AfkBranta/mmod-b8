/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.NoiseGeneratorOctaves2;
import net.minecraft.src.World;

public class WorldChunkManager {
    private NoiseGeneratorOctaves2 field_4194_e;
    private NoiseGeneratorOctaves2 field_4193_f;
    private NoiseGeneratorOctaves2 field_4192_g;
    public double[] temperature;
    public double[] humidity;
    public double[] field_4196_c;
    public MobSpawnerBase[] field_4195_d;

    protected WorldChunkManager() {
    }

    public WorldChunkManager(World world1) {
        this.field_4194_e = new NoiseGeneratorOctaves2(new Random(world1.getRandomSeed() * 9871L), 4);
        this.field_4193_f = new NoiseGeneratorOctaves2(new Random(world1.getRandomSeed() * 39811L), 4);
        this.field_4192_g = new NoiseGeneratorOctaves2(new Random(world1.getRandomSeed() * 543321L), 2);
    }

    public MobSpawnerBase func_4074_a(ChunkCoordIntPair chunkCoordIntPair1) {
        return this.func_4073_a(chunkCoordIntPair1.chunkXPos << 4, chunkCoordIntPair1.chunkZPos << 4);
    }

    public MobSpawnerBase func_4073_a(int i1, int i2) {
        return this.func_4069_a(i1, i2, 1, 1)[0];
    }

    public double func_4072_b(int i1, int i2) {
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, i1, i2, 1, 1, 0.025f, 0.025f, 0.5);
        return this.temperature[0];
    }

    public MobSpawnerBase[] func_4069_a(int i1, int i2, int i3, int i4) {
        this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, i1, i2, i3, i4);
        return this.field_4195_d;
    }

    public double[] getTemperatures(double[] d1, int i2, int i3, int i4, int i5) {
        if (d1 == null || d1.length < i4 * i5) {
            d1 = new double[i4 * i5];
        }
        d1 = this.field_4194_e.func_4112_a(d1, i2, i3, i4, i5, 0.025f, 0.025f, 0.25);
        this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, i2, i3, i4, i5, 0.25, 0.25, 0.5882352941176471);
        int i6 = 0;
        int i7 = 0;
        while (i7 < i4) {
            int i8 = 0;
            while (i8 < i5) {
                double d9 = this.field_4196_c[i6] * 1.1 + 0.5;
                double d11 = 0.01;
                double d13 = 1.0 - d11;
                double d15 = (d1[i6] * 0.15 + 0.7) * d13 + d9 * d11;
                if ((d15 = 1.0 - (1.0 - d15) * (1.0 - d15)) < 0.0) {
                    d15 = 0.0;
                }
                if (d15 > 1.0) {
                    d15 = 1.0;
                }
                d1[i6] = d15;
                ++i6;
                ++i8;
            }
            ++i7;
        }
        return d1;
    }

    public MobSpawnerBase[] loadBlockGeneratorData(MobSpawnerBase[] mobSpawnerBase1, int i2, int i3, int i4, int i5) {
        if (mobSpawnerBase1 == null || mobSpawnerBase1.length < i4 * i5) {
            mobSpawnerBase1 = new MobSpawnerBase[i4 * i5];
        }
        this.temperature = this.field_4194_e.func_4112_a(this.temperature, i2, i3, i4, i4, 0.025f, 0.025f, 0.25);
        this.humidity = this.field_4193_f.func_4112_a(this.humidity, i2, i3, i4, i4, 0.05f, 0.05f, 0.3333333333333333);
        this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, i2, i3, i4, i4, 0.25, 0.25, 0.5882352941176471);
        int i6 = 0;
        int i7 = 0;
        while (i7 < i4) {
            int i8 = 0;
            while (i8 < i5) {
                double d9 = this.field_4196_c[i6] * 1.1 + 0.5;
                double d11 = 0.01;
                double d13 = 1.0 - d11;
                double d15 = (this.temperature[i6] * 0.15 + 0.7) * d13 + d9 * d11;
                d11 = 0.002;
                d13 = 1.0 - d11;
                double d17 = (this.humidity[i6] * 0.15 + 0.5) * d13 + d9 * d11;
                if ((d15 = 1.0 - (1.0 - d15) * (1.0 - d15)) < 0.0) {
                    d15 = 0.0;
                }
                if (d17 < 0.0) {
                    d17 = 0.0;
                }
                if (d15 > 1.0) {
                    d15 = 1.0;
                }
                if (d17 > 1.0) {
                    d17 = 1.0;
                }
                this.temperature[i6] = d15;
                this.humidity[i6] = d17;
                mobSpawnerBase1[i6++] = MobSpawnerBase.getBiomeFromLookup(d15, d17);
                ++i8;
            }
            ++i7;
        }
        return mobSpawnerBase1;
    }
}

