/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class MapGenCaves
extends MapGenBase {
    protected void func_870_a(int i1, int i2, byte[] b3, double d4, double d6, double d8) {
        this.releaseEntitySkin(i1, i2, b3, d4, d6, d8, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }

    protected void releaseEntitySkin(int i1, int i2, byte[] b3, double d4, double d6, double d8, float f10, float f11, float f12, int i13, int i14, double d15) {
        double d17 = i1 * 16 + 8;
        double d19 = i2 * 16 + 8;
        float f21 = 0.0f;
        float f22 = 0.0f;
        Random random23 = new Random(this.rand.nextLong());
        if (i14 <= 0) {
            int i24 = this.field_1306_a * 16 - 16;
            i14 = i24 - random23.nextInt(i24 / 4);
        }
        boolean z52 = false;
        if (i13 == -1) {
            i13 = i14 / 2;
            z52 = true;
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
            if (!z52 && i13 == i25 && f10 > 1.0f) {
                this.releaseEntitySkin(i1, i2, b3, d4, d6, d8, random23.nextFloat() * 0.5f + 0.5f, f11 - 1.5707964f, f12 / 3.0f, i13, i14, 1.0);
                this.releaseEntitySkin(i1, i2, b3, d4, d6, d8, random23.nextFloat() * 0.5f + 0.5f, f11 + 1.5707964f, f12 / 3.0f, i13, i14, 1.0);
                return;
            }
            if (z52 || random23.nextInt(4) != 0) {
                double d33 = d4 - d17;
                double d35 = d8 - d19;
                double d37 = i14 - i13;
                double d39 = f10 + 2.0f + 16.0f;
                if (d33 * d33 + d35 * d35 - d37 * d37 > d39 * d39) {
                    return;
                }
                if (d4 >= d17 - 16.0 - d27 * 2.0 && d8 >= d19 - 16.0 - d27 * 2.0 && d4 <= d17 + 16.0 + d27 * 2.0 && d8 <= d19 + 16.0 + d27 * 2.0) {
                    int i43;
                    int i53 = MathHelper.floor_double(d4 - d27) - i1 * 16 - 1;
                    int i34 = MathHelper.floor_double(d4 + d27) - i1 * 16 + 1;
                    int i54 = MathHelper.floor_double(d6 - d29) - 1;
                    int i36 = MathHelper.floor_double(d6 + d29) + 1;
                    int i55 = MathHelper.floor_double(d8 - d27) - i2 * 16 - 1;
                    int i38 = MathHelper.floor_double(d8 + d27) - i2 * 16 + 1;
                    if (i53 < 0) {
                        i53 = 0;
                    }
                    if (i34 > 16) {
                        i34 = 16;
                    }
                    if (i54 < 1) {
                        i54 = 1;
                    }
                    if (i36 > 120) {
                        i36 = 120;
                    }
                    if (i55 < 0) {
                        i55 = 0;
                    }
                    if (i38 > 16) {
                        i38 = 16;
                    }
                    boolean z56 = false;
                    int i40 = i53;
                    while (!z56 && i40 < i34) {
                        int i41 = i55;
                        while (!z56 && i41 < i38) {
                            int i42 = i36 + 1;
                            while (!z56 && i42 >= i54 - 1) {
                                i43 = (i40 * 16 + i41) * 128 + i42;
                                if (i42 >= 0 && i42 < 128) {
                                    if (b3[i43] == Block.waterMoving.blockID || b3[i43] == Block.waterStill.blockID) {
                                        z56 = true;
                                    }
                                    if (i42 != i54 - 1 && i40 != i53 && i40 != i34 - 1 && i41 != i55 && i41 != i38 - 1) {
                                        i42 = i54;
                                    }
                                }
                                --i42;
                            }
                            ++i41;
                        }
                        ++i40;
                    }
                    if (!z56) {
                        i40 = i53;
                        while (i40 < i34) {
                            double d57 = ((double)(i40 + i1 * 16) + 0.5 - d4) / d27;
                            i43 = i55;
                            while (i43 < i38) {
                                double d44 = ((double)(i43 + i2 * 16) + 0.5 - d8) / d27;
                                int i46 = (i40 * 16 + i43) * 128 + i36;
                                boolean z47 = false;
                                if (d57 * d57 + d44 * d44 < 1.0) {
                                    int i48 = i36 - 1;
                                    while (i48 >= i54) {
                                        double d49 = ((double)i48 + 0.5 - d6) / d29;
                                        if (d49 > -0.7 && d57 * d57 + d49 * d49 + d44 * d44 < 1.0) {
                                            byte b51 = b3[i46];
                                            if (b51 == Block.grass.blockID) {
                                                z47 = true;
                                            }
                                            if (b51 == Block.stone.blockID || b51 == Block.dirt.blockID || b51 == Block.grass.blockID) {
                                                if (i48 < 10) {
                                                    b3[i46] = (byte)Block.lavaMoving.blockID;
                                                } else {
                                                    b3[i46] = 0;
                                                    if (z47 && b3[i46 - 1] == Block.dirt.blockID) {
                                                        b3[i46 - 1] = (byte)Block.grass.blockID;
                                                    }
                                                }
                                            }
                                        }
                                        --i46;
                                        --i48;
                                    }
                                }
                                ++i43;
                            }
                            ++i40;
                        }
                        if (z52) break;
                    }
                }
            }
            ++i13;
        }
    }

    @Override
    protected void func_868_a(World world1, int i2, int i3, int i4, int i5, byte[] b6) {
        int i7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
        if (this.rand.nextInt(15) != 0) {
            i7 = 0;
        }
        int i8 = 0;
        while (i8 < i7) {
            double d9 = i2 * 16 + this.rand.nextInt(16);
            double d11 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            double d13 = i3 * 16 + this.rand.nextInt(16);
            int i15 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.func_870_a(i4, i5, b6, d9, d11, d13);
                i15 += this.rand.nextInt(4);
            }
            int i16 = 0;
            while (i16 < i15) {
                float f17 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                float f18 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float f19 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                this.releaseEntitySkin(i4, i5, b6, d9, d11, d13, f19, f17, f18, 0, 0, 1.0);
                ++i16;
            }
            ++i8;
        }
    }
}

