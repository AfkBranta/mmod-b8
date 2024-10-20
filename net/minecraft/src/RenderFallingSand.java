/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class RenderFallingSand
extends Render {
    private RenderBlocks field_197_d = new RenderBlocks();

    public RenderFallingSand() {
        this.shadowSize = 0.5f;
    }

    public void func_156_a(EntityFallingSand entityFallingSand1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        this.loadTexture("/terrain.png");
        Block block10 = Block.blocksList[entityFallingSand1.blockID];
        World world11 = entityFallingSand1.func_465_i();
        GL11.glDisable((int)2896);
        this.field_197_d.renderBlockFallingSand(block10, world11, MathHelper.floor_double(entityFallingSand1.posX), MathHelper.floor_double(entityFallingSand1.posY), MathHelper.floor_double(entityFallingSand1.posZ));
        GL11.glEnable((int)2896);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_156_a((EntityFallingSand)entity1, d2, d4, d6, f8, f9);
    }
}

