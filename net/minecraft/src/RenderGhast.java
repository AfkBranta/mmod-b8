/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelGhast;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderGhast
extends RenderLiving {
    public RenderGhast() {
        super(new ModelGhast(), 0.5f);
    }

    protected void func_4014_a(EntityGhast entityGhast1, float f2) {
        float f4 = ((float)entityGhast1.prevAttackCounter + (float)(entityGhast1.attackCounter - entityGhast1.prevAttackCounter) * f2) / 20.0f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        f4 = 1.0f / (f4 * f4 * f4 * f4 * f4 * 2.0f + 1.0f);
        float f5 = (8.0f + f4) / 2.0f;
        float f6 = (8.0f + 1.0f / f4) / 2.0f;
        GL11.glScalef((float)f6, (float)f5, (float)f6);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.func_4014_a((EntityGhast)entityLiving1, f2);
    }
}

