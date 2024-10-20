/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelMinecart
extends ModelBase {
    public ModelRenderer[] sideModels = new ModelRenderer[7];

    public ModelMinecart() {
        this.sideModels[0] = new ModelRenderer(0, 10);
        this.sideModels[1] = new ModelRenderer(0, 0);
        this.sideModels[2] = new ModelRenderer(0, 0);
        this.sideModels[3] = new ModelRenderer(0, 0);
        this.sideModels[4] = new ModelRenderer(0, 0);
        this.sideModels[5] = new ModelRenderer(44, 10);
        int b1 = 20;
        int b2 = 8;
        int b3 = 16;
        int b4 = 4;
        this.sideModels[0].addBox(-b1 / 2, -b3 / 2, -1.0f, b1, b3, 2, 0.0f);
        this.sideModels[0].setPosition(0.0f, 0 + b4, 0.0f);
        this.sideModels[5].addBox(-b1 / 2 + 1, -b3 / 2 + 1, -1.0f, b1 - 2, b3 - 2, 1, 0.0f);
        this.sideModels[5].setPosition(0.0f, 0 + b4, 0.0f);
        this.sideModels[1].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.sideModels[1].setPosition(-b1 / 2 + 1, 0 + b4, 0.0f);
        this.sideModels[2].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.sideModels[2].setPosition(b1 / 2 - 1, 0 + b4, 0.0f);
        this.sideModels[3].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.sideModels[3].setPosition(0.0f, 0 + b4, -b3 / 2 + 1);
        this.sideModels[4].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.sideModels[4].setPosition(0.0f, 0 + b4, b3 / 2 - 1);
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = (float)Math.PI;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.sideModels[5].offsetY = 4.0f - f3;
        int i7 = 0;
        while (i7 < 6) {
            this.sideModels[i7].render(f6);
            ++i7;
        }
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
    }
}

