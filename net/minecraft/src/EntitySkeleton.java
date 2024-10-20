/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySkeleton
extends EntityMobs {
    private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

    public EntitySkeleton(World world1) {
        super(world1);
        this.texture = "/mob/skeleton.png";
    }

    @Override
    protected String getLivingSound() {
        return "mob.skeleton";
    }

    @Override
    protected String getHurtSound() {
        return "mob.skeletonhurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeletonhurt";
    }

    @Override
    public void onLivingUpdate() {
        float f1;
        if (this.worldObj.isDaytime() && (f1 = this.getEntityBrightness(1.0f)) > 0.5f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (f1 - 0.4f) * 2.0f) {
            this.fire = 300;
        }
        super.onLivingUpdate();
    }

    @Override
    protected void attackEntity(Entity entity1, float f2) {
        if (f2 < 10.0f) {
            double d3 = entity1.posX - this.posX;
            double d5 = entity1.posZ - this.posZ;
            if (this.attackTime == 0) {
                EntityArrow entityArrow7 = new EntityArrow(this.worldObj, this);
                entityArrow7.posY += 1.0;
                double d8 = entity1.posY - (double)0.2f - entityArrow7.posY;
                float f10 = MathHelper.sqrt_double(d3 * d3 + d5 * d5) * 0.2f;
                this.worldObj.playSoundAtEntity(this, "random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
                this.worldObj.entityJoinedWorld(entityArrow7);
                entityArrow7.setArrowHeading(d3, d8 + (double)f10, d5, 0.6f, 12.0f);
                this.attackTime = 30;
            }
            this.rotationYaw = (float)(Math.atan2(d5, d3) * 180.0 / 3.1415927410125732) - 90.0f;
            this.hasAttacked = true;
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
        return Item.arrow.shiftedIndex;
    }

    @Override
    protected void dropFewItems() {
        int i1 = this.rand.nextInt(3);
        int i2 = 0;
        while (i2 < i1) {
            this.dropItem(Item.arrow.shiftedIndex, 1);
            ++i2;
        }
        i1 = this.rand.nextInt(3);
        i2 = 0;
        while (i2 < i1) {
            this.dropItem(Item.bone.shiftedIndex, 1);
            ++i2;
        }
    }

    @Override
    public ItemStack getHeldItem() {
        return defaultHeldItem;
    }
}

