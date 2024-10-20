/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.EntityFlying;
import net.minecraft.src.IMobs;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityGhast
extends EntityFlying
implements IMobs {
    public int courseChangeCooldown = 0;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity = null;
    private int aggroCooldown = 0;
    public int prevAttackCounter = 0;
    public int attackCounter = 0;

    public EntityGhast(World world1) {
        super(world1);
        this.texture = "/mob/ghast.png";
        this.setSize(4.0f, 4.0f);
        this.isImmuneToFire = true;
    }

    @Override
    protected void updatePlayerActionState() {
        if (this.worldObj.difficultySetting == 0) {
            this.setEntityDead();
        }
        this.prevAttackCounter = this.attackCounter;
        double d1 = this.waypointX - this.posX;
        double d3 = this.waypointY - this.posY;
        double d5 = this.waypointZ - this.posZ;
        double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
        if (d7 < 1.0 || d7 > 60.0) {
            this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f);
        }
        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d7)) {
                this.motionX += d1 / d7 * 0.1;
                this.motionY += d3 / d7 * 0.1;
                this.motionZ += d5 / d7 * 0.1;
            } else {
                this.waypointX = this.posX;
                this.waypointY = this.posY;
                this.waypointZ = this.posZ;
            }
        }
        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }
        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.worldObj.getClosestPlayerToEntity(this, 100.0);
            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }
        }
        double d9 = 64.0;
        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < d9 * d9) {
            double d11 = this.targetedEntity.posX - this.posX;
            double d13 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0f) - (this.posY + (double)(this.height / 2.0f));
            double d15 = this.targetedEntity.posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(d11, d15)) * 180.0f / (float)Math.PI;
            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                }
                ++this.attackCounter;
                if (this.attackCounter == 20) {
                    this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                    EntityFireball entityFireball17 = new EntityFireball(this.worldObj, this, d11, d13, d15);
                    double d18 = 4.0;
                    Vec3D vec3D20 = this.getLook(1.0f);
                    entityFireball17.posX = this.posX + vec3D20.xCoord * d18;
                    entityFireball17.posY = this.posY + (double)(this.height / 2.0f) + 0.5;
                    entityFireball17.posZ = this.posZ + vec3D20.zCoord * d18;
                    this.worldObj.entityJoinedWorld(entityFireball17);
                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0f / (float)Math.PI;
            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
        this.texture = this.attackCounter > 10 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
    }

    private boolean isCourseTraversable(double d1, double d3, double d5, double d7) {
        double d9 = (this.waypointX - this.posX) / d7;
        double d11 = (this.waypointY - this.posY) / d7;
        double d13 = (this.waypointZ - this.posZ) / d7;
        AxisAlignedBB axisAlignedBB15 = this.boundingBox.copy();
        int i16 = 1;
        while ((double)i16 < d7) {
            axisAlignedBB15.offset(d9, d11, d13);
            if (this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB15).size() > 0) {
                return false;
            }
            ++i16;
        }
        return true;
    }

    @Override
    protected String getLivingSound() {
        return "mob.ghast.moan";
    }

    @Override
    protected String getHurtSound() {
        return "mob.ghast.scream";
    }

    @Override
    protected String getDeathSound() {
        return "mob.ghast.death";
    }

    @Override
    protected int getDropItemId() {
        return Item.gunpowder.shiftedIndex;
    }

    @Override
    protected float getSoundVolume() {
        return 10.0f;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.difficultySetting > 0;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }
}

