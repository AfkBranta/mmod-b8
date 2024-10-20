/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFish;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderFish
extends Render {
    public void func_4011_a(EntityFish entityFish1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glEnable((int)32826);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        int b10 = 1;
        int b11 = 2;
        this.loadTexture("/particles.png");
        Tessellator tessellator12 = Tessellator.instance;
        float f13 = (float)(b10 * 8 + 0) / 128.0f;
        float f14 = (float)(b10 * 8 + 8) / 128.0f;
        float f15 = (float)(b11 * 8 + 0) / 128.0f;
        float f16 = (float)(b11 * 8 + 8) / 128.0f;
        float f17 = 1.0f;
        float f18 = 0.5f;
        float f19 = 0.5f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        tessellator12.startDrawingQuads();
        tessellator12.setNormal(0.0f, 1.0f, 0.0f);
        tessellator12.addVertexWithUV(0.0f - f18, 0.0f - f19, 0.0, f13, f16);
        tessellator12.addVertexWithUV(f17 - f18, 0.0f - f19, 0.0, f14, f16);
        tessellator12.addVertexWithUV(f17 - f18, 1.0f - f19, 0.0, f14, f15);
        tessellator12.addVertexWithUV(0.0f - f18, 1.0f - f19, 0.0, f13, f15);
        tessellator12.draw();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
        if (entityFish1.angler != null) {
            float f20 = (entityFish1.angler.prevRotationYaw + (entityFish1.angler.rotationYaw - entityFish1.angler.prevRotationYaw) * f9) * (float)Math.PI / 180.0f;
            float f21 = (entityFish1.angler.prevRotationPitch + (entityFish1.angler.rotationPitch - entityFish1.angler.prevRotationPitch) * f9) * (float)Math.PI / 180.0f;
            double d22 = MathHelper.sin(f20);
            double d24 = MathHelper.cos(f20);
            double d26 = MathHelper.sin(f21);
            double d28 = MathHelper.cos(f21);
            double d30 = entityFish1.angler.prevPosX + (entityFish1.angler.posX - entityFish1.angler.prevPosX) * (double)f9 - d24 * 0.7 - d22 * 0.5 * d28;
            double d32 = entityFish1.angler.prevPosY + (entityFish1.angler.posY - entityFish1.angler.prevPosY) * (double)f9 - d26 * 0.5;
            double d34 = entityFish1.angler.prevPosZ + (entityFish1.angler.posZ - entityFish1.angler.prevPosZ) * (double)f9 - d22 * 0.7 + d24 * 0.5 * d28;
            if (this.renderManager.options.thirdPersonView) {
                f20 = (entityFish1.angler.prevRenderYawOffset + (entityFish1.angler.renderYawOffset - entityFish1.angler.prevRenderYawOffset) * f9) * (float)Math.PI / 180.0f;
                d22 = MathHelper.sin(f20);
                d24 = MathHelper.cos(f20);
                d30 = entityFish1.angler.prevPosX + (entityFish1.angler.posX - entityFish1.angler.prevPosX) * (double)f9 - d24 * 0.35 - d22 * 0.85;
                d32 = entityFish1.angler.prevPosY + (entityFish1.angler.posY - entityFish1.angler.prevPosY) * (double)f9 - 0.45;
                d34 = entityFish1.angler.prevPosZ + (entityFish1.angler.posZ - entityFish1.angler.prevPosZ) * (double)f9 - d22 * 0.35 + d24 * 0.85;
            }
            double d36 = entityFish1.prevPosX + (entityFish1.posX - entityFish1.prevPosX) * (double)f9;
            double d38 = entityFish1.prevPosY + (entityFish1.posY - entityFish1.prevPosY) * (double)f9 + 0.25;
            double d40 = entityFish1.prevPosZ + (entityFish1.posZ - entityFish1.prevPosZ) * (double)f9;
            double d42 = (float)(d30 - d36);
            double d44 = (float)(d32 - d38);
            double d46 = (float)(d34 - d40);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            tessellator12.startDrawing(3);
            tessellator12.setColorOpaque_I(0);
            int b48 = 16;
            int i49 = 0;
            while (i49 <= b48) {
                float f50 = (float)i49 / (float)b48;
                tessellator12.addVertex(d2 + d42 * (double)f50, d4 + d44 * (double)(f50 * f50 + f50) * 0.5 + 0.25, d6 + d46 * (double)f50);
                ++i49;
            }
            tessellator12.draw();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_4011_a((EntityFish)entity1, d2, d4, d6, f8, f9);
    }
}

