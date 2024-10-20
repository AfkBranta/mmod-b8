/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.NoiseGenerator;
import net.minecraft.src.NoiseGenerator2;

public class NoiseGeneratorOctaves2
extends NoiseGenerator {
    private NoiseGenerator2[] field_4234_a;
    private int field_4233_b;

    public NoiseGeneratorOctaves2(Random random1, int i2) {
        this.field_4233_b = i2;
        this.field_4234_a = new NoiseGenerator2[i2];
        int i3 = 0;
        while (i3 < i2) {
            this.field_4234_a[i3] = new NoiseGenerator2(random1);
            ++i3;
        }
    }

    public double[] func_4112_a(double[] d1, double d2, double d4, int i6, int i7, double d8, double d10, double d12) {
        return this.func_4111_a(d1, d2, d4, i6, i7, d8, d10, d12, 0.5);
    }

    public double[] func_4111_a(double[] d1, double d2, double d4, int i6, int i7, double d8, double d10, double d12, double d14) {
        d8 /= 1.5;
        d10 /= 1.5;
        if (d1 != null && d1.length >= i6 * i7) {
            int i16 = 0;
            while (i16 < d1.length) {
                d1[i16] = 0.0;
                ++i16;
            }
        } else {
            d1 = new double[i6 * i7];
        }
        double d21 = 1.0;
        double d18 = 1.0;
        int i20 = 0;
        while (i20 < this.field_4233_b) {
            this.field_4234_a[i20].func_4157_a(d1, d2, d4, i6, i7, d8 * d18, d10 * d18, 0.55 / d21);
            d18 *= d12;
            d21 *= d14;
            ++i20;
        }
        return d1;
    }
}

