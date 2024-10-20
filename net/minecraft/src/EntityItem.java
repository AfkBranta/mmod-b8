/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityItem
extends Entity {
    public ItemStack item;
    private int field_803_e;
    public int age = 0;
    public int delayBeforeCanPickup;
    private int health = 5;
    public float field_804_d = (float)(Math.random() * Math.PI * 2.0);

    public EntityItem(World world1, double d2, double d4, double d6, ItemStack itemStack8) {
        super(world1);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(d2, d4, d6);
        this.item = itemStack8;
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * (double)0.2f - (double)0.1f);
        this.motionY = 0.2f;
        this.motionZ = (float)(Math.random() * (double)0.2f - (double)0.1f);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityItem(World world1) {
        super(world1);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.delayBeforeCanPickup > 0) {
            --this.delayBeforeCanPickup;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)0.04f;
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
            this.motionY = 0.2f;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.worldObj.playSoundAtEntity(this, "random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        this.func_466_g(this.posX, this.posY, this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f1 = 0.98f;
        if (this.onGround) {
            f1 = 0.58800006f;
            int i2 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (i2 > 0) {
                f1 = Block.blocksList[i2].slipperiness * 0.98f;
            }
        }
        this.motionX *= (double)f1;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)f1;
        if (this.onGround) {
            this.motionY *= -0.5;
        }
        ++this.field_803_e;
        ++this.age;
        if (this.age >= 6000) {
            this.setEntityDead();
        }
    }

    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    private boolean func_466_g(double d1, double d3, double d5) {
        int i7 = MathHelper.floor_double(d1);
        int i8 = MathHelper.floor_double(d3);
        int i9 = MathHelper.floor_double(d5);
        double d10 = d1 - (double)i7;
        double d12 = d3 - (double)i8;
        double d14 = d5 - (double)i9;
        if (Block.opaqueCubeLookup[this.worldObj.getBlockId(i7, i8, i9)]) {
            boolean z16 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(i7 - 1, i8, i9)];
            boolean z17 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(i7 + 1, i8, i9)];
            boolean z18 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(i7, i8 - 1, i9)];
            boolean z19 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(i7, i8 + 1, i9)];
            boolean z20 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(i7, i8, i9 - 1)];
            boolean z21 = !Block.opaqueCubeLookup[this.worldObj.getBlockId(i7, i8, i9 + 1)];
            int b22 = -1;
            double d23 = 9999.0;
            if (z16 && d10 < d23) {
                d23 = d10;
                b22 = 0;
            }
            if (z17 && 1.0 - d10 < d23) {
                d23 = 1.0 - d10;
                b22 = 1;
            }
            if (z18 && d12 < d23) {
                d23 = d12;
                b22 = 2;
            }
            if (z19 && 1.0 - d12 < d23) {
                d23 = 1.0 - d12;
                b22 = 3;
            }
            if (z20 && d14 < d23) {
                d23 = d14;
                b22 = 4;
            }
            if (z21 && 1.0 - d14 < d23) {
                d23 = 1.0 - d14;
                b22 = 5;
            }
            float f25 = this.rand.nextFloat() * 0.2f + 0.1f;
            if (b22 == 0) {
                this.motionX = -f25;
            }
            if (b22 == 1) {
                this.motionX = f25;
            }
            if (b22 == 2) {
                this.motionY = -f25;
            }
            if (b22 == 3) {
                this.motionY = f25;
            }
            if (b22 == 4) {
                this.motionZ = -f25;
            }
            if (b22 == 5) {
                this.motionZ = f25;
            }
        }
        return false;
    }

    @Override
    protected void dealFireDamage(int i1) {
        this.attackEntityFrom(null, i1);
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        this.setBeenAttacked();
        this.health -= i2;
        if (this.health <= 0) {
            this.setEntityDead();
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setShort("Health", (byte)this.health);
        nBTTagCompound1.setShort("Age", (short)this.age);
        nBTTagCompound1.setCompoundTag("Item", this.item.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.health = nBTTagCompound1.getShort("Health") & 0xFF;
        this.age = nBTTagCompound1.getShort("Age");
        NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompoundTag("Item");
        this.item = new ItemStack(nBTTagCompound2);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityPlayer1) {
        if (!this.worldObj.multiplayerWorld) {
            int i2 = this.item.stackSize;
            if (this.delayBeforeCanPickup == 0 && entityPlayer1.inventory.addItemStackToInventory(this.item)) {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityPlayer1.onItemPickup(this, i2);
                this.setEntityDead();
            }
        }
    }
}

