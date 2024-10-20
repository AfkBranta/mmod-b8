/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IMobs;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityMobs
extends EntityCreature
implements IMobs {
    protected int attackStrength = 2;

    public EntityMobs(World world1) {
        super(world1);
        this.health = 20;
    }

    @Override
    public void onLivingUpdate() {
        float f1 = this.getEntityBrightness(1.0f);
        if (f1 > 0.5f) {
            this.field_9344_ag += 2;
        }
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.difficultySetting == 0) {
            this.setEntityDead();
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, 16.0);
        return entityPlayer1 != null && this.canEntityBeSeen(entityPlayer1) ? entityPlayer1 : null;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (super.attackEntityFrom(entity1, i2)) {
            if (this.riddenByEntity != entity1 && this.ridingEntity != entity1) {
                if (entity1 != this) {
                    this.playerToAttack = entity1;
                }
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void attackEntity(Entity entity1, float f2) {
        if (this.attackTime <= 0 && f2 < 2.0f && entity1.boundingBox.maxY > this.boundingBox.minY && entity1.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            entity1.attackEntityFrom(this, this.attackStrength);
        }
    }

    @Override
    protected float getBlockPathWeight(int i1, int i2, int i3) {
        return 0.5f - this.worldObj.getLightBrightness(i1, i2, i3);
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
    public boolean getCanSpawnHere() {
        int i3;
        int i2;
        int i1 = MathHelper.floor_double(this.posX);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i1, i2 = MathHelper.floor_double(this.boundingBox.minY), i3 = MathHelper.floor_double(this.posZ)) > this.rand.nextInt(32)) {
            return false;
        }
        int i4 = this.worldObj.getBlockLightValue(i1, i2, i3);
        return i4 <= this.rand.nextInt(8) && super.getCanSpawnHere();
    }
}

