/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TextureFX;

public class TextureLavaFlowFX
extends TextureFX {
    protected float[] field_1143_g = new float[256];
    protected float[] field_1142_h = new float[256];
    protected float[] field_1141_i = new float[256];
    protected float[] field_1140_j = new float[256];
    int field_1139_k = 0;

    public TextureLavaFlowFX() {
        super(Block.lavaMoving.blockIndexInTexture + 1);
        this.tileSize = 2;
    }

    @Override
    public void onTick() {
        int i9;
        int i8;
        int i7;
        int i6;
        int i5;
        float f3;
        int i2;
        ++this.field_1139_k;
        int i1 = 0;
        while (i1 < 16) {
            i2 = 0;
            while (i2 < 16) {
                f3 = 0.0f;
                int i4 = (int)(MathHelper.sin((float)i2 * (float)Math.PI * 2.0f / 16.0f) * 1.2f);
                i5 = (int)(MathHelper.sin((float)i1 * (float)Math.PI * 2.0f / 16.0f) * 1.2f);
                i6 = i1 - 1;
                while (i6 <= i1 + 1) {
                    i7 = i2 - 1;
                    while (i7 <= i2 + 1) {
                        i8 = i6 + i4 & 0xF;
                        i9 = i7 + i5 & 0xF;
                        f3 += this.field_1143_g[i8 + i9 * 16];
                        ++i7;
                    }
                    ++i6;
                }
                this.field_1142_h[i1 + i2 * 16] = f3 / 10.0f + (this.field_1141_i[(i1 + 0 & 0xF) + (i2 + 0 & 0xF) * 16] + this.field_1141_i[(i1 + 1 & 0xF) + (i2 + 0 & 0xF) * 16] + this.field_1141_i[(i1 + 1 & 0xF) + (i2 + 1 & 0xF) * 16] + this.field_1141_i[(i1 + 0 & 0xF) + (i2 + 1 & 0xF) * 16]) / 4.0f * 0.8f;
                int n = i1 + i2 * 16;
                this.field_1141_i[n] = this.field_1141_i[n] + this.field_1140_j[i1 + i2 * 16] * 0.01f;
                if (this.field_1141_i[i1 + i2 * 16] < 0.0f) {
                    this.field_1141_i[i1 + i2 * 16] = 0.0f;
                }
                int n2 = i1 + i2 * 16;
                this.field_1140_j[n2] = this.field_1140_j[n2] - 0.06f;
                if (Math.random() < 0.005) {
                    this.field_1140_j[i1 + i2 * 16] = 1.5f;
                }
                ++i2;
            }
            ++i1;
        }
        float[] f11 = this.field_1142_h;
        this.field_1142_h = this.field_1143_g;
        this.field_1143_g = f11;
        i2 = 0;
        while (i2 < 256) {
            f3 = this.field_1143_g[i2 - this.field_1139_k / 3 * 16 & 0xFF] * 2.0f;
            if (f3 > 1.0f) {
                f3 = 1.0f;
            }
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            i5 = (int)(f3 * 100.0f + 155.0f);
            i6 = (int)(f3 * f3 * 255.0f);
            i7 = (int)(f3 * f3 * f3 * f3 * 128.0f);
            if (this.anaglyphEnabled) {
                i8 = (i5 * 30 + i6 * 59 + i7 * 11) / 100;
                i9 = (i5 * 30 + i6 * 70) / 100;
                int i10 = (i5 * 30 + i7 * 70) / 100;
                i5 = i8;
                i6 = i9;
                i7 = i10;
            }
            this.imageData[i2 * 4 + 0] = (byte)i5;
            this.imageData[i2 * 4 + 1] = (byte)i6;
            this.imageData[i2 * 4 + 2] = (byte)i7;
            this.imageData[i2 * 4 + 3] = -1;
            ++i2;
        }
    }
}

