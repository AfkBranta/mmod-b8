/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.NoiseGenerator;
import net.minecraft.src.NoiseGeneratorPerlin;

public class NoiseGeneratorOctaves
extends NoiseGenerator {
    private NoiseGeneratorPerlin[] generatorCollection;
    private int field_1191_b;

    public NoiseGeneratorOctaves(Random random1, int i2) {
        this.field_1191_b = i2;
        this.generatorCollection = new NoiseGeneratorPerlin[i2];
        int i3 = 0;
        while (i3 < i2) {
            this.generatorCollection[i3] = new NoiseGeneratorPerlin(random1);
            ++i3;
        }
    }

    public double func_806_a(double d1, double d3) {
        double d5 = 0.0;
        double d7 = 1.0;
        int i9 = 0;
        while (i9 < this.field_1191_b) {
            d5 += this.generatorCollection[i9].func_801_a(d1 * d7, d3 * d7) / d7;
            d7 /= 2.0;
            ++i9;
        }
        return d5;
    }

    public double[] generateNoiseOctaves(double[] d1, double d2, double d4, double d6, int i8, int i9, int i10, double d11, double d13, double d15) {
        if (d1 == null) {
            d1 = new double[i8 * i9 * i10];
        } else {
            int i17 = 0;
            while (i17 < d1.length) {
                d1[i17] = 0.0;
                ++i17;
            }
        }
        double d20 = 1.0;
        int i19 = 0;
        while (i19 < this.field_1191_b) {
            this.generatorCollection[i19].func_805_a(d1, d2, d4, d6, i8, i9, i10, d11 * d20, d13 * d20, d15 * d20, d20);
            d20 /= 2.0;
            ++i19;
        }
        return d1;
    }

    public double[] func_4109_a(double[] d1, int i2, int i3, int i4, int i5, double d6, double d8, double d10) {
        return this.generateNoiseOctaves(d1, i2, 10.0, i3, i4, 1, i5, d6, 1.0, d8);
    }
}

