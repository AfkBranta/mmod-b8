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

public class EntitySnowball
extends Entity {
    private int xTileSnowball = -1;
    private int yTileSnowball = -1;
    private int zTileSnowball = -1;
    private int inTileSnowball = 0;
    private boolean inGroundSnowball = false;
    public int shakeSnowball = 0;
    private EntityLiving thrower;
    private int field_810_h;
    private int field_809_i = 0;

    public EntitySnowball(World world1) {
        super(world1);
        this.setSize(0.25f, 0.25f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean isInRangeToRenderDist(double d1) {
        double d3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        return d1 < (d3 *= 64.0) * d3;
    }

    public EntitySnowball(World world1, EntityLiving entityLiving2) {
        super(world1);
        this.thrower = entityLiving2;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(entityLiving2.posX, entityLiving2.posY + (double)entityLiving2.getEyeHeight(), entityLiving2.posZ, entityLiving2.rotationYaw, entityLiving2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float f3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * f3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * f3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI) * f3;
        this.func_467_a(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    public EntitySnowball(World world1, double d2, double d4, double d6) {
        super(world1);
        this.field_810_h = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(d2, d4, d6);
        this.yOffset = 0.0f;
    }

    public void func_467_a(double d1, double d3, double d5, float f7, float f8) {
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
        this.field_810_h = 0;
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
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.shakeSnowball > 0) {
            --this.shakeSnowball;
        }
        if (this.inGroundSnowball) {
            int i1 = this.worldObj.getBlockId(this.xTileSnowball, this.yTileSnowball, this.zTileSnowball);
            if (i1 == this.inTileSnowball) {
                ++this.field_810_h;
                if (this.field_810_h == 1200) {
                    this.setEntityDead();
                }
                return;
            }
            this.inGroundSnowball = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
            this.field_810_h = 0;
            this.field_809_i = 0;
        } else {
            ++this.field_809_i;
        }
        Vec3D vec3D15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        Vec3D vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingObjectPosition3 = this.worldObj.rayTraceBlocks(vec3D15, vec3D2);
        vec3D15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingObjectPosition3 != null) {
            vec3D2 = Vec3D.createVector(movingObjectPosition3.hitVec.xCoord, movingObjectPosition3.hitVec.yCoord, movingObjectPosition3.hitVec.zCoord);
        }
        if (!this.worldObj.multiplayerWorld) {
            Entity entity4 = null;
            List list5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double d6 = 0.0;
            int i8 = 0;
            while (i8 < list5.size()) {
                double d13;
                float f10;
                AxisAlignedBB axisAlignedBB11;
                MovingObjectPosition movingObjectPosition12;
                Entity entity9 = (Entity)list5.get(i8);
                if (entity9.canBeCollidedWith() && (entity9 != this.thrower || this.field_809_i >= 5) && (movingObjectPosition12 = (axisAlignedBB11 = entity9.boundingBox.expand(f10 = 0.3f, f10, f10)).func_1169_a(vec3D15, vec3D2)) != null && ((d13 = vec3D15.distanceTo(movingObjectPosition12.hitVec)) < d6 || d6 == 0.0)) {
                    entity4 = entity9;
                    d6 = d13;
                }
                ++i8;
            }
            if (entity4 != null) {
                movingObjectPosition3 = new MovingObjectPosition(entity4);
            }
        }
        if (movingObjectPosition3 != null) {
            if (movingObjectPosition3.entityHit == null || movingObjectPosition3.entityHit.attackEntityFrom(this.thrower, 0)) {
                // empty if block
            }
            int i16 = 0;
            while (i16 < 8) {
                this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                ++i16;
            }
            this.setEntityDead();
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
        float f19 = 0.03f;
        if (this.handleWaterMovement()) {
            int i7 = 0;
            while (i7 < 4) {
                float f20 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f20, this.posY - this.motionY * (double)f20, this.posZ - this.motionZ * (double)f20, this.motionX, this.motionY, this.motionZ);
                ++i7;
            }
            f18 = 0.8f;
        }
        this.motionX *= (double)f18;
        this.motionY *= (double)f18;
        this.motionZ *= (double)f18;
        this.motionY -= (double)f19;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setShort("xTile", (short)this.xTileSnowball);
        nBTTagCompound1.setShort("yTile", (short)this.yTileSnowball);
        nBTTagCompound1.setShort("zTile", (short)this.zTileSnowball);
        nBTTagCompound1.setByte("inTile", (byte)this.inTileSnowball);
        nBTTagCompound1.setByte("shake", (byte)this.shakeSnowball);
        nBTTagCompound1.setByte("inGround", (byte)(this.inGroundSnowball ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.xTileSnowball = nBTTagCompound1.getShort("xTile");
        this.yTileSnowball = nBTTagCompound1.getShort("yTile");
        this.zTileSnowball = nBTTagCompound1.getShort("zTile");
        this.inTileSnowball = nBTTagCompound1.getByte("inTile") & 0xFF;
        this.shakeSnowball = nBTTagCompound1.getByte("shake") & 0xFF;
        this.inGroundSnowball = nBTTagCompound1.getByte("inGround") == 1;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityPlayer1) {
        if (this.inGroundSnowball && this.thrower == entityPlayer1 && this.shakeSnowball <= 0 && entityPlayer1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
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

