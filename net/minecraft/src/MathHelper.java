/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class MathHelper {
    private static float[] SIN_TABLE = new float[65536];

    static {
        int i0 = 0;
        while (i0 < 65536) {
            MathHelper.SIN_TABLE[i0] = (float)Math.sin((double)i0 * Math.PI * 2.0 / 65536.0);
            ++i0;
        }
    }

    public static final float sin(float f0) {
        return SIN_TABLE[(int)(f0 * 10430.378f) & 0xFFFF];
    }

    public static final float cos(float f0) {
        return SIN_TABLE[(int)(f0 * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static final float sqrt_float(float f0) {
        return (float)Math.sqrt(f0);
    }

    public static float clamp_float(float par0, float par1, float par2) {
        return par0 < par1 ? par1 : (par0 > par2 ? par2 : par0);
    }

    public static int ceiling_double_int(double par0) {
        int var2 = (int)par0;
        return par0 > (double)var2 ? var2 + 1 : var2;
    }

    public static final float sqrt_double(double d0) {
        return (float)Math.sqrt(d0);
    }

    public static int floor_float(float f0) {
        int i1 = (int)f0;
        return f0 < (float)i1 ? i1 - 1 : i1;
    }

    public static int floor_double(double d0) {
        int i2 = (int)d0;
        return d0 < (double)i2 ? i2 - 1 : i2;
    }

    public static float abs(float f0) {
        return f0 >= 0.0f ? f0 : -f0;
    }

    public static double abs_max(double d0, double d2) {
        if (d0 < 0.0) {
            d0 = -d0;
        }
        if (d2 < 0.0) {
            d2 = -d2;
        }
        return d0 > d2 ? d0 : d2;
    }

    public static int bucketInt(int i0, int i1) {
        return i0 < 0 ? -((-i0 - 1) / i1) - 1 : i0 / i1;
    }

    public static boolean stringNullOrLengthZero(String string0) {
        return string0 == null || string0.length() == 0;
    }
}

