/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityMinecart
extends Entity
implements IInventory {
    private ItemStack[] cargoItems = new ItemStack[36];
    public int minecartCurrentDamage = 0;
    public int minecartTimeSinceHit = 0;
    public int minecartRockDirection = 1;
    private boolean field_856_i = false;
    public int minecartType;
    public int fuel;
    public double pushX;
    public double pushZ;
    private static final int[][][] field_855_j;
    private int field_9415_k;
    private double field_9414_l;
    private double field_9413_m;
    private double field_9412_n;
    private double field_9411_o;
    private double field_9410_p;
    private double field_9409_q;
    private double field_9408_r;
    private double field_9407_s;

    static {
        int[][][] nArrayArray = new int[10][][];
        int[][] nArrayArray2 = new int[2][];
        int[] nArray = new int[3];
        nArray[2] = -1;
        nArrayArray2[0] = nArray;
        int[] nArray2 = new int[3];
        nArray2[2] = 1;
        nArrayArray2[1] = nArray2;
        nArrayArray[0] = nArrayArray2;
        int[][] nArrayArray3 = new int[2][];
        int[] nArray3 = new int[3];
        nArray3[0] = -1;
        nArrayArray3[0] = nArray3;
        int[] nArray4 = new int[3];
        nArray4[0] = 1;
        nArrayArray3[1] = nArray4;
        nArrayArray[1] = nArrayArray3;
        int[][] nArrayArray4 = new int[2][];
        int[] nArray5 = new int[3];
        nArray5[0] = -1;
        nArray5[1] = -1;
        nArrayArray4[0] = nArray5;
        int[] nArray6 = new int[3];
        nArray6[0] = 1;
        nArrayArray4[1] = nArray6;
        nArrayArray[2] = nArrayArray4;
        int[][] nArrayArray5 = new int[2][];
        int[] nArray7 = new int[3];
        nArray7[0] = -1;
        nArrayArray5[0] = nArray7;
        int[] nArray8 = new int[3];
        nArray8[0] = 1;
        nArray8[1] = -1;
        nArrayArray5[1] = nArray8;
        nArrayArray[3] = nArrayArray5;
        int[][] nArrayArray6 = new int[2][];
        int[] nArray9 = new int[3];
        nArray9[2] = -1;
        nArrayArray6[0] = nArray9;
        int[] nArray10 = new int[3];
        nArray10[1] = -1;
        nArray10[2] = 1;
        nArrayArray6[1] = nArray10;
        nArrayArray[4] = nArrayArray6;
        int[][] nArrayArray7 = new int[2][];
        int[] nArray11 = new int[3];
        nArray11[1] = -1;
        nArray11[2] = -1;
        nArrayArray7[0] = nArray11;
        int[] nArray12 = new int[3];
        nArray12[2] = 1;
        nArrayArray7[1] = nArray12;
        nArrayArray[5] = nArrayArray7;
        int[][] nArrayArray8 = new int[2][];
        int[] nArray13 = new int[3];
        nArray13[2] = 1;
        nArrayArray8[0] = nArray13;
        int[] nArray14 = new int[3];
        nArray14[0] = 1;
        nArrayArray8[1] = nArray14;
        nArrayArray[6] = nArrayArray8;
        int[][] nArrayArray9 = new int[2][];
        int[] nArray15 = new int[3];
        nArray15[2] = 1;
        nArrayArray9[0] = nArray15;
        int[] nArray16 = new int[3];
        nArray16[0] = -1;
        nArrayArray9[1] = nArray16;
        nArrayArray[7] = nArrayArray9;
        int[][] nArrayArray10 = new int[2][];
        int[] nArray17 = new int[3];
        nArray17[2] = -1;
        nArrayArray10[0] = nArray17;
        int[] nArray18 = new int[3];
        nArray18[0] = -1;
        nArrayArray10[1] = nArray18;
        nArrayArray[8] = nArrayArray10;
        int[][] nArrayArray11 = new int[2][];
        int[] nArray19 = new int[3];
        nArray19[2] = -1;
        nArrayArray11[0] = nArray19;
        int[] nArray20 = new int[3];
        nArray20[0] = 1;
        nArrayArray11[1] = nArray20;
        nArrayArray[9] = nArrayArray11;
        field_855_j = nArrayArray;
    }

    public EntityMinecart(World world1) {
        super(world1);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
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
        return null;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityMinecart(World world1, double d2, double d4, double d6, int i8) {
        this(world1);
        this.setPosition(d2, d4 + (double)this.yOffset, d6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d2;
        this.prevPosY = d4;
        this.prevPosZ = d6;
        this.minecartType = i8;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.0 - (double)0.3f;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (!this.worldObj.multiplayerWorld && !this.isDead) {
            this.minecartRockDirection = -this.minecartRockDirection;
            this.minecartTimeSinceHit = 10;
            this.setBeenAttacked();
            this.minecartCurrentDamage += i2 * 10;
            if (this.minecartCurrentDamage > 40) {
                this.dropItemWithOffset(Item.minecartEmpty.shiftedIndex, 1, 0.0f);
                if (this.minecartType == 1) {
                    this.dropItemWithOffset(Block.crate.blockID, 1, 0.0f);
                } else if (this.minecartType == 2) {
                    this.dropItemWithOffset(Block.stoneOvenIdle.blockID, 1, 0.0f);
                }
                this.setEntityDead();
            }
            return true;
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        System.out.println("Animating hurt");
        this.minecartRockDirection = -this.minecartRockDirection;
        this.minecartTimeSinceHit = 10;
        this.minecartCurrentDamage += this.minecartCurrentDamage * 10;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setEntityDead() {
        int i1 = 0;
        while (i1 < this.getSizeInventory()) {
            ItemStack itemStack2 = this.getStackInSlot(i1);
            if (itemStack2 != null) {
                float f3 = this.rand.nextFloat() * 0.8f + 0.1f;
                float f4 = this.rand.nextFloat() * 0.8f + 0.1f;
                float f5 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (itemStack2.stackSize > 0) {
                    int i6 = this.rand.nextInt(21) + 10;
                    if (i6 > itemStack2.stackSize) {
                        i6 = itemStack2.stackSize;
                    }
                    itemStack2.stackSize -= i6;
                    EntityItem entityItem7 = new EntityItem(this.worldObj, this.posX + (double)f3, this.posY + (double)f4, this.posZ + (double)f5, new ItemStack(itemStack2.itemID, i6, itemStack2.getItemDamage()));
                    float f8 = 0.05f;
                    entityItem7.motionX = (float)this.rand.nextGaussian() * f8;
                    entityItem7.motionY = (float)this.rand.nextGaussian() * f8 + 0.2f;
                    entityItem7.motionZ = (float)this.rand.nextGaussian() * f8;
                    this.worldObj.entityJoinedWorld(entityItem7);
                }
            }
            ++i1;
        }
        super.setEntityDead();
    }

    @Override
    public void onUpdate() {
        if (this.minecartTimeSinceHit > 0) {
            --this.minecartTimeSinceHit;
        }
        if (this.minecartCurrentDamage > 0) {
            --this.minecartCurrentDamage;
        }
        if (this.worldObj.multiplayerWorld && this.field_9415_k > 0) {
            if (this.field_9415_k > 0) {
                double d41 = this.posX + (this.field_9414_l - this.posX) / (double)this.field_9415_k;
                double d42 = this.posY + (this.field_9413_m - this.posY) / (double)this.field_9415_k;
                double d5 = this.posZ + (this.field_9412_n - this.posZ) / (double)this.field_9415_k;
                double d7 = this.field_9411_o - (double)this.rotationYaw;
                while (d7 < -180.0) {
                    d7 += 360.0;
                }
                while (d7 >= 180.0) {
                    d7 -= 360.0;
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.field_9415_k);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9410_p - (double)this.rotationPitch) / (double)this.field_9415_k);
                --this.field_9415_k;
                this.setPosition(d41, d42, d5);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        } else {
            int i3;
            int i2;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= (double)0.04f;
            int i1 = MathHelper.floor_double(this.posX);
            if (this.worldObj.getBlockId(i1, (i2 = MathHelper.floor_double(this.posY)) - 1, i3 = MathHelper.floor_double(this.posZ)) == Block.minecartTrack.blockID) {
                --i2;
            }
            double d4 = 0.4;
            boolean z6 = false;
            double d7 = 0.0078125;
            if (this.worldObj.getBlockId(i1, i2, i3) == Block.minecartTrack.blockID) {
                double d39;
                double d36;
                double d34;
                double d32;
                Vec3D vec3D9 = this.func_514_g(this.posX, this.posY, this.posZ);
                int i10 = this.worldObj.getBlockMetadata(i1, i2, i3);
                this.posY = i2;
                if (i10 >= 2 && i10 <= 5) {
                    this.posY = i2 + 1;
                }
                if (i10 == 2) {
                    this.motionX -= d7;
                }
                if (i10 == 3) {
                    this.motionX += d7;
                }
                if (i10 == 4) {
                    this.motionZ += d7;
                }
                if (i10 == 5) {
                    this.motionZ -= d7;
                }
                int[][] i11 = field_855_j[i10];
                double d12 = i11[1][0] - i11[0][0];
                double d14 = i11[1][2] - i11[0][2];
                double d16 = Math.sqrt(d12 * d12 + d14 * d14);
                double d18 = this.motionX * d12 + this.motionZ * d14;
                if (d18 < 0.0) {
                    d12 = -d12;
                    d14 = -d14;
                }
                double d20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.motionX = d20 * d12 / d16;
                this.motionZ = d20 * d14 / d16;
                double d22 = 0.0;
                double d24 = (double)i1 + 0.5 + (double)i11[0][0] * 0.5;
                double d26 = (double)i3 + 0.5 + (double)i11[0][2] * 0.5;
                double d28 = (double)i1 + 0.5 + (double)i11[1][0] * 0.5;
                double d30 = (double)i3 + 0.5 + (double)i11[1][2] * 0.5;
                d12 = d28 - d24;
                d14 = d30 - d26;
                if (d12 == 0.0) {
                    this.posX = (double)i1 + 0.5;
                    d22 = this.posZ - (double)i3;
                } else if (d14 == 0.0) {
                    this.posZ = (double)i3 + 0.5;
                    d22 = this.posX - (double)i1;
                } else {
                    d32 = this.posX - d24;
                    d34 = this.posZ - d26;
                    d22 = d36 = (d32 * d12 + d34 * d14) * 2.0;
                }
                this.posX = d24 + d12 * d22;
                this.posZ = d26 + d14 * d22;
                this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);
                d32 = this.motionX;
                d34 = this.motionZ;
                if (this.riddenByEntity != null) {
                    d32 *= 0.75;
                    d34 *= 0.75;
                }
                if (d32 < -d4) {
                    d32 = -d4;
                }
                if (d32 > d4) {
                    d32 = d4;
                }
                if (d34 < -d4) {
                    d34 = -d4;
                }
                if (d34 > d4) {
                    d34 = d4;
                }
                this.moveEntity(d32, 0.0, d34);
                if (i11[0][1] != 0 && MathHelper.floor_double(this.posX) - i1 == i11[0][0] && MathHelper.floor_double(this.posZ) - i3 == i11[0][2]) {
                    this.setPosition(this.posX, this.posY + (double)i11[0][1], this.posZ);
                } else if (i11[1][1] != 0 && MathHelper.floor_double(this.posX) - i1 == i11[1][0] && MathHelper.floor_double(this.posZ) - i3 == i11[1][2]) {
                    this.setPosition(this.posX, this.posY + (double)i11[1][1], this.posZ);
                }
                if (this.riddenByEntity != null) {
                    this.motionX *= (double)0.997f;
                    this.motionY *= 0.0;
                    this.motionZ *= (double)0.997f;
                } else {
                    if (this.minecartType == 2) {
                        d36 = MathHelper.sqrt_double(this.pushX * this.pushX + this.pushZ * this.pushZ);
                        if (d36 > 0.01) {
                            z6 = true;
                            this.pushX /= d36;
                            this.pushZ /= d36;
                            double d38 = 0.04;
                            this.motionX *= (double)0.8f;
                            this.motionY *= 0.0;
                            this.motionZ *= (double)0.8f;
                            this.motionX += this.pushX * d38;
                            this.motionZ += this.pushZ * d38;
                        } else {
                            this.motionX *= (double)0.9f;
                            this.motionY *= 0.0;
                            this.motionZ *= (double)0.9f;
                        }
                    }
                    this.motionX *= (double)0.96f;
                    this.motionY *= 0.0;
                    this.motionZ *= (double)0.96f;
                }
                Vec3D vec3D46 = this.func_514_g(this.posX, this.posY, this.posZ);
                if (vec3D46 != null && vec3D9 != null) {
                    double d37 = (vec3D9.yCoord - vec3D46.yCoord) * 0.05;
                    d20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    if (d20 > 0.0) {
                        this.motionX = this.motionX / d20 * (d20 + d37);
                        this.motionZ = this.motionZ / d20 * (d20 + d37);
                    }
                    this.setPosition(this.posX, vec3D46.yCoord, this.posZ);
                }
                int i47 = MathHelper.floor_double(this.posX);
                int i48 = MathHelper.floor_double(this.posZ);
                if (i47 != i1 || i48 != i3) {
                    d20 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    this.motionX = d20 * (double)(i47 - i1);
                    this.motionZ = d20 * (double)(i48 - i3);
                }
                if (this.minecartType == 2 && (d39 = (double)MathHelper.sqrt_double(this.pushX * this.pushX + this.pushZ * this.pushZ)) > 0.01 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
                    this.pushX /= d39;
                    this.pushZ /= d39;
                    if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                        this.pushX = 0.0;
                        this.pushZ = 0.0;
                    } else {
                        this.pushX = this.motionX;
                        this.pushZ = this.motionZ;
                    }
                }
            } else {
                if (this.motionX < -d4) {
                    this.motionX = -d4;
                }
                if (this.motionX > d4) {
                    this.motionX = d4;
                }
                if (this.motionZ < -d4) {
                    this.motionZ = -d4;
                }
                if (this.motionZ > d4) {
                    this.motionZ = d4;
                }
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                if (!this.onGround) {
                    this.motionX *= (double)0.95f;
                    this.motionY *= (double)0.95f;
                    this.motionZ *= (double)0.95f;
                }
            }
            this.rotationPitch = 0.0f;
            double d43 = this.prevPosX - this.posX;
            double d44 = this.prevPosZ - this.posZ;
            if (d43 * d43 + d44 * d44 > 0.001) {
                this.rotationYaw = (float)(Math.atan2(d44, d43) * 180.0 / Math.PI);
                if (this.field_856_i) {
                    this.rotationYaw += 180.0f;
                }
            }
            double d13 = this.rotationYaw - this.prevRotationYaw;
            while (d13 >= 180.0) {
                d13 -= 360.0;
            }
            while (d13 < -180.0) {
                d13 += 360.0;
            }
            if (d13 < -170.0 || d13 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.field_856_i = !this.field_856_i;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            List list15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
            if (list15 != null && list15.size() > 0) {
                int i45 = 0;
                while (i45 < list15.size()) {
                    Entity entity17 = (Entity)list15.get(i45);
                    if (entity17 != this.riddenByEntity && entity17.canBePushed() && entity17 instanceof EntityMinecart) {
                        entity17.applyEntityCollision(this);
                    }
                    ++i45;
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                this.riddenByEntity = null;
            }
            if (z6 && this.rand.nextInt(4) == 0) {
                --this.fuel;
                if (this.fuel < 0) {
                    this.pushZ = 0.0;
                    this.pushX = 0.0;
                }
                this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    public Vec3D func_515_a(double d1, double d3, double d5, double d7) {
        int i11;
        int i10;
        int i9 = MathHelper.floor_double(d1);
        if (this.worldObj.getBlockId(i9, (i10 = MathHelper.floor_double(d3)) - 1, i11 = MathHelper.floor_double(d5)) == Block.minecartTrack.blockID) {
            --i10;
        }
        if (this.worldObj.getBlockId(i9, i10, i11) == Block.minecartTrack.blockID) {
            int i12 = this.worldObj.getBlockMetadata(i9, i10, i11);
            d3 = i10;
            if (i12 >= 2 && i12 <= 5) {
                d3 = i10 + 1;
            }
            int[][] i13 = field_855_j[i12];
            double d14 = i13[1][0] - i13[0][0];
            double d16 = i13[1][2] - i13[0][2];
            double d18 = Math.sqrt(d14 * d14 + d16 * d16);
            if (i13[0][1] != 0 && MathHelper.floor_double(d1 += (d14 /= d18) * d7) - i9 == i13[0][0] && MathHelper.floor_double(d5 += (d16 /= d18) * d7) - i11 == i13[0][2]) {
                d3 += (double)i13[0][1];
            } else if (i13[1][1] != 0 && MathHelper.floor_double(d1) - i9 == i13[1][0] && MathHelper.floor_double(d5) - i11 == i13[1][2]) {
                d3 += (double)i13[1][1];
            }
            return this.func_514_g(d1, d3, d5);
        }
        return null;
    }

    public Vec3D func_514_g(double d1, double d3, double d5) {
        int i9;
        int i8;
        int i7 = MathHelper.floor_double(d1);
        if (this.worldObj.getBlockId(i7, (i8 = MathHelper.floor_double(d3)) - 1, i9 = MathHelper.floor_double(d5)) == Block.minecartTrack.blockID) {
            --i8;
        }
        if (this.worldObj.getBlockId(i7, i8, i9) == Block.minecartTrack.blockID) {
            int i10 = this.worldObj.getBlockMetadata(i7, i8, i9);
            d3 = i8;
            if (i10 >= 2 && i10 <= 5) {
                d3 = i8 + 1;
            }
            int[][] i11 = field_855_j[i10];
            double d12 = 0.0;
            double d14 = (double)i7 + 0.5 + (double)i11[0][0] * 0.5;
            double d16 = (double)i8 + 0.5 + (double)i11[0][1] * 0.5;
            double d18 = (double)i9 + 0.5 + (double)i11[0][2] * 0.5;
            double d20 = (double)i7 + 0.5 + (double)i11[1][0] * 0.5;
            double d22 = (double)i8 + 0.5 + (double)i11[1][1] * 0.5;
            double d24 = (double)i9 + 0.5 + (double)i11[1][2] * 0.5;
            double d26 = d20 - d14;
            double d28 = (d22 - d16) * 2.0;
            double d30 = d24 - d18;
            if (d26 == 0.0) {
                d1 = (double)i7 + 0.5;
                d12 = d5 - (double)i9;
            } else if (d30 == 0.0) {
                d5 = (double)i9 + 0.5;
                d12 = d1 - (double)i7;
            } else {
                double d36;
                double d32 = d1 - d14;
                double d34 = d5 - d18;
                d12 = d36 = (d32 * d26 + d34 * d30) * 2.0;
            }
            d1 = d14 + d26 * d12;
            d3 = d16 + d28 * d12;
            d5 = d18 + d30 * d12;
            if (d28 < 0.0) {
                d3 += 1.0;
            }
            if (d28 > 0.0) {
                d3 += 0.5;
            }
            return Vec3D.createVector(d1, d3, d5);
        }
        return null;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setInteger("Type", this.minecartType);
        if (this.minecartType == 2) {
            nBTTagCompound1.setDouble("PushX", this.pushX);
            nBTTagCompound1.setDouble("PushZ", this.pushZ);
            nBTTagCompound1.setShort("Fuel", (short)this.fuel);
        } else if (this.minecartType == 1) {
            NBTTagList nBTTagList2 = new NBTTagList();
            int i3 = 0;
            while (i3 < this.cargoItems.length) {
                if (this.cargoItems[i3] != null) {
                    NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                    nBTTagCompound4.setByte("Slot", (byte)i3);
                    this.cargoItems[i3].writeToNBT(nBTTagCompound4);
                    nBTTagList2.setTag(nBTTagCompound4);
                }
                ++i3;
            }
            nBTTagCompound1.setTag("Items", nBTTagList2);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.minecartType = nBTTagCompound1.getInteger("Type");
        if (this.minecartType == 2) {
            this.pushX = nBTTagCompound1.getDouble("PushX");
            this.pushZ = nBTTagCompound1.getDouble("PushZ");
            this.fuel = nBTTagCompound1.getShort("Fuel");
        } else if (this.minecartType == 1) {
            NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Items");
            this.cargoItems = new ItemStack[this.getSizeInventory()];
            int i3 = 0;
            while (i3 < nBTTagList2.tagCount()) {
                NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nBTTagList2.tagAt(i3);
                int i5 = nBTTagCompound4.getByte("Slot") & 0xFF;
                if (i5 >= 0 && i5 < this.cargoItems.length) {
                    this.cargoItems[i5] = new ItemStack(nBTTagCompound4);
                }
                ++i3;
            }
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public void applyEntityCollision(Entity entity1) {
        if (!this.worldObj.multiplayerWorld && entity1 != this.riddenByEntity) {
            double d4;
            double d2;
            double d6;
            if (entity1 instanceof EntityLiving && !(entity1 instanceof EntityPlayer) && this.minecartType == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && entity1.ridingEntity == null) {
                entity1.mountEntity(this);
            }
            if ((d6 = (d2 = entity1.posX - this.posX) * d2 + (d4 = entity1.posZ - this.posZ) * d4) >= (double)1.0E-4f) {
                d6 = MathHelper.sqrt_double(d6);
                d2 /= d6;
                d4 /= d6;
                double d8 = 1.0 / d6;
                if (d8 > 1.0) {
                    d8 = 1.0;
                }
                d2 *= d8;
                d4 *= d8;
                d2 *= (double)0.1f;
                d4 *= (double)0.1f;
                d2 *= (double)(1.0f - this.entityCollisionReduction);
                d4 *= (double)(1.0f - this.entityCollisionReduction);
                d2 *= 0.5;
                d4 *= 0.5;
                if (entity1 instanceof EntityMinecart) {
                    double d10 = entity1.motionX + this.motionX;
                    double d12 = entity1.motionZ + this.motionZ;
                    if (((EntityMinecart)entity1).minecartType == 2 && this.minecartType != 2) {
                        this.motionX *= (double)0.2f;
                        this.motionZ *= (double)0.2f;
                        this.addVelocity(entity1.motionX - d2, 0.0, entity1.motionZ - d4);
                        entity1.motionX *= (double)0.7f;
                        entity1.motionZ *= (double)0.7f;
                    } else if (((EntityMinecart)entity1).minecartType != 2 && this.minecartType == 2) {
                        entity1.motionX *= (double)0.2f;
                        entity1.motionZ *= (double)0.2f;
                        entity1.addVelocity(this.motionX + d2, 0.0, this.motionZ + d4);
                        this.motionX *= (double)0.7f;
                        this.motionZ *= (double)0.7f;
                    } else {
                        this.motionX *= (double)0.2f;
                        this.motionZ *= (double)0.2f;
                        this.addVelocity((d10 /= 2.0) - d2, 0.0, (d12 /= 2.0) - d4);
                        entity1.motionX *= (double)0.2f;
                        entity1.motionZ *= (double)0.2f;
                        entity1.addVelocity(d10 + d2, 0.0, d12 + d4);
                    }
                } else {
                    this.addVelocity(-d2, 0.0, -d4);
                    entity1.addVelocity(d2 / 4.0, 0.0, d4 / 4.0);
                }
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return this.cargoItems[i1];
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        if (this.cargoItems[i1] != null) {
            if (this.cargoItems[i1].stackSize <= i2) {
                ItemStack itemStack3 = this.cargoItems[i1];
                this.cargoItems[i1] = null;
                return itemStack3;
            }
            ItemStack itemStack3 = this.cargoItems[i1].splitStack(i2);
            if (this.cargoItems[i1].stackSize == 0) {
                this.cargoItems[i1] = null;
            }
            return itemStack3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.cargoItems[i1] = itemStack2;
        if (itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
            itemStack2.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Minecart";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean interact(EntityPlayer entityPlayer1) {
        if (this.minecartType == 0) {
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer1) {
                return true;
            }
            if (!this.worldObj.multiplayerWorld) {
                entityPlayer1.mountEntity(this);
            }
        } else if (this.minecartType == 1) {
            if (!this.worldObj.multiplayerWorld) {
                entityPlayer1.displayGUIChest(this);
            }
        } else if (this.minecartType == 2) {
            ItemStack itemStack2 = entityPlayer1.inventory.getCurrentItem();
            if (itemStack2 != null && itemStack2.itemID == Item.coal.shiftedIndex) {
                if (--itemStack2.stackSize == 0) {
                    entityPlayer1.inventory.setInventorySlotContents(entityPlayer1.inventory.currentItem, null);
                }
                this.fuel += 1200;
            }
            this.pushX = this.posX - entityPlayer1.posX;
            this.pushZ = this.posZ - entityPlayer1.posZ;
        }
        return true;
    }

    @Override
    public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
        this.field_9414_l = d1;
        this.field_9413_m = d3;
        this.field_9412_n = d5;
        this.field_9411_o = f7;
        this.field_9410_p = f8;
        this.field_9415_k = i9 + 2;
        this.motionX = this.field_9409_q;
        this.motionY = this.field_9408_r;
        this.motionZ = this.field_9407_s;
    }

    @Override
    public void setVelocity(double d1, double d3, double d5) {
        this.field_9409_q = this.motionX = d1;
        this.field_9408_r = this.motionY = d3;
        this.field_9407_s = this.motionZ = d5;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.isDead ? false : entityPlayer1.getDistanceSqToEntity(this) <= 64.0;
    }
}

