/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelChicken
extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;

    public ModelChicken() {
        int b1 = 16;
        this.head = new ModelRenderer(0, 0);
        this.head.addBox(-2.0f, -6.0f, -2.0f, 4, 6, 3, 0.0f);
        this.head.setPosition(0.0f, -1 + b1, -4.0f);
        this.bill = new ModelRenderer(14, 0);
        this.bill.addBox(-2.0f, -4.0f, -4.0f, 4, 2, 2, 0.0f);
        this.bill.setPosition(0.0f, -1 + b1, -4.0f);
        this.chin = new ModelRenderer(14, 4);
        this.chin.addBox(-1.0f, -2.0f, -3.0f, 2, 2, 2, 0.0f);
        this.chin.setPosition(0.0f, -1 + b1, -4.0f);
        this.body = new ModelRenderer(0, 9);
        this.body.addBox(-3.0f, -4.0f, -3.0f, 6, 8, 6, 0.0f);
        this.body.setPosition(0.0f, 0 + b1, 0.0f);
        this.rightLeg = new ModelRenderer(26, 0);
        this.rightLeg.addBox(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.rightLeg.setPosition(-2.0f, 3 + b1, 1.0f);
        this.leftLeg = new ModelRenderer(26, 0);
        this.leftLeg.addBox(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.leftLeg.setPosition(1.0f, 3 + b1, 1.0f);
        this.rightWing = new ModelRenderer(24, 13);
        this.rightWing.addBox(0.0f, 0.0f, -3.0f, 1, 4, 6);
        this.rightWing.setPosition(-4.0f, -3 + b1, 0.0f);
        this.leftWing = new ModelRenderer(24, 13);
        this.leftWing.addBox(-1.0f, 0.0f, -3.0f, 1, 4, 6);
        this.leftWing.setPosition(4.0f, -3 + b1, 0.0f);
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.head.render(f6);
        this.bill.render(f6);
        this.chin.render(f6);
        this.body.render(f6);
        this.rightLeg.render(f6);
        this.leftLeg.render(f6);
        this.rightWing.render(f6);
        this.leftWing.render(f6);
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.head.rotateAngleX = -(f5 / 57.295776f);
        this.head.rotateAngleY = f4 / 57.295776f;
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = 1.5707964f;
        this.rightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
        this.leftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.rightWing.rotateAngleZ = f3;
        this.leftWing.rotateAngleZ = -f3;
    }
}

