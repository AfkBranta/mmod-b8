/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Modules.Player.SpeedMine;
import MEDMEX.Modules.World.CivBreaker;
import MEDMEX.Utils.Vec3D;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet102;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet7;
import net.minecraft.src.PlayerController;
import net.minecraft.src.World;

public class PlayerControllerMP
extends PlayerController {
    private int field_9445_c = -1;
    private int field_9444_d = -1;
    private int field_9443_e = -1;
    private float field_9442_f = 0.0f;
    private float field_1080_g = 0.0f;
    private float field_9441_h = 0.0f;
    private int field_9440_i = 0;
    private boolean field_9439_j = false;
    private NetClientHandler netClientHandler;
    private int field_1075_l = 0;

    public PlayerControllerMP(Minecraft minecraft1, NetClientHandler netClientHandler2) {
        super(minecraft1);
        this.netClientHandler = netClientHandler2;
    }

    @Override
    public void flipPlayer(EntityPlayer entityPlayer1) {
        entityPlayer1.rotationYaw = -180.0f;
    }

    @Override
    public boolean sendBlockRemoved(int i1, int i2, int i3, int i4) {
        int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
        boolean z6 = super.sendBlockRemoved(i1, i2, i3, i4);
        ItemStack itemStack7 = this.mc.thePlayer.getCurrentEquippedItem();
        if (itemStack7 != null) {
            itemStack7.func_25191_a(i5, i1, i2, i3, this.mc.thePlayer);
            if (itemStack7.stackSize == 0) {
                itemStack7.func_1097_a(this.mc.thePlayer);
                this.mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        return z6;
    }

    @Override
    public void clickBlock(int i1, int i2, int i3, int i4) {
        if (!this.field_9439_j || i1 != this.field_9445_c || i2 != this.field_9444_d || i3 != this.field_9443_e) {
            this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, i1, i2, i3, i4));
            int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
            if (i5 > 0 && this.field_9442_f == 0.0f) {
                Block.blocksList[i5].onBlockClicked(this.mc.theWorld, i1, i2, i3, this.mc.thePlayer);
            }
            if (i5 > 0 && Block.blocksList[i5].blockStrength(this.mc.thePlayer) >= 1.0f) {
                this.sendBlockRemoved(i1, i2, i3, i4);
            } else {
                this.field_9439_j = true;
                this.field_9445_c = i1;
                this.field_9444_d = i2;
                this.field_9443_e = i3;
                this.field_9442_f = 0.0f;
                this.field_1080_g = 0.0f;
                this.field_9441_h = 0.0f;
            }
        }
    }

    @Override
    public void func_6468_a() {
        this.field_9442_f = 0.0f;
        this.field_9439_j = false;
    }

    @Override
    public void sendBlockRemoving(int i1, int i2, int i3, int i4) {
        if (CivBreaker.instance.onBreak(new Vec3D(i1, i2, i3), i4)) {
            return;
        }
        if (SpeedMine.instance.isEnabled && SpeedMine.instance.getSet("Mode").getValString().equalsIgnoreCase("Speed")) {
            this.field_9440_i = 0;
        }
        if (this.field_9439_j) {
            this.func_730_e();
            if (this.field_9440_i > 0) {
                --this.field_9440_i;
            } else if (i1 == this.field_9445_c && i2 == this.field_9444_d && i3 == this.field_9443_e) {
                int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
                if (i5 == 0) {
                    this.field_9439_j = false;
                    return;
                }
                Block block6 = Block.blocksList[i5];
                this.field_9442_f = (float)((double)this.field_9442_f + (SpeedMine.instance.isEnabled && SpeedMine.instance.getSet("Mode").getValString().equalsIgnoreCase("Speed") ? (double)block6.blockStrength(this.mc.thePlayer) * SpeedMine.instance.getSet("Speed").getValDouble() : (double)block6.blockStrength(this.mc.thePlayer)));
                if (this.field_9441_h % 4.0f == 0.0f && block6 != null) {
                    this.mc.sndManager.playSound(block6.stepSound.func_1145_d(), (float)i1 + 0.5f, (float)i2 + 0.5f, (float)i3 + 0.5f, (block6.stepSound.func_1147_b() + 1.0f) / 8.0f, block6.stepSound.func_1144_c() * 0.5f);
                }
                this.field_9441_h += 1.0f;
                if (this.field_9442_f >= 1.0f) {
                    this.field_9439_j = false;
                    if (!CivBreaker.instance.cancelMiningAbortPacket()) {
                        this.netClientHandler.addToSendQueue(new Packet14BlockDig(2, i1, i2, i3, i4));
                    }
                    this.sendBlockRemoved(i1, i2, i3, i4);
                    this.field_9442_f = 0.0f;
                    this.field_1080_g = 0.0f;
                    this.field_9441_h = 0.0f;
                    this.field_9440_i = 5;
                }
            } else {
                this.clickBlock(i1, i2, i3, i4);
            }
        }
    }

    @Override
    public void setPartialTime(float f1) {
        if (this.field_9442_f <= 0.0f) {
            this.mc.ingameGUI.field_6446_b = 0.0f;
            this.mc.renderGlobal.field_1450_i = 0.0f;
        } else {
            float f2;
            this.mc.ingameGUI.field_6446_b = f2 = this.field_1080_g + (this.field_9442_f - this.field_1080_g) * f1;
            this.mc.renderGlobal.field_1450_i = f2;
        }
    }

    @Override
    public float getBlockReachDistance() {
        return 4.0f;
    }

    @Override
    public void func_717_a(World world1) {
        super.func_717_a(world1);
    }

    @Override
    public void updateController() {
        this.func_730_e();
        this.field_1080_g = this.field_9442_f;
        this.mc.sndManager.playRandomMusicIfReady();
    }

    private void func_730_e() {
        int i1 = this.mc.thePlayer.inventory.currentItem;
        if (i1 != this.field_1075_l) {
            this.field_1075_l = i1;
            this.netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(this.field_1075_l));
        }
    }

    @Override
    public boolean sendPlaceBlock(EntityPlayer entityPlayer1, World world2, ItemStack itemStack3, int i4, int i5, int i6, int i7) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet15Place(i4, i5, i6, i7, entityPlayer1.inventory.getCurrentItem()));
        boolean z8 = super.sendPlaceBlock(entityPlayer1, world2, itemStack3, i4, i5, i6, i7);
        return z8;
    }

    @Override
    public boolean sendUseItem(EntityPlayer entityPlayer1, World world2, ItemStack itemStack3) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, entityPlayer1.inventory.getCurrentItem()));
        boolean z4 = super.sendUseItem(entityPlayer1, world2, itemStack3);
        return z4;
    }

    @Override
    public EntityPlayer createPlayer(World world1) {
        return new EntityClientPlayerMP(this.mc, world1, this.mc.session, this.netClientHandler);
    }

    @Override
    public void func_6472_b(EntityPlayer entityPlayer1, Entity entity2) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet7(entityPlayer1.entityId, entity2.entityId, 1));
        entityPlayer1.attackTargetEntityWithCurrentItem(entity2);
    }

    @Override
    public void func_6475_a(EntityPlayer entityPlayer1, Entity entity2) {
        this.func_730_e();
        this.netClientHandler.addToSendQueue(new Packet7(entityPlayer1.entityId, entity2.entityId, 0));
        entityPlayer1.useCurrentItemOnEntity(entity2);
    }

    @Override
    public ItemStack func_20085_a(int i1, int i2, int i3, EntityPlayer entityPlayer4) {
        short s5 = entityPlayer4.craftingInventory.func_20111_a(entityPlayer4.inventory);
        ItemStack itemStack6 = super.func_20085_a(i1, i2, i3, entityPlayer4);
        this.netClientHandler.addToSendQueue(new Packet102(i1, i2, i3, itemStack6, s5));
        return itemStack6;
    }

    @Override
    public void func_20086_a(int i1, EntityPlayer entityPlayer2) {
        if (i1 != -9999) {
            // empty if block
        }
    }
}

