/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySpider
extends EntityMobs {
    public EntitySpider(World world1) {
        super(world1);
        this.texture = "/mob/spider.png";
        this.setSize(1.4f, 0.9f);
        this.moveSpeed = 0.8f;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.75 - 0.5;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected Entity findPlayerToAttack() {
        float f1 = this.getEntityBrightness(1.0f);
        if (f1 < 0.5f) {
            double d2 = 16.0;
            return this.worldObj.getClosestPlayerToEntity(this, d2);
        }
        return null;
    }

    @Override
    protected String getLivingSound() {
        return "mob.spider";
    }

    @Override
    protected String getHurtSound() {
        return "mob.spider";
    }

    @Override
    protected String getDeathSound() {
        return "mob.spiderdeath";
    }

    @Override
    protected void attackEntity(Entity entity1, float f2) {
        float f3 = this.getEntityBrightness(1.0f);
        if (f3 > 0.5f && this.rand.nextInt(100) == 0) {
            this.playerToAttack = null;
        } else if (f2 > 2.0f && f2 < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double d4 = entity1.posX - this.posX;
                double d6 = entity1.posZ - this.posZ;
                float f8 = MathHelper.sqrt_double(d4 * d4 + d6 * d6);
                this.motionX = d4 / (double)f8 * 0.5 * (double)0.8f + this.motionX * (double)0.2f;
                this.motionZ = d6 / (double)f8 * 0.5 * (double)0.8f + this.motionZ * (double)0.2f;
                this.motionY = 0.4f;
            }
        } else {
            super.attackEntity(entity1, f2);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
    }

    @Override
    protected int getDropItemId() {
        return Item.silk.shiftedIndex;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }
}

