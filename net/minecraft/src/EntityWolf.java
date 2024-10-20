/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.World;

public class EntityWolf
extends EntityAnimals {
    private boolean looksWithInterest = false;
    private float field_25048_b;
    private float field_25054_c;
    private boolean field_25053_f;
    private boolean field_25052_g;
    private float timeWolfIsShaking;
    private float field_25050_i;

    public EntityWolf(World world1) {
        super(world1);
        this.texture = "/mob/wolf.png";
        this.setSize(0.8f, 0.8f);
        this.moveSpeed = 1.1f;
        this.health = 8;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
        this.dataWatcher.addObject(17, "");
        this.dataWatcher.addObject(18, new Integer(this.health));
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public String getEntityTexture() {
        return this.isWolfTamed() ? "/mob/wolf_tame.png" : (this.isWolfAngry() ? "/mob/wolf_angry.png" : super.getEntityTexture());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
        nBTTagCompound1.setBoolean("Angry", this.isWolfAngry());
        nBTTagCompound1.setBoolean("Sitting", this.isWolfSitting());
        if (this.getWolfOwner() == null) {
            nBTTagCompound1.setString("Owner", "");
        } else {
            nBTTagCompound1.setString("Owner", this.getWolfOwner());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
        this.setWolfAngry(nBTTagCompound1.getBoolean("Angry"));
        this.setWolfSitting(nBTTagCompound1.getBoolean("Sitting"));
        String string2 = nBTTagCompound1.getString("Owner");
        if (string2.length() > 0) {
            this.setWolfOwner(string2);
            this.func_25038_d(true);
        }
    }

    @Override
    protected boolean canDespawn() {
        return !this.isWolfTamed();
    }

    @Override
    protected String getLivingSound() {
        return this.isWolfAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) == 0 ? (this.isWolfTamed() && this.health < 10 ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected int getDropItemId() {
        return -1;
    }

    @Override
    protected void updatePlayerActionState() {
        List list1;
        super.updatePlayerActionState();
        if (!this.hasAttacked && !this.hasPath() && this.isWolfTamed()) {
            EntityPlayer entityPlayer3 = this.worldObj.getPlayerEntityByName(this.getWolfOwner());
            if (entityPlayer3 != null) {
                float f2 = entityPlayer3.getDistanceToEntity(this);
                if (f2 > 5.0f) {
                    this.getPathOrWalkableBlock(entityPlayer3, f2);
                }
            } else if (!this.handleWaterMovement()) {
                this.setWolfSitting(true);
            }
        } else if (!(this.playerToAttack != null || this.hasPath() || this.isWolfTamed() || this.worldObj.rand.nextInt(100) != 0 || (list1 = this.worldObj.getEntitiesWithinAABB(EntitySheep.class, AxisAlignedBB.getBoundingBoxFromPool(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0))).isEmpty())) {
            this.setTarget((Entity)list1.get(this.worldObj.rand.nextInt(list1.size())));
        }
        if (this.handleWaterMovement()) {
            this.setWolfSitting(false);
        }
        if (!this.worldObj.multiplayerWorld) {
            this.dataWatcher.updateObject(18, this.health);
        }
    }

    @Override
    public void onLivingUpdate() {
        Entity entity1;
        super.onLivingUpdate();
        this.looksWithInterest = false;
        if (this.func_25025_V() && !this.hasPath() && !this.isWolfAngry() && (entity1 = this.getCurrentTarget()) instanceof EntityPlayer) {
            EntityPlayer entityPlayer2 = (EntityPlayer)entity1;
            ItemStack itemStack3 = entityPlayer2.inventory.getCurrentItem();
            if (itemStack3 != null) {
                if (!this.isWolfTamed() && itemStack3.itemID == Item.bone.shiftedIndex) {
                    this.looksWithInterest = true;
                } else if (this.isWolfTamed() && Item.itemsList[itemStack3.itemID] instanceof ItemFood) {
                    this.looksWithInterest = ((ItemFood)Item.itemsList[itemStack3.itemID]).getIsWolfsFavoriteMeat();
                }
            }
        }
        if (!this.field_9343_G && this.field_25053_f && !this.field_25052_g && !this.hasPath()) {
            this.field_25052_g = true;
            this.timeWolfIsShaking = 0.0f;
            this.field_25050_i = 0.0f;
            this.worldObj.func_9425_a(this, (byte)8);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_25054_c = this.field_25048_b;
        this.field_25048_b = this.looksWithInterest ? (this.field_25048_b += (1.0f - this.field_25048_b) * 0.4f) : (this.field_25048_b += (0.0f - this.field_25048_b) * 0.4f);
        if (this.looksWithInterest) {
            this.numTicksToChaseTarget = 10;
        }
        if (this.handleWaterMovement()) {
            this.field_25053_f = true;
            this.field_25052_g = false;
            this.timeWolfIsShaking = 0.0f;
            this.field_25050_i = 0.0f;
        } else if ((this.field_25053_f || this.field_25052_g) && this.field_25052_g) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.worldObj.playSoundAtEntity(this, "mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.field_25050_i = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.field_25050_i >= 2.0f) {
                this.field_25053_f = false;
                this.field_25052_g = false;
                this.field_25050_i = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                float f1 = (float)this.boundingBox.minY;
                int i2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * (float)Math.PI) * 7.0f);
                int i3 = 0;
                while (i3 < i2) {
                    float f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.worldObj.spawnParticle("splash", this.posX + (double)f4, f1 + 0.8f, this.posZ + (double)f5, this.motionX, this.motionY, this.motionZ);
                    ++i3;
                }
            }
        }
    }

    public boolean func_25039_v() {
        return this.field_25053_f;
    }

    public float func_25043_b_(float f1) {
        return 0.75f + (this.field_25050_i + (this.timeWolfIsShaking - this.field_25050_i) * f1) / 2.0f * 0.25f;
    }

    public float func_25042_a(float f1, float f2) {
        float f3 = (this.field_25050_i + (this.timeWolfIsShaking - this.field_25050_i) * f1 + f2) / 1.8f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        return MathHelper.sin(f3 * (float)Math.PI) * MathHelper.sin(f3 * (float)Math.PI * 11.0f) * 0.15f * (float)Math.PI;
    }

    public float func_25033_c(float f1) {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * f1) * 0.15f * (float)Math.PI;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }

    @Override
    protected int func_25026_x() {
        return this.isWolfSitting() ? 20 : super.func_25026_x();
    }

    private void getPathOrWalkableBlock(Entity entity1, float f2) {
        PathEntity pathEntity3 = this.worldObj.getPathToEntity(this, entity1, 16.0f);
        if (pathEntity3 == null && f2 > 12.0f) {
            int i4 = MathHelper.floor_double(entity1.posX) - 2;
            int i5 = MathHelper.floor_double(entity1.posZ) - 2;
            int i6 = MathHelper.floor_double(entity1.boundingBox.minY);
            int i7 = 0;
            while (i7 <= 4) {
                int i8 = 0;
                while (i8 <= 4) {
                    if (!(i7 >= 1 && i8 >= 1 && i7 <= 3 && i8 <= 3 || !this.worldObj.isBlockOpaqueCube(i4 + i7, i6 - 1, i5 + i8) || this.worldObj.isBlockOpaqueCube(i4 + i7, i6, i5 + i8) || this.worldObj.isBlockOpaqueCube(i4 + i7, i6 + 1, i5 + i8))) {
                        this.setLocationAndAngles((float)(i4 + i7) + 0.5f, i6, (float)(i5 + i8) + 0.5f, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                    ++i8;
                }
                ++i7;
            }
        } else {
            this.setPathToEntity(pathEntity3);
        }
    }

    @Override
    protected boolean func_25028_d_() {
        return this.isWolfSitting() || this.field_25052_g;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        this.setWolfSitting(false);
        if (entity1 != null && !(entity1 instanceof EntityPlayer) && !(entity1 instanceof EntityArrow)) {
            i2 = (i2 + 1) / 2;
        }
        if (!super.attackEntityFrom(entity1, i2)) {
            return false;
        }
        if (!this.isWolfTamed() && !this.isWolfAngry()) {
            if (entity1 instanceof EntityPlayer) {
                this.setWolfAngry(true);
                this.playerToAttack = entity1;
            }
            if (entity1 instanceof EntityArrow && ((EntityArrow)entity1).archer != null) {
                entity1 = ((EntityArrow)entity1).archer;
            }
            if (entity1 instanceof EntityLiving) {
                List list3 = this.worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getBoundingBoxFromPool(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0));
                for (Entity entity5 : list3) {
                    EntityWolf entityWolf6 = (EntityWolf)entity5;
                    if (entityWolf6.isWolfTamed() || entityWolf6.playerToAttack != null) continue;
                    entityWolf6.playerToAttack = entity1;
                    if (!(entity1 instanceof EntityPlayer)) continue;
                    entityWolf6.setWolfAngry(true);
                }
            }
        } else if (entity1 != this && entity1 != null) {
            if (this.isWolfTamed() && entity1 instanceof EntityPlayer && ((EntityPlayer)entity1).username.equals(this.getWolfOwner())) {
                return true;
            }
            this.playerToAttack = entity1;
        }
        return true;
    }

    @Override
    protected Entity findPlayerToAttack() {
        return this.isWolfAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0) : null;
    }

    @Override
    protected void attackEntity(Entity entity1, float f2) {
        if (f2 > 2.0f && f2 < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double d8 = entity1.posX - this.posX;
                double d5 = entity1.posZ - this.posZ;
                float f7 = MathHelper.sqrt_double(d8 * d8 + d5 * d5);
                this.motionX = d8 / (double)f7 * 0.5 * (double)0.8f + this.motionX * (double)0.2f;
                this.motionZ = d5 / (double)f7 * 0.5 * (double)0.8f + this.motionZ * (double)0.2f;
                this.motionY = 0.4f;
            }
        } else if ((double)f2 < 1.5 && entity1.boundingBox.maxY > this.boundingBox.minY && entity1.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            int b3 = 2;
            if (this.isWolfTamed()) {
                b3 = 4;
            }
            entity1.attackEntityFrom(this, b3);
        }
    }

    @Override
    public boolean interact(EntityPlayer entityPlayer1) {
        ItemStack itemStack2 = entityPlayer1.inventory.getCurrentItem();
        if (!this.isWolfTamed()) {
            if (itemStack2 != null && itemStack2.itemID == Item.bone.shiftedIndex && !this.isWolfAngry()) {
                --itemStack2.stackSize;
                if (itemStack2.stackSize <= 0) {
                    entityPlayer1.inventory.setInventorySlotContents(entityPlayer1.inventory.currentItem, null);
                }
                if (!this.worldObj.multiplayerWorld) {
                    if (this.rand.nextInt(3) == 0) {
                        this.func_25038_d(true);
                        this.setPathToEntity(null);
                        this.setWolfSitting(true);
                        this.health = 20;
                        this.setWolfOwner(entityPlayer1.username);
                        this.showHeartsOrSmokeFX(true);
                        this.worldObj.func_9425_a(this, (byte)7);
                    } else {
                        this.showHeartsOrSmokeFX(false);
                        this.worldObj.func_9425_a(this, (byte)6);
                    }
                }
                return true;
            }
        } else {
            ItemFood itemFood3;
            if (itemStack2 != null && Item.itemsList[itemStack2.itemID] instanceof ItemFood && (itemFood3 = (ItemFood)Item.itemsList[itemStack2.itemID]).getIsWolfsFavoriteMeat() && this.dataWatcher.func_25115_b(18) < 20) {
                --itemStack2.stackSize;
                if (itemStack2.stackSize <= 0) {
                    entityPlayer1.inventory.setInventorySlotContents(entityPlayer1.inventory.currentItem, null);
                }
                this.heal(((ItemFood)Item.porkRaw).getHealAmount());
                return true;
            }
            if (entityPlayer1.username.equals(this.getWolfOwner())) {
                if (!this.worldObj.multiplayerWorld) {
                    this.setWolfSitting(!this.isWolfSitting());
                    this.isJumping = false;
                    this.setPathToEntity(null);
                }
                return true;
            }
        }
        return false;
    }

    void showHeartsOrSmokeFX(boolean z1) {
        String string2 = "heart";
        if (!z1) {
            string2 = "smoke";
        }
        int i3 = 0;
        while (i3 < 7) {
            double d4 = this.rand.nextGaussian() * 0.02;
            double d6 = this.rand.nextGaussian() * 0.02;
            double d8 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(string2, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, d4, d6, d8);
            ++i3;
        }
    }

    @Override
    public void handleHealthUpdate(byte b1) {
        if (b1 == 7) {
            this.showHeartsOrSmokeFX(true);
        } else if (b1 == 6) {
            this.showHeartsOrSmokeFX(false);
        } else if (b1 == 8) {
            this.field_25052_g = true;
            this.timeWolfIsShaking = 0.0f;
            this.field_25050_i = 0.0f;
        } else {
            super.handleHealthUpdate(b1);
        }
    }

    public float func_25037_z() {
        return this.isWolfAngry() ? 1.5393804f : (this.isWolfTamed() ? (0.55f - (float)(20 - this.dataWatcher.func_25115_b(18)) * 0.02f) * (float)Math.PI : 0.62831855f);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    public String getWolfOwner() {
        return this.dataWatcher.func_25116_c(17);
    }

    public void setWolfOwner(String string1) {
        this.dataWatcher.updateObject(17, string1);
    }

    public boolean isWolfSitting() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setWolfSitting(boolean z1) {
        byte b2 = this.dataWatcher.getWatchableObjectByte(16);
        if (z1) {
            this.dataWatcher.updateObject(16, (byte)(b2 | 1));
        } else {
            this.dataWatcher.updateObject(16, (byte)(b2 & 0xFFFFFFFE));
        }
    }

    public boolean isWolfAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setWolfAngry(boolean z1) {
        byte b2 = this.dataWatcher.getWatchableObjectByte(16);
        if (z1) {
            this.dataWatcher.updateObject(16, (byte)(b2 | 2));
        } else {
            this.dataWatcher.updateObject(16, (byte)(b2 & 0xFFFFFFFD));
        }
    }

    public boolean isWolfTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void func_25038_d(boolean z1) {
        byte b2 = this.dataWatcher.getWatchableObjectByte(16);
        if (z1) {
            this.dataWatcher.updateObject(16, (byte)(b2 | 4));
        } else {
            this.dataWatcher.updateObject(16, (byte)(b2 & 0xFFFFFFFB));
        }
    }
}

