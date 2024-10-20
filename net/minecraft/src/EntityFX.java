/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityFX
extends Entity {
    protected int particleTextureIndex;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected int particleAge = 0;
    protected int particleMaxAge = 0;
    protected float particleScale;
    protected float particleGravity;
    protected float particleRed;
    protected float particleGreen;
    protected float particleBlue;
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;

    public EntityFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12) {
        super(world1);
        this.setSize(0.2f, 0.2f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(d2, d4, d6);
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.motionX = d8 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.motionY = d10 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.motionZ = d12 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        float f14 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        float f15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double)f15 * (double)f14 * (double)0.4f;
        this.motionY = this.motionY / (double)f15 * (double)f14 * (double)0.4f + (double)0.1f;
        this.motionZ = this.motionZ / (double)f15 * (double)f14 * (double)0.4f;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = 0;
    }

    public EntityFX func_407_b(float f1) {
        this.motionX *= (double)f1;
        this.motionY = (this.motionY - (double)0.1f) * (double)f1 + (double)0.1f;
        this.motionZ *= (double)f1;
        return this;
    }

    public EntityFX func_405_d(float f1) {
        this.setSize(0.2f * f1, 0.2f * f1);
        this.particleScale *= f1;
        return this;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.motionY -= 0.04 * (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.98f;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)0.98f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }

    public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = (float)(this.particleTextureIndex % 16) / 16.0f;
        float f9 = f8 + 0.0624375f;
        float f10 = (float)(this.particleTextureIndex / 16) / 16.0f;
        float f11 = f10 + 0.0624375f;
        float f12 = 0.1f * this.particleScale;
        float f13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f2 - interpPosX);
        float f14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f2 - interpPosY);
        float f15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f2 - interpPosZ);
        float f16 = this.getEntityBrightness(f2);
        tessellator1.setColorOpaque_F(this.particleRed * f16, this.particleGreen * f16, this.particleBlue * f16);
        tessellator1.addVertexWithUV(f13 - f3 * f12 - f6 * f12, f14 - f4 * f12, f15 - f5 * f12 - f7 * f12, f9, f11);
        tessellator1.addVertexWithUV(f13 - f3 * f12 + f6 * f12, f14 + f4 * f12, f15 - f5 * f12 + f7 * f12, f9, f10);
        tessellator1.addVertexWithUV(f13 + f3 * f12 + f6 * f12, f14 + f4 * f12, f15 + f5 * f12 + f7 * f12, f8, f10);
        tessellator1.addVertexWithUV(f13 + f3 * f12 - f6 * f12, f14 - f4 * f12, f15 + f5 * f12 - f7 * f12, f8, f11);
    }

    public int getFXLayer() {
        return 0;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
    }
}

