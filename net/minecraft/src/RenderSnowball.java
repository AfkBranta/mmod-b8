/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderSnowball
extends Render {
    private int field_20003_a;

    public RenderSnowball(int i1) {
        this.field_20003_a = i1;
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glEnable((int)32826);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.loadTexture("/gui/items.png");
        Tessellator tessellator10 = Tessellator.instance;
        float f11 = (float)(this.field_20003_a % 16 * 16 + 0) / 256.0f;
        float f12 = (float)(this.field_20003_a % 16 * 16 + 16) / 256.0f;
        float f13 = (float)(this.field_20003_a / 16 * 16 + 0) / 256.0f;
        float f14 = (float)(this.field_20003_a / 16 * 16 + 16) / 256.0f;
        float f15 = 1.0f;
        float f16 = 0.5f;
        float f17 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        tessellator10.startDrawingQuads();
        tessellator10.setNormal(0.0f, 1.0f, 0.0f);
        tessellator10.addVertexWithUV(0.0f - f16, 0.0f - f17, 0.0, f11, f14);
        tessellator10.addVertexWithUV(f15 - f16, 0.0f - f17, 0.0, f12, f14);
        tessellator10.addVertexWithUV(f15 - f16, 1.0f - f17, 0.0, f12, f13);
        tessellator10.addVertexWithUV(0.0f - f16, 1.0f - f17, 0.0, f11, f13);
        tessellator10.draw();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }
}

