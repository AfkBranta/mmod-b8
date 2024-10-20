/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Chunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.MapGenCavesHell;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenFire;
import net.minecraft.src.WorldGenFlowers;
import net.minecraft.src.WorldGenHellLava;
import net.minecraft.src.WorldGenLightStone1;
import net.minecraft.src.WorldGenLightStone2;

public class ChunkProviderHell
implements IChunkProvider {
    private Random hellRNG;
    private NoiseGeneratorOctaves field_4169_i;
    private NoiseGeneratorOctaves field_4168_j;
    private NoiseGeneratorOctaves field_4167_k;
    private NoiseGeneratorOctaves field_4166_l;
    private NoiseGeneratorOctaves field_4165_m;
    public NoiseGeneratorOctaves field_4177_a;
    public NoiseGeneratorOctaves field_4176_b;
    private World worldObj;
    private double[] field_4163_o;
    private double[] field_4162_p = new double[256];
    private double[] field_4161_q = new double[256];
    private double[] field_4160_r = new double[256];
    private MapGenBase field_4159_s = new MapGenCavesHell();
    double[] field_4175_c;
    double[] field_4174_d;
    double[] field_4173_e;
    double[] field_4172_f;
    double[] field_4171_g;

    public ChunkProviderHell(World world1, long j2) {
        this.worldObj = world1;
        this.hellRNG = new Random(j2);
        this.field_4169_i = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.field_4168_j = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.field_4167_k = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.field_4166_l = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.field_4165_m = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.field_4177_a = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.field_4176_b = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }

    public void func_4059_a(int i1, int i2, byte[] b3) {
        int b4 = 4;
        int b5 = 32;
        int i6 = b4 + 1;
        int b7 = 17;
        int i8 = b4 + 1;
        this.field_4163_o = this.func_4057_a(this.field_4163_o, i1 * b4, 0, i2 * b4, i6, b7, i8);
        int i9 = 0;
        while (i9 < b4) {
            int i10 = 0;
            while (i10 < b4) {
                int i11 = 0;
                while (i11 < 16) {
                    double d12 = 0.125;
                    double d14 = this.field_4163_o[((i9 + 0) * i8 + i10 + 0) * b7 + i11 + 0];
                    double d16 = this.field_4163_o[((i9 + 0) * i8 + i10 + 1) * b7 + i11 + 0];
                    double d18 = this.field_4163_o[((i9 + 1) * i8 + i10 + 0) * b7 + i11 + 0];
                    double d20 = this.field_4163_o[((i9 + 1) * i8 + i10 + 1) * b7 + i11 + 0];
                    double d22 = (this.field_4163_o[((i9 + 0) * i8 + i10 + 0) * b7 + i11 + 1] - d14) * d12;
                    double d24 = (this.field_4163_o[((i9 + 0) * i8 + i10 + 1) * b7 + i11 + 1] - d16) * d12;
                    double d26 = (this.field_4163_o[((i9 + 1) * i8 + i10 + 0) * b7 + i11 + 1] - d18) * d12;
                    double d28 = (this.field_4163_o[((i9 + 1) * i8 + i10 + 1) * b7 + i11 + 1] - d20) * d12;
                    int i30 = 0;
                    while (i30 < 8) {
                        double d31 = 0.25;
                        double d33 = d14;
                        double d35 = d16;
                        double d37 = (d18 - d14) * d31;
                        double d39 = (d20 - d16) * d31;
                        int i41 = 0;
                        while (i41 < 4) {
                            int i42 = i41 + i9 * 4 << 11 | 0 + i10 * 4 << 7 | i11 * 8 + i30;
                            int s43 = 128;
                            double d44 = 0.25;
                            double d46 = d33;
                            double d48 = (d35 - d33) * d44;
                            int i50 = 0;
                            while (i50 < 4) {
                                int i51 = 0;
                                if (i11 * 8 + i30 < b5) {
                                    i51 = Block.lavaStill.blockID;
                                }
                                if (d46 > 0.0) {
                                    i51 = Block.bloodStone.blockID;
                                }
                                b3[i42] = (byte)i51;
                                i42 += s43;
                                d46 += d48;
                                ++i50;
                            }
                            d33 += d37;
                            d35 += d39;
                            ++i41;
                        }
                        d14 += d22;
                        d16 += d24;
                        d18 += d26;
                        d20 += d28;
                        ++i30;
                    }
                    ++i11;
                }
                ++i10;
            }
            ++i9;
        }
    }

    public void func_4058_b(int i1, int i2, byte[] b3) {
        int b4 = 64;
        double d5 = 0.03125;
        this.field_4162_p = this.field_4166_l.generateNoiseOctaves(this.field_4162_p, i1 * 16, i2 * 16, 0.0, 16, 16, 1, d5, d5, 1.0);
        this.field_4161_q = this.field_4166_l.generateNoiseOctaves(this.field_4161_q, i1 * 16, 109.0134, i2 * 16, 16, 1, 16, d5, 1.0, d5);
        this.field_4160_r = this.field_4165_m.generateNoiseOctaves(this.field_4160_r, i1 * 16, i2 * 16, 0.0, 16, 16, 1, d5 * 2.0, d5 * 2.0, d5 * 2.0);
        int i7 = 0;
        while (i7 < 16) {
            int i8 = 0;
            while (i8 < 16) {
                boolean z9 = this.field_4162_p[i7 + i8 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                boolean z10 = this.field_4161_q[i7 + i8 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                int i11 = (int)(this.field_4160_r[i7 + i8 * 16] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
                int i12 = -1;
                byte b13 = (byte)Block.bloodStone.blockID;
                byte b14 = (byte)Block.bloodStone.blockID;
                int i15 = 127;
                while (i15 >= 0) {
                    int i16 = (i8 * 16 + i7) * 128 + i15;
                    if (i15 >= 127 - this.hellRNG.nextInt(5)) {
                        b3[i16] = (byte)Block.bedrock.blockID;
                    } else if (i15 <= 0 + this.hellRNG.nextInt(5)) {
                        b3[i16] = (byte)Block.bedrock.blockID;
                    } else {
                        byte b17 = b3[i16];
                        if (b17 == 0) {
                            i12 = -1;
                        } else if (b17 == Block.bloodStone.blockID) {
                            if (i12 == -1) {
                                if (i11 <= 0) {
                                    b13 = 0;
                                    b14 = (byte)Block.bloodStone.blockID;
                                } else if (i15 >= b4 - 4 && i15 <= b4 + 1) {
                                    b13 = (byte)Block.bloodStone.blockID;
                                    b14 = (byte)Block.bloodStone.blockID;
                                    if (z10) {
                                        b13 = (byte)Block.gravel.blockID;
                                    }
                                    if (z10) {
                                        b14 = (byte)Block.bloodStone.blockID;
                                    }
                                    if (z9) {
                                        b13 = (byte)Block.slowSand.blockID;
                                    }
                                    if (z9) {
                                        b14 = (byte)Block.slowSand.blockID;
                                    }
                                }
                                if (i15 < b4 && b13 == 0) {
                                    b13 = (byte)Block.lavaStill.blockID;
                                }
                                i12 = i11;
                                b3[i16] = i15 >= b4 - 1 ? b13 : b14;
                            } else if (i12 > 0) {
                                --i12;
                                b3[i16] = b14;
                            }
                        }
                    }
                    --i15;
                }
                ++i8;
            }
            ++i7;
        }
    }

    @Override
    public Chunk func_538_d(int i1, int i2) {
        return this.provideChunk(i1, i2);
    }

    @Override
    public Chunk provideChunk(int i1, int i2) {
        this.hellRNG.setSeed((long)i1 * 341873128712L + (long)i2 * 132897987541L);
        byte[] b3 = new byte[32768];
        this.func_4059_a(i1, i2, b3);
        this.func_4058_b(i1, i2, b3);
        this.field_4159_s.func_867_a(this, this.worldObj, i1, i2, b3);
        Chunk chunk4 = new Chunk(this.worldObj, b3, i1, i2);
        return chunk4;
    }

    private double[] func_4057_a(double[] d1, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (d1 == null) {
            d1 = new double[i5 * i6 * i7];
        }
        double d8 = 684.412;
        double d10 = 2053.236;
        this.field_4172_f = this.field_4177_a.generateNoiseOctaves(this.field_4172_f, i2, i3, i4, i5, 1, i7, 1.0, 0.0, 1.0);
        this.field_4171_g = this.field_4176_b.generateNoiseOctaves(this.field_4171_g, i2, i3, i4, i5, 1, i7, 100.0, 0.0, 100.0);
        this.field_4175_c = this.field_4167_k.generateNoiseOctaves(this.field_4175_c, i2, i3, i4, i5, i6, i7, d8 / 80.0, d10 / 60.0, d8 / 80.0);
        this.field_4174_d = this.field_4169_i.generateNoiseOctaves(this.field_4174_d, i2, i3, i4, i5, i6, i7, d8, d10, d8);
        this.field_4173_e = this.field_4168_j.generateNoiseOctaves(this.field_4173_e, i2, i3, i4, i5, i6, i7, d8, d10, d8);
        int i12 = 0;
        int i13 = 0;
        double[] d14 = new double[i6];
        int i15 = 0;
        while (i15 < i6) {
            d14[i15] = Math.cos((double)i15 * Math.PI * 6.0 / (double)i6) * 2.0;
            double d16 = i15;
            if (i15 > i6 / 2) {
                d16 = i6 - 1 - i15;
            }
            if (d16 < 4.0) {
                d16 = 4.0 - d16;
                int n = i15;
                d14[n] = d14[n] - d16 * d16 * d16 * 10.0;
            }
            ++i15;
        }
        i15 = 0;
        while (i15 < i5) {
            int i36 = 0;
            while (i36 < i7) {
                double d17 = (this.field_4172_f[i13] + 256.0) / 512.0;
                if (d17 > 1.0) {
                    d17 = 1.0;
                }
                double d19 = 0.0;
                double d21 = this.field_4171_g[i13] / 8000.0;
                if (d21 < 0.0) {
                    d21 = -d21;
                }
                if ((d21 = d21 * 3.0 - 3.0) < 0.0) {
                    if ((d21 /= 2.0) < -1.0) {
                        d21 = -1.0;
                    }
                    d21 /= 1.4;
                    d21 /= 2.0;
                    d17 = 0.0;
                } else {
                    if (d21 > 1.0) {
                        d21 = 1.0;
                    }
                    d21 /= 6.0;
                }
                d17 += 0.5;
                d21 = d21 * (double)i6 / 16.0;
                ++i13;
                int i23 = 0;
                while (i23 < i6) {
                    double d34;
                    double d24 = 0.0;
                    double d26 = d14[i23];
                    double d28 = this.field_4174_d[i12] / 512.0;
                    double d30 = this.field_4173_e[i12] / 512.0;
                    double d32 = (this.field_4175_c[i12] / 10.0 + 1.0) / 2.0;
                    d24 = d32 < 0.0 ? d28 : (d32 > 1.0 ? d30 : d28 + (d30 - d28) * d32);
                    d24 -= d26;
                    if (i23 > i6 - 4) {
                        d34 = (float)(i23 - (i6 - 4)) / 3.0f;
                        d24 = d24 * (1.0 - d34) + -10.0 * d34;
                    }
                    if ((double)i23 < d19) {
                        d34 = (d19 - (double)i23) / 4.0;
                        if (d34 < 0.0) {
                            d34 = 0.0;
                        }
                        if (d34 > 1.0) {
                            d34 = 1.0;
                        }
                        d24 = d24 * (1.0 - d34) + -10.0 * d34;
                    }
                    d1[i12] = d24;
                    ++i12;
                    ++i23;
                }
                ++i36;
            }
            ++i15;
        }
        return d1;
    }

    @Override
    public boolean chunkExists(int i1, int i2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider iChunkProvider1, int i2, int i3) {
        int i10;
        int i9;
        int i8;
        int i7;
        BlockSand.fallInstantly = true;
        int i4 = i2 * 16;
        int i5 = i3 * 16;
        int i6 = 0;
        while (i6 < 8) {
            i7 = i4 + this.hellRNG.nextInt(16) + 8;
            i8 = this.hellRNG.nextInt(120) + 4;
            i9 = i5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenHellLava(Block.lavaMoving.blockID).generate(this.worldObj, this.hellRNG, i7, i8, i9);
            ++i6;
        }
        i6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
        i7 = 0;
        while (i7 < i6) {
            i8 = i4 + this.hellRNG.nextInt(16) + 8;
            i9 = this.hellRNG.nextInt(120) + 4;
            i10 = i5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFire().generate(this.worldObj, this.hellRNG, i8, i9, i10);
            ++i7;
        }
        i6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);
        i7 = 0;
        while (i7 < i6) {
            i8 = i4 + this.hellRNG.nextInt(16) + 8;
            i9 = this.hellRNG.nextInt(120) + 4;
            i10 = i5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenLightStone1().generate(this.worldObj, this.hellRNG, i8, i9, i10);
            ++i7;
        }
        i7 = 0;
        while (i7 < 10) {
            i8 = i4 + this.hellRNG.nextInt(16) + 8;
            i9 = this.hellRNG.nextInt(128);
            i10 = i5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenLightStone2().generate(this.worldObj, this.hellRNG, i8, i9, i10);
            ++i7;
        }
        if (this.hellRNG.nextInt(1) == 0) {
            i7 = i4 + this.hellRNG.nextInt(16) + 8;
            i8 = this.hellRNG.nextInt(128);
            i9 = i5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Block.mushroomBrown.blockID).generate(this.worldObj, this.hellRNG, i7, i8, i9);
        }
        if (this.hellRNG.nextInt(1) == 0) {
            i7 = i4 + this.hellRNG.nextInt(16) + 8;
            i8 = this.hellRNG.nextInt(128);
            i9 = i5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Block.mushroomRed.blockID).generate(this.worldObj, this.hellRNG, i7, i8, i9);
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
        return "HellRandomLevelSource";
    }
}

