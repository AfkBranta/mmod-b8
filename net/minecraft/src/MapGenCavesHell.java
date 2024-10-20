/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class MapGenCavesHell
extends MapGenBase {
    protected void func_4129_a(int i1, int i2, byte[] b3, double d4, double d6, double d8) {
        this.func_4128_a(i1, i2, b3, d4, d6, d8, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }

    protected void func_4128_a(int i1, int i2, byte[] b3, double d4, double d6, double d8, float f10, float f11, float f12, int i13, int i14, double d15) {
        double d17 = i1 * 16 + 8;
        double d19 = i2 * 16 + 8;
        float f21 = 0.0f;
        float f22 = 0.0f;
        Random random23 = new Random(this.rand.nextLong());
        if (i14 <= 0) {
            int i24 = this.field_1306_a * 16 - 16;
            i14 = i24 - random23.nextInt(i24 / 4);
        }
        boolean z51 = false;
        if (i13 == -1) {
            i13 = i14 / 2;
            z51 = true;
        }
        int i25 = random23.nextInt(i14 / 2) + i14 / 4;
        boolean z26 = random23.nextInt(6) == 0;
        while (i13 < i14) {
            double d27 = 1.5 + (double)(MathHelper.sin((float)i13 * (float)Math.PI / (float)i14) * f10 * 1.0f);
            double d29 = d27 * d15;
            float f31 = MathHelper.cos(f12);
            float f32 = MathHelper.sin(f12);
            d4 += (double)(MathHelper.cos(f11) * f31);
            d6 += (double)f32;
            d8 += (double)(MathHelper.sin(f11) * f31);
            f12 = z26 ? (f12 *= 0.92f) : (f12 *= 0.7f);
            f12 += f22 * 0.1f;
            f11 += f21 * 0.1f;
            f22 *= 0.9f;
            f21 *= 0.75f;
            f22 += (random23.nextFloat() - random23.nextFloat()) * random23.nextFloat() * 2.0f;
            f21 += (random23.nextFloat() - random23.nextFloat()) * random23.nextFloat() * 4.0f;
            if (!z51 && i13 == i25 && f10 > 1.0f) {
                this.func_4128_a(i1, i2, b3, d4, d6, d8, random23.nextFloat() * 0.5f + 0.5f, f11 - 1.5707964f, f12 / 3.0f, i13, i14, 1.0);
                this.func_4128_a(i1, i2, b3, d4, d6, d8, random23.nextFloat() * 0.5f + 0.5f, f11 + 1.5707964f, f12 / 3.0f, i13, i14, 1.0);
                return;
            }
            if (z51 || random23.nextInt(4) != 0) {
                double d33 = d4 - d17;
                double d35 = d8 - d19;
                double d37 = i14 - i13;
                double d39 = f10 + 2.0f + 16.0f;
                if (d33 * d33 + d35 * d35 - d37 * d37 > d39 * d39) {
                    return;
                }
                if (d4 >= d17 - 16.0 - d27 * 2.0 && d8 >= d19 - 16.0 - d27 * 2.0 && d4 <= d17 + 16.0 + d27 * 2.0 && d8 <= d19 + 16.0 + d27 * 2.0) {
                    int i43;
                    int i52 = MathHelper.floor_double(d4 - d27) - i1 * 16 - 1;
                    int i34 = MathHelper.floor_double(d4 + d27) - i1 * 16 + 1;
                    int i53 = MathHelper.floor_double(d6 - d29) - 1;
                    int i36 = MathHelper.floor_double(d6 + d29) + 1;
                    int i54 = MathHelper.floor_double(d8 - d27) - i2 * 16 - 1;
                    int i38 = MathHelper.floor_double(d8 + d27) - i2 * 16 + 1;
                    if (i52 < 0) {
                        i52 = 0;
                    }
                    if (i34 > 16) {
                        i34 = 16;
                    }
                    if (i53 < 1) {
                        i53 = 1;
                    }
                    if (i36 > 120) {
                        i36 = 120;
                    }
                    if (i54 < 0) {
                        i54 = 0;
                    }
                    if (i38 > 16) {
                        i38 = 16;
                    }
                    boolean z55 = false;
                    int i40 = i52;
                    while (!z55 && i40 < i34) {
                        int i41 = i54;
                        while (!z55 && i41 < i38) {
                            int i42 = i36 + 1;
                            while (!z55 && i42 >= i53 - 1) {
                                i43 = (i40 * 16 + i41) * 128 + i42;
                                if (i42 >= 0 && i42 < 128) {
                                    if (b3[i43] == Block.lavaMoving.blockID || b3[i43] == Block.lavaStill.blockID) {
                                        z55 = true;
                                    }
                                    if (i42 != i53 - 1 && i40 != i52 && i40 != i34 - 1 && i41 != i54 && i41 != i38 - 1) {
                                        i42 = i53;
                                    }
                                }
                                --i42;
                            }
                            ++i41;
                        }
                        ++i40;
                    }
                    if (!z55) {
                        i40 = i52;
                        while (i40 < i34) {
                            double d56 = ((double)(i40 + i1 * 16) + 0.5 - d4) / d27;
                            i43 = i54;
                            while (i43 < i38) {
                                double d44 = ((double)(i43 + i2 * 16) + 0.5 - d8) / d27;
                                int i46 = (i40 * 16 + i43) * 128 + i36;
                                int i47 = i36 - 1;
                                while (i47 >= i53) {
                                    byte b50;
                                    double d48 = ((double)i47 + 0.5 - d6) / d29;
                                    if (d48 > -0.7 && d56 * d56 + d48 * d48 + d44 * d44 < 1.0 && ((b50 = b3[i46]) == Block.bloodStone.blockID || b50 == Block.dirt.blockID || b50 == Block.grass.blockID)) {
                                        b3[i46] = 0;
                                    }
                                    --i46;
                                    --i47;
                                }
                                ++i43;
                            }
                            ++i40;
                        }
                        if (z51) break;
                    }
                }
            }
            ++i13;
        }
    }

    @Override
    protected void func_868_a(World world1, int i2, int i3, int i4, int i5, byte[] b6) {
        int i7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
        if (this.rand.nextInt(5) != 0) {
            i7 = 0;
        }
        int i8 = 0;
        while (i8 < i7) {
            double d9 = i2 * 16 + this.rand.nextInt(16);
            double d11 = this.rand.nextInt(128);
            double d13 = i3 * 16 + this.rand.nextInt(16);
            int i15 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.func_4129_a(i4, i5, b6, d9, d11, d13);
                i15 += this.rand.nextInt(4);
            }
            int i16 = 0;
            while (i16 < i15) {
                float f17 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                float f18 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float f19 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                this.func_4128_a(i4, i5, b6, d9, d11, d13, f19 * 2.0f, f17, f18, 0, 0, 0.5);
                ++i16;
            }
            ++i8;
        }
    }
}

