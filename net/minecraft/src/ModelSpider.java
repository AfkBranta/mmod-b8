/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelSpider
extends ModelBase {
    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;

    public ModelSpider() {
        float f1 = 0.0f;
        int b2 = 15;
        this.spiderHead = new ModelRenderer(32, 4);
        this.spiderHead.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, f1);
        this.spiderHead.setPosition(0.0f, 0 + b2, -3.0f);
        this.spiderNeck = new ModelRenderer(0, 0);
        this.spiderNeck.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, f1);
        this.spiderNeck.setPosition(0.0f, b2, 0.0f);
        this.spiderBody = new ModelRenderer(0, 12);
        this.spiderBody.addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, f1);
        this.spiderBody.setPosition(0.0f, 0 + b2, 9.0f);
        this.spiderLeg1 = new ModelRenderer(18, 0);
        this.spiderLeg1.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg1.setPosition(-4.0f, 0 + b2, 2.0f);
        this.spiderLeg2 = new ModelRenderer(18, 0);
        this.spiderLeg2.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg2.setPosition(4.0f, 0 + b2, 2.0f);
        this.spiderLeg3 = new ModelRenderer(18, 0);
        this.spiderLeg3.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg3.setPosition(-4.0f, 0 + b2, 1.0f);
        this.spiderLeg4 = new ModelRenderer(18, 0);
        this.spiderLeg4.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg4.setPosition(4.0f, 0 + b2, 1.0f);
        this.spiderLeg5 = new ModelRenderer(18, 0);
        this.spiderLeg5.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg5.setPosition(-4.0f, 0 + b2, 0.0f);
        this.spiderLeg6 = new ModelRenderer(18, 0);
        this.spiderLeg6.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg6.setPosition(4.0f, 0 + b2, 0.0f);
        this.spiderLeg7 = new ModelRenderer(18, 0);
        this.spiderLeg7.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg7.setPosition(-4.0f, 0 + b2, -1.0f);
        this.spiderLeg8 = new ModelRenderer(18, 0);
        this.spiderLeg8.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, f1);
        this.spiderLeg8.setPosition(4.0f, 0 + b2, -1.0f);
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.spiderHead.render(f6);
        this.spiderNeck.render(f6);
        this.spiderBody.render(f6);
        this.spiderLeg1.render(f6);
        this.spiderLeg2.render(f6);
        this.spiderLeg3.render(f6);
        this.spiderLeg4.render(f6);
        this.spiderLeg5.render(f6);
        this.spiderLeg6.render(f6);
        this.spiderLeg7.render(f6);
        this.spiderLeg8.render(f6);
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.spiderHead.rotateAngleY = f4 / 57.295776f;
        this.spiderHead.rotateAngleX = f5 / 57.295776f;
        float f7 = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = -f7;
        this.spiderLeg2.rotateAngleZ = f7;
        this.spiderLeg3.rotateAngleZ = -f7 * 0.74f;
        this.spiderLeg4.rotateAngleZ = f7 * 0.74f;
        this.spiderLeg5.rotateAngleZ = -f7 * 0.74f;
        this.spiderLeg6.rotateAngleZ = f7 * 0.74f;
        this.spiderLeg7.rotateAngleZ = -f7;
        this.spiderLeg8.rotateAngleZ = f7;
        float f8 = -0.0f;
        float f9 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = f9 * 2.0f + f8;
        this.spiderLeg2.rotateAngleY = -f9 * 2.0f - f8;
        this.spiderLeg3.rotateAngleY = f9 * 1.0f + f8;
        this.spiderLeg4.rotateAngleY = -f9 * 1.0f - f8;
        this.spiderLeg5.rotateAngleY = -f9 * 1.0f + f8;
        this.spiderLeg6.rotateAngleY = f9 * 1.0f - f8;
        this.spiderLeg7.rotateAngleY = -f9 * 2.0f + f8;
        this.spiderLeg8.rotateAngleY = f9 * 2.0f - f8;
        float f10 = -(MathHelper.cos(f1 * 0.6662f * 2.0f + 0.0f) * 0.4f) * f2;
        float f11 = -(MathHelper.cos(f1 * 0.6662f * 2.0f + (float)Math.PI) * 0.4f) * f2;
        float f12 = -(MathHelper.cos(f1 * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f2;
        float f13 = -(MathHelper.cos(f1 * 0.6662f * 2.0f + 4.712389f) * 0.4f) * f2;
        float f14 = Math.abs(MathHelper.sin(f1 * 0.6662f + 0.0f) * 0.4f) * f2;
        float f15 = Math.abs(MathHelper.sin(f1 * 0.6662f + (float)Math.PI) * 0.4f) * f2;
        float f16 = Math.abs(MathHelper.sin(f1 * 0.6662f + 1.5707964f) * 0.4f) * f2;
        float f17 = Math.abs(MathHelper.sin(f1 * 0.6662f + 4.712389f) * 0.4f) * f2;
        this.spiderLeg1.rotateAngleY += f10;
        this.spiderLeg2.rotateAngleY += -f10;
        this.spiderLeg3.rotateAngleY += f11;
        this.spiderLeg4.rotateAngleY += -f11;
        this.spiderLeg5.rotateAngleY += f12;
        this.spiderLeg6.rotateAngleY += -f12;
        this.spiderLeg7.rotateAngleY += f13;
        this.spiderLeg8.rotateAngleY += -f13;
        this.spiderLeg1.rotateAngleZ += f14;
        this.spiderLeg2.rotateAngleZ += -f14;
        this.spiderLeg3.rotateAngleZ += f15;
        this.spiderLeg4.rotateAngleZ += -f15;
        this.spiderLeg5.rotateAngleZ += f16;
        this.spiderLeg6.rotateAngleZ += -f16;
        this.spiderLeg7.rotateAngleZ += f17;
        this.spiderLeg8.rotateAngleZ += -f17;
    }
}

