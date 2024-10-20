/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelMinecart;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Vec3D;
import org.lwjgl.opengl.GL11;

public class RenderMinecart
extends Render {
    protected ModelBase modelMinecart;

    public RenderMinecart() {
        this.shadowSize = 0.5f;
        this.modelMinecart = new ModelMinecart();
    }

    public void func_152_a(EntityMinecart entityMinecart1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        double d10 = entityMinecart1.lastTickPosX + (entityMinecart1.posX - entityMinecart1.lastTickPosX) * (double)f9;
        double d12 = entityMinecart1.lastTickPosY + (entityMinecart1.posY - entityMinecart1.lastTickPosY) * (double)f9;
        double d14 = entityMinecart1.lastTickPosZ + (entityMinecart1.posZ - entityMinecart1.lastTickPosZ) * (double)f9;
        double d16 = 0.3f;
        Vec3D vec3D18 = entityMinecart1.func_514_g(d10, d12, d14);
        float f19 = entityMinecart1.prevRotationPitch + (entityMinecart1.rotationPitch - entityMinecart1.prevRotationPitch) * f9;
        if (vec3D18 != null) {
            Vec3D vec3D20 = entityMinecart1.func_515_a(d10, d12, d14, d16);
            Vec3D vec3D21 = entityMinecart1.func_515_a(d10, d12, d14, -d16);
            if (vec3D20 == null) {
                vec3D20 = vec3D18;
            }
            if (vec3D21 == null) {
                vec3D21 = vec3D18;
            }
            d2 += vec3D18.xCoord - d10;
            d4 += (vec3D20.yCoord + vec3D21.yCoord) / 2.0 - d12;
            d6 += vec3D18.zCoord - d14;
            Vec3D vec3D22 = vec3D21.addVector(-vec3D20.xCoord, -vec3D20.yCoord, -vec3D20.zCoord);
            if (vec3D22.lengthVector() != 0.0) {
                vec3D22 = vec3D22.normalize();
                f8 = (float)(Math.atan2(vec3D22.zCoord, vec3D22.xCoord) * 180.0 / Math.PI);
                f19 = (float)(Math.atan(vec3D22.yCoord) * 73.0);
            }
        }
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        GL11.glRotatef((float)(180.0f - f8), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-f19), (float)0.0f, (float)0.0f, (float)1.0f);
        float f23 = (float)entityMinecart1.minecartTimeSinceHit - f9;
        float f24 = (float)entityMinecart1.minecartCurrentDamage - f9;
        if (f24 < 0.0f) {
            f24 = 0.0f;
        }
        if (f23 > 0.0f) {
            GL11.glRotatef((float)(MathHelper.sin(f23) * f23 * f24 / 10.0f * (float)entityMinecart1.minecartRockDirection), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (entityMinecart1.minecartType != 0) {
            this.loadTexture("/terrain.png");
            float f25 = 0.75f;
            GL11.glScalef((float)f25, (float)f25, (float)f25);
            GL11.glTranslatef((float)0.0f, (float)0.3125f, (float)0.0f);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            if (entityMinecart1.minecartType == 1) {
                new RenderBlocks().renderBlockOnInventory(Block.crate, 0);
            } else if (entityMinecart1.minecartType == 2) {
                new RenderBlocks().renderBlockOnInventory(Block.stoneOvenIdle, 0);
            }
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.3125f, (float)0.0f);
            GL11.glScalef((float)(1.0f / f25), (float)(1.0f / f25), (float)(1.0f / f25));
        }
        this.loadTexture("/item/cart.png");
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        this.modelMinecart.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_152_a((EntityMinecart)entity1, d2, d4, d6, f8, f9);
    }
}

