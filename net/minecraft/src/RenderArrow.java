/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderArrow
extends Render {
    public void func_154_a(EntityArrow entityArrow1, double d2, double d4, double d6, float f8, float f9) {
        this.loadTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glRotatef((float)(entityArrow1.prevRotationYaw + (entityArrow1.rotationYaw - entityArrow1.prevRotationYaw) * f9 - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(entityArrow1.prevRotationPitch + (entityArrow1.rotationPitch - entityArrow1.prevRotationPitch) * f9), (float)0.0f, (float)0.0f, (float)1.0f);
        Tessellator tessellator10 = Tessellator.instance;
        int b11 = 0;
        float f12 = 0.0f;
        float f13 = 0.5f;
        float f14 = (float)(0 + b11 * 10) / 32.0f;
        float f15 = (float)(5 + b11 * 10) / 32.0f;
        float f16 = 0.0f;
        float f17 = 0.15625f;
        float f18 = (float)(5 + b11 * 10) / 32.0f;
        float f19 = (float)(10 + b11 * 10) / 32.0f;
        float f20 = 0.05625f;
        GL11.glEnable((int)32826);
        float f21 = (float)entityArrow1.arrowShake - f9;
        if (f21 > 0.0f) {
            float f22 = -MathHelper.sin(f21 * 3.0f) * f21;
            GL11.glRotatef((float)f22, (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GL11.glRotatef((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)f20, (float)f20, (float)f20);
        GL11.glTranslatef((float)-4.0f, (float)0.0f, (float)0.0f);
        GL11.glNormal3f((float)f20, (float)0.0f, (float)0.0f);
        tessellator10.startDrawingQuads();
        tessellator10.addVertexWithUV(-7.0, -2.0, -2.0, f16, f18);
        tessellator10.addVertexWithUV(-7.0, -2.0, 2.0, f17, f18);
        tessellator10.addVertexWithUV(-7.0, 2.0, 2.0, f17, f19);
        tessellator10.addVertexWithUV(-7.0, 2.0, -2.0, f16, f19);
        tessellator10.draw();
        GL11.glNormal3f((float)(-f20), (float)0.0f, (float)0.0f);
        tessellator10.startDrawingQuads();
        tessellator10.addVertexWithUV(-7.0, 2.0, -2.0, f16, f18);
        tessellator10.addVertexWithUV(-7.0, 2.0, 2.0, f17, f18);
        tessellator10.addVertexWithUV(-7.0, -2.0, 2.0, f17, f19);
        tessellator10.addVertexWithUV(-7.0, -2.0, -2.0, f16, f19);
        tessellator10.draw();
        int i23 = 0;
        while (i23 < 4) {
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)f20);
            tessellator10.startDrawingQuads();
            tessellator10.addVertexWithUV(-8.0, -2.0, 0.0, f12, f14);
            tessellator10.addVertexWithUV(8.0, -2.0, 0.0, f13, f14);
            tessellator10.addVertexWithUV(8.0, 2.0, 0.0, f13, f15);
            tessellator10.addVertexWithUV(-8.0, 2.0, 0.0, f12, f15);
            tessellator10.draw();
            ++i23;
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_154_a((EntityArrow)entity1, d2, d4, d6, f8, f9);
    }
}

