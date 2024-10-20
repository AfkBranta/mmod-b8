/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBoat;
import net.minecraft.src.Render;
import org.lwjgl.opengl.GL11;

public class RenderBoat
extends Render {
    protected ModelBase modelBoat;

    public RenderBoat() {
        this.shadowSize = 0.5f;
        this.modelBoat = new ModelBoat();
    }

    public void func_157_a(EntityBoat entityBoat1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glRotatef((float)(180.0f - f8), (float)0.0f, (float)1.0f, (float)0.0f);
        float f10 = (float)entityBoat1.boatTimeSinceHit - f9;
        float f11 = (float)entityBoat1.boatCurrentDamage - f9;
        if (f11 < 0.0f) {
            f11 = 0.0f;
        }
        if (f10 > 0.0f) {
            GL11.glRotatef((float)(MathHelper.sin(f10) * f10 * f11 / 10.0f * (float)entityBoat1.boatRockDirection), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        this.loadTexture("/terrain.png");
        float f12 = 0.75f;
        GL11.glScalef((float)f12, (float)f12, (float)f12);
        GL11.glScalef((float)(1.0f / f12), (float)(1.0f / f12), (float)(1.0f / f12));
        this.loadTexture("/item/boat.png");
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        this.modelBoat.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_157_a((EntityBoat)entity1, d2, d4, d6, f8, f9);
    }
}

