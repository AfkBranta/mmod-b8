/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityPigZombie
extends EntityZombie {
    private int angerLevel = 0;
    private int randomSoundDelay = 0;
    private static final ItemStack defaultHeldItem = new ItemStack(Item.swordGold, 1);

    public EntityPigZombie(World world1) {
        super(world1);
        this.texture = "/mob/pigzombie.png";
        this.moveSpeed = 0.5f;
        this.attackStrength = 5;
        this.isImmuneToFire = true;
    }

    @Override
    public void onUpdate() {
        float f = this.moveSpeed = this.playerToAttack != null ? 0.95f : 0.5f;
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.worldObj.playSoundAtEntity(this, "mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.onUpdate();
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting > 0 && this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
        nBTTagCompound1.setShort("Anger", (short)this.angerLevel);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
        this.angerLevel = nBTTagCompound1.getShort("Anger");
    }

    @Override
    protected Entity findPlayerToAttack() {
        return this.angerLevel == 0 ? null : super.findPlayerToAttack();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (entity1 instanceof EntityPlayer) {
            List list3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            int i4 = 0;
            while (i4 < list3.size()) {
                Entity entity5 = (Entity)list3.get(i4);
                if (entity5 instanceof EntityPigZombie) {
                    EntityPigZombie entityPigZombie6 = (EntityPigZombie)entity5;
                    entityPigZombie6.becomeAngryAt(entity1);
                }
                ++i4;
            }
            this.becomeAngryAt(entity1);
        }
        return super.attackEntityFrom(entity1, i2);
    }

    private void becomeAngryAt(Entity entity1) {
        this.playerToAttack = entity1;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }

    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }

    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    @Override
    protected int getDropItemId() {
        return Item.porkCooked.shiftedIndex;
    }

    @Override
    public ItemStack getHeldItem() {
        return defaultHeldItem;
    }
}

