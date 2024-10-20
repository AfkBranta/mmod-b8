/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import org.lwjgl.opengl.GL11;

public class RenderTNTPrimed
extends Render {
    private RenderBlocks field_196_d = new RenderBlocks();

    public RenderTNTPrimed() {
        this.shadowSize = 0.5f;
    }

    public void func_153_a(EntityTNTPrimed entityTNTPrimed1, double d2, double d4, double d6, float f8, float f9) {
        float f10;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        if ((float)entityTNTPrimed1.fuse - f9 + 1.0f < 10.0f) {
            f10 = 1.0f - ((float)entityTNTPrimed1.fuse - f9 + 1.0f) / 10.0f;
            if (f10 < 0.0f) {
                f10 = 0.0f;
            }
            if (f10 > 1.0f) {
                f10 = 1.0f;
            }
            f10 *= f10;
            f10 *= f10;
            float f11 = 1.0f + f10 * 0.3f;
            GL11.glScalef((float)f11, (float)f11, (float)f11);
        }
        f10 = (1.0f - ((float)entityTNTPrimed1.fuse - f9 + 1.0f) / 100.0f) * 0.8f;
        this.loadTexture("/terrain.png");
        this.field_196_d.renderBlockOnInventory(Block.tnt, 0);
        if (entityTNTPrimed1.fuse / 5 % 2 == 0) {
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)772);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)f10);
            this.field_196_d.renderBlockOnInventory(Block.tnt, 0);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_153_a((EntityTNTPrimed)entity1, d2, d4, d6, f8, f9);
    }
}

