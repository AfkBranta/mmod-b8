/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.Item;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderFireball
extends Render {
    public void func_4012_a(EntityFireball entityFireball1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glEnable((int)32826);
        float f10 = 2.0f;
        GL11.glScalef((float)(f10 / 1.0f), (float)(f10 / 1.0f), (float)(f10 / 1.0f));
        int i11 = Item.snowball.getIconIndex(null);
        this.loadTexture("/gui/items.png");
        Tessellator tessellator12 = Tessellator.instance;
        float f13 = (float)(i11 % 16 * 16 + 0) / 256.0f;
        float f14 = (float)(i11 % 16 * 16 + 16) / 256.0f;
        float f15 = (float)(i11 / 16 * 16 + 0) / 256.0f;
        float f16 = (float)(i11 / 16 * 16 + 16) / 256.0f;
        float f17 = 1.0f;
        float f18 = 0.5f;
        float f19 = 0.25f;
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
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_4012_a((EntityFireball)entity1, d2, d4, d6, f8, f9);
    }
}

