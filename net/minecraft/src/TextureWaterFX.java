/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.TextureFX;

public class TextureWaterFX
extends TextureFX {
    protected float[] field_1158_g = new float[256];
    protected float[] field_1157_h = new float[256];
    protected float[] field_1156_i = new float[256];
    protected float[] field_1155_j = new float[256];
    private int tickCounter = 0;

    public TextureWaterFX() {
        super(Block.waterMoving.blockIndexInTexture);
    }

    @Override
    public void onTick() {
        int i6;
        int i5;
        float f3;
        int i2;
        ++this.tickCounter;
        int i1 = 0;
        while (i1 < 16) {
            i2 = 0;
            while (i2 < 16) {
                f3 = 0.0f;
                int i4 = i1 - 1;
                while (i4 <= i1 + 1) {
                    i5 = i4 & 0xF;
                    i6 = i2 & 0xF;
                    f3 += this.field_1158_g[i5 + i6 * 16];
                    ++i4;
                }
                this.field_1157_h[i1 + i2 * 16] = f3 / 3.3f + this.field_1156_i[i1 + i2 * 16] * 0.8f;
                ++i2;
            }
            ++i1;
        }
        i1 = 0;
        while (i1 < 16) {
            i2 = 0;
            while (i2 < 16) {
                int n = i1 + i2 * 16;
                this.field_1156_i[n] = this.field_1156_i[n] + this.field_1155_j[i1 + i2 * 16] * 0.05f;
                if (this.field_1156_i[i1 + i2 * 16] < 0.0f) {
                    this.field_1156_i[i1 + i2 * 16] = 0.0f;
                }
                int n2 = i1 + i2 * 16;
                this.field_1155_j[n2] = this.field_1155_j[n2] - 0.1f;
                if (Math.random() < 0.05) {
                    this.field_1155_j[i1 + i2 * 16] = 0.5f;
                }
                ++i2;
            }
            ++i1;
        }
        float[] f12 = this.field_1157_h;
        this.field_1157_h = this.field_1158_g;
        this.field_1158_g = f12;
        i2 = 0;
        while (i2 < 256) {
            f3 = this.field_1158_g[i2];
            if (f3 > 1.0f) {
                f3 = 1.0f;
            }
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            float f13 = f3 * f3;
            i5 = (int)(32.0f + f13 * 32.0f);
            i6 = (int)(50.0f + f13 * 64.0f);
            int i7 = 255;
            int i8 = (int)(146.0f + f13 * 50.0f);
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
            this.imageData[i2 * 4 + 3] = (byte)i8;
            ++i2;
        }
    }
}

