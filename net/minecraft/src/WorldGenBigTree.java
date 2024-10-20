/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenBigTree
extends WorldGenerator {
    static final byte[] field_882_a;
    Random field_881_b = new Random();
    World worldObj;
    int[] basePos = new int[3];
    int field_878_e = 0;
    int height;
    double field_876_g = 0.618;
    double field_875_h = 1.0;
    double field_874_i = 0.381;
    double field_873_j = 1.0;
    double field_872_k = 1.0;
    int field_871_l = 1;
    int field_870_m = 12;
    int field_869_n = 4;
    int[][] field_868_o;

    static {
        byte[] byArray = new byte[6];
        byArray[0] = 2;
        byArray[3] = 1;
        byArray[4] = 2;
        byArray[5] = 1;
        field_882_a = byArray;
    }

    void func_521_a() {
        int i1;
        this.height = (int)((double)this.field_878_e * this.field_876_g);
        if (this.height >= this.field_878_e) {
            this.height = this.field_878_e - 1;
        }
        if ((i1 = (int)(1.382 + Math.pow(this.field_872_k * (double)this.field_878_e / 13.0, 2.0))) < 1) {
            i1 = 1;
        }
        int[][] i2 = new int[i1 * this.field_878_e][4];
        int i3 = this.basePos[1] + this.field_878_e - this.field_869_n;
        int i4 = 1;
        int i5 = this.basePos[1] + this.height;
        int i6 = i3 - this.basePos[1];
        i2[0][0] = this.basePos[0];
        i2[0][1] = i3--;
        i2[0][2] = this.basePos[2];
        i2[0][3] = i5;
        while (i6 >= 0) {
            int i7 = 0;
            float f8 = this.func_528_a(i6);
            if (f8 < 0.0f) {
                --i3;
                --i6;
                continue;
            }
            double d9 = 0.5;
            while (i7 < i1) {
                int[] i18;
                int i16;
                double d13;
                double d11 = this.field_873_j * (double)f8 * ((double)this.field_881_b.nextFloat() + 0.328);
                int i15 = (int)(d11 * Math.sin(d13 = (double)this.field_881_b.nextFloat() * 2.0 * 3.14159) + (double)this.basePos[0] + d9);
                int[] i17 = new int[]{i15, i3, i16 = (int)(d11 * Math.cos(d13) + (double)this.basePos[2] + d9)};
                if (this.func_524_a(i17, i18 = new int[]{i15, i3 + this.field_869_n, i16}) == -1) {
                    int[] i19 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
                    double d20 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - i17[0]), 2.0) + Math.pow(Math.abs(this.basePos[2] - i17[2]), 2.0));
                    double d22 = d20 * this.field_874_i;
                    i19[1] = (double)i17[1] - d22 > (double)i5 ? i5 : (int)((double)i17[1] - d22);
                    if (this.func_524_a(i19, i17) == -1) {
                        i2[i4][0] = i15;
                        i2[i4][1] = i3;
                        i2[i4][2] = i16;
                        i2[i4][3] = i19[1];
                        ++i4;
                    }
                }
                ++i7;
            }
            --i3;
            --i6;
        }
        this.field_868_o = new int[i4][4];
        System.arraycopy(i2, 0, this.field_868_o, 0, i4);
    }

    void func_523_a(int i1, int i2, int i3, float f4, byte b5, int i6) {
        int i7 = (int)((double)f4 + 0.618);
        byte b8 = field_882_a[b5];
        byte b9 = field_882_a[b5 + 3];
        int[] i10 = new int[]{i1, i2, i3};
        int[] i11 = new int[3];
        int i12 = -i7;
        int i13 = -i7;
        i11[b5] = i10[b5];
        while (i12 <= i7) {
            i11[b8] = i10[b8] + i12;
            i13 = -i7;
            while (i13 <= i7) {
                double d15 = Math.sqrt(Math.pow((double)Math.abs(i12) + 0.5, 2.0) + Math.pow((double)Math.abs(i13) + 0.5, 2.0));
                if (d15 > (double)f4) {
                    ++i13;
                    continue;
                }
                i11[b9] = i10[b9] + i13;
                int i14 = this.worldObj.getBlockId(i11[0], i11[1], i11[2]);
                if (i14 != 0 && i14 != 18) {
                    ++i13;
                    continue;
                }
                this.worldObj.setBlock(i11[0], i11[1], i11[2], i6);
                ++i13;
            }
            ++i12;
        }
    }

    float func_528_a(int i1) {
        if ((double)i1 < (double)this.field_878_e * 0.3) {
            return -1.618f;
        }
        float f2 = (float)this.field_878_e / 2.0f;
        float f3 = (float)this.field_878_e / 2.0f - (float)i1;
        float f4 = f3 == 0.0f ? f2 : (Math.abs(f3) >= f2 ? 0.0f : (float)Math.sqrt(Math.pow(Math.abs(f2), 2.0) - Math.pow(Math.abs(f3), 2.0)));
        return f4 *= 0.5f;
    }

    float func_526_b(int i1) {
        return i1 >= 0 && i1 < this.field_869_n ? (i1 != 0 && i1 != this.field_869_n - 1 ? 3.0f : 2.0f) : -1.0f;
    }

    void func_520_a(int i1, int i2, int i3) {
        int i4 = i2;
        int i5 = i2 + this.field_869_n;
        while (i4 < i5) {
            float f6 = this.func_526_b(i4 - i2);
            this.func_523_a(i1, i4, i3, f6, (byte)1, 18);
            ++i4;
        }
    }

    void func_522_a(int[] i1, int[] i2, int i3) {
        int[] i4 = new int[3];
        int b5 = 0;
        int b6 = 0;
        while (b5 < 3) {
            i4[b5] = i2[b5] - i1[b5];
            if (Math.abs(i4[b5]) > Math.abs(i4[b6])) {
                b6 = b5;
            }
            b5 = (byte)(b5 + 1);
        }
        if (i4[b6] != 0) {
            byte b7 = field_882_a[b6];
            byte b8 = field_882_a[b6 + 3];
            int b9 = i4[b6] > 0 ? 1 : -1;
            double d10 = (double)i4[b7] / (double)i4[b6];
            double d12 = (double)i4[b8] / (double)i4[b6];
            int[] i14 = new int[3];
            int i15 = 0;
            int i16 = i4[b6] + b9;
            while (i15 != i16) {
                i14[b6] = MathHelper.floor_double((double)(i1[b6] + i15) + 0.5);
                i14[b7] = MathHelper.floor_double((double)i1[b7] + (double)i15 * d10 + 0.5);
                i14[b8] = MathHelper.floor_double((double)i1[b8] + (double)i15 * d12 + 0.5);
                this.worldObj.setBlock(i14[0], i14[1], i14[2], i3);
                i15 += b9;
            }
        }
    }

    void func_518_b() {
        int i1 = 0;
        int i2 = this.field_868_o.length;
        while (i1 < i2) {
            int i3 = this.field_868_o[i1][0];
            int i4 = this.field_868_o[i1][1];
            int i5 = this.field_868_o[i1][2];
            this.func_520_a(i3, i4, i5);
            ++i1;
        }
    }

    boolean func_527_c(int i1) {
        return (double)i1 >= (double)this.field_878_e * 0.2;
    }

    void func_529_c() {
        int i1 = this.basePos[0];
        int i2 = this.basePos[1];
        int i3 = this.basePos[1] + this.height;
        int i4 = this.basePos[2];
        int[] i5 = new int[]{i1, i2, i4};
        int[] i6 = new int[]{i1, i3, i4};
        this.func_522_a(i5, i6, 17);
        if (this.field_871_l == 2) {
            i5[0] = i5[0] + 1;
            i6[0] = i6[0] + 1;
            this.func_522_a(i5, i6, 17);
            i5[2] = i5[2] + 1;
            i6[2] = i6[2] + 1;
            this.func_522_a(i5, i6, 17);
            i5[0] = i5[0] + -1;
            i6[0] = i6[0] + -1;
            this.func_522_a(i5, i6, 17);
        }
    }

    void func_525_d() {
        int i1 = 0;
        int i2 = this.field_868_o.length;
        int[] i3 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
        while (i1 < i2) {
            int[] i4 = this.field_868_o[i1];
            int[] i5 = new int[]{i4[0], i4[1], i4[2]};
            i3[1] = i4[3];
            int i6 = i3[1] - this.basePos[1];
            if (this.func_527_c(i6)) {
                this.func_522_a(i3, i5, 17);
            }
            ++i1;
        }
    }

    int func_524_a(int[] i1, int[] i2) {
        int[] i3 = new int[3];
        int b4 = 0;
        int b5 = 0;
        while (b4 < 3) {
            i3[b4] = i2[b4] - i1[b4];
            if (Math.abs(i3[b4]) > Math.abs(i3[b5])) {
                b5 = b4;
            }
            b4 = (byte)(b4 + 1);
        }
        if (i3[b5] == 0) {
            return -1;
        }
        byte b6 = field_882_a[b5];
        byte b7 = field_882_a[b5 + 3];
        int b8 = i3[b5] > 0 ? 1 : -1;
        double d9 = (double)i3[b6] / (double)i3[b5];
        double d11 = (double)i3[b7] / (double)i3[b5];
        int[] i13 = new int[3];
        int i14 = 0;
        int i15 = i3[b5] + b8;
        while (i14 != i15) {
            i13[b5] = i1[b5] + i14;
            i13[b6] = (int)((double)i1[b6] + (double)i14 * d9);
            i13[b7] = (int)((double)i1[b7] + (double)i14 * d11);
            int i16 = this.worldObj.getBlockId(i13[0], i13[1], i13[2]);
            if (i16 != 0 && i16 != 18) break;
            i14 += b8;
        }
        return i14 == i15 ? -1 : Math.abs(i14);
    }

    boolean func_519_e() {
        int[] i1 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
        int[] i2 = new int[]{this.basePos[0], this.basePos[1] + this.field_878_e - 1, this.basePos[2]};
        int i3 = this.worldObj.getBlockId(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);
        if (i3 != 2 && i3 != 3) {
            return false;
        }
        int i4 = this.func_524_a(i1, i2);
        if (i4 == -1) {
            return true;
        }
        if (i4 < 6) {
            return false;
        }
        this.field_878_e = i4;
        return true;
    }

    @Override
    public void func_517_a(double d1, double d3, double d5) {
        this.field_870_m = (int)(d1 * 12.0);
        if (d1 > 0.5) {
            this.field_869_n = 5;
        }
        this.field_873_j = d3;
        this.field_872_k = d5;
    }

    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        this.worldObj = world1;
        long j6 = random2.nextLong();
        this.field_881_b.setSeed(j6);
        this.basePos[0] = i3;
        this.basePos[1] = i4;
        this.basePos[2] = i5;
        if (this.field_878_e == 0) {
            this.field_878_e = 5 + this.field_881_b.nextInt(this.field_870_m);
        }
        if (!this.func_519_e()) {
            return false;
        }
        this.func_521_a();
        this.func_518_b();
        this.func_529_c();
        this.func_525_d();
        return true;
    }
}

