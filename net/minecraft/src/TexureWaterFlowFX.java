/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.TextureFX;

public class TexureWaterFlowFX
extends TextureFX {
    protected float[] field_1138_g = new float[256];
    protected float[] field_1137_h = new float[256];
    protected float[] field_1136_i = new float[256];
    protected float[] field_1135_j = new float[256];
    private int field_1134_k = 0;

    public TexureWaterFlowFX() {
        super(Block.waterMoving.blockIndexInTexture + 1);
        this.tileSize = 2;
    }

    @Override
    public void onTick() {
        int i6;
        int i5;
        float f3;
        int i2;
        ++this.field_1134_k;
        int i1 = 0;
        while (i1 < 16) {
            i2 = 0;
            while (i2 < 16) {
                f3 = 0.0f;
                int i4 = i2 - 2;
                while (i4 <= i2) {
                    i5 = i1 & 0xF;
                    i6 = i4 & 0xF;
                    f3 += this.field_1138_g[i5 + i6 * 16];
                    ++i4;
                }
                this.field_1137_h[i1 + i2 * 16] = f3 / 3.2f + this.field_1136_i[i1 + i2 * 16] * 0.8f;
                ++i2;
            }
            ++i1;
        }
        i1 = 0;
        while (i1 < 16) {
            i2 = 0;
            while (i2 < 16) {
                int n = i1 + i2 * 16;
                this.field_1136_i[n] = this.field_1136_i[n] + this.field_1135_j[i1 + i2 * 16] * 0.05f;
                if (this.field_1136_i[i1 + i2 * 16] < 0.0f) {
                    this.field_1136_i[i1 + i2 * 16] = 0.0f;
                }
                int n2 = i1 + i2 * 16;
                this.field_1135_j[n2] = this.field_1135_j[n2] - 0.3f;
                if (Math.random() < 0.2) {
                    this.field_1135_j[i1 + i2 * 16] = 0.5f;
                }
                ++i2;
            }
            ++i1;
        }
        float[] f12 = this.field_1137_h;
        this.field_1137_h = this.field_1138_g;
        this.field_1138_g = f12;
        i2 = 0;
        while (i2 < 256) {
            f3 = this.field_1138_g[i2 - this.field_1134_k * 16 & 0xFF];
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

