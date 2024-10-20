/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityArrow
extends Entity {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public int arrowShake = 0;
    public EntityLiving archer;
    private int timeTillDeath;
    private int flyTime = 0;

    public EntityArrow(World world1) {
        super(world1);
        this.setSize(0.5f, 0.5f);
    }

    public EntityArrow(World world1, double d2, double d4, double d6) {
        super(world1);
        this.setSize(0.5f, 0.5f);
        this.setPosition(d2, d4, d6);
        this.yOffset = 0.0f;
    }

    public EntityArrow(World world1, EntityLiving entityLiving2) {
        super(world1);
        this.archer = entityLiving2;
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(entityLiving2.posX, entityLiving2.posY + (double)entityLiving2.getEyeHeight(), entityLiving2.posZ, entityLiving2.rotationYaw, entityLiving2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI);
        this.setArrowHeading(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    @Override
    protected void entityInit() {
    }

    public void setArrowHeading(double d1, double d3, double d5, float f7, float f8) {
        float f9 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
        d1 /= (double)f9;
        d3 /= (double)f9;
        d5 /= (double)f9;
        d1 += this.rand.nextGaussian() * (double)0.0075f * (double)f8;
        d3 += this.rand.nextGaussian() * (double)0.0075f * (double)f8;
        d5 += this.rand.nextGaussian() * (double)0.0075f * (double)f8;
        this.motionX = d1 *= (double)f7;
        this.motionY = d3 *= (double)f7;
        this.motionZ = d5 *= (double)f7;
        float f10 = MathHelper.sqrt_double(d1 * d1 + d5 * d5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(d1, d5) * 180.0 / 3.1415927410125732);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(d3, f10) * 180.0 / 3.1415927410125732);
        this.timeTillDeath = 0;
    }

    @Override
    public void setVelocity(double d1, double d3, double d5) {
        this.motionX = d1;
        this.motionY = d3;
        this.motionZ = d5;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float f7 = MathHelper.sqrt_double(d1 * d1 + d5 * d5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(d1, d5) * 180.0 / 3.1415927410125732);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(d3, f7) * 180.0 / 3.1415927410125732);
        }
    }

    @Override
    public void onUpdate() {
        float f10;
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, f1) * 180.0 / 3.1415927410125732);
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            int i15 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            if (i15 == this.inTile) {
                ++this.timeTillDeath;
                if (this.timeTillDeath == 1200) {
                    this.setEntityDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
            this.timeTillDeath = 0;
            this.flyTime = 0;
        } else {
            ++this.flyTime;
        }
        Vec3D vec3D16 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        Vec3D vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingObjectPosition3 = this.worldObj.rayTraceBlocks(vec3D16, vec3D2);
        vec3D16 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingObjectPosition3 != null) {
            vec3D2 = Vec3D.createVector(movingObjectPosition3.hitVec.xCoord, movingObjectPosition3.hitVec.yCoord, movingObjectPosition3.hitVec.zCoord);
        }
        Entity entity4 = null;
        List list5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
        double d6 = 0.0;
        int i8 = 0;
        while (i8 < list5.size()) {
            double d13;
            AxisAlignedBB axisAlignedBB11;
            MovingObjectPosition movingObjectPosition12;
            Entity entity9 = (Entity)list5.get(i8);
            if (entity9.canBeCollidedWith() && (entity9 != this.archer || this.flyTime >= 5) && (movingObjectPosition12 = (axisAlignedBB11 = entity9.boundingBox.expand(f10 = 0.3f, f10, f10)).func_1169_a(vec3D16, vec3D2)) != null && ((d13 = vec3D16.distanceTo(movingObjectPosition12.hitVec)) < d6 || d6 == 0.0)) {
                entity4 = entity9;
                d6 = d13;
            }
            ++i8;
        }
        if (entity4 != null) {
            movingObjectPosition3 = new MovingObjectPosition(entity4);
        }
        if (movingObjectPosition3 != null) {
            if (movingObjectPosition3.entityHit != null) {
                if (movingObjectPosition3.entityHit.attackEntityFrom(this.archer, 4)) {
                    this.worldObj.playSoundAtEntity(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.setEntityDead();
                } else {
                    this.motionX *= (double)-0.1f;
                    this.motionY *= (double)-0.1f;
                    this.motionZ *= (double)-0.1f;
                    this.rotationYaw += 180.0f;
                    this.prevRotationYaw += 180.0f;
                    this.flyTime = 0;
                }
            } else {
                this.xTile = movingObjectPosition3.blockX;
                this.yTile = movingObjectPosition3.blockY;
                this.zTile = movingObjectPosition3.blockZ;
                this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                this.motionX = (float)(movingObjectPosition3.hitVec.xCoord - this.posX);
                this.motionY = (float)(movingObjectPosition3.hitVec.yCoord - this.posY);
                this.motionZ = (float)(movingObjectPosition3.hitVec.zCoord - this.posZ);
                float f17 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double)f17 * (double)0.05f;
                this.posY -= this.motionY / (double)f17 * (double)0.05f;
                this.posZ -= this.motionZ / (double)f17 * (double)0.05f;
                this.worldObj.playSoundAtEntity(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                this.inGround = true;
                this.arrowShake = 7;
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f17 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
        this.rotationPitch = (float)(Math.atan2(this.motionY, f17) * 180.0 / 3.1415927410125732);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        float f18 = 0.99f;
        f10 = 0.03f;
        if (this.handleWaterMovement()) {
            int i19 = 0;
            while (i19 < 4) {
                float f20 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f20, this.posY - this.motionY * (double)f20, this.posZ - this.motionZ * (double)f20, this.motionX, this.motionY, this.motionZ);
                ++i19;
            }
            f18 = 0.8f;
        }
        this.motionX *= (double)f18;
        this.motionY *= (double)f18;
        this.motionZ *= (double)f18;
        this.motionY -= (double)f10;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setShort("xTile", (short)this.xTile);
        nBTTagCompound1.setShort("yTile", (short)this.yTile);
        nBTTagCompound1.setShort("zTile", (short)this.zTile);
        nBTTagCompound1.setByte("inTile", (byte)this.inTile);
        nBTTagCompound1.setByte("shake", (byte)this.arrowShake);
        nBTTagCompound1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.xTile = nBTTagCompound1.getShort("xTile");
        this.yTile = nBTTagCompound1.getShort("yTile");
        this.zTile = nBTTagCompound1.getShort("zTile");
        this.inTile = nBTTagCompound1.getByte("inTile") & 0xFF;
        this.arrowShake = nBTTagCompound1.getByte("shake") & 0xFF;
        this.inGround = nBTTagCompound1.getByte("inGround") == 1;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityPlayer1) {
        if (!this.worldObj.multiplayerWorld && this.inGround && this.archer == entityPlayer1 && this.arrowShake <= 0 && entityPlayer1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            entityPlayer1.onItemPickup(this, 1);
            this.setEntityDead();
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }
}

