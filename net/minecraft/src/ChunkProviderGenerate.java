/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Calendar;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Chunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.MapGenCaves;
import net.minecraft.src.Material;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenCactus;
import net.minecraft.src.WorldGenClay;
import net.minecraft.src.WorldGenDungeons;
import net.minecraft.src.WorldGenFlowers;
import net.minecraft.src.WorldGenLakes;
import net.minecraft.src.WorldGenLiquids;
import net.minecraft.src.WorldGenMinable;
import net.minecraft.src.WorldGenPumpkin;
import net.minecraft.src.WorldGenReed;
import net.minecraft.src.WorldGenerator;

public class ChunkProviderGenerate
implements IChunkProvider {
    private Random rand;
    private NoiseGeneratorOctaves field_912_k;
    private NoiseGeneratorOctaves field_911_l;
    private NoiseGeneratorOctaves field_910_m;
    private NoiseGeneratorOctaves field_909_n;
    private NoiseGeneratorOctaves field_908_o;
    public NoiseGeneratorOctaves field_922_a;
    public NoiseGeneratorOctaves field_921_b;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World worldObj;
    private double[] field_4180_q;
    private double[] sandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] stoneNoise = new double[256];
    private MapGenBase field_902_u = new MapGenCaves();
    private MobSpawnerBase[] biomesForGeneration;
    double[] field_4185_d;
    double[] field_4184_e;
    double[] field_4183_f;
    double[] field_4182_g;
    double[] field_4181_h;
    int[][] field_914_i = new int[32][32];
    private double[] generatedTemperatures;

    public ChunkProviderGenerate(World world1, long j2) {
        this.worldObj = world1;
        this.rand = new Random(j2);
        this.field_912_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_911_l = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_910_m = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_909_n = new NoiseGeneratorOctaves(this.rand, 4);
        this.field_908_o = new NoiseGeneratorOctaves(this.rand, 4);
        this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
        this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
    }

    public void generateTerrain(int i1, int i2, byte[] b3, MobSpawnerBase[] mobSpawnerBase4, double[] d5) {
        int b6 = 4;
        int b7 = 64;
        int i8 = b6 + 1;
        int b9 = 17;
        int i10 = b6 + 1;
        this.field_4180_q = this.func_4061_a(this.field_4180_q, i1 * b6, 0, i2 * b6, i8, b9, i10);
        int i11 = 0;
        while (i11 < b6) {
            int i12 = 0;
            while (i12 < b6) {
                int i13 = 0;
                while (i13 < 16) {
                    double d14 = 0.125;
                    double d16 = this.field_4180_q[((i11 + 0) * i10 + i12 + 0) * b9 + i13 + 0];
                    double d18 = this.field_4180_q[((i11 + 0) * i10 + i12 + 1) * b9 + i13 + 0];
                    double d20 = this.field_4180_q[((i11 + 1) * i10 + i12 + 0) * b9 + i13 + 0];
                    double d22 = this.field_4180_q[((i11 + 1) * i10 + i12 + 1) * b9 + i13 + 0];
                    double d24 = (this.field_4180_q[((i11 + 0) * i10 + i12 + 0) * b9 + i13 + 1] - d16) * d14;
                    double d26 = (this.field_4180_q[((i11 + 0) * i10 + i12 + 1) * b9 + i13 + 1] - d18) * d14;
                    double d28 = (this.field_4180_q[((i11 + 1) * i10 + i12 + 0) * b9 + i13 + 1] - d20) * d14;
                    double d30 = (this.field_4180_q[((i11 + 1) * i10 + i12 + 1) * b9 + i13 + 1] - d22) * d14;
                    int i32 = 0;
                    while (i32 < 8) {
                        double d33 = 0.25;
                        double d35 = d16;
                        double d37 = d18;
                        double d39 = (d20 - d16) * d33;
                        double d41 = (d22 - d18) * d33;
                        int i43 = 0;
                        while (i43 < 4) {
                            int i44 = i43 + i11 * 4 << 11 | 0 + i12 * 4 << 7 | i13 * 8 + i32;
                            int s45 = 128;
                            double d46 = 0.25;
                            double d48 = d35;
                            double d50 = (d37 - d35) * d46;
                            int i52 = 0;
                            while (i52 < 4) {
                                double d53 = d5[(i11 * 4 + i43) * 16 + i12 * 4 + i52];
                                int i55 = 0;
                                if (i13 * 8 + i32 < b7) {
                                    i55 = d53 < 0.5 && i13 * 8 + i32 >= b7 - 1 ? Block.ice.blockID : Block.waterStill.blockID;
                                }
                                if (d48 > 0.0) {
                                    i55 = Block.stone.blockID;
                                }
                                b3[i44] = (byte)i55;
                                i44 += s45;
                                d48 += d50;
                                ++i52;
                            }
                            d35 += d39;
                            d37 += d41;
                            ++i43;
                        }
                        d16 += d24;
                        d18 += d26;
                        d20 += d28;
                        d22 += d30;
                        ++i32;
                    }
                    ++i13;
                }
                ++i12;
            }
            ++i11;
        }
    }

    public void replaceBlocksForBiome(int i1, int i2, byte[] b3, MobSpawnerBase[] mobSpawnerBase4) {
        int b5 = 64;
        double d6 = 0.03125;
        this.sandNoise = this.field_909_n.generateNoiseOctaves(this.sandNoise, i1 * 16, i2 * 16, 0.0, 16, 16, 1, d6, d6, 1.0);
        this.gravelNoise = this.field_909_n.generateNoiseOctaves(this.gravelNoise, i1 * 16, 109.0134, i2 * 16, 16, 1, 16, d6, 1.0, d6);
        this.stoneNoise = this.field_908_o.generateNoiseOctaves(this.stoneNoise, i1 * 16, i2 * 16, 0.0, 16, 16, 1, d6 * 2.0, d6 * 2.0, d6 * 2.0);
        int i8 = 0;
        while (i8 < 16) {
            int i9 = 0;
            while (i9 < 16) {
                MobSpawnerBase mobSpawnerBase10 = mobSpawnerBase4[i8 + i9 * 16];
                boolean z11 = this.sandNoise[i8 + i9 * 16] + this.rand.nextDouble() * 0.2 > 0.0;
                boolean z12 = this.gravelNoise[i8 + i9 * 16] + this.rand.nextDouble() * 0.2 > 3.0;
                int i13 = (int)(this.stoneNoise[i8 + i9 * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int i14 = -1;
                byte b15 = mobSpawnerBase10.topBlock;
                byte b16 = mobSpawnerBase10.fillerBlock;
                int i17 = 127;
                while (i17 >= 0) {
                    int i18 = (i9 * 16 + i8) * 128 + i17;
                    if (i17 <= 0 + this.rand.nextInt(5)) {
                        b3[i18] = (byte)Block.bedrock.blockID;
                    } else {
                        byte b19 = b3[i18];
                        if (b19 == 0) {
                            i14 = -1;
                        } else if (b19 == Block.stone.blockID) {
                            if (i14 == -1) {
                                if (i13 <= 0) {
                                    b15 = 0;
                                    b16 = (byte)Block.stone.blockID;
                                } else if (i17 >= b5 - 4 && i17 <= b5 + 1) {
                                    b15 = mobSpawnerBase10.topBlock;
                                    b16 = mobSpawnerBase10.fillerBlock;
                                    if (z12) {
                                        b15 = 0;
                                    }
                                    if (z12) {
                                        b16 = (byte)Block.gravel.blockID;
                                    }
                                    if (z11) {
                                        b15 = (byte)Block.sand.blockID;
                                    }
                                    if (z11) {
                                        b16 = (byte)Block.sand.blockID;
                                    }
                                }
                                if (i17 < b5 && b15 == 0) {
                                    b15 = (byte)Block.waterStill.blockID;
                                }
                                i14 = i13;
                                b3[i18] = i17 >= b5 - 1 ? b15 : b16;
                            } else if (i14 > 0) {
                                b3[i18] = b16;
                                if (--i14 == 0 && b16 == Block.sand.blockID) {
                                    i14 = this.rand.nextInt(4);
                                    b16 = (byte)Block.sandStone.blockID;
                                }
                            }
                        }
                    }
                    --i17;
                }
                ++i9;
            }
            ++i8;
        }
    }

    @Override
    public Chunk func_538_d(int i1, int i2) {
        return this.provideChunk(i1, i2);
    }

    @Override
    public Chunk provideChunk(int i1, int i2) {
        this.rand.setSeed((long)i1 * 341873128712L + (long)i2 * 132897987541L);
        byte[] b3 = new byte[32768];
        Chunk chunk4 = new Chunk(this.worldObj, b3, i1, i2);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, i1 * 16, i2 * 16, 16, 16);
        double[] d5 = this.worldObj.getWorldChunkManager().temperature;
        this.generateTerrain(i1, i2, b3, this.biomesForGeneration, d5);
        this.replaceBlocksForBiome(i1, i2, b3, this.biomesForGeneration);
        this.field_902_u.func_867_a(this, this.worldObj, i1, i2, b3);
        chunk4.func_1024_c();
        return chunk4;
    }

    private double[] func_4061_a(double[] d1, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (d1 == null) {
            d1 = new double[i5 * i6 * i7];
        }
        double d8 = 684.412;
        double d10 = 684.412;
        double[] d12 = this.worldObj.getWorldChunkManager().temperature;
        double[] d13 = this.worldObj.getWorldChunkManager().humidity;
        this.field_4182_g = this.field_922_a.func_4109_a(this.field_4182_g, i2, i4, i5, i7, 1.121, 1.121, 0.5);
        this.field_4181_h = this.field_921_b.func_4109_a(this.field_4181_h, i2, i4, i5, i7, 200.0, 200.0, 0.5);
        this.field_4185_d = this.field_910_m.generateNoiseOctaves(this.field_4185_d, i2, i3, i4, i5, i6, i7, d8 / 80.0, d10 / 160.0, d8 / 80.0);
        this.field_4184_e = this.field_912_k.generateNoiseOctaves(this.field_4184_e, i2, i3, i4, i5, i6, i7, d8, d10, d8);
        this.field_4183_f = this.field_911_l.generateNoiseOctaves(this.field_4183_f, i2, i3, i4, i5, i6, i7, d8, d10, d8);
        int i14 = 0;
        int i15 = 0;
        int i16 = 16 / i5;
        int i17 = 0;
        while (i17 < i5) {
            int i18 = i17 * i16 + i16 / 2;
            int i19 = 0;
            while (i19 < i7) {
                double d29;
                int i20 = i19 * i16 + i16 / 2;
                double d21 = d12[i18 * 16 + i20];
                double d23 = d13[i18 * 16 + i20] * d21;
                double d25 = 1.0 - d23;
                d25 *= d25;
                d25 *= d25;
                d25 = 1.0 - d25;
                double d27 = (this.field_4182_g[i15] + 256.0) / 512.0;
                if ((d27 *= d25) > 1.0) {
                    d27 = 1.0;
                }
                if ((d29 = this.field_4181_h[i15] / 8000.0) < 0.0) {
                    d29 = -d29 * 0.3;
                }
                if ((d29 = d29 * 3.0 - 2.0) < 0.0) {
                    if ((d29 /= 2.0) < -1.0) {
                        d29 = -1.0;
                    }
                    d29 /= 1.4;
                    d29 /= 2.0;
                    d27 = 0.0;
                } else {
                    if (d29 > 1.0) {
                        d29 = 1.0;
                    }
                    d29 /= 8.0;
                }
                if (d27 < 0.0) {
                    d27 = 0.0;
                }
                d27 += 0.5;
                d29 = d29 * (double)i6 / 16.0;
                double d31 = (double)i6 / 2.0 + d29 * 4.0;
                ++i15;
                int i33 = 0;
                while (i33 < i6) {
                    double d34 = 0.0;
                    double d36 = ((double)i33 - d31) * 12.0 / d27;
                    if (d36 < 0.0) {
                        d36 *= 4.0;
                    }
                    double d38 = this.field_4184_e[i14] / 512.0;
                    double d40 = this.field_4183_f[i14] / 512.0;
                    double d42 = (this.field_4185_d[i14] / 10.0 + 1.0) / 2.0;
                    d34 = d42 < 0.0 ? d38 : (d42 > 1.0 ? d40 : d38 + (d40 - d38) * d42);
                    d34 -= d36;
                    if (i33 > i6 - 4) {
                        double d44 = (float)(i33 - (i6 - 4)) / 3.0f;
                        d34 = d34 * (1.0 - d44) + -10.0 * d44;
                    }
                    d1[i14] = d34;
                    ++i14;
                    ++i33;
                }
                ++i19;
            }
            ++i17;
        }
        return d1;
    }

    @Override
    public boolean chunkExists(int i1, int i2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider iChunkProvider1, int i2, int i3) {
        int i23;
        int i19;
        int i17;
        int i16;
        int i15;
        int i14;
        int i13;
        BlockSand.fallInstantly = true;
        int i4 = i2 * 16;
        int i5 = i3 * 16;
        MobSpawnerBase mobSpawnerBase6 = this.worldObj.getWorldChunkManager().func_4073_a(i4 + 16, i5 + 16);
        this.rand.setSeed(this.worldObj.getRandomSeed());
        long j7 = this.rand.nextLong() / 2L * 2L + 1L;
        long j9 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)i2 * j7 + (long)i3 * j9 ^ this.worldObj.getRandomSeed());
        double d11 = 0.25;
        if (this.rand.nextInt(4) == 0) {
            i13 = i4 + this.rand.nextInt(16) + 8;
            i14 = this.rand.nextInt(128);
            i15 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenLakes(Block.waterStill.blockID).generate(this.worldObj, this.rand, i13, i14, i15);
        }
        if (this.rand.nextInt(8) == 0) {
            i13 = i4 + this.rand.nextInt(16) + 8;
            i14 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            i15 = i5 + this.rand.nextInt(16) + 8;
            if (i14 < 64 || this.rand.nextInt(10) == 0) {
                new WorldGenLakes(Block.lavaStill.blockID).generate(this.worldObj, this.rand, i13, i14, i15);
            }
        }
        i13 = 0;
        while (i13 < 8) {
            i14 = i4 + this.rand.nextInt(16) + 8;
            i15 = this.rand.nextInt(128);
            i16 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenDungeons().generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 10) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(128);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenClay(32).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 20) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(128);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.dirt.blockID, 32).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 10) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(128);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.gravel.blockID, 32).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 20) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(128);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.oreCoal.blockID, 16).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 20) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(64);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.oreIron.blockID, 8).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 2) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(32);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.oreGold.blockID, 8).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 8) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(16);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.oreRedstone.blockID, 7).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 1) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(16);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.oreDiamond.blockID, 7).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        i13 = 0;
        while (i13 < 1) {
            i14 = i4 + this.rand.nextInt(16);
            i15 = this.rand.nextInt(16) + this.rand.nextInt(16);
            i16 = i5 + this.rand.nextInt(16);
            new WorldGenMinable(Block.oreLapis.blockID, 6).generate(this.worldObj, this.rand, i14, i15, i16);
            ++i13;
        }
        d11 = 0.5;
        i13 = (int)((this.mobSpawnerNoise.func_806_a((double)i4 * d11, (double)i5 * d11) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
        i14 = 0;
        if (this.rand.nextInt(10) == 0) {
            ++i14;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.forest) {
            i14 += i13 + 5;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.rainforest) {
            i14 += i13 + 5;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.seasonalForest) {
            i14 += i13 + 2;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.taiga) {
            i14 += i13 + 5;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.desert) {
            i14 -= 20;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.tundra) {
            i14 -= 20;
        }
        if (mobSpawnerBase6 == MobSpawnerBase.plains) {
            i14 -= 20;
        }
        i15 = 0;
        while (i15 < i14) {
            i16 = i4 + this.rand.nextInt(16) + 8;
            i17 = i5 + this.rand.nextInt(16) + 8;
            WorldGenerator worldGenerator18 = mobSpawnerBase6.getRandomWorldGenForTrees(this.rand);
            worldGenerator18.func_517_a(1.0, 1.0, 1.0);
            worldGenerator18.generate(this.worldObj, this.rand, i16, this.worldObj.getHeightValue(i16, i17), i17);
            ++i15;
        }
        i15 = 0;
        while (i15 < 2) {
            i16 = i4 + this.rand.nextInt(16) + 8;
            i17 = this.rand.nextInt(128);
            int i232 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenFlowers(Block.plantYellow.blockID).generate(this.worldObj, this.rand, i16, i17, i232);
            ++i15;
        }
        if (this.rand.nextInt(2) == 0) {
            i15 = i4 + this.rand.nextInt(16) + 8;
            i16 = this.rand.nextInt(128);
            i17 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenFlowers(Block.plantRed.blockID).generate(this.worldObj, this.rand, i15, i16, i17);
        }
        if (this.rand.nextInt(4) == 0) {
            i15 = i4 + this.rand.nextInt(16) + 8;
            i16 = this.rand.nextInt(128);
            i17 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenFlowers(Block.mushroomBrown.blockID).generate(this.worldObj, this.rand, i15, i16, i17);
        }
        if (this.rand.nextInt(8) == 0) {
            i15 = i4 + this.rand.nextInt(16) + 8;
            i16 = this.rand.nextInt(128);
            i17 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenFlowers(Block.mushroomRed.blockID).generate(this.worldObj, this.rand, i15, i16, i17);
        }
        i15 = 0;
        while (i15 < 10) {
            i16 = i4 + this.rand.nextInt(16) + 8;
            i17 = this.rand.nextInt(128);
            int i233 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenReed().generate(this.worldObj, this.rand, i16, i17, i233);
            ++i15;
        }
        if (this.rand.nextInt(32) == 0) {
            i15 = i4 + this.rand.nextInt(16) + 8;
            i16 = this.rand.nextInt(128);
            i17 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenPumpkin().generate(this.worldObj, this.rand, i15, i16, i17);
        }
        i15 = 0;
        if (mobSpawnerBase6 == MobSpawnerBase.desert) {
            i15 += 10;
        }
        i16 = 0;
        while (i16 < i15) {
            i17 = i4 + this.rand.nextInt(16) + 8;
            int i234 = this.rand.nextInt(128);
            i19 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenCactus().generate(this.worldObj, this.rand, i17, i234, i19);
            ++i16;
        }
        i16 = 0;
        while (i16 < 50) {
            i17 = i4 + this.rand.nextInt(16) + 8;
            int i235 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            i19 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenLiquids(Block.waterMoving.blockID).generate(this.worldObj, this.rand, i17, i235, i19);
            ++i16;
        }
        i16 = 0;
        while (i16 < 20) {
            i17 = i4 + this.rand.nextInt(16) + 8;
            int i236 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            i19 = i5 + this.rand.nextInt(16) + 8;
            new WorldGenLiquids(Block.lavaMoving.blockID).generate(this.worldObj, this.rand, i17, i236, i19);
            ++i16;
        }
        this.generatedTemperatures = this.worldObj.getWorldChunkManager().getTemperatures(this.generatedTemperatures, i4 + 8, i5 + 8, 16, 16);
        i16 = i4 + 8;
        while (i16 < i4 + 8 + 16) {
            i17 = i5 + 8;
            while (i17 < i5 + 8 + 16) {
                int i237 = i16 - (i4 + 8);
                i19 = i17 - (i5 + 8);
                int i20 = this.worldObj.findTopSolidBlock(i16, i17);
                double d21 = this.generatedTemperatures[i237 * 16 + i19] - (double)(i20 - 64) / 64.0 * 0.3;
                if (d21 < 0.5 && i20 > 0 && i20 < 128 && this.worldObj.isAirBlock(i16, i20, i17) && this.worldObj.getBlockMaterial(i16, i20 - 1, i17).getIsSolid() && this.worldObj.getBlockMaterial(i16, i20 - 1, i17) != Material.ice) {
                    this.worldObj.setBlockWithNotify(i16, i20, i17, Block.snow.blockID);
                }
                ++i17;
            }
            ++i16;
        }
        Calendar calendar24 = Calendar.getInstance();
        calendar24.setTimeInMillis(System.currentTimeMillis());
        if (calendar24.get(2) == 3 && calendar24.get(5) == 1 && this.worldObj.getBlockId(i17 = i4 + this.rand.nextInt(16) + 8, i23 = this.rand.nextInt(128), i19 = i5 + this.rand.nextInt(16) + 8) == 0 && this.worldObj.isBlockOpaqueCube(i17, i23 - 1, i19)) {
            System.out.println("added a chest!!");
            this.worldObj.setBlockWithNotify(i17, i23, i19, Block.lockedChest.blockID);
        }
        BlockSand.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean z1, IProgressUpdate iProgressUpdate2) {
        return true;
    }

    @Override
    public boolean func_532_a() {
        return false;
    }

    @Override
    public boolean func_536_b() {
        return true;
    }

    @Override
    public String toString() {
        return "RandomLevelSource";
    }
}

