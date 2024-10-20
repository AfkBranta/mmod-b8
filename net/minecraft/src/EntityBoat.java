/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityBoat
extends Entity {
    public int boatCurrentDamage = 0;
    public int boatTimeSinceHit = 0;
    public int boatRockDirection = 1;
    private int field_9394_d;
    private double field_9393_e;
    private double field_9392_f;
    private double field_9391_g;
    private double field_9390_h;
    private double field_9389_i;
    private double field_9388_j;
    private double field_9387_k;
    private double field_9386_l;

    public EntityBoat(World world1) {
        super(world1);
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity1) {
        return entity1.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityBoat(World world1, double d2, double d4, double d6) {
        this(world1);
        this.setPosition(d2, d4 + (double)this.yOffset, d6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d2;
        this.prevPosY = d4;
        this.prevPosZ = d6;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.0 - (double)0.3f;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (!this.worldObj.multiplayerWorld && !this.isDead) {
            this.boatRockDirection = -this.boatRockDirection;
            this.boatTimeSinceHit = 10;
            this.boatCurrentDamage += i2 * 10;
            this.setBeenAttacked();
            if (this.boatCurrentDamage > 40) {
                int i3 = 0;
                while (i3 < 3) {
                    this.dropItemWithOffset(Block.planks.blockID, 1, 0.0f);
                    ++i3;
                }
                i3 = 0;
                while (i3 < 2) {
                    this.dropItemWithOffset(Item.stick.shiftedIndex, 1, 0.0f);
                    ++i3;
                }
                this.setEntityDead();
            }
            return true;
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        this.boatRockDirection = -this.boatRockDirection;
        this.boatTimeSinceHit = 10;
        this.boatCurrentDamage += this.boatCurrentDamage * 10;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
        this.field_9393_e = d1;
        this.field_9392_f = d3;
        this.field_9391_g = d5;
        this.field_9390_h = f7;
        this.field_9389_i = f8;
        this.field_9394_d = i9 + 4;
        this.motionX = this.field_9388_j;
        this.motionY = this.field_9387_k;
        this.motionZ = this.field_9386_l;
    }

    @Override
    public void setVelocity(double d1, double d3, double d5) {
        this.field_9388_j = this.motionX = d1;
        this.field_9387_k = this.motionY = d3;
        this.field_9386_l = this.motionZ = d5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.boatTimeSinceHit > 0) {
            --this.boatTimeSinceHit;
        }
        if (this.boatCurrentDamage > 0) {
            --this.boatCurrentDamage;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        int b1 = 5;
        double d2 = 0.0;
        int i4 = 0;
        while (i4 < b1) {
            double d5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i4 + 0) / (double)b1 - 0.125;
            double d7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i4 + 1) / (double)b1 - 0.125;
            AxisAlignedBB axisAlignedBB9 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d5, this.boundingBox.minZ, this.boundingBox.maxX, d7, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(axisAlignedBB9, Material.water)) {
                d2 += 1.0 / (double)b1;
            }
            ++i4;
        }
        if (this.worldObj.multiplayerWorld) {
            if (this.field_9394_d > 0) {
                double d23 = this.posX + (this.field_9393_e - this.posX) / (double)this.field_9394_d;
                double d6 = this.posY + (this.field_9392_f - this.posY) / (double)this.field_9394_d;
                double d8 = this.posZ + (this.field_9391_g - this.posZ) / (double)this.field_9394_d;
                double d10 = this.field_9390_h - (double)this.rotationYaw;
                while (d10 < -180.0) {
                    d10 += 360.0;
                }
                while (d10 >= 180.0) {
                    d10 -= 360.0;
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.field_9394_d);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9389_i - (double)this.rotationPitch) / (double)this.field_9394_d);
                --this.field_9394_d;
                this.setPosition(d23, d6, d8);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                double d23 = this.posX + this.motionX;
                double d6 = this.posY + this.motionY;
                double d8 = this.posZ + this.motionZ;
                this.setPosition(d23, d6, d8);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= (double)0.99f;
                this.motionY *= (double)0.95f;
                this.motionZ *= (double)0.99f;
            }
        } else {
            double d12;
            double d10;
            double d6;
            double d23 = d2 * 2.0 - 1.0;
            this.motionY += (double)0.04f * d23;
            if (this.riddenByEntity != null) {
                this.motionX += this.riddenByEntity.motionX * 0.2;
                this.motionZ += this.riddenByEntity.motionZ * 0.2;
            }
            if (this.motionX < -(d6 = 0.4)) {
                this.motionX = -d6;
            }
            if (this.motionX > d6) {
                this.motionX = d6;
            }
            if (this.motionZ < -d6) {
                this.motionZ = -d6;
            }
            if (this.motionZ > d6) {
                this.motionZ = d6;
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d8 > 0.15) {
                d10 = Math.cos((double)this.rotationYaw * Math.PI / 180.0);
                d12 = Math.sin((double)this.rotationYaw * Math.PI / 180.0);
                int i14 = 0;
                while ((double)i14 < 1.0 + d8 * 60.0) {
                    double d21;
                    double d19;
                    double d15 = this.rand.nextFloat() * 2.0f - 1.0f;
                    double d17 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                    if (this.rand.nextBoolean()) {
                        d19 = this.posX - d10 * d15 * 0.8 + d12 * d17;
                        d21 = this.posZ - d12 * d15 * 0.8 - d10 * d17;
                        this.worldObj.spawnParticle("splash", d19, this.posY - 0.125, d21, this.motionX, this.motionY, this.motionZ);
                    } else {
                        d19 = this.posX + d10 + d12 * d15 * 0.7;
                        d21 = this.posZ + d12 - d10 * d15 * 0.7;
                        this.worldObj.spawnParticle("splash", d19, this.posY - 0.125, d21, this.motionX, this.motionY, this.motionZ);
                    }
                    ++i14;
                }
            }
            if (this.isCollidedHorizontally && d8 > 0.15) {
                if (!this.worldObj.multiplayerWorld) {
                    this.setEntityDead();
                    int i24 = 0;
                    while (i24 < 3) {
                        this.dropItemWithOffset(Block.planks.blockID, 1, 0.0f);
                        ++i24;
                    }
                    i24 = 0;
                    while (i24 < 2) {
                        this.dropItemWithOffset(Item.stick.shiftedIndex, 1, 0.0f);
                        ++i24;
                    }
                }
            } else {
                this.motionX *= (double)0.99f;
                this.motionY *= (double)0.95f;
                this.motionZ *= (double)0.99f;
            }
            this.rotationPitch = 0.0f;
            d10 = this.rotationYaw;
            d12 = this.prevPosX - this.posX;
            double d25 = this.prevPosZ - this.posZ;
            if (d12 * d12 + d25 * d25 > 0.001) {
                d10 = (float)(Math.atan2(d25, d12) * 180.0 / Math.PI);
            }
            double d16 = d10 - (double)this.rotationYaw;
            while (d16 >= 180.0) {
                d16 -= 360.0;
            }
            while (d16 < -180.0) {
                d16 += 360.0;
            }
            if (d16 > 20.0) {
                d16 = 20.0;
            }
            if (d16 < -20.0) {
                d16 = -20.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + d16);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            List list18 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
            if (list18 != null && list18.size() > 0) {
                int i26 = 0;
                while (i26 < list18.size()) {
                    Entity entity20 = (Entity)list18.get(i26);
                    if (entity20 != this.riddenByEntity && entity20.canBePushed() && entity20 instanceof EntityBoat) {
                        entity20.applyEntityCollision(this);
                    }
                    ++i26;
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                this.riddenByEntity = null;
            }
        }
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double d1 = Math.cos((double)this.rotationYaw * Math.PI / 180.0) * 0.4;
            double d3 = Math.sin((double)this.rotationYaw * Math.PI / 180.0) * 0.4;
            this.riddenByEntity.setPosition(this.posX + d1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d3);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public boolean interact(EntityPlayer entityPlayer1) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer1) {
            return true;
        }
        if (!this.worldObj.multiplayerWorld) {
            entityPlayer1.mountEntity(this);
        }
        return true;
    }
}

