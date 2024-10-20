/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelQuadraped
extends ModelBase {
    public ModelRenderer head = new ModelRenderer(0, 0);
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public ModelQuadraped(int i1, float f2) {
        this.head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, f2);
        this.head.setPosition(0.0f, 18 - i1, -6.0f);
        this.body = new ModelRenderer(28, 8);
        this.body.addBox(-5.0f, -10.0f, -7.0f, 10, 16, 8, f2);
        this.body.setPosition(0.0f, 17 - i1, 2.0f);
        this.leg1 = new ModelRenderer(0, 16);
        this.leg1.addBox(-2.0f, 0.0f, -2.0f, 4, i1, 4, f2);
        this.leg1.setPosition(-3.0f, 24 - i1, 7.0f);
        this.leg2 = new ModelRenderer(0, 16);
        this.leg2.addBox(-2.0f, 0.0f, -2.0f, 4, i1, 4, f2);
        this.leg2.setPosition(3.0f, 24 - i1, 7.0f);
        this.leg3 = new ModelRenderer(0, 16);
        this.leg3.addBox(-2.0f, 0.0f, -2.0f, 4, i1, 4, f2);
        this.leg3.setPosition(-3.0f, 24 - i1, -5.0f);
        this.leg4 = new ModelRenderer(0, 16);
        this.leg4.addBox(-2.0f, 0.0f, -2.0f, 4, i1, 4, f2);
        this.leg4.setPosition(3.0f, 24 - i1, -5.0f);
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.head.render(f6);
        this.body.render(f6);
        this.leg1.render(f6);
        this.leg2.render(f6);
        this.leg3.render(f6);
        this.leg4.render(f6);
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.head.rotateAngleX = -(f5 / 57.295776f);
        this.head.rotateAngleY = f4 / 57.295776f;
        this.body.rotateAngleX = 1.5707964f;
        this.leg1.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
        this.leg2.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.leg3.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.leg4.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
    }
}

