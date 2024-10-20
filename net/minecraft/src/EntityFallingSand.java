/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityFallingSand
extends Entity {
    public int blockID;
    public int fallTime = 0;

    public EntityFallingSand(World world1) {
        super(world1);
    }

    public EntityFallingSand(World world1, double d2, double d4, double d6, int i8) {
        super(world1);
        this.blockID = i8;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(d2, d4, d6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d2;
        this.prevPosY = d4;
        this.prevPosZ = d6;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        if (this.blockID == 0) {
            this.setEntityDead();
        } else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY -= (double)0.04f;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.98f;
            this.motionY *= (double)0.98f;
            this.motionZ *= (double)0.98f;
            int i1 = MathHelper.floor_double(this.posX);
            int i2 = MathHelper.floor_double(this.posY);
            int i3 = MathHelper.floor_double(this.posZ);
            if (this.worldObj.getBlockId(i1, i2, i3) == this.blockID) {
                this.worldObj.setBlockWithNotify(i1, i2, i3, 0);
            }
            if (this.onGround) {
                this.motionX *= (double)0.7f;
                this.motionZ *= (double)0.7f;
                this.motionY *= -0.5;
                this.setEntityDead();
                if (!(this.worldObj.canBlockBePlacedAt(this.blockID, i1, i2, i3, true) && this.worldObj.setBlockWithNotify(i1, i2, i3, this.blockID) || this.worldObj.multiplayerWorld)) {
                    this.dropItem(this.blockID, 1);
                }
            } else if (this.fallTime > 100 && !this.worldObj.multiplayerWorld) {
                this.dropItem(this.blockID, 1);
                this.setEntityDead();
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setByte("Tile", (byte)this.blockID);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.blockID = nBTTagCompound1.getByte("Tile") & 0xFF;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public World func_465_i() {
        return this.worldObj;
    }
}

