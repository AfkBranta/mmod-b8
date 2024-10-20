/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.events.EventMoveEntity;
import MEDMEX.Events.events.EventMoveFlying;
import MEDMEX.Modules.Player.Freecam;
import java.util.List;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluids;
import net.minecraft.src.DataWatcher;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagDouble;
import net.minecraft.src.NBTTagFloat;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.StepSound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public abstract class Entity {
    private static int nextEntityID = 0;
    public int entityId = nextEntityID++;
    public double renderDistanceWeight = 1.0;
    public boolean preventEntitySpawning = false;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public boolean onGround = false;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided = false;
    public boolean beenAttacked = false;
    public boolean field_9293_aM = true;
    public boolean isDead = false;
    public float yOffset = 0.0f;
    public float width = 0.6f;
    public float height = 1.8f;
    public float prevDistanceWalkedModified = 0.0f;
    public float distanceWalkedModified = 0.0f;
    public float fallDistance = 0.0f;
    private int nextStepDistance = 1;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize = 0.0f;
    public float stepHeight = 0.0f;
    public boolean noClip = false;
    public float entityCollisionReduction = 0.0f;
    public boolean field_9313_bc = false;
    protected Random rand = new Random();
    public int ticksExisted = 0;
    public int fireResistance = 1;
    public int fire = 0;
    protected int maxAir = 300;
    protected boolean inWater = false;
    public int field_9306_bj = 0;
    public int air = 300;
    private boolean isFirstUpdate = true;
    public String skinUrl;
    public String cloakUrl;
    protected boolean isImmuneToFire = false;
    protected DataWatcher dataWatcher = new DataWatcher();
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk = false;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;

    public Entity(World world1) {
        this.worldObj = world1;
        this.setPosition(0.0, 0.0, 0.0);
        this.dataWatcher.addObject(0, (byte)0);
        this.entityInit();
    }

    protected abstract void entityInit();

    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    public boolean equals(Object object1) {
        return object1 instanceof Entity ? ((Entity)object1).entityId == this.entityId : false;
    }

    public int hashCode() {
        return this.entityId;
    }

    public void copyDataFrom(Entity par1Entity, boolean par2) {
        NBTTagCompound var3 = new NBTTagCompound();
        par1Entity.writeToNBT(var3);
        this.readFromNBT(var3);
    }

    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0) break;
                this.posY += 1.0;
            }
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.rotationPitch = 0.0f;
        }
    }

    public void setEntityDead() {
        this.isDead = true;
    }

    protected void setSize(float f1, float f2) {
        this.width = f1;
        this.height = f2;
    }

    protected void setRotation(float f1, float f2) {
        this.rotationYaw = f1;
        this.rotationPitch = f2;
    }

    public void setPosition(double d1, double d3, double d5) {
        this.posX = d1;
        this.posY = d3;
        this.posZ = d5;
        float f7 = this.width / 2.0f;
        float f8 = this.height;
        this.boundingBox.setBounds(d1 - (double)f7, d3 - (double)this.yOffset + (double)this.ySize, d5 - (double)f7, d1 + (double)f7, d3 - (double)this.yOffset + (double)this.ySize + (double)f8, d5 + (double)f7);
    }

    public void func_346_d(float f1, float f2) {
        float f3 = this.rotationPitch;
        float f4 = this.rotationYaw;
        this.rotationYaw = (float)((double)this.rotationYaw + (double)f1 * 0.15);
        this.rotationPitch = (float)((double)this.rotationPitch - (double)f2 * 0.15);
        if (this.rotationPitch < -90.0f) {
            this.rotationPitch = -90.0f;
        }
        if (this.rotationPitch > 90.0f) {
            this.rotationPitch = 90.0f;
        }
        this.prevRotationPitch += this.rotationPitch - f3;
        this.prevRotationYaw += this.rotationYaw - f4;
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        ++this.ticksExisted;
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (this.handleWaterMovement()) {
            if (!this.inWater && !this.isFirstUpdate) {
                float f5;
                float f4;
                float f1 = MathHelper.sqrt_double(this.motionX * this.motionX * (double)0.2f + this.motionY * this.motionY + this.motionZ * this.motionZ * (double)0.2f) * 0.2f;
                if (f1 > 1.0f) {
                    f1 = 1.0f;
                }
                this.worldObj.playSoundAtEntity(this, "random.splash", f1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                float f2 = MathHelper.floor_double(this.boundingBox.minY);
                int i3 = 0;
                while ((float)i3 < 1.0f + this.width * 20.0f) {
                    f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("bubble", this.posX + (double)f4, f2 + 1.0f, this.posZ + (double)f5, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2f), this.motionZ);
                    ++i3;
                }
                i3 = 0;
                while ((float)i3 < 1.0f + this.width * 20.0f) {
                    f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("splash", this.posX + (double)f4, f2 + 1.0f, this.posZ + (double)f5, this.motionX, this.motionY, this.motionZ);
                    ++i3;
                }
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        } else {
            this.inWater = false;
        }
        if (this.worldObj.multiplayerWorld) {
            this.fire = 0;
        } else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            } else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(null, 1);
                }
                --this.fire;
            }
        }
        if (this.handleLavaMovement()) {
            this.setOnFireFromLava();
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.multiplayerWorld) {
            this.setEntityFlag(0, this.fire > 0);
            this.setEntityFlag(2, this.ridingEntity != null);
        }
        this.isFirstUpdate = false;
    }

    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(null, 4);
            this.fire = 600;
        }
    }

    protected void kill() {
        this.setEntityDead();
    }

    public boolean isOffsetPositionInLiquid(double d1, double d3, double d5) {
        AxisAlignedBB axisAlignedBB7 = this.boundingBox.getOffsetBoundingBox(d1, d3, d5);
        List list8 = this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB7);
        return list8.size() > 0 ? false : !this.worldObj.getIsAnyLiquid(axisAlignedBB7);
    }

    public void moveEntity(double d1, double d3, double d5) {
        EventMoveEntity e = new EventMoveEntity(d1, d3, d5);
        Client.onEvent(e);
        if (e.getCancelled()) {
            return;
        }
        d1 = e.getMotionX();
        d3 = e.getMotionY();
        d5 = e.getMotionZ();
        if (this.noClip) {
            this.boundingBox.offset(d1, d3, d5);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        } else {
            int i30;
            int i41;
            int i40;
            int i26;
            int i38;
            int i28;
            double d23;
            double d37;
            boolean z18;
            double d7 = this.posX;
            double d9 = this.posZ;
            double d11 = d1;
            double d13 = d3;
            double d15 = d5;
            AxisAlignedBB axisAlignedBB17 = this.boundingBox.copy();
            boolean bl = z18 = this.onGround && this.isSneaking();
            if (z18) {
                double d19 = 0.05;
                while (d1 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(d1, -1.0, 0.0)).size() == 0) {
                    d1 = d1 < d19 && d1 >= -d19 ? 0.0 : (d1 > 0.0 ? (d1 -= d19) : (d1 += d19));
                    d11 = d1;
                }
                while (d5 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0, -1.0, d5)).size() == 0) {
                    d5 = d5 < d19 && d5 >= -d19 ? 0.0 : (d5 > 0.0 ? (d5 -= d19) : (d5 += d19));
                    d15 = d5;
                }
            }
            List list35 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(d1, d3, d5));
            int i20 = 0;
            while (i20 < list35.size()) {
                d3 = ((AxisAlignedBB)list35.get(i20)).calculateYOffset(this.boundingBox, d3);
                ++i20;
            }
            this.boundingBox.offset(0.0, d3, 0.0);
            if (!this.field_9293_aM && d13 != d3) {
                d5 = 0.0;
                d3 = 0.0;
                d1 = 0.0;
            }
            boolean z36 = this.onGround || d13 != d3 && d13 < 0.0;
            int i21 = 0;
            while (i21 < list35.size()) {
                d1 = ((AxisAlignedBB)list35.get(i21)).calculateXOffset(this.boundingBox, d1);
                ++i21;
            }
            this.boundingBox.offset(d1, 0.0, 0.0);
            if (!this.field_9293_aM && d11 != d1) {
                d5 = 0.0;
                d3 = 0.0;
                d1 = 0.0;
            }
            i21 = 0;
            while (i21 < list35.size()) {
                d5 = ((AxisAlignedBB)list35.get(i21)).calculateZOffset(this.boundingBox, d5);
                ++i21;
            }
            this.boundingBox.offset(0.0, 0.0, d5);
            if (!this.field_9293_aM && d15 != d5) {
                d5 = 0.0;
                d3 = 0.0;
                d1 = 0.0;
            }
            if (this.stepHeight > 0.0f && z36 && this.ySize < 0.05f && (d11 != d1 || d15 != d5)) {
                d37 = d1;
                d23 = d3;
                double d25 = d5;
                d1 = d11;
                d3 = this.stepHeight;
                d5 = d15;
                AxisAlignedBB axisAlignedBB27 = this.boundingBox.copy();
                this.boundingBox.setBB(axisAlignedBB17);
                list35 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(d11, d3, d15));
                i28 = 0;
                while (i28 < list35.size()) {
                    d3 = ((AxisAlignedBB)list35.get(i28)).calculateYOffset(this.boundingBox, d3);
                    ++i28;
                }
                this.boundingBox.offset(0.0, d3, 0.0);
                if (!this.field_9293_aM && d13 != d3) {
                    d5 = 0.0;
                    d3 = 0.0;
                    d1 = 0.0;
                }
                i28 = 0;
                while (i28 < list35.size()) {
                    d1 = ((AxisAlignedBB)list35.get(i28)).calculateXOffset(this.boundingBox, d1);
                    ++i28;
                }
                this.boundingBox.offset(d1, 0.0, 0.0);
                if (!this.field_9293_aM && d11 != d1) {
                    d5 = 0.0;
                    d3 = 0.0;
                    d1 = 0.0;
                }
                i28 = 0;
                while (i28 < list35.size()) {
                    d5 = ((AxisAlignedBB)list35.get(i28)).calculateZOffset(this.boundingBox, d5);
                    ++i28;
                }
                this.boundingBox.offset(0.0, 0.0, d5);
                if (!this.field_9293_aM && d15 != d5) {
                    d5 = 0.0;
                    d3 = 0.0;
                    d1 = 0.0;
                }
                if (d37 * d37 + d25 * d25 >= d1 * d1 + d5 * d5) {
                    d1 = d37;
                    d3 = d23;
                    d5 = d25;
                    this.boundingBox.setBB(axisAlignedBB27);
                } else {
                    this.ySize = (float)((double)this.ySize + 0.5);
                }
            }
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            this.isCollidedHorizontally = d11 != d1 || d15 != d5;
            this.isCollidedVertically = d13 != d3;
            this.onGround = d13 != d3 && d13 < 0.0;
            this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
            this.updateFallState(d3, this.onGround);
            if (d11 != d1) {
                this.motionX = 0.0;
            }
            if (d13 != d3) {
                this.motionY = 0.0;
            }
            if (d15 != d5) {
                this.motionZ = 0.0;
            }
            d37 = this.posX - d7;
            d23 = this.posZ - d9;
            if (this.canTriggerWalking() && !z18) {
                this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt_double(d37 * d37 + d23 * d23) * 0.6);
                i38 = MathHelper.floor_double(this.posX);
                int i262 = MathHelper.floor_double(this.posY - (double)0.2f - (double)this.yOffset);
                int i402 = MathHelper.floor_double(this.posZ);
                i28 = this.worldObj.getBlockId(i38, i262, i402);
                if (this.distanceWalkedModified > (float)this.nextStepDistance && i28 > 0) {
                    ++this.nextStepDistance;
                    StepSound stepSound29 = Block.blocksList[i28].stepSound;
                    if (this.worldObj.getBlockId(i38, i262 + 1, i402) == Block.snow.blockID) {
                        stepSound29 = Block.snow.stepSound;
                        this.worldObj.playSoundAtEntity(this, stepSound29.func_1145_d(), stepSound29.func_1147_b() * 0.15f, stepSound29.func_1144_c());
                    } else if (!Block.blocksList[i28].blockMaterial.getIsLiquid()) {
                        this.worldObj.playSoundAtEntity(this, stepSound29.func_1145_d(), stepSound29.func_1147_b() * 0.15f, stepSound29.func_1144_c());
                    }
                    Block.blocksList[i28].onEntityWalking(this.worldObj, i38, i262, i402, this);
                }
            }
            if (this.worldObj.checkChunksExist(i38 = MathHelper.floor_double(this.boundingBox.minX), i26 = MathHelper.floor_double(this.boundingBox.minY), i40 = MathHelper.floor_double(this.boundingBox.minZ), i28 = MathHelper.floor_double(this.boundingBox.maxX), i41 = MathHelper.floor_double(this.boundingBox.maxY), i30 = MathHelper.floor_double(this.boundingBox.maxZ))) {
                int i31 = i38;
                while (i31 <= i28) {
                    int i32 = i26;
                    while (i32 <= i41) {
                        int i33 = i40;
                        while (i33 <= i30) {
                            int i34 = this.worldObj.getBlockId(i31, i32, i33);
                            if (i34 > 0) {
                                Block.blocksList[i34].onEntityCollidedWithBlock(this.worldObj, i31, i32, i33, this);
                            }
                            ++i33;
                        }
                        ++i32;
                    }
                    ++i31;
                }
            }
            this.ySize *= 0.4f;
            boolean z39 = this.handleWaterMovement();
            if (this.worldObj.isBoundingBoxBurning(this.boundingBox)) {
                this.dealFireDamage(1);
                if (!z39) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.fire = 300;
                    }
                }
            } else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (z39 && this.fire > 0) {
                this.worldObj.playSoundAtEntity(this, "random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
        }
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void updateFallState(double d1, boolean z3) {
        if (z3) {
            if (this.fallDistance > 0.0f) {
                this.fall(this.fallDistance);
                this.fallDistance = 0.0f;
            }
        } else if (d1 < 0.0) {
            this.fallDistance = (float)((double)this.fallDistance - d1);
        }
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    protected void dealFireDamage(int i1) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(null, i1);
        }
    }

    protected void fall(float f1) {
    }

    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.4f, 0.0), Material.water, this);
    }

    public boolean isInsideOfMaterial(Material material1) {
        int i6;
        int i5;
        double d2 = this.posY + (double)this.getEyeHeight();
        int i4 = MathHelper.floor_double(this.posX);
        int i7 = this.worldObj.getBlockId(i4, i5 = MathHelper.floor_float(MathHelper.floor_double(d2)), i6 = MathHelper.floor_double(this.posZ));
        if (i7 != 0 && Block.blocksList[i7].blockMaterial == material1) {
            float f8 = BlockFluids.getPercentAir(this.worldObj.getBlockMetadata(i4, i5, i6)) - 0.11111111f;
            float f9 = (float)(i5 + 1) - f8;
            return d2 < (double)f9;
        }
        return false;
    }

    public float getEyeHeight() {
        return 0.0f;
    }

    public boolean handleLavaMovement() {
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.1f, -0.4f, -0.1f), Material.lava);
    }

    public void moveFlying(float f1, float f2, float f3) {
        EventMoveFlying event = new EventMoveFlying(this.rotationYaw);
        Client.onEvent(event);
        float f4 = MathHelper.sqrt_float(f1 * f1 + f2 * f2);
        if (f4 >= 0.01f) {
            if (f4 < 1.0f) {
                f4 = 1.0f;
            }
            f4 = f3 / f4;
            float f5 = MathHelper.sin(event.getYaw() * (float)Math.PI / 180.0f);
            float f6 = MathHelper.cos(event.getYaw() * (float)Math.PI / 180.0f);
            this.motionX += (double)((f1 *= f4) * f6 - (f2 *= f4) * f5);
            this.motionZ += (double)(f2 * f6 + f1 * f5);
        }
    }

    public float getEntityBrightness(float f1) {
        int i2 = MathHelper.floor_double(this.posX);
        double d3 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
        int i5 = MathHelper.floor_double(this.posY - (double)this.yOffset + d3);
        int i6 = MathHelper.floor_double(this.posZ);
        return this.worldObj.checkChunksExist(MathHelper.floor_double(this.boundingBox.minX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.boundingBox.minZ), MathHelper.floor_double(this.boundingBox.maxX), MathHelper.floor_double(this.boundingBox.maxY), MathHelper.floor_double(this.boundingBox.maxZ)) ? this.worldObj.getLightBrightness(i2, i5, i6) : 0.0f;
    }

    public void setWorld(World world1) {
        this.worldObj = world1;
    }

    public void setPositionAndRotation(double d1, double d3, double d5, float f7, float f8) {
        this.prevPosX = this.posX = d1;
        this.prevPosY = this.posY = d3;
        this.prevPosZ = this.posZ = d5;
        this.prevRotationYaw = this.rotationYaw = f7;
        this.prevRotationPitch = this.rotationPitch = f8;
        this.ySize = 0.0f;
        double d9 = this.prevRotationYaw - f7;
        if (d9 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (d9 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(f7, f8);
    }

    public void setLocationAndAngles(double d1, double d3, double d5, float f7, float f8) {
        this.prevPosX = this.posX = d1;
        this.lastTickPosX = this.posX;
        this.prevPosY = this.posY = d3 + (double)this.yOffset;
        this.lastTickPosY = this.posY;
        this.prevPosZ = this.posZ = d5;
        this.lastTickPosZ = this.posZ;
        this.rotationYaw = f7;
        this.rotationPitch = f8;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public float getDistanceToEntity(Entity entity1) {
        float f2 = (float)(this.posX - entity1.posX);
        float f3 = (float)(this.posY - entity1.posY);
        float f4 = (float)(this.posZ - entity1.posZ);
        return MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4);
    }

    public double getDistanceSq(double d1, double d3, double d5) {
        double d7 = this.posX - d1;
        double d9 = this.posY - d3;
        double d11 = this.posZ - d5;
        return d7 * d7 + d9 * d9 + d11 * d11;
    }

    public double getDistance(double d1, double d3, double d5) {
        double d7 = this.posX - d1;
        double d9 = this.posY - d3;
        double d11 = this.posZ - d5;
        return MathHelper.sqrt_double(d7 * d7 + d9 * d9 + d11 * d11);
    }

    public double getDistanceSqToEntity(Entity entity1) {
        double d2 = this.posX - entity1.posX;
        double d4 = this.posY - entity1.posY;
        double d6 = this.posZ - entity1.posZ;
        return d2 * d2 + d4 * d4 + d6 * d6;
    }

    public void onCollideWithPlayer(EntityPlayer entityPlayer1) {
    }

    public void applyEntityCollision(Entity entity1) {
        double d4;
        double d2;
        double d6;
        if (entity1.riddenByEntity != this && entity1.ridingEntity != this && (d6 = MathHelper.abs_max(d2 = entity1.posX - this.posX, d4 = entity1.posZ - this.posZ)) >= (double)0.01f) {
            d6 = MathHelper.sqrt_double(d6);
            d2 /= d6;
            d4 /= d6;
            double d8 = 1.0 / d6;
            if (d8 > 1.0) {
                d8 = 1.0;
            }
            d2 *= d8;
            d4 *= d8;
            d2 *= (double)0.05f;
            d4 *= (double)0.05f;
            this.addVelocity(-(d2 *= (double)(1.0f - this.entityCollisionReduction)), 0.0, -(d4 *= (double)(1.0f - this.entityCollisionReduction)));
            entity1.addVelocity(d2, 0.0, d4);
        }
    }

    public void addVelocity(double d1, double d3, double d5) {
        this.motionX += d1;
        this.motionY += d3;
        this.motionZ += d5;
    }

    protected void setBeenAttacked() {
        this.beenAttacked = true;
    }

    public boolean attackEntityFrom(Entity entity1, int i2) {
        this.setBeenAttacked();
        return false;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    public void addToPlayerScore(Entity entity1, int i2) {
    }

    public boolean isInRangeToRenderVec3D(Vec3D vec3D1) {
        double d2 = this.posX - vec3D1.xCoord;
        double d4 = this.posY - vec3D1.yCoord;
        double d6 = this.posZ - vec3D1.zCoord;
        double d8 = d2 * d2 + d4 * d4 + d6 * d6;
        return this.isInRangeToRenderDist(d8);
    }

    public boolean isInRangeToRenderDist(double d1) {
        double d3 = this.boundingBox.getAverageEdgeLength();
        return d1 < (d3 *= 64.0 * this.renderDistanceWeight) * d3;
    }

    public String getEntityTexture() {
        return null;
    }

    public boolean addEntityID(NBTTagCompound nBTTagCompound1) {
        String string2 = this.getEntityString();
        if (!this.isDead && string2 != null) {
            nBTTagCompound1.setString("id", string2);
            this.writeToNBT(nBTTagCompound1);
            return true;
        }
        return false;
    }

    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
        nBTTagCompound1.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
        nBTTagCompound1.setTag("Rotation", this.func_377_a(this.rotationYaw, this.rotationPitch));
        nBTTagCompound1.setFloat("FallDistance", this.fallDistance);
        nBTTagCompound1.setShort("Fire", (short)this.fire);
        nBTTagCompound1.setShort("Air", (short)this.air);
        nBTTagCompound1.setBoolean("OnGround", this.onGround);
        this.writeEntityToNBT(nBTTagCompound1);
    }

    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Pos");
        NBTTagList nBTTagList3 = nBTTagCompound1.getTagList("Motion");
        NBTTagList nBTTagList4 = nBTTagCompound1.getTagList("Rotation");
        this.setPosition(0.0, 0.0, 0.0);
        this.motionX = ((NBTTagDouble)nBTTagList3.tagAt((int)0)).doubleValue;
        this.motionY = ((NBTTagDouble)nBTTagList3.tagAt((int)1)).doubleValue;
        this.motionZ = ((NBTTagDouble)nBTTagList3.tagAt((int)2)).doubleValue;
        if (Math.abs(this.motionX) > 10.0) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) > 10.0) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) > 10.0) {
            this.motionZ = 0.0;
        }
        this.lastTickPosX = this.posX = ((NBTTagDouble)nBTTagList2.tagAt((int)0)).doubleValue;
        this.prevPosX = this.posX;
        this.lastTickPosY = this.posY = ((NBTTagDouble)nBTTagList2.tagAt((int)1)).doubleValue;
        this.prevPosY = this.posY;
        this.lastTickPosZ = this.posZ = ((NBTTagDouble)nBTTagList2.tagAt((int)2)).doubleValue;
        this.prevPosZ = this.posZ;
        this.prevRotationYaw = this.rotationYaw = ((NBTTagFloat)nBTTagList4.tagAt((int)0)).floatValue % ((float)Math.PI * 2);
        this.prevRotationPitch = this.rotationPitch = ((NBTTagFloat)nBTTagList4.tagAt((int)1)).floatValue % ((float)Math.PI * 2);
        this.fallDistance = nBTTagCompound1.getFloat("FallDistance");
        this.fire = nBTTagCompound1.getShort("Fire");
        this.air = nBTTagCompound1.getShort("Air");
        this.onGround = nBTTagCompound1.getBoolean("OnGround");
        this.setPosition(this.posX, this.posY, this.posZ);
        this.readEntityFromNBT(nBTTagCompound1);
    }

    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }

    protected abstract void readEntityFromNBT(NBTTagCompound var1);

    protected abstract void writeEntityToNBT(NBTTagCompound var1);

    protected NBTTagList newDoubleNBTList(double ... d1) {
        NBTTagList nBTTagList2 = new NBTTagList();
        double[] d3 = d1;
        int i4 = d1.length;
        int i5 = 0;
        while (i5 < i4) {
            double d6 = d3[i5];
            nBTTagList2.setTag(new NBTTagDouble(d6));
            ++i5;
        }
        return nBTTagList2;
    }

    protected NBTTagList func_377_a(float ... f1) {
        NBTTagList nBTTagList2 = new NBTTagList();
        float[] f3 = f1;
        int i4 = f1.length;
        int i5 = 0;
        while (i5 < i4) {
            float f6 = f3[i5];
            nBTTagList2.setTag(new NBTTagFloat(f6));
            ++i5;
        }
        return nBTTagList2;
    }

    public float getShadowSize() {
        return this.height / 2.0f;
    }

    public EntityItem dropItem(int i1, int i2) {
        return this.dropItemWithOffset(i1, i2, 0.0f);
    }

    public EntityItem dropItemWithOffset(int i1, int i2, float f3) {
        return this.entityDropItem(new ItemStack(i1, i2, 0), f3);
    }

    public EntityItem entityDropItem(ItemStack itemStack1, float f2) {
        EntityItem entityItem3 = new EntityItem(this.worldObj, this.posX, this.posY + (double)f2, this.posZ, itemStack1);
        entityItem3.delayBeforeCanPickup = 10;
        this.worldObj.entityJoinedWorld(entityItem3);
        return entityItem3;
    }

    public boolean isEntityAlive() {
        return !this.isDead;
    }

    public boolean isEntityInsideOpaqueBlock() {
        if (Freecam.instance.isEnabled) {
            return false;
        }
        int i1 = MathHelper.floor_double(this.posX);
        int i2 = MathHelper.floor_double(this.posY + (double)this.getEyeHeight());
        int i3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.isBlockOpaqueCube(i1, i2, i3);
    }

    public boolean interact(EntityPlayer entityPlayer1) {
        return false;
    }

    public AxisAlignedBB getCollisionBox(Entity entity1) {
        return null;
    }

    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        } else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            this.ridingEntity.updateRiderPosition();
            this.entityRiderYawDelta += (double)(this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
            this.entityRiderPitchDelta += (double)(this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch);
            while (this.entityRiderYawDelta >= 180.0) {
                this.entityRiderYawDelta -= 360.0;
            }
            while (this.entityRiderYawDelta < -180.0) {
                this.entityRiderYawDelta += 360.0;
            }
            while (this.entityRiderPitchDelta >= 180.0) {
                this.entityRiderPitchDelta -= 360.0;
            }
            while (this.entityRiderPitchDelta < -180.0) {
                this.entityRiderPitchDelta += 360.0;
            }
            double d1 = this.entityRiderYawDelta * 0.5;
            double d3 = this.entityRiderPitchDelta * 0.5;
            float f5 = 10.0f;
            if (d1 > (double)f5) {
                d1 = f5;
            }
            if (d1 < (double)(-f5)) {
                d1 = -f5;
            }
            if (d3 > (double)f5) {
                d3 = f5;
            }
            if (d3 < (double)(-f5)) {
                d3 = -f5;
            }
            this.entityRiderYawDelta -= d1;
            this.entityRiderPitchDelta -= d3;
            this.rotationYaw = (float)((double)this.rotationYaw + d1);
            this.rotationPitch = (float)((double)this.rotationPitch + d3);
        }
    }

    public void updateRiderPosition() {
        this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getMountedYOffset() {
        return (double)this.height * 0.75;
    }

    public void mountEntity(Entity entity1) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (entity1 == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + (double)this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        } else if (this.ridingEntity == entity1) {
            this.ridingEntity.riddenByEntity = null;
            this.ridingEntity = null;
            this.setLocationAndAngles(entity1.posX, entity1.boundingBox.minY + (double)entity1.height, entity1.posZ, this.rotationYaw, this.rotationPitch);
        } else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            if (entity1.riddenByEntity != null) {
                entity1.riddenByEntity.ridingEntity = null;
            }
            this.ridingEntity = entity1;
            entity1.riddenByEntity = this;
        }
    }

    public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
        this.setPosition(d1, d3, d5);
        this.setRotation(f7, f8);
    }

    public float getCollisionBorderSize() {
        return 0.1f;
    }

    public Vec3D getLookVec() {
        return null;
    }

    public void setInPortal() {
    }

    public void setVelocity(double d1, double d3, double d5) {
        this.motionX = d1;
        this.motionY = d3;
        this.motionZ = d5;
    }

    public void handleHealthUpdate(byte b1) {
    }

    public void performHurtAnimation() {
    }

    public void updateCloak() {
    }

    public void outfitWithItem(int i1, int i2, int i3) {
    }

    public boolean isBurning() {
        return this.fire > 0 || this.getEntityFlag(0);
    }

    public boolean isRiding() {
        return this.ridingEntity != null || this.getEntityFlag(2);
    }

    public boolean isSneaking() {
        return this.getEntityFlag(1);
    }

    protected boolean getEntityFlag(int i1) {
        return (this.dataWatcher.getWatchableObjectByte(0) & 1 << i1) != 0;
    }

    protected void setEntityFlag(int i1, boolean z2) {
        byte b3 = this.dataWatcher.getWatchableObjectByte(0);
        if (z2) {
            this.dataWatcher.updateObject(0, (byte)(b3 | 1 << i1));
        } else {
            this.dataWatcher.updateObject(0, (byte)(b3 & ~(1 << i1)));
        }
    }
}

