/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityCow
extends EntityAnimals {
    public EntityCow(World world1) {
        super(world1);
        this.texture = "/mob/cow.png";
        this.setSize(0.9f, 1.3f);
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
    protected String getLivingSound() {
        return "mob.cow";
    }

    @Override
    protected String getHurtSound() {
        return "mob.cowhurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.cowhurt";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected int getDropItemId() {
        return Item.leather.shiftedIndex;
    }

    @Override
    public boolean interact(EntityPlayer entityPlayer1) {
        ItemStack itemStack2 = entityPlayer1.inventory.getCurrentItem();
        if (itemStack2 != null && itemStack2.itemID == Item.bucketEmpty.shiftedIndex) {
            entityPlayer1.inventory.setInventorySlotContents(entityPlayer1.inventory.currentItem, new ItemStack(Item.bucketMilk));
            return true;
        }
        return false;
    }
}

