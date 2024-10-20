/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelSquid
extends ModelBase {
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles = new ModelRenderer[8];

    public ModelSquid() {
        int b1 = -16;
        this.squidBody = new ModelRenderer(0, 0);
        this.squidBody.addBox(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        this.squidBody.offsetY += (float)(24 + b1);
        int i2 = 0;
        while (i2 < this.squidTentacles.length) {
            this.squidTentacles[i2] = new ModelRenderer(48, 0);
            double d3 = (double)i2 * Math.PI * 2.0 / (double)this.squidTentacles.length;
            float f5 = (float)Math.cos(d3) * 5.0f;
            float f6 = (float)Math.sin(d3) * 5.0f;
            this.squidTentacles[i2].addBox(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.squidTentacles[i2].offsetX = f5;
            this.squidTentacles[i2].offsetZ = f6;
            this.squidTentacles[i2].offsetY = 31 + b1;
            d3 = (double)i2 * Math.PI * -2.0 / (double)this.squidTentacles.length + 1.5707963267948966;
            this.squidTentacles[i2].rotateAngleY = (float)d3;
            ++i2;
        }
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        int i7 = 0;
        while (i7 < this.squidTentacles.length) {
            this.squidTentacles[i7].rotateAngleX = f3;
            ++i7;
        }
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.squidBody.render(f6);
        int i7 = 0;
        while (i7 < this.squidTentacles.length) {
            this.squidTentacles[i7].render(f6);
            ++i7;
        }
    }
}

