/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityTNTPrimed
extends Entity {
    public int fuse = 0;

    public EntityTNTPrimed(World world1) {
        super(world1);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
    }

    public EntityTNTPrimed(World world1, double d2, double d4, double d6) {
        this(world1);
        this.setPosition(d2, d4, d6);
        float f8 = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.motionX = -MathHelper.sin(f8 * (float)Math.PI / 180.0f) * 0.02f;
        this.motionY = 0.2f;
        this.motionZ = -MathHelper.cos(f8 * (float)Math.PI / 180.0f) * 0.02f;
        this.fuse = 80;
        this.prevPosX = d2;
        this.prevPosY = d4;
        this.prevPosZ = d6;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)0.04f;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.98f;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)0.98f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
            this.motionY *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.setEntityDead();
            this.explode();
        } else {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
    }

    private void explode() {
        float f1 = 4.0f;
        this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, f1);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setByte("Fuse", (byte)this.fuse);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.fuse = nBTTagCompound1.getByte("Fuse");
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }
}

