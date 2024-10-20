/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EnumArt;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityPainting
extends Entity {
    private int field_695_c = 0;
    public int direction = 0;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public EnumArt art;

    public EntityPainting(World world1) {
        super(world1);
        this.yOffset = 0.0f;
        this.setSize(0.5f, 0.5f);
    }

    public EntityPainting(World world1, int i2, int i3, int i4, int i5) {
        this(world1);
        this.xPosition = i2;
        this.yPosition = i3;
        this.zPosition = i4;
        ArrayList<EnumArt> arrayList6 = new ArrayList<EnumArt>();
        EnumArt[] enumArt7 = EnumArt.values();
        int i8 = enumArt7.length;
        int i9 = 0;
        while (i9 < i8) {
            EnumArt enumArt10;
            this.art = enumArt10 = enumArt7[i9];
            this.func_412_b(i5);
            if (this.func_410_i()) {
                arrayList6.add(enumArt10);
            }
            ++i9;
        }
        if (arrayList6.size() > 0) {
            this.art = (EnumArt)((Object)arrayList6.get(this.rand.nextInt(arrayList6.size())));
        }
        this.func_412_b(i5);
    }

    public EntityPainting(World world1, int i2, int i3, int i4, int i5, String string6) {
        this(world1);
        this.xPosition = i2;
        this.yPosition = i3;
        this.zPosition = i4;
        EnumArt[] enumArt7 = EnumArt.values();
        int i8 = enumArt7.length;
        int i9 = 0;
        while (i9 < i8) {
            EnumArt enumArt10 = enumArt7[i9];
            if (enumArt10.title.equals(string6)) {
                this.art = enumArt10;
                break;
            }
            ++i9;
        }
        this.func_412_b(i5);
    }

    @Override
    protected void entityInit() {
    }

    public void func_412_b(int i1) {
        this.direction = i1;
        this.prevRotationYaw = this.rotationYaw = (float)(i1 * 90);
        float f2 = this.art.sizeX;
        float f3 = this.art.sizeY;
        float f4 = this.art.sizeX;
        if (i1 != 0 && i1 != 2) {
            f2 = 0.5f;
        } else {
            f4 = 0.5f;
        }
        f2 /= 32.0f;
        f3 /= 32.0f;
        f4 /= 32.0f;
        float f5 = (float)this.xPosition + 0.5f;
        float f6 = (float)this.yPosition + 0.5f;
        float f7 = (float)this.zPosition + 0.5f;
        float f8 = 0.5625f;
        if (i1 == 0) {
            f7 -= f8;
        }
        if (i1 == 1) {
            f5 -= f8;
        }
        if (i1 == 2) {
            f7 += f8;
        }
        if (i1 == 3) {
            f5 += f8;
        }
        if (i1 == 0) {
            f5 -= this.func_411_c(this.art.sizeX);
        }
        if (i1 == 1) {
            f7 += this.func_411_c(this.art.sizeX);
        }
        if (i1 == 2) {
            f5 += this.func_411_c(this.art.sizeX);
        }
        if (i1 == 3) {
            f7 -= this.func_411_c(this.art.sizeX);
        }
        this.setPosition(f5, f6 += this.func_411_c(this.art.sizeY), f7);
        float f9 = -0.00625f;
        this.boundingBox.setBounds(f5 - f2 - f9, f6 - f3 - f9, f7 - f4 - f9, f5 + f2 + f9, f6 + f3 + f9, f7 + f4 + f9);
    }

    private float func_411_c(int i1) {
        return i1 == 32 ? 0.5f : (i1 == 64 ? 0.5f : 0.0f);
    }

    @Override
    public void onUpdate() {
        if (this.field_695_c++ == 100 && !this.worldObj.multiplayerWorld) {
            this.field_695_c = 0;
            if (!this.func_410_i()) {
                this.setEntityDead();
                this.worldObj.entityJoinedWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.painting)));
            }
        }
    }

    public boolean func_410_i() {
        int i7;
        if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() > 0) {
            return false;
        }
        int i1 = this.art.sizeX / 16;
        int i2 = this.art.sizeY / 16;
        int i3 = this.xPosition;
        int i4 = this.yPosition;
        int i5 = this.zPosition;
        if (this.direction == 0) {
            i3 = MathHelper.floor_double(this.posX - (double)((float)this.art.sizeX / 32.0f));
        }
        if (this.direction == 1) {
            i5 = MathHelper.floor_double(this.posZ - (double)((float)this.art.sizeX / 32.0f));
        }
        if (this.direction == 2) {
            i3 = MathHelper.floor_double(this.posX - (double)((float)this.art.sizeX / 32.0f));
        }
        if (this.direction == 3) {
            i5 = MathHelper.floor_double(this.posZ - (double)((float)this.art.sizeX / 32.0f));
        }
        i4 = MathHelper.floor_double(this.posY - (double)((float)this.art.sizeY / 32.0f));
        int i6 = 0;
        while (i6 < i1) {
            i7 = 0;
            while (i7 < i2) {
                Material material8 = this.direction != 0 && this.direction != 2 ? this.worldObj.getBlockMaterial(this.xPosition, i4 + i7, i5 + i6) : this.worldObj.getBlockMaterial(i3 + i6, i4 + i7, this.zPosition);
                if (!material8.isSolid()) {
                    return false;
                }
                ++i7;
            }
            ++i6;
        }
        List list9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
        i7 = 0;
        while (i7 < list9.size()) {
            if (list9.get(i7) instanceof EntityPainting) {
                return false;
            }
            ++i7;
        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (!this.isDead && !this.worldObj.multiplayerWorld) {
            this.setEntityDead();
            this.setBeenAttacked();
            this.worldObj.entityJoinedWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.painting)));
        }
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setByte("Dir", (byte)this.direction);
        nBTTagCompound1.setString("Motive", this.art.title);
        nBTTagCompound1.setInteger("TileX", this.xPosition);
        nBTTagCompound1.setInteger("TileY", this.yPosition);
        nBTTagCompound1.setInteger("TileZ", this.zPosition);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.direction = nBTTagCompound1.getByte("Dir");
        this.xPosition = nBTTagCompound1.getInteger("TileX");
        this.yPosition = nBTTagCompound1.getInteger("TileY");
        this.zPosition = nBTTagCompound1.getInteger("TileZ");
        String string2 = nBTTagCompound1.getString("Motive");
        EnumArt[] enumArt3 = EnumArt.values();
        int i4 = enumArt3.length;
        int i5 = 0;
        while (i5 < i4) {
            EnumArt enumArt6 = enumArt3[i5];
            if (enumArt6.title.equals(string2)) {
                this.art = enumArt6;
            }
            ++i5;
        }
        if (this.art == null) {
            this.art = EnumArt.Kebab;
        }
        this.func_412_b(this.direction);
    }
}

