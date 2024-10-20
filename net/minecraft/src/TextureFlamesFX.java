/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.TextureFX;

public class TextureFlamesFX
extends TextureFX {
    protected float[] field_1133_g = new float[320];
    protected float[] field_1132_h = new float[320];

    public TextureFlamesFX(int i1) {
        super(Block.fire.blockIndexInTexture + i1 * 16);
    }

    @Override
    public void onTick() {
        int i6;
        int i5;
        float f4;
        int i2;
        int i1 = 0;
        while (i1 < 16) {
            i2 = 0;
            while (i2 < 20) {
                int i3 = 18;
                f4 = this.field_1133_g[i1 + (i2 + 1) % 20 * 16] * (float)i3;
                i5 = i1 - 1;
                while (i5 <= i1 + 1) {
                    i6 = i2;
                    while (i6 <= i2 + 1) {
                        if (i5 >= 0 && i6 >= 0 && i5 < 16 && i6 < 20) {
                            f4 += this.field_1133_g[i5 + i6 * 16];
                        }
                        ++i3;
                        ++i6;
                    }
                    ++i5;
                }
                this.field_1132_h[i1 + i2 * 16] = f4 / ((float)i3 * 1.06f);
                if (i2 >= 19) {
                    this.field_1132_h[i1 + i2 * 16] = (float)(Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * (double)0.1f + (double)0.2f);
                }
                ++i2;
            }
            ++i1;
        }
        float[] f12 = this.field_1132_h;
        this.field_1132_h = this.field_1133_g;
        this.field_1133_g = f12;
        i2 = 0;
        while (i2 < 256) {
            float f13 = this.field_1133_g[i2] * 1.8f;
            if (f13 > 1.0f) {
                f13 = 1.0f;
            }
            if (f13 < 0.0f) {
                f13 = 0.0f;
            }
            i5 = (int)(f13 * 155.0f + 100.0f);
            i6 = (int)(f13 * f13 * 255.0f);
            int i7 = (int)(f13 * f13 * f13 * f13 * f13 * f13 * f13 * f13 * f13 * f13 * 255.0f);
            int s8 = 255;
            if (f13 < 0.5f) {
                s8 = 0;
            }
            f4 = (f13 - 0.5f) * 2.0f;
            if (this.anaglyphEnabled) {
                int i9 = (i5 * 30 + i6 * 59 + i7 * 11) / 100;
                int i10 = (i5 * 30 + i6 * 70) / 100;
                int i11 = (i5 * 30 + i7 * 70) / 100;
                i5 = i9;
                i6 = i10;
                i7 = i11;
            }
            this.imageData[i2 * 4 + 0] = (byte)i5;
            this.imageData[i2 * 4 + 1] = (byte)i6;
            this.imageData[i2 * 4 + 2] = (byte)i7;
            this.imageData[i2 * 4 + 3] = (byte)s8;
            ++i2;
        }
    }
}

