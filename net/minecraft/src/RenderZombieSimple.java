/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityZombieSimple;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderZombieSimple
extends RenderLiving {
    private float scale;

    public RenderZombieSimple(ModelBase modelBase1, float f2, float f3) {
        super(modelBase1, f2 * f3);
        this.scale = f3;
    }

    protected void preRenderScale(EntityZombieSimple entityZombieSimple1, float f2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.preRenderScale((EntityZombieSimple)entityLiving1, f2);
    }
}

