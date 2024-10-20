/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StepSound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public abstract class EntityLiving
extends Entity {
    public int field_9366_o = 20;
    public float field_9365_p;
    public float field_9363_r;
    public float renderYawOffset = 0.0f;
    public float prevRenderYawOffset = 0.0f;
    protected float field_9362_u;
    protected float field_9361_v;
    protected float field_9360_w;
    protected float field_9359_x;
    protected boolean field_9358_y = true;
    protected String texture = "/mob/char.png";
    protected boolean field_9355_A = true;
    protected float field_9353_B = 0.0f;
    protected String field_9351_C = null;
    protected float field_9349_D = 1.0f;
    protected int scoreValue = 0;
    protected float field_9345_F = 0.0f;
    public boolean field_9343_G = false;
    public float prevSwingProgress;
    public float swingProgress;
    public int health = 10;
    public int prevHealth;
    private int livingSoundTime;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw = 0.0f;
    public int deathTime = 0;
    public int attackTime = 0;
    public float cameraPitch;
    public float field_9328_R;
    protected boolean unused_flag = false;
    public int field_9326_T = -1;
    public float field_9325_U = (float)(Math.random() * (double)0.9f + (double)0.1f);
    public float field_705_Q;
    public float field_704_R;
    public float field_703_S;
    protected int newPosRotationIncrements;
    protected double newPosX;
    protected double newPosY;
    protected double newPosZ;
    protected double newRotationYaw;
    protected double newRotationPitch;
    float field_9348_ae = 0.0f;
    protected int field_9346_af = 0;
    protected int field_9344_ag = 0;
    public float moveStrafing;
    public float moveForward;
    protected float randomYawVelocity;
    protected boolean isJumping = false;
    protected float defaultPitch = 0.0f;
    protected float moveSpeed = 0.7f;
    private Entity currentTarget;
    protected int numTicksToChaseTarget = 0;

    public EntityLiving(World world1) {
        super(world1);
        this.preventEntitySpawning = true;
        this.field_9363_r = (float)(Math.random() + 1.0) * 0.01f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_9365_p = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.stepHeight = 0.5f;
    }

    @Override
    protected void entityInit() {
    }

    public boolean canEntityBeSeen(Entity entity1) {
        return this.worldObj.rayTraceBlocks(Vec3D.createVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3D.createVector(entity1.posX, entity1.posY + (double)entity1.getEyeHeight(), entity1.posZ)) == null;
    }

    @Override
    public String getEntityTexture() {
        return this.texture;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.85f;
    }

    public int getTalkInterval() {
        return 80;
    }

    public void playLivingSound() {
        String string1 = this.getLivingSound();
        if (string1 != null) {
            this.worldObj.playSoundAtEntity(this, string1, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    @Override
    public void onEntityUpdate() {
        int i1;
        this.prevSwingProgress = this.swingProgress;
        super.onEntityUpdate();
        if (this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }
        if (this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(null, 1);
        }
        if (this.isImmuneToFire || this.worldObj.multiplayerWorld) {
            this.fire = 0;
        }
        if (this.isEntityAlive() && this.isInsideOfMaterial(Material.water) && !this.canBreatheUnderwater()) {
            --this.air;
            if (this.air == -20) {
                this.air = 0;
                i1 = 0;
                while (i1 < 8) {
                    float f2 = this.rand.nextFloat() - this.rand.nextFloat();
                    float f3 = this.rand.nextFloat() - this.rand.nextFloat();
                    float f4 = this.rand.nextFloat() - this.rand.nextFloat();
                    this.worldObj.spawnParticle("bubble", this.posX + (double)f2, this.posY + (double)f3, this.posZ + (double)f4, this.motionX, this.motionY, this.motionZ);
                    ++i1;
                }
                this.attackEntityFrom(null, 2);
            }
            this.fire = 0;
        } else {
            this.air = this.maxAir;
        }
        this.cameraPitch = this.field_9328_R;
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.field_9306_bj > 0) {
            --this.field_9306_bj;
        }
        if (this.health <= 0) {
            ++this.deathTime;
            if (this.deathTime > 20) {
                this.unusedEntityMethod();
                this.setEntityDead();
                i1 = 0;
                while (i1 < 20) {
                    double d8 = this.rand.nextGaussian() * 0.02;
                    double d9 = this.rand.nextGaussian() * 0.02;
                    double d6 = this.rand.nextGaussian() * 0.02;
                    this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, d8, d9, d6);
                    ++i1;
                }
            }
        }
        this.field_9359_x = this.field_9360_w;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void spawnExplosionParticle() {
        int i1 = 0;
        while (i1 < 20) {
            double d2 = this.rand.nextGaussian() * 0.02;
            double d4 = this.rand.nextGaussian() * 0.02;
            double d6 = this.rand.nextGaussian() * 0.02;
            double d8 = 10.0;
            this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width - d2 * d8, this.posY + (double)(this.rand.nextFloat() * this.height) - d4 * d8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width - d6 * d8, d2, d4, d6);
            ++i1;
        }
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_9362_u = this.field_9361_v;
        this.field_9361_v = 0.0f;
    }

    @Override
    public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
        this.yOffset = 0.0f;
        this.newPosX = d1;
        this.newPosY = d3;
        this.newPosZ = d5;
        this.newRotationYaw = f7;
        this.newRotationPitch = f8;
        this.newPosRotationIncrements = i9;
    }

    @Override
    public void onUpdate() {
        boolean z11;
        super.onUpdate();
        this.onLivingUpdate();
        double d1 = this.posX - this.prevPosX;
        double d3 = this.posZ - this.prevPosZ;
        float f5 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
        float f6 = this.renderYawOffset;
        float f7 = 0.0f;
        this.field_9362_u = this.field_9361_v;
        float f8 = 0.0f;
        if (f5 > 0.05f) {
            f8 = 1.0f;
            f7 = f5 * 3.0f;
            f6 = (float)Math.atan2(d3, d1) * 180.0f / (float)Math.PI - 90.0f;
        }
        if (this.swingProgress > 0.0f) {
            f6 = this.rotationYaw;
        }
        if (!this.onGround) {
            f8 = 0.0f;
        }
        this.field_9361_v += (f8 - this.field_9361_v) * 0.3f;
        float f9 = f6 - this.renderYawOffset;
        while (f9 < -180.0f) {
            f9 += 360.0f;
        }
        while (f9 >= 180.0f) {
            f9 -= 360.0f;
        }
        this.renderYawOffset += f9 * 0.3f;
        float f10 = this.rotationYaw - this.renderYawOffset;
        while (f10 < -180.0f) {
            f10 += 360.0f;
        }
        while (f10 >= 180.0f) {
            f10 -= 360.0f;
        }
        boolean bl = z11 = f10 < -90.0f || f10 >= 90.0f;
        if (f10 < -75.0f) {
            f10 = -75.0f;
        }
        if (f10 >= 75.0f) {
            f10 = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - f10;
        if (f10 * f10 > 2500.0f) {
            this.renderYawOffset += f10 * 0.2f;
        }
        if (z11) {
            f7 *= -1.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        this.field_9360_w += f7;
    }

    @Override
    protected void setSize(float f1, float f2) {
        super.setSize(f1, f2);
    }

    public void heal(int i1) {
        if (this.health > 0) {
            this.health += i1;
            if (this.health > 20) {
                this.health = 20;
            }
            this.field_9306_bj = this.field_9366_o / 2;
        }
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (this.worldObj.multiplayerWorld) {
            return false;
        }
        this.field_9344_ag = 0;
        if (this.health <= 0) {
            return false;
        }
        this.field_704_R = 1.5f;
        boolean z3 = true;
        if ((float)this.field_9306_bj > (float)this.field_9366_o / 2.0f) {
            if (i2 <= this.field_9346_af) {
                return false;
            }
            this.damageEntity(i2 - this.field_9346_af);
            this.field_9346_af = i2;
            z3 = false;
        } else {
            this.field_9346_af = i2;
            this.prevHealth = this.health;
            this.field_9306_bj = this.field_9366_o;
            this.damageEntity(i2);
            this.maxHurtTime = 10;
            this.hurtTime = 10;
        }
        this.attackedAtYaw = 0.0f;
        if (z3) {
            this.worldObj.func_9425_a(this, (byte)2);
            this.setBeenAttacked();
            if (entity1 != null) {
                double d4 = entity1.posX - this.posX;
                double d6 = entity1.posZ - this.posZ;
                while (d4 * d4 + d6 * d6 < 1.0E-4) {
                    d4 = (Math.random() - Math.random()) * 0.01;
                    d6 = (Math.random() - Math.random()) * 0.01;
                }
                this.attackedAtYaw = (float)(Math.atan2(d6, d4) * 180.0 / 3.1415927410125732) - this.rotationYaw;
                this.knockBack(entity1, i2, d4, d6);
            } else {
                this.attackedAtYaw = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (z3) {
                this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.onDeath(entity1);
        } else if (z3) {
            this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        this.maxHurtTime = 10;
        this.hurtTime = 10;
        this.attackedAtYaw = 0.0f;
    }

    protected void damageEntity(int i1) {
        this.health -= i1;
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "random.hurt";
    }

    protected String getDeathSound() {
        return "random.hurt";
    }

    public void knockBack(Entity entity1, int i2, double d3, double d5) {
        float f7 = MathHelper.sqrt_double(d3 * d3 + d5 * d5);
        float f8 = 0.4f;
        this.motionX /= 2.0;
        this.motionY /= 2.0;
        this.motionZ /= 2.0;
        this.motionX -= d3 / (double)f7 * (double)f8;
        this.motionY += (double)0.4f;
        this.motionZ -= d5 / (double)f7 * (double)f8;
        if (this.motionY > (double)0.4f) {
            this.motionY = 0.4f;
        }
    }

    public void onDeath(Entity entity1) {
        if (this.scoreValue >= 0 && entity1 != null) {
            entity1.addToPlayerScore(this, this.scoreValue);
        }
        this.unused_flag = true;
        if (!this.worldObj.multiplayerWorld) {
            this.dropFewItems();
        }
        this.worldObj.func_9425_a(this, (byte)3);
    }

    protected void dropFewItems() {
        int i1 = this.getDropItemId();
        if (i1 > 0) {
            int i2 = this.rand.nextInt(3);
            int i3 = 0;
            while (i3 < i2) {
                this.dropItem(i1, 1);
                ++i3;
            }
        }
    }

    protected int getDropItemId() {
        return 0;
    }

    @Override
    protected void fall(float f1) {
        int i2 = (int)Math.ceil(f1 - 3.0f);
        if (i2 > 0) {
            this.attackEntityFrom(null, i2);
            int i3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - (double)0.2f - (double)this.yOffset), MathHelper.floor_double(this.posZ));
            if (i3 > 0) {
                StepSound stepSound4 = Block.blocksList[i3].stepSound;
                this.worldObj.playSoundAtEntity(this, stepSound4.func_1145_d(), stepSound4.func_1147_b() * 0.5f, stepSound4.func_1144_c() * 0.75f);
            }
        }
    }

    public void moveEntityWithHeading(float f1, float f2) {
        double d3;
        if (this.handleWaterMovement()) {
            d3 = this.posY;
            this.moveFlying(f1, f2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.8f;
            this.motionY *= (double)0.8f;
            this.motionZ *= (double)0.8f;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6f - this.posY + d3, this.motionZ)) {
                this.motionY = 0.3f;
            }
        } else if (this.handleLavaMovement()) {
            d3 = this.posY;
            this.moveFlying(f1, f2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6f - this.posY + d3, this.motionZ)) {
                this.motionY = 0.3f;
            }
        } else {
            float f8 = 0.91f;
            if (this.onGround) {
                f8 = 0.54600006f;
                int i4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (i4 > 0) {
                    f8 = Block.blocksList[i4].slipperiness * 0.91f;
                }
            }
            float f9 = 0.16277136f / (f8 * f8 * f8);
            this.moveFlying(f1, f2, this.onGround ? 0.1f * f9 : 0.02f);
            f8 = 0.91f;
            if (this.onGround) {
                f8 = 0.54600006f;
                int i5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (i5 > 0) {
                    f8 = Block.blocksList[i5].slipperiness * 0.91f;
                }
            }
            if (this.isOnLadder()) {
                this.fallDistance = 0.0f;
                if (this.motionY < -0.15) {
                    this.motionY = -0.15;
                }
                if (this.isSneaking() && this.motionY < 0.0) {
                    this.motionY = 0.0;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2;
            }
            this.motionY -= 0.08;
            this.motionY *= (double)0.98f;
            this.motionX *= (double)f8;
            this.motionZ *= (double)f8;
        }
        this.field_705_Q = this.field_704_R;
        d3 = this.posX - this.prevPosX;
        double d10 = this.posZ - this.prevPosZ;
        float f7 = MathHelper.sqrt_double(d3 * d3 + d10 * d10) * 4.0f;
        if (f7 > 1.0f) {
            f7 = 1.0f;
        }
        this.field_704_R += (f7 - this.field_704_R) * 0.4f;
        this.field_703_S += this.field_704_R;
    }

    public boolean isOnLadder() {
        int i3;
        int i2;
        int i1 = MathHelper.floor_double(this.posX);
        return this.worldObj.getBlockId(i1, i2 = MathHelper.floor_double(this.boundingBox.minY), i3 = MathHelper.floor_double(this.posZ)) == Block.ladder.blockID || this.worldObj.getBlockId(i1, i2 + 1, i3) == Block.ladder.blockID;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setShort("Health", (short)this.health);
        nBTTagCompound1.setShort("HurtTime", (short)this.hurtTime);
        nBTTagCompound1.setShort("DeathTime", (short)this.deathTime);
        nBTTagCompound1.setShort("AttackTime", (short)this.attackTime);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.health = nBTTagCompound1.getShort("Health");
        if (!nBTTagCompound1.hasKey("Health")) {
            this.health = 10;
        }
        this.hurtTime = nBTTagCompound1.getShort("HurtTime");
        this.deathTime = nBTTagCompound1.getShort("DeathTime");
        this.attackTime = nBTTagCompound1.getShort("AttackTime");
    }

    @Override
    public boolean isEntityAlive() {
        return !this.isDead && this.health > 0;
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public void onLivingUpdate() {
        if (this.newPosRotationIncrements > 0) {
            double d1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
            double d3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
            double d5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;
            double d7 = this.newRotationYaw - (double)this.rotationYaw;
            while (d7 < -180.0) {
                d7 += 360.0;
            }
            while (d7 >= 180.0) {
                d7 -= 360.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.newPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d1, d3, d5);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        } else if (!this.field_9343_G) {
            this.updatePlayerActionState();
        }
        boolean z9 = this.handleWaterMovement();
        boolean z2 = this.handleLavaMovement();
        if (this.isJumping) {
            if (z9) {
                this.motionY += (double)0.04f;
            } else if (z2) {
                this.motionY += (double)0.04f;
            } else if (this.onGround) {
                this.jump();
            }
        }
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        List list10 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
        if (list10 != null && list10.size() > 0) {
            int i4 = 0;
            while (i4 < list10.size()) {
                Entity entity11 = (Entity)list10.get(i4);
                if (entity11.canBePushed()) {
                    entity11.applyEntityCollision(this);
                }
                ++i4;
            }
        }
    }

    protected boolean isMovementBlocked() {
        return this.health <= 0;
    }

    protected void jump() {
        this.motionY = 0.42f;
    }

    protected boolean canDespawn() {
        return true;
    }

    protected void updatePlayerActionState() {
        ++this.field_9344_ag;
        EntityPlayer entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, -1.0);
        if (this.canDespawn() && entityPlayer1 != null) {
            double d2 = entityPlayer1.posX - this.posX;
            double d4 = entityPlayer1.posY - this.posY;
            double d6 = entityPlayer1.posZ - this.posZ;
            double d8 = d2 * d2 + d4 * d4 + d6 * d6;
            if (d8 > 16384.0) {
                this.setEntityDead();
            }
            if (this.field_9344_ag > 600 && this.rand.nextInt(800) == 0) {
                if (d8 < 1024.0) {
                    this.field_9344_ag = 0;
                } else {
                    this.setEntityDead();
                }
            }
        }
        this.moveStrafing = 0.0f;
        this.moveForward = 0.0f;
        float f10 = 8.0f;
        if (this.rand.nextFloat() < 0.02f) {
            entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, f10);
            if (entityPlayer1 != null) {
                this.currentTarget = entityPlayer1;
                this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
            } else {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.currentTarget != null) {
            this.faceEntity(this.currentTarget, 10.0f, this.func_25026_x());
            if (this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > (double)(f10 * f10)) {
                this.currentTarget = null;
            }
        } else {
            if (this.rand.nextFloat() < 0.05f) {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
            this.rotationYaw += this.randomYawVelocity;
            this.rotationPitch = this.defaultPitch;
        }
        boolean z3 = this.handleWaterMovement();
        boolean z11 = this.handleLavaMovement();
        if (z3 || z11) {
            this.isJumping = this.rand.nextFloat() < 0.8f;
        }
    }

    protected int func_25026_x() {
        return 10;
    }

    public void faceEntity(Entity entity1, float f2, float f3) {
        double d6;
        double d4 = entity1.posX - this.posX;
        double d8 = entity1.posZ - this.posZ;
        if (entity1 instanceof EntityLiving) {
            EntityLiving entityLiving10 = (EntityLiving)entity1;
            d6 = this.posY + (double)this.getEyeHeight() - (entityLiving10.posY + (double)entityLiving10.getEyeHeight());
        } else {
            d6 = (entity1.boundingBox.minY + entity1.boundingBox.maxY) / 2.0 - (this.posY + (double)this.getEyeHeight());
        }
        double d14 = MathHelper.sqrt_double(d4 * d4 + d8 * d8);
        float f12 = (float)(Math.atan2(d8, d4) * 180.0 / 3.1415927410125732) - 90.0f;
        float f13 = (float)(Math.atan2(d6, d14) * 180.0 / 3.1415927410125732);
        this.rotationPitch = -this.updateRotation(this.rotationPitch, f13, f3);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f12, f2);
    }

    public boolean func_25025_V() {
        return this.currentTarget != null;
    }

    public Entity getCurrentTarget() {
        return this.currentTarget;
    }

    private float updateRotation(float f1, float f2, float f3) {
        float f4 = f2 - f1;
        while (f4 < -180.0f) {
            f4 += 360.0f;
        }
        while (f4 >= 180.0f) {
            f4 -= 360.0f;
        }
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f1 + f4;
    }

    public void unusedEntityMethod() {
    }

    public boolean getCanSpawnHere() {
        return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
    }

    @Override
    protected void kill() {
        this.attackEntityFrom(null, 4);
    }

    public float getSwingProgress(float f1) {
        float f2 = this.swingProgress - this.prevSwingProgress;
        if (f2 < 0.0f) {
            f2 += 1.0f;
        }
        return this.prevSwingProgress + f2 * f1;
    }

    public Vec3D getPosition(float f1) {
        if (f1 == 1.0f) {
            return Vec3D.createVector(this.posX, this.posY, this.posZ);
        }
        double d2 = this.prevPosX + (this.posX - this.prevPosX) * (double)f1;
        double d4 = this.prevPosY + (this.posY - this.prevPosY) * (double)f1;
        double d6 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f1;
        return Vec3D.createVector(d2, d4, d6);
    }

    @Override
    public Vec3D getLookVec() {
        return this.getLook(1.0f);
    }

    public Vec3D getLook(float f1) {
        if (f1 == 1.0f) {
            float f2 = MathHelper.cos(-this.rotationYaw * ((float)Math.PI / 180) - (float)Math.PI);
            float f3 = MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180) - (float)Math.PI);
            float f4 = -MathHelper.cos(-this.rotationPitch * ((float)Math.PI / 180));
            float f5 = MathHelper.sin(-this.rotationPitch * ((float)Math.PI / 180));
            return Vec3D.createVector(f3 * f4, f5, f2 * f4);
        }
        float f2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * f1;
        float f3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * f1;
        float f4 = MathHelper.cos(-f3 * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = MathHelper.sin(-f3 * ((float)Math.PI / 180) - (float)Math.PI);
        float f6 = -MathHelper.cos(-f2 * ((float)Math.PI / 180));
        float f7 = MathHelper.sin(-f2 * ((float)Math.PI / 180));
        return Vec3D.createVector(f5 * f6, f7, f4 * f6);
    }

    public MovingObjectPosition rayTrace(double d1, float f3) {
        Vec3D vec3D4 = this.getPosition(f3);
        Vec3D vec3D5 = this.getLook(f3);
        Vec3D vec3D6 = vec3D4.addVector(vec3D5.xCoord * d1, vec3D5.yCoord * d1, vec3D5.zCoord * d1);
        return this.worldObj.rayTraceBlocks(vec3D4, vec3D6);
    }

    public int getMaxSpawnedInChunk() {
        return 4;
    }

    public ItemStack getHeldItem() {
        return null;
    }

    @Override
    public void handleHealthUpdate(byte b1) {
        if (b1 == 2) {
            this.field_704_R = 1.5f;
            this.field_9306_bj = this.field_9366_o;
            this.maxHurtTime = 10;
            this.hurtTime = 10;
            this.attackedAtYaw = 0.0f;
            this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.attackEntityFrom(null, 0);
        } else if (b1 == 3) {
            this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.health = 0;
            this.onDeath(null);
        } else {
            super.handleHealthUpdate(b1);
        }
    }

    public boolean isPlayerSleeping() {
        return false;
    }
}

