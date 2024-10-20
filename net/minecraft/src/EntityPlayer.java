/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.CraftingInventoryPlayerCB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.EnumStatus;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.StatBasic;
import net.minecraft.src.StatList;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;

public abstract class EntityPlayer
extends EntityLiving {
    public InventoryPlayer inventory = new InventoryPlayer(this);
    public CraftingInventoryCB inventorySlots;
    public CraftingInventoryCB craftingInventory;
    public byte field_9371_f = 0;
    public int score = 0;
    public float field_775_e;
    public float field_774_f;
    public boolean isSwinging = false;
    public int swingProgressInt = 0;
    public String username;
    public int dimension;
    public String playerCloakUrl;
    public double field_20066_r;
    public double field_20065_s;
    public double field_20064_t;
    public double field_20063_u;
    public double field_20062_v;
    public double field_20061_w;
    private boolean sleeping;
    private ChunkCoordinates bedChunkCoordinates;
    private int sleepTimer;
    public float field_22063_x;
    public float field_22062_y;
    public float field_22061_z;
    private ChunkCoordinates playerSpawnCoordinate;
    private int damageRemainder = 0;
    public EntityFish fishEntity = null;

    public EntityPlayer(World world1) {
        super(world1);
        this.craftingInventory = this.inventorySlots = new CraftingInventoryPlayerCB(this.inventory, !world1.multiplayerWorld);
        this.yOffset = 1.62f;
        ChunkCoordinates chunkCoordinates2 = world1.getSpawnPoint();
        this.setLocationAndAngles((double)chunkCoordinates2.x + 0.5, chunkCoordinates2.y + 1, (double)chunkCoordinates2.z + 0.5, 0.0f, 0.0f);
        this.health = 20;
        this.field_9351_C = "humanoid";
        this.field_9353_B = 180.0f;
        this.fireResistance = 20;
        this.texture = "/mob/char.png";
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    public void onUpdate() {
        if (this.isPlayerSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.isInBed()) {
                this.wakeUpPlayer(true, true, false);
            } else if (!this.worldObj.multiplayerWorld && this.worldObj.isDaytime()) {
                this.wakeUpPlayer(false, true, true);
            }
        } else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        super.onUpdate();
        if (!this.worldObj.multiplayerWorld && this.craftingInventory != null && !this.craftingInventory.isUsableByPlayer(this)) {
            this.func_20059_m();
            this.craftingInventory = this.inventorySlots;
        }
        this.field_20066_r = this.field_20063_u;
        this.field_20065_s = this.field_20062_v;
        this.field_20064_t = this.field_20061_w;
        double d1 = this.posX - this.field_20063_u;
        double d3 = this.posY - this.field_20062_v;
        double d5 = this.posZ - this.field_20061_w;
        double d7 = 10.0;
        if (d1 > d7) {
            this.field_20066_r = this.field_20063_u = this.posX;
        }
        if (d5 > d7) {
            this.field_20064_t = this.field_20061_w = this.posZ;
        }
        if (d3 > d7) {
            this.field_20065_s = this.field_20062_v = this.posY;
        }
        if (d1 < -d7) {
            this.field_20066_r = this.field_20063_u = this.posX;
        }
        if (d5 < -d7) {
            this.field_20064_t = this.field_20061_w = this.posZ;
        }
        if (d3 < -d7) {
            this.field_20065_s = this.field_20062_v = this.posY;
        }
        this.field_20063_u += d1 * 0.25;
        this.field_20061_w += d5 * 0.25;
        this.field_20062_v += d3 * 0.25;
        this.addStat(StatList.field_25179_j, 1);
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.health <= 0 || this.isPlayerSleeping();
    }

    protected void func_20059_m() {
        this.craftingInventory = this.inventorySlots;
    }

    @Override
    public void updateCloak() {
        this.cloakUrl = this.playerCloakUrl = "http://s3.amazonaws.com/MinecraftCloaks/" + this.username + ".png";
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_775_e = this.field_774_f;
        this.field_774_f = 0.0f;
    }

    @Override
    public void preparePlayerToSpawn() {
        this.yOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.health = 20;
        this.deathTime = 0;
    }

    @Override
    protected void updatePlayerActionState() {
        if (this.isSwinging) {
            ++this.swingProgressInt;
            if (this.swingProgressInt == 8) {
                this.swingProgressInt = 0;
                this.isSwinging = false;
            }
        } else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = (float)this.swingProgressInt / 8.0f;
    }

    @Override
    public void onLivingUpdate() {
        List list3;
        if (this.worldObj.difficultySetting == 0 && this.health < 20 && this.ticksExisted % 20 * 12 == 0) {
            this.heal(1);
        }
        this.inventory.decrementAnimations();
        this.field_775_e = this.field_774_f;
        super.onLivingUpdate();
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f2 = (float)Math.atan(-this.motionY * (double)0.2f) * 15.0f;
        if (f1 > 0.1f) {
            f1 = 0.1f;
        }
        if (!this.onGround || this.health <= 0) {
            f1 = 0.0f;
        }
        if (this.onGround || this.health <= 0) {
            f2 = 0.0f;
        }
        this.field_774_f += (f1 - this.field_774_f) * 0.4f;
        this.field_9328_R += (f2 - this.field_9328_R) * 0.8f;
        if (this.health > 0 && (list3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0, 0.0, 1.0))) != null) {
            int i4 = 0;
            while (i4 < list3.size()) {
                Entity entity5 = (Entity)list3.get(i4);
                if (!entity5.isDead) {
                    this.func_451_h(entity5);
                }
                ++i4;
            }
        }
    }

    private void func_451_h(Entity entity1) {
        entity1.onCollideWithPlayer(this);
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public void onDeath(Entity entity1) {
        super.onDeath(entity1);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.1f;
        if (this.username.equals("Notch")) {
            this.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }
        this.inventory.dropAllItems();
        if (entity1 != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0f) * 0.1f;
        } else {
            this.motionZ = 0.0;
            this.motionX = 0.0;
        }
        this.yOffset = 0.1f;
        this.addStat(StatList.field_25163_u, 1);
    }

    @Override
    public void addToPlayerScore(Entity entity1, int i2) {
        this.score += i2;
        if (entity1 instanceof EntityPlayer) {
            this.addStat(StatList.field_25161_w, 1);
        } else {
            this.addStat(StatList.field_25162_v, 1);
        }
    }

    public void dropCurrentItem() {
        this.dropPlayerItemWithRandomChoice(this.inventory.decrStackSize(this.inventory.currentItem, 1), false);
    }

    public void dropPlayerItem(ItemStack itemStack1) {
        this.dropPlayerItemWithRandomChoice(itemStack1, false);
    }

    public void dropPlayerItemWithRandomChoice(ItemStack itemStack1, boolean z2) {
        if (itemStack1 != null) {
            EntityItem entityItem3 = new EntityItem(this.worldObj, this.posX, this.posY - (double)0.3f + (double)this.getEyeHeight(), this.posZ, itemStack1);
            entityItem3.delayBeforeCanPickup = 40;
            float f4 = 0.1f;
            if (z2) {
                float f5 = this.rand.nextFloat() * 0.5f;
                float f6 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                entityItem3.motionX = -MathHelper.sin(f6) * f5;
                entityItem3.motionZ = MathHelper.cos(f6) * f5;
                entityItem3.motionY = 0.2f;
            } else {
                f4 = 0.3f;
                entityItem3.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * f4;
                entityItem3.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * f4;
                entityItem3.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI) * f4 + 0.1f;
                f4 = 0.02f;
                float f5 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                entityItem3.motionX += Math.cos(f5) * (double)(f4 *= this.rand.nextFloat());
                entityItem3.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                entityItem3.motionZ += Math.sin(f5) * (double)f4;
            }
            this.joinEntityItemWithWorld(entityItem3);
            this.addStat(StatList.field_25168_r, 1);
        }
    }

    protected void joinEntityItemWithWorld(EntityItem entityItem1) {
        this.worldObj.entityJoinedWorld(entityItem1);
    }

    public float getCurrentPlayerStrVsBlock(Block block1) {
        float f2 = this.inventory.getStrVsBlock(block1);
        if (this.isInsideOfMaterial(Material.water)) {
            f2 /= 5.0f;
        }
        if (!this.onGround) {
            f2 /= 5.0f;
        }
        return f2;
    }

    public boolean canHarvestBlock(Block block1) {
        return this.inventory.canHarvestBlock(block1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Inventory");
        this.inventory.readFromNBT(nBTTagList2);
        this.dimension = nBTTagCompound1.getInteger("Dimension");
        this.sleeping = nBTTagCompound1.getBoolean("Sleeping");
        this.sleepTimer = nBTTagCompound1.getShort("SleepTimer");
        if (this.sleeping) {
            this.bedChunkCoordinates = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.wakeUpPlayer(true, true, false);
        }
        if (nBTTagCompound1.hasKey("SpawnX") && nBTTagCompound1.hasKey("SpawnY") && nBTTagCompound1.hasKey("SpawnZ")) {
            this.playerSpawnCoordinate = new ChunkCoordinates(nBTTagCompound1.getInteger("SpawnX"), nBTTagCompound1.getInteger("SpawnY"), nBTTagCompound1.getInteger("SpawnZ"));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
        nBTTagCompound1.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        nBTTagCompound1.setInteger("Dimension", this.dimension);
        nBTTagCompound1.setBoolean("Sleeping", this.sleeping);
        nBTTagCompound1.setShort("SleepTimer", (short)this.sleepTimer);
        if (this.playerSpawnCoordinate != null) {
            nBTTagCompound1.setInteger("SpawnX", this.playerSpawnCoordinate.x);
            nBTTagCompound1.setInteger("SpawnY", this.playerSpawnCoordinate.y);
            nBTTagCompound1.setInteger("SpawnZ", this.playerSpawnCoordinate.z);
        }
    }

    public void displayGUIChest(IInventory iInventory1) {
    }

    public void displayWorkbenchGUI(int i1, int i2, int i3) {
    }

    public void onItemPickup(Entity entity1, int i2) {
    }

    @Override
    public float getEyeHeight() {
        return 0.12f;
    }

    protected void resetHeight() {
        this.yOffset = 1.62f;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        this.field_9344_ag = 0;
        if (this.health <= 0) {
            return false;
        }
        if (this.isPlayerSleeping()) {
            this.wakeUpPlayer(true, true, false);
        }
        if (entity1 instanceof EntityMobs || entity1 instanceof EntityArrow) {
            if (this.worldObj.difficultySetting == 0) {
                i2 = 0;
            }
            if (this.worldObj.difficultySetting == 1) {
                i2 = i2 / 3 + 1;
            }
            if (this.worldObj.difficultySetting == 3) {
                i2 = i2 * 3 / 2;
            }
        }
        if (i2 == 0) {
            return false;
        }
        Entity object3 = entity1;
        if (entity1 instanceof EntityArrow && ((EntityArrow)entity1).archer != null) {
            object3 = ((EntityArrow)entity1).archer;
        }
        if (object3 instanceof EntityLiving) {
            this.alertWolves((EntityLiving)object3, false);
        }
        this.addStat(StatList.field_25165_t, i2);
        return super.attackEntityFrom(entity1, i2);
    }

    protected void alertWolves(EntityLiving entityLiving1, boolean z2) {
        if (!(entityLiving1 instanceof EntityCreeper) && !(entityLiving1 instanceof EntityGhast)) {
            EntityWolf entityWolf3;
            if (entityLiving1 instanceof EntityWolf && (entityWolf3 = (EntityWolf)entityLiving1).isWolfTamed() && this.username.equals(entityWolf3.getWolfOwner())) {
                return;
            }
            List list7 = this.worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getBoundingBoxFromPool(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0));
            Iterator iterator4 = list7.iterator();
            while (true) {
                if (!iterator4.hasNext()) {
                    return;
                }
                Entity entity5 = (Entity)iterator4.next();
                EntityWolf entityWolf6 = (EntityWolf)entity5;
                if (!entityWolf6.isWolfTamed() || entityWolf6.getTarget() != null || !this.username.equals(entityWolf6.getWolfOwner()) || z2 && entityWolf6.isWolfSitting()) continue;
                entityWolf6.setWolfSitting(false);
                entityWolf6.setTarget(entityLiving1);
            }
        }
    }

    @Override
    protected void damageEntity(int i1) {
        int i2 = 25 - this.inventory.getTotalArmorValue();
        int i3 = i1 * i2 + this.damageRemainder;
        this.inventory.damageArmor(i1);
        i1 = i3 / 25;
        this.damageRemainder = i3 % 25;
        super.damageEntity(i1);
    }

    public void displayGUIFurnace(TileEntityFurnace tileEntityFurnace1) {
    }

    public void displayGUIDispenser(TileEntityDispenser tileEntityDispenser1) {
    }

    public void displayGUIEditSign(TileEntitySign tileEntitySign1) {
    }

    public void useCurrentItemOnEntity(Entity entity1) {
        ItemStack itemStack2;
        if (!entity1.interact(this) && (itemStack2 = this.getCurrentEquippedItem()) != null && entity1 instanceof EntityLiving) {
            itemStack2.useItemOnEntity((EntityLiving)entity1);
            if (itemStack2.stackSize <= 0) {
                itemStack2.func_1097_a(this);
                this.destroyCurrentEquippedItem();
            }
        }
    }

    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }

    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }

    @Override
    public double getYOffset() {
        return this.yOffset - 0.5f;
    }

    public void swingItem() {
        this.swingProgressInt = -1;
        this.isSwinging = true;
    }

    public void attackTargetEntityWithCurrentItem(Entity entity1) {
        int i2 = this.inventory.getDamageVsEntity(entity1);
        if (i2 > 0) {
            entity1.attackEntityFrom(this, i2);
            ItemStack itemStack3 = this.getCurrentEquippedItem();
            if (itemStack3 != null && entity1 instanceof EntityLiving) {
                itemStack3.hitEntity((EntityLiving)entity1, this);
                if (itemStack3.stackSize <= 0) {
                    itemStack3.func_1097_a(this);
                    this.destroyCurrentEquippedItem();
                }
            }
            if (entity1 instanceof EntityLiving) {
                if (entity1.isEntityAlive()) {
                    this.alertWolves((EntityLiving)entity1, true);
                }
                this.addStat(StatList.field_25167_s, i2);
            }
        }
    }

    public void respawnPlayer() {
    }

    public abstract void func_6420_o();

    public void onItemStackChanged(ItemStack itemStack1) {
    }

    @Override
    public void setEntityDead() {
        super.setEntityDead();
        this.inventorySlots.onCraftGuiClosed(this);
        if (this.craftingInventory != null) {
            this.craftingInventory.onCraftGuiClosed(this);
        }
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }

    public EnumStatus sleepInBedAt(int i1, int i2, int i3) {
        if (!this.isPlayerSleeping() && this.isEntityAlive()) {
            if (this.worldObj.worldProvider.field_4220_c) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - (double)i1) <= 3.0 && Math.abs(this.posY - (double)i2) <= 2.0 && Math.abs(this.posZ - (double)i3) <= 3.0) {
                this.setSize(0.2f, 0.2f);
                this.yOffset = 0.2f;
                if (this.worldObj.blockExists(i1, i2, i3)) {
                    int i4 = this.worldObj.getBlockMetadata(i1, i2, i3);
                    int i5 = BlockBed.getDirectionFromMetadata(i4);
                    float f6 = 0.5f;
                    float f7 = 0.5f;
                    switch (i5) {
                        case 0: {
                            f7 = 0.9f;
                            break;
                        }
                        case 1: {
                            f6 = 0.1f;
                            break;
                        }
                        case 2: {
                            f7 = 0.1f;
                            break;
                        }
                        case 3: {
                            f6 = 0.9f;
                        }
                    }
                    this.func_22052_e(i5);
                    this.setPosition((float)i1 + f6, (float)i2 + 0.9375f, (float)i3 + f7);
                } else {
                    this.setPosition((float)i1 + 0.5f, (float)i2 + 0.9375f, (float)i3 + 0.5f);
                }
                this.sleeping = true;
                this.sleepTimer = 0;
                this.bedChunkCoordinates = new ChunkCoordinates(i1, i2, i3);
                this.motionY = 0.0;
                this.motionZ = 0.0;
                this.motionX = 0.0;
                if (!this.worldObj.multiplayerWorld) {
                    this.worldObj.updateAllPlayersSleepingFlag();
                }
                return EnumStatus.OK;
            }
            return EnumStatus.TOO_FAR_AWAY;
        }
        return EnumStatus.OTHER_PROBLEM;
    }

    private void func_22052_e(int i1) {
        this.field_22063_x = 0.0f;
        this.field_22061_z = 0.0f;
        switch (i1) {
            case 0: {
                this.field_22061_z = -1.8f;
                break;
            }
            case 1: {
                this.field_22063_x = 1.8f;
                break;
            }
            case 2: {
                this.field_22061_z = 1.8f;
                break;
            }
            case 3: {
                this.field_22063_x = -1.8f;
            }
        }
    }

    public void wakeUpPlayer(boolean z1, boolean z2, boolean z3) {
        this.setSize(0.6f, 1.8f);
        this.resetHeight();
        ChunkCoordinates chunkCoordinates4 = this.bedChunkCoordinates;
        ChunkCoordinates chunkCoordinates5 = this.bedChunkCoordinates;
        if (chunkCoordinates4 != null && this.worldObj.getBlockId(chunkCoordinates4.x, chunkCoordinates4.y, chunkCoordinates4.z) == Block.blockBed.blockID) {
            BlockBed.setBedOccupied(this.worldObj, chunkCoordinates4.x, chunkCoordinates4.y, chunkCoordinates4.z, false);
            chunkCoordinates5 = BlockBed.getNearestEmptyChunkCoordinates(this.worldObj, chunkCoordinates4.x, chunkCoordinates4.y, chunkCoordinates4.z, 0);
            if (chunkCoordinates5 == null) {
                chunkCoordinates5 = new ChunkCoordinates(chunkCoordinates4.x, chunkCoordinates4.y + 1, chunkCoordinates4.z);
            }
            this.setPosition((float)chunkCoordinates5.x + 0.5f, (float)chunkCoordinates5.y + this.yOffset + 0.1f, (float)chunkCoordinates5.z + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.multiplayerWorld && z2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        this.sleepTimer = z1 ? 0 : 100;
        if (z3) {
            this.setPlayerSpawnCoordinate(this.bedChunkCoordinates);
        }
    }

    private boolean isInBed() {
        return this.worldObj.getBlockId(this.bedChunkCoordinates.x, this.bedChunkCoordinates.y, this.bedChunkCoordinates.z) == Block.blockBed.blockID;
    }

    public static ChunkCoordinates func_25060_a(World world0, ChunkCoordinates chunkCoordinates1) {
        IChunkProvider iChunkProvider2 = world0.getIChunkProvider();
        iChunkProvider2.func_538_d(chunkCoordinates1.x - 3 >> 4, chunkCoordinates1.z - 3 >> 4);
        iChunkProvider2.func_538_d(chunkCoordinates1.x + 3 >> 4, chunkCoordinates1.z - 3 >> 4);
        iChunkProvider2.func_538_d(chunkCoordinates1.x - 3 >> 4, chunkCoordinates1.z + 3 >> 4);
        iChunkProvider2.func_538_d(chunkCoordinates1.x + 3 >> 4, chunkCoordinates1.z + 3 >> 4);
        if (world0.getBlockId(chunkCoordinates1.x, chunkCoordinates1.y, chunkCoordinates1.z) != Block.blockBed.blockID) {
            return null;
        }
        ChunkCoordinates chunkCoordinates3 = BlockBed.getNearestEmptyChunkCoordinates(world0, chunkCoordinates1.x, chunkCoordinates1.y, chunkCoordinates1.z, 0);
        return chunkCoordinates3;
    }

    public float getBedOrientationInDegrees() {
        if (this.bedChunkCoordinates != null) {
            int i1 = this.worldObj.getBlockMetadata(this.bedChunkCoordinates.x, this.bedChunkCoordinates.y, this.bedChunkCoordinates.z);
            int i2 = BlockBed.getDirectionFromMetadata(i1);
            switch (i2) {
                case 0: {
                    return 90.0f;
                }
                case 1: {
                    return 0.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }

    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }

    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }

    public int func_22060_M() {
        return this.sleepTimer;
    }

    public void addChatMessage(String string1) {
    }

    public ChunkCoordinates getPlayerSpawnCoordinate() {
        return this.playerSpawnCoordinate;
    }

    public void setPlayerSpawnCoordinate(ChunkCoordinates chunkCoordinates1) {
        this.playerSpawnCoordinate = chunkCoordinates1 != null ? new ChunkCoordinates(chunkCoordinates1) : null;
    }

    public void addStat(StatBasic statBasic1, int i2) {
    }

    @Override
    public void jump() {
        super.jump();
        this.addStat(StatList.field_25171_q, 1);
    }

    @Override
    public void moveEntityWithHeading(float f1, float f2) {
        double d3 = this.posX;
        double d5 = this.posY;
        double d7 = this.posZ;
        super.moveEntityWithHeading(f1, f2);
        this.addMovementStat(this.posX - d3, this.posY - d5, this.posZ - d7);
    }

    private void addMovementStat(double d1, double d3, double d5) {
        if (this.isInsideOfMaterial(Material.water)) {
            int i7 = Math.round(MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5) * 100.0f);
            if (i7 > 0) {
                this.addStat(StatList.field_25173_p, i7);
            }
        } else if (this.handleWaterMovement()) {
            int i7 = Math.round(MathHelper.sqrt_double(d1 * d1 + d5 * d5) * 100.0f);
            if (i7 > 0) {
                this.addStat(StatList.field_25177_l, i7);
            }
        } else if (this.isOnLadder()) {
            if (d3 > 0.0) {
                this.addStat(StatList.field_25175_n, (int)Math.round(d3 * 100.0));
            }
        } else if (this.onGround) {
            int i7 = Math.round(MathHelper.sqrt_double(d1 * d1 + d5 * d5) * 100.0f);
            if (i7 > 0) {
                this.addStat(StatList.field_25178_k, i7);
            }
        } else {
            int i7 = Math.round(MathHelper.sqrt_double(d1 * d1 + d5 * d5) * 100.0f);
            if (i7 > 25) {
                this.addStat(StatList.field_25174_o, i7);
            }
        }
    }

    @Override
    protected void fall(float f1) {
        if (f1 >= 2.0f) {
            this.addStat(StatList.field_25176_m, (int)Math.round((double)f1 * 100.0));
        }
        super.fall(f1);
    }
}

