/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EnumArt;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderPainting
extends Render {
    private Random rand = new Random();

    public void func_158_a(EntityPainting entityPainting1, double d2, double d4, double d6, float f8, float f9) {
        this.rand.setSeed(187L);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glRotatef((float)f8, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glEnable((int)32826);
        this.loadTexture("/art/kz.png");
        EnumArt enumArt10 = entityPainting1.art;
        float f11 = 0.0625f;
        GL11.glScalef((float)f11, (float)f11, (float)f11);
        this.func_159_a(entityPainting1, enumArt10.sizeX, enumArt10.sizeY, enumArt10.offsetX, enumArt10.offsetY);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    private void func_159_a(EntityPainting entityPainting1, int i2, int i3, int i4, int i5) {
        float f6 = (float)(-i2) / 2.0f;
        float f7 = (float)(-i3) / 2.0f;
        float f8 = -0.5f;
        float f9 = 0.5f;
        int i10 = 0;
        while (i10 < i2 / 16) {
            int i11 = 0;
            while (i11 < i3 / 16) {
                float f12 = f6 + (float)((i10 + 1) * 16);
                float f13 = f6 + (float)(i10 * 16);
                float f14 = f7 + (float)((i11 + 1) * 16);
                float f15 = f7 + (float)(i11 * 16);
                this.func_160_a(entityPainting1, (f12 + f13) / 2.0f, (f14 + f15) / 2.0f);
                float f16 = (float)(i4 + i2 - i10 * 16) / 256.0f;
                float f17 = (float)(i4 + i2 - (i10 + 1) * 16) / 256.0f;
                float f18 = (float)(i5 + i3 - i11 * 16) / 256.0f;
                float f19 = (float)(i5 + i3 - (i11 + 1) * 16) / 256.0f;
                float f20 = 0.75f;
                float f21 = 0.8125f;
                float f22 = 0.0f;
                float f23 = 0.0625f;
                float f24 = 0.75f;
                float f25 = 0.8125f;
                float f26 = 0.001953125f;
                float f27 = 0.001953125f;
                float f28 = 0.7519531f;
                float f29 = 0.7519531f;
                float f30 = 0.0f;
                float f31 = 0.0625f;
                Tessellator tessellator32 = Tessellator.instance;
                tessellator32.startDrawingQuads();
                tessellator32.setNormal(0.0f, 0.0f, -1.0f);
                tessellator32.addVertexWithUV(f12, f15, f8, f17, f18);
                tessellator32.addVertexWithUV(f13, f15, f8, f16, f18);
                tessellator32.addVertexWithUV(f13, f14, f8, f16, f19);
                tessellator32.addVertexWithUV(f12, f14, f8, f17, f19);
                tessellator32.setNormal(0.0f, 0.0f, 1.0f);
                tessellator32.addVertexWithUV(f12, f14, f9, f20, f22);
                tessellator32.addVertexWithUV(f13, f14, f9, f21, f22);
                tessellator32.addVertexWithUV(f13, f15, f9, f21, f23);
                tessellator32.addVertexWithUV(f12, f15, f9, f20, f23);
                tessellator32.setNormal(0.0f, -1.0f, 0.0f);
                tessellator32.addVertexWithUV(f12, f14, f8, f24, f26);
                tessellator32.addVertexWithUV(f13, f14, f8, f25, f26);
                tessellator32.addVertexWithUV(f13, f14, f9, f25, f27);
                tessellator32.addVertexWithUV(f12, f14, f9, f24, f27);
                tessellator32.setNormal(0.0f, 1.0f, 0.0f);
                tessellator32.addVertexWithUV(f12, f15, f9, f24, f26);
                tessellator32.addVertexWithUV(f13, f15, f9, f25, f26);
                tessellator32.addVertexWithUV(f13, f15, f8, f25, f27);
                tessellator32.addVertexWithUV(f12, f15, f8, f24, f27);
                tessellator32.setNormal(-1.0f, 0.0f, 0.0f);
                tessellator32.addVertexWithUV(f12, f14, f9, f29, f30);
                tessellator32.addVertexWithUV(f12, f15, f9, f29, f31);
                tessellator32.addVertexWithUV(f12, f15, f8, f28, f31);
                tessellator32.addVertexWithUV(f12, f14, f8, f28, f30);
                tessellator32.setNormal(1.0f, 0.0f, 0.0f);
                tessellator32.addVertexWithUV(f13, f14, f8, f29, f30);
                tessellator32.addVertexWithUV(f13, f15, f8, f29, f31);
                tessellator32.addVertexWithUV(f13, f15, f9, f28, f31);
                tessellator32.addVertexWithUV(f13, f14, f9, f28, f30);
                tessellator32.draw();
                ++i11;
            }
            ++i10;
        }
    }

    private void func_160_a(EntityPainting entityPainting1, float f2, float f3) {
        int i4 = MathHelper.floor_double(entityPainting1.posX);
        int i5 = MathHelper.floor_double(entityPainting1.posY + (double)(f3 / 16.0f));
        int i6 = MathHelper.floor_double(entityPainting1.posZ);
        if (entityPainting1.direction == 0) {
            i4 = MathHelper.floor_double(entityPainting1.posX + (double)(f2 / 16.0f));
        }
        if (entityPainting1.direction == 1) {
            i6 = MathHelper.floor_double(entityPainting1.posZ - (double)(f2 / 16.0f));
        }
        if (entityPainting1.direction == 2) {
            i4 = MathHelper.floor_double(entityPainting1.posX - (double)(f2 / 16.0f));
        }
        if (entityPainting1.direction == 3) {
            i6 = MathHelper.floor_double(entityPainting1.posZ + (double)(f2 / 16.0f));
        }
        float f7 = this.renderManager.worldObj.getLightBrightness(i4, i5, i6);
        GL11.glColor3f((float)f7, (float)f7, (float)f7);
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_158_a((EntityPainting)entity1, d2, d4, d6, f8, f9);
    }
}

