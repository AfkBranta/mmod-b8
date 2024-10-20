/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelCreeper
extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer field_1270_b;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public ModelCreeper() {
        float f1 = 0.0f;
        int b2 = 4;
        this.head = new ModelRenderer(0, 0);
        this.head.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f1);
        this.head.setPosition(0.0f, b2, 0.0f);
        this.field_1270_b = new ModelRenderer(32, 0);
        this.field_1270_b.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f1 + 0.5f);
        this.field_1270_b.setPosition(0.0f, b2, 0.0f);
        this.body = new ModelRenderer(16, 16);
        this.body.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f1);
        this.body.setPosition(0.0f, b2, 0.0f);
        this.leg1 = new ModelRenderer(0, 16);
        this.leg1.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, f1);
        this.leg1.setPosition(-2.0f, 12 + b2, 4.0f);
        this.leg2 = new ModelRenderer(0, 16);
        this.leg2.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, f1);
        this.leg2.setPosition(2.0f, 12 + b2, 4.0f);
        this.leg3 = new ModelRenderer(0, 16);
        this.leg3.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, f1);
        this.leg3.setPosition(-2.0f, 12 + b2, -4.0f);
        this.leg4 = new ModelRenderer(0, 16);
        this.leg4.addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, f1);
        this.leg4.setPosition(2.0f, 12 + b2, -4.0f);
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
        this.head.rotateAngleY = f4 / 57.295776f;
        this.head.rotateAngleX = f5 / 57.295776f;
        this.leg1.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
        this.leg2.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.leg3.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.leg4.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
    }
}

