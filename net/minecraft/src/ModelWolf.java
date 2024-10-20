/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;
import org.lwjgl.opengl.GL11;

public class ModelWolf
extends ModelBase {
    public ModelRenderer wolfHeadMain;
    public ModelRenderer wolfBody;
    public ModelRenderer wolfLeg1;
    public ModelRenderer wolfLeg2;
    public ModelRenderer wolfLeg3;
    public ModelRenderer wolfLeg4;
    ModelRenderer wolfRightEar;
    ModelRenderer wolfLeftEar;
    ModelRenderer wolfSnout;
    ModelRenderer wolfTail;
    ModelRenderer wolfMane;

    public ModelWolf() {
        float f1 = 0.0f;
        float f2 = 13.5f;
        this.wolfHeadMain = new ModelRenderer(0, 0);
        this.wolfHeadMain.addBox(-3.0f, -3.0f, -2.0f, 6, 6, 4, f1);
        this.wolfHeadMain.setPosition(-1.0f, f2, -7.0f);
        this.wolfBody = new ModelRenderer(18, 14);
        this.wolfBody.addBox(-4.0f, -2.0f, -3.0f, 6, 9, 6, f1);
        this.wolfBody.setPosition(0.0f, 14.0f, 2.0f);
        this.wolfMane = new ModelRenderer(21, 0);
        this.wolfMane.addBox(-4.0f, -3.0f, -3.0f, 8, 6, 7, f1);
        this.wolfMane.setPosition(-1.0f, 14.0f, 2.0f);
        this.wolfLeg1 = new ModelRenderer(0, 18);
        this.wolfLeg1.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, f1);
        this.wolfLeg1.setPosition(-2.5f, 16.0f, 7.0f);
        this.wolfLeg2 = new ModelRenderer(0, 18);
        this.wolfLeg2.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, f1);
        this.wolfLeg2.setPosition(0.5f, 16.0f, 7.0f);
        this.wolfLeg3 = new ModelRenderer(0, 18);
        this.wolfLeg3.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, f1);
        this.wolfLeg3.setPosition(-2.5f, 16.0f, -4.0f);
        this.wolfLeg4 = new ModelRenderer(0, 18);
        this.wolfLeg4.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, f1);
        this.wolfLeg4.setPosition(0.5f, 16.0f, -4.0f);
        this.wolfTail = new ModelRenderer(9, 18);
        this.wolfTail.addBox(-1.0f, 0.0f, -1.0f, 2, 8, 2, f1);
        this.wolfTail.setPosition(-1.0f, 12.0f, 8.0f);
        this.wolfRightEar = new ModelRenderer(16, 14);
        this.wolfRightEar.addBox(-3.0f, -5.0f, 0.0f, 2, 2, 1, f1);
        this.wolfRightEar.setPosition(-1.0f, f2, -7.0f);
        this.wolfLeftEar = new ModelRenderer(16, 14);
        this.wolfLeftEar.addBox(1.0f, -5.0f, 0.0f, 2, 2, 1, f1);
        this.wolfLeftEar.setPosition(-1.0f, f2, -7.0f);
        this.wolfSnout = new ModelRenderer(0, 10);
        this.wolfSnout.addBox(-2.0f, 0.0f, -5.0f, 3, 3, 4, f1);
        this.wolfSnout.setPosition(-0.5f, f2, -7.0f);
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        super.render(f1, f2, f3, f4, f5, f6);
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.wolfHeadMain.func_25122_b(f6);
        this.wolfBody.render(f6);
        this.wolfLeg1.render(f6);
        this.wolfLeg2.render(f6);
        this.wolfLeg3.render(f6);
        this.wolfLeg4.render(f6);
        this.wolfRightEar.func_25122_b(f6);
        this.wolfLeftEar.func_25122_b(f6);
        this.wolfSnout.func_25122_b(f6);
        this.wolfTail.func_25122_b(f6);
        this.wolfMane.render(f6);
    }

    @Override
    public void func_25103_a(EntityLiving entityLiving1, float f2, float f3, float f4) {
        float f6;
        EntityWolf entityWolf5 = (EntityWolf)entityLiving1;
        this.wolfTail.rotateAngleY = entityWolf5.isWolfAngry() ? 0.0f : MathHelper.cos(f2 * 0.6662f) * 1.4f * f3;
        if (entityWolf5.isWolfSitting()) {
            this.wolfMane.setPosition(-1.0f, 16.0f, -3.0f);
            this.wolfMane.rotateAngleX = 1.2566371f;
            this.wolfMane.rotateAngleY = 0.0f;
            this.wolfBody.setPosition(0.0f, 18.0f, 0.0f);
            this.wolfBody.rotateAngleX = 0.7853982f;
            this.wolfTail.setPosition(-1.0f, 21.0f, 6.0f);
            this.wolfLeg1.setPosition(-2.5f, 22.0f, 2.0f);
            this.wolfLeg1.rotateAngleX = 4.712389f;
            this.wolfLeg2.setPosition(0.5f, 22.0f, 2.0f);
            this.wolfLeg2.rotateAngleX = 4.712389f;
            this.wolfLeg3.rotateAngleX = 5.811947f;
            this.wolfLeg3.setPosition(-2.49f, 17.0f, -4.0f);
            this.wolfLeg4.rotateAngleX = 5.811947f;
            this.wolfLeg4.setPosition(0.51f, 17.0f, -4.0f);
        } else {
            this.wolfBody.setPosition(0.0f, 14.0f, 2.0f);
            this.wolfBody.rotateAngleX = 1.5707964f;
            this.wolfMane.setPosition(-1.0f, 14.0f, -3.0f);
            this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
            this.wolfTail.setPosition(-1.0f, 12.0f, 8.0f);
            this.wolfLeg1.setPosition(-2.5f, 16.0f, 7.0f);
            this.wolfLeg2.setPosition(0.5f, 16.0f, 7.0f);
            this.wolfLeg3.setPosition(-2.5f, 16.0f, -4.0f);
            this.wolfLeg4.setPosition(0.5f, 16.0f, -4.0f);
            this.wolfLeg1.rotateAngleX = MathHelper.cos(f2 * 0.6662f) * 1.4f * f3;
            this.wolfLeg2.rotateAngleX = MathHelper.cos(f2 * 0.6662f + (float)Math.PI) * 1.4f * f3;
            this.wolfLeg3.rotateAngleX = MathHelper.cos(f2 * 0.6662f + (float)Math.PI) * 1.4f * f3;
            this.wolfLeg4.rotateAngleX = MathHelper.cos(f2 * 0.6662f) * 1.4f * f3;
        }
        this.wolfHeadMain.rotateAngleZ = f6 = entityWolf5.func_25033_c(f4) + entityWolf5.func_25042_a(f4, 0.0f);
        this.wolfRightEar.rotateAngleZ = f6;
        this.wolfLeftEar.rotateAngleZ = f6;
        this.wolfSnout.rotateAngleZ = f6;
        this.wolfMane.rotateAngleZ = entityWolf5.func_25042_a(f4, -0.08f);
        this.wolfBody.rotateAngleZ = entityWolf5.func_25042_a(f4, -0.16f);
        this.wolfTail.rotateAngleZ = entityWolf5.func_25042_a(f4, -0.2f);
        if (entityWolf5.func_25039_v()) {
            float f7 = entityWolf5.getEntityBrightness(f4) * entityWolf5.func_25043_b_(f4);
            GL11.glColor3f((float)f7, (float)f7, (float)f7);
        }
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        super.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.wolfHeadMain.rotateAngleX = -(f5 / 57.295776f);
        this.wolfRightEar.rotateAngleY = this.wolfHeadMain.rotateAngleY = f4 / 57.295776f;
        this.wolfRightEar.rotateAngleX = this.wolfHeadMain.rotateAngleX;
        this.wolfLeftEar.rotateAngleY = this.wolfHeadMain.rotateAngleY;
        this.wolfLeftEar.rotateAngleX = this.wolfHeadMain.rotateAngleX;
        this.wolfSnout.rotateAngleY = this.wolfHeadMain.rotateAngleY;
        this.wolfSnout.rotateAngleX = this.wolfHeadMain.rotateAngleX;
        this.wolfTail.rotateAngleX = f3;
    }
}

