/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.events.EventChat;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Events.events.EventTick;
import MEDMEX.Modules.Movement.Speed;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet18ArmAnimation;
import net.minecraft.src.Packet19;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet9;
import net.minecraft.src.Session;
import net.minecraft.src.World;

public class EntityClientPlayerMP
extends EntityPlayerSP {
    public NetClientHandler sendQueue;
    private int field_9380_bx = 0;
    private boolean field_21093_bH = false;
    private double field_9379_by;
    private double field_9378_bz;
    private double field_9377_bA;
    private double field_9376_bB;
    private float field_9385_bC;
    private float field_9384_bD;
    private boolean field_9382_bF = false;
    private boolean field_9381_bG = false;
    private int field_12242_bI = 0;

    public EntityClientPlayerMP(Minecraft minecraft1, World world2, Session session3, NetClientHandler netClientHandler4) {
        super(minecraft1, world2, session3, 0);
        this.sendQueue = netClientHandler4;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        return false;
    }

    @Override
    public void heal(int i1) {
    }

    @Override
    public void onUpdate() {
        EventTick event = new EventTick();
        Client.onEvent(event);
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 64, MathHelper.floor_double(this.posZ))) {
            super.onUpdate();
            this.func_4056_N();
        }
        if (Speed.instance.isEnabled) {
            float cfr_renamed_23;
            float cfr_renamed_22;
            float cfr_renamed_13;
            float cfr_renamed_12;
            float cfr_renamed_11;
            float cfr_renamed_10;
            int cfr_renamed_8;
            float cfr_renamed_7;
            float cfr_renamed_6;
            int cfr_renamed_5;
            if (Speed.instance.getSet("Mode").getValString().equalsIgnoreCase("Normal") && this.mc.thePlayer.ticksExisted % 20 == 0) {
                cfr_renamed_5 = this.hurtTime;
                cfr_renamed_6 = this.prevSwingProgress;
                cfr_renamed_7 = this.swingProgress;
                cfr_renamed_8 = this.swingProgressInt;
                cfr_renamed_10 = this.rotationYaw;
                cfr_renamed_11 = this.prevRotationYaw;
                cfr_renamed_12 = this.renderYawOffset;
                cfr_renamed_13 = this.prevRenderYawOffset;
                cfr_renamed_22 = this.distanceWalkedModified;
                cfr_renamed_23 = this.prevDistanceWalkedModified;
                super.onUpdate();
                this.hurtTime = cfr_renamed_5;
                this.prevSwingProgress = cfr_renamed_6;
                this.swingProgress = cfr_renamed_7;
                this.swingProgressInt = cfr_renamed_8;
                this.rotationYaw = cfr_renamed_10;
                this.prevRotationYaw = cfr_renamed_11;
                this.renderYawOffset = cfr_renamed_12;
                this.prevRenderYawOffset = cfr_renamed_13;
                this.distanceWalkedModified = cfr_renamed_22;
                this.prevDistanceWalkedModified = cfr_renamed_23;
                this.func_4056_N();
            }
            if (Speed.instance.getSet("Mode").getValString().equalsIgnoreCase("Boost")) {
                cfr_renamed_5 = this.hurtTime;
                cfr_renamed_6 = this.prevSwingProgress;
                cfr_renamed_7 = this.swingProgress;
                cfr_renamed_8 = this.swingProgressInt;
                cfr_renamed_10 = this.rotationYaw;
                cfr_renamed_11 = this.prevRotationYaw;
                cfr_renamed_12 = this.renderYawOffset;
                cfr_renamed_13 = this.prevRenderYawOffset;
                cfr_renamed_22 = this.distanceWalkedModified;
                cfr_renamed_23 = this.prevDistanceWalkedModified;
                super.onUpdate();
                this.hurtTime = cfr_renamed_5;
                this.prevSwingProgress = cfr_renamed_6;
                this.swingProgress = cfr_renamed_7;
                this.swingProgressInt = cfr_renamed_8;
                this.rotationYaw = cfr_renamed_10;
                this.prevRotationYaw = cfr_renamed_11;
                this.renderYawOffset = cfr_renamed_12;
                this.prevRenderYawOffset = cfr_renamed_13;
                this.distanceWalkedModified = cfr_renamed_22;
                this.prevDistanceWalkedModified = cfr_renamed_23;
                this.func_4056_N();
            }
            if (Speed.instance.getSet("Mode").getValString().equalsIgnoreCase("NoCheat")) {
                int i = 0;
                while (i < 20) {
                    int cfr_renamed_52 = this.hurtTime;
                    float cfr_renamed_62 = this.prevSwingProgress;
                    float cfr_renamed_72 = this.swingProgress;
                    int cfr_renamed_82 = this.swingProgressInt;
                    float cfr_renamed_102 = this.rotationYaw;
                    float cfr_renamed_112 = this.prevRotationYaw;
                    float cfr_renamed_122 = this.renderYawOffset;
                    float cfr_renamed_132 = this.prevRenderYawOffset;
                    float cfr_renamed_222 = this.distanceWalkedModified;
                    float cfr_renamed_232 = this.prevDistanceWalkedModified;
                    super.onUpdate();
                    this.hurtTime = cfr_renamed_52;
                    this.prevSwingProgress = cfr_renamed_62;
                    this.swingProgress = cfr_renamed_72;
                    this.swingProgressInt = cfr_renamed_82;
                    this.rotationYaw = cfr_renamed_102;
                    this.prevRotationYaw = cfr_renamed_112;
                    this.renderYawOffset = cfr_renamed_122;
                    this.prevRenderYawOffset = cfr_renamed_132;
                    this.distanceWalkedModified = cfr_renamed_222;
                    this.prevDistanceWalkedModified = cfr_renamed_232;
                    this.func_4056_N();
                    ++i;
                }
            }
        }
    }

    public void func_4056_N() {
        boolean z15;
        boolean z1;
        EventPlayer e = new EventPlayer(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
        Client.onEvent(e);
        double posX = e.getPosX();
        double minY = e.getMinY();
        double posY = e.getPosY();
        double posZ = e.getPosZ();
        float rotationYaw = e.getYaw();
        float rotationPitch = e.getPitch();
        boolean onGround = e.isOnground();
        if (e.getCancelled()) {
            return;
        }
        if (this.field_9380_bx++ == 20) {
            this.sendInventoryChanged();
            this.field_9380_bx = 0;
        }
        if ((z1 = this.isSneaking()) != this.field_9381_bG) {
            if (z1) {
                this.sendQueue.addToSendQueue(new Packet19(this, 1));
            } else {
                this.sendQueue.addToSendQueue(new Packet19(this, 2));
            }
            this.field_9381_bG = z1;
        }
        double d2 = this.posX - this.field_9379_by;
        double d4 = this.boundingBox.minY - this.field_9378_bz;
        double d6 = this.posY - this.field_9377_bA;
        double d8 = this.posZ - this.field_9376_bB;
        double d10 = this.rotationYaw - this.field_9385_bC;
        double d12 = this.rotationPitch - this.field_9384_bD;
        boolean z14 = d4 != 0.0 || d6 != 0.0 || d2 != 0.0 || d8 != 0.0;
        boolean bl = z15 = d10 != 0.0 || d12 != 0.0;
        if (this.ridingEntity != null) {
            if (z15) {
                this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.motionX, -999.0, -999.0, this.motionZ, onGround));
            } else {
                this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0, -999.0, this.motionZ, rotationYaw, rotationPitch, this.onGround));
            }
            z14 = false;
        } else if (z14 && z15) {
            this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(posX, minY, posY, posZ, rotationYaw, rotationPitch, onGround));
            this.field_12242_bI = 0;
        } else if (z14) {
            this.sendQueue.addToSendQueue(new Packet11PlayerPosition(posX, minY, posY, posZ, onGround));
            this.field_12242_bI = 0;
        } else if (z15) {
            this.sendQueue.addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
            this.field_12242_bI = 0;
        } else {
            this.sendQueue.addToSendQueue(new Packet10Flying(onGround));
            this.field_12242_bI = this.field_9382_bF == this.onGround && this.field_12242_bI <= 200 ? ++this.field_12242_bI : 0;
        }
        this.field_9382_bF = onGround;
        if (z14) {
            this.field_9379_by = posX;
            this.field_9378_bz = minY;
            this.field_9377_bA = posY;
            this.field_9376_bB = posZ;
        }
        if (z15) {
            this.field_9385_bC = rotationYaw;
            this.field_9384_bD = rotationPitch;
        }
    }

    @Override
    public void dropCurrentItem() {
        this.sendQueue.addToSendQueue(new Packet14BlockDig(4, 0, 0, 0, 0));
    }

    private void sendInventoryChanged() {
    }

    @Override
    protected void joinEntityItemWithWorld(EntityItem entityItem1) {
    }

    @Override
    public void sendChatMessage(String string1) {
        EventChat event = new EventChat(string1);
        Client.onEvent(event);
        if (event.getCancelled()) {
            return;
        }
        this.sendQueue.addToSendQueue(new Packet3Chat(string1));
    }

    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new Packet18ArmAnimation(this, 1));
    }

    @Override
    public void respawnPlayer() {
        this.sendInventoryChanged();
        this.sendQueue.addToSendQueue(new Packet9());
    }

    @Override
    protected void damageEntity(int i1) {
        this.health -= i1;
    }

    @Override
    public void func_20059_m() {
        this.sendQueue.addToSendQueue(new Packet101(this.craftingInventory.windowId));
        this.inventory.setItemStack(null);
        super.func_20059_m();
    }

    @Override
    public void setHealth(int i1) {
        if (this.field_21093_bH) {
            super.setHealth(i1);
        } else {
            this.health = i1;
            this.field_21093_bH = true;
        }
    }
}

