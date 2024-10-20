/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelBiped
extends ModelBase {
    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedEars;
    public ModelRenderer bipedCloak = new ModelRenderer(0, 0);
    public boolean field_1279_h = false;
    public boolean field_1278_i = false;
    public boolean isSneak = false;

    public ModelBiped() {
        this(0.0f);
    }

    public ModelBiped(float f1) {
        this(f1, 0.0f);
    }

    public ModelBiped(float f1, float f2) {
        this.bipedCloak.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, f1);
        this.bipedEars = new ModelRenderer(24, 0);
        this.bipedEars.addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, f1);
        this.bipedHead = new ModelRenderer(0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f1);
        this.bipedHead.setPosition(0.0f, 0.0f + f2, 0.0f);
        this.bipedHeadwear = new ModelRenderer(32, 0);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f1 + 0.5f);
        this.bipedHeadwear.setPosition(0.0f, 0.0f + f2, 0.0f);
        this.bipedBody = new ModelRenderer(16, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f1);
        this.bipedBody.setPosition(0.0f, 0.0f + f2, 0.0f);
        this.bipedRightArm = new ModelRenderer(40, 16);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f1);
        this.bipedRightArm.setPosition(-5.0f, 2.0f + f2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f1);
        this.bipedLeftArm.setPosition(5.0f, 2.0f + f2, 0.0f);
        this.bipedRightLeg = new ModelRenderer(0, 16);
        this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f1);
        this.bipedRightLeg.setPosition(-2.0f, 12.0f + f2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, f1);
        this.bipedLeftLeg.setPosition(2.0f, 12.0f + f2, 0.0f);
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.bipedHead.render(f6);
        this.bipedBody.render(f6);
        this.bipedRightArm.render(f6);
        this.bipedLeftArm.render(f6);
        this.bipedRightLeg.render(f6);
        this.bipedLeftLeg.render(f6);
        this.bipedHeadwear.render(f6);
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.bipedHead.rotateAngleY = f4 / 57.295776f;
        this.bipedHead.rotateAngleX = f5 / 57.295776f;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 2.0f * f2 * 0.5f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 2.0f * f2 * 0.5f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662f) * 1.4f * f2;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f1 * 0.6662f + (float)Math.PI) * 1.4f * f2;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        if (this.isRiding) {
            this.bipedRightArm.rotateAngleX += -0.62831855f;
            this.bipedLeftArm.rotateAngleX += -0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.2566371f;
            this.bipedLeftLeg.rotateAngleX = -1.2566371f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        if (this.field_1279_h) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f;
        }
        if (this.field_1278_i) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f;
        }
        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedLeftArm.rotateAngleY = 0.0f;
        if (this.onGround > -9990.0f) {
            float f7 = this.onGround;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI * 2.0f) * 0.2f;
            this.bipedRightArm.offsetZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.offsetX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.offsetZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.offsetX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f7 = 1.0f - this.onGround;
            f7 *= f7;
            f7 *= f7;
            f7 = 1.0f - f7;
            float f8 = MathHelper.sin(f7 * (float)Math.PI);
            float f9 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX - ((double)f8 * 1.2 + (double)f9));
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4f;
        }
        if (this.isSneak) {
            this.bipedBody.rotateAngleX = 0.5f;
            this.bipedRightLeg.rotateAngleX -= 0.0f;
            this.bipedLeftLeg.rotateAngleX -= 0.0f;
            this.bipedRightArm.rotateAngleX += 0.4f;
            this.bipedLeftArm.rotateAngleX += 0.4f;
            this.bipedRightLeg.offsetZ = 4.0f;
            this.bipedLeftLeg.offsetZ = 4.0f;
            this.bipedRightLeg.offsetY = 9.0f;
            this.bipedLeftLeg.offsetY = 9.0f;
            this.bipedHead.offsetY = 1.0f;
        } else {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.offsetZ = 0.0f;
            this.bipedLeftLeg.offsetZ = 0.0f;
            this.bipedRightLeg.offsetY = 12.0f;
            this.bipedLeftLeg.offsetY = 12.0f;
            this.bipedHead.offsetY = 0.0f;
        }
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(f3 * 0.09f) * 0.05f + 0.05f;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(f3 * 0.09f) * 0.05f + 0.05f;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(f3 * 0.067f) * 0.05f;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f3 * 0.067f) * 0.05f;
    }

    public void renderEars(float f1) {
        this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedEars.offsetX = 0.0f;
        this.bipedEars.offsetY = 0.0f;
        this.bipedEars.render(f1);
    }

    public void renderCloak(float f1) {
        this.bipedCloak.render(f1);
    }
}

