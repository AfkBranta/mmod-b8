/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TextureFX;

public class TexturePortalFX
extends TextureFX {
    private int field_4227_g = 0;
    private byte[][] field_4226_h = new byte[32][1024];

    public TexturePortalFX() {
        super(Block.portal.blockIndexInTexture);
        Random random1 = new Random(100L);
        int i2 = 0;
        while (i2 < 32) {
            int i3 = 0;
            while (i3 < 16) {
                int i4 = 0;
                while (i4 < 16) {
                    float f5 = 0.0f;
                    int i6 = 0;
                    while (i6 < 2) {
                        float f7 = i6 * 8;
                        float f8 = i6 * 8;
                        float f9 = ((float)i3 - f7) / 16.0f * 2.0f;
                        float f10 = ((float)i4 - f8) / 16.0f * 2.0f;
                        if (f9 < -1.0f) {
                            f9 += 2.0f;
                        }
                        if (f9 >= 1.0f) {
                            f9 -= 2.0f;
                        }
                        if (f10 < -1.0f) {
                            f10 += 2.0f;
                        }
                        if (f10 >= 1.0f) {
                            f10 -= 2.0f;
                        }
                        float f11 = f9 * f9 + f10 * f10;
                        float f12 = (float)Math.atan2(f10, f9) + ((float)i2 / 32.0f * (float)Math.PI * 2.0f - f11 * 10.0f + (float)(i6 * 2)) * (float)(i6 * 2 - 1);
                        f12 = (MathHelper.sin(f12) + 1.0f) / 2.0f;
                        f5 += (f12 /= f11 + 1.0f) * 0.5f;
                        ++i6;
                    }
                    i6 = (int)((f5 += random1.nextFloat() * 0.1f) * 100.0f + 155.0f);
                    int i13 = (int)(f5 * f5 * 200.0f + 55.0f);
                    int i14 = (int)(f5 * f5 * f5 * f5 * 255.0f);
                    int i15 = (int)(f5 * 100.0f + 155.0f);
                    int i16 = i4 * 16 + i3;
                    this.field_4226_h[i2][i16 * 4 + 0] = (byte)i13;
                    this.field_4226_h[i2][i16 * 4 + 1] = (byte)i14;
                    this.field_4226_h[i2][i16 * 4 + 2] = (byte)i6;
                    this.field_4226_h[i2][i16 * 4 + 3] = (byte)i15;
                    ++i4;
                }
                ++i3;
            }
            ++i2;
        }
    }

    @Override
    public void onTick() {
        ++this.field_4227_g;
        byte[] b1 = this.field_4226_h[this.field_4227_g & 0x1F];
        int i2 = 0;
        while (i2 < 256) {
            int i3 = b1[i2 * 4 + 0] & 0xFF;
            int i4 = b1[i2 * 4 + 1] & 0xFF;
            int i5 = b1[i2 * 4 + 2] & 0xFF;
            int i6 = b1[i2 * 4 + 3] & 0xFF;
            if (this.anaglyphEnabled) {
                int i7 = (i3 * 30 + i4 * 59 + i5 * 11) / 100;
                int i8 = (i3 * 30 + i4 * 70) / 100;
                int i9 = (i3 * 30 + i5 * 70) / 100;
                i3 = i7;
                i4 = i8;
                i5 = i9;
            }
            this.imageData[i2 * 4 + 0] = (byte)i3;
            this.imageData[i2 * 4 + 1] = (byte)i4;
            this.imageData[i2 * 4 + 2] = (byte)i5;
            this.imageData[i2 * 4 + 3] = (byte)i6;
            ++i2;
        }
    }
}

