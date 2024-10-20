/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;

public class NoiseGenerator2 {
    private static int[][] field_4296_d;
    private int[] field_4295_e = new int[512];
    public double field_4292_a;
    public double field_4291_b;
    public double field_4297_c;
    private static final double field_4294_f;
    private static final double field_4293_g;

    static {
        int[][] nArrayArray = new int[12][];
        int[] nArray = new int[3];
        nArray[0] = 1;
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[3];
        nArray2[0] = -1;
        nArray2[1] = 1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[3];
        nArray3[0] = 1;
        nArray3[1] = -1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[3];
        nArray4[0] = -1;
        nArray4[1] = -1;
        nArrayArray[3] = nArray4;
        int[] nArray5 = new int[3];
        nArray5[0] = 1;
        nArray5[2] = 1;
        nArrayArray[4] = nArray5;
        int[] nArray6 = new int[3];
        nArray6[0] = -1;
        nArray6[2] = 1;
        nArrayArray[5] = nArray6;
        int[] nArray7 = new int[3];
        nArray7[0] = 1;
        nArray7[2] = -1;
        nArrayArray[6] = nArray7;
        int[] nArray8 = new int[3];
        nArray8[0] = -1;
        nArray8[2] = -1;
        nArrayArray[7] = nArray8;
        int[] nArray9 = new int[3];
        nArray9[1] = 1;
        nArray9[2] = 1;
        nArrayArray[8] = nArray9;
        int[] nArray10 = new int[3];
        nArray10[1] = -1;
        nArray10[2] = 1;
        nArrayArray[9] = nArray10;
        int[] nArray11 = new int[3];
        nArray11[1] = 1;
        nArray11[2] = -1;
        nArrayArray[10] = nArray11;
        int[] nArray12 = new int[3];
        nArray12[1] = -1;
        nArray12[2] = -1;
        nArrayArray[11] = nArray12;
        field_4296_d = nArrayArray;
        field_4294_f = 0.5 * (Math.sqrt(3.0) - 1.0);
        field_4293_g = (3.0 - Math.sqrt(3.0)) / 6.0;
    }

    public NoiseGenerator2() {
        this(new Random());
    }

    public NoiseGenerator2(Random random1) {
        this.field_4292_a = random1.nextDouble() * 256.0;
        this.field_4291_b = random1.nextDouble() * 256.0;
        this.field_4297_c = random1.nextDouble() * 256.0;
        int i2 = 0;
        while (i2 < 256) {
            this.field_4295_e[i2] = i2++;
        }
        i2 = 0;
        while (i2 < 256) {
            int i3 = random1.nextInt(256 - i2) + i2;
            int i4 = this.field_4295_e[i2];
            this.field_4295_e[i2] = this.field_4295_e[i3];
            this.field_4295_e[i3] = i4;
            this.field_4295_e[i2 + 256] = this.field_4295_e[i2];
            ++i2;
        }
    }

    private static int wrap(double d0) {
        return d0 > 0.0 ? (int)d0 : (int)d0 - 1;
    }

    private static double func_4156_a(int[] i0, double d1, double d3) {
        return (double)i0[0] * d1 + (double)i0[1] * d3;
    }

    public void func_4157_a(double[] d1, double d2, double d4, int i6, int i7, double d8, double d10, double d12) {
        int i14 = 0;
        int i15 = 0;
        while (i15 < i6) {
            double d16 = (d2 + (double)i15) * d8 + this.field_4292_a;
            int i18 = 0;
            while (i18 < i7) {
                int i10001;
                double d25;
                double d23;
                double d21;
                int b42;
                int b41;
                double d35;
                double d39;
                int i30;
                double d31;
                double d19 = (d4 + (double)i18) * d10 + this.field_4291_b;
                double d27 = (d16 + d19) * field_4294_f;
                int i29 = NoiseGenerator2.wrap(d16 + d27);
                double d33 = (double)i29 - (d31 = (double)(i29 + (i30 = NoiseGenerator2.wrap(d19 + d27))) * field_4293_g);
                double d37 = d16 - d33;
                if (d37 > (d39 = d19 - (d35 = (double)i30 - d31))) {
                    b41 = 1;
                    b42 = 0;
                } else {
                    b41 = 0;
                    b42 = 1;
                }
                double d43 = d37 - (double)b41 + field_4293_g;
                double d45 = d39 - (double)b42 + field_4293_g;
                double d47 = d37 - 1.0 + 2.0 * field_4293_g;
                double d49 = d39 - 1.0 + 2.0 * field_4293_g;
                int i51 = i29 & 0xFF;
                int i52 = i30 & 0xFF;
                int i53 = this.field_4295_e[i51 + this.field_4295_e[i52]] % 12;
                int i54 = this.field_4295_e[i51 + b41 + this.field_4295_e[i52 + b42]] % 12;
                int i55 = this.field_4295_e[i51 + 1 + this.field_4295_e[i52 + 1]] % 12;
                double d56 = 0.5 - d37 * d37 - d39 * d39;
                if (d56 < 0.0) {
                    d21 = 0.0;
                } else {
                    d56 *= d56;
                    d21 = d56 * d56 * NoiseGenerator2.func_4156_a(field_4296_d[i53], d37, d39);
                }
                double d58 = 0.5 - d43 * d43 - d45 * d45;
                if (d58 < 0.0) {
                    d23 = 0.0;
                } else {
                    d58 *= d58;
                    d23 = d58 * d58 * NoiseGenerator2.func_4156_a(field_4296_d[i54], d43, d45);
                }
                double d60 = 0.5 - d47 * d47 - d49 * d49;
                if (d60 < 0.0) {
                    d25 = 0.0;
                } else {
                    d60 *= d60;
                    d25 = d60 * d60 * NoiseGenerator2.func_4156_a(field_4296_d[i55], d47, d49);
                }
                int n = i10001 = i14++;
                d1[n] = d1[n] + 70.0 * (d21 + d23 + d25) * d12;
                ++i18;
            }
            ++i15;
        }
    }
}

