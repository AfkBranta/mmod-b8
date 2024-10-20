/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import net.minecraft.src.World;

public class PlayerControllerSP
extends PlayerController {
    private int field_1074_c = -1;
    private int field_1073_d = -1;
    private int field_1072_e = -1;
    private float curBlockDamage = 0.0f;
    private float prevBlockDamage = 0.0f;
    private float field_1069_h = 0.0f;
    private int field_1068_i = 0;

    public PlayerControllerSP(Minecraft minecraft1) {
        super(minecraft1);
    }

    @Override
    public void flipPlayer(EntityPlayer entityPlayer1) {
        entityPlayer1.rotationYaw = -180.0f;
    }

    @Override
    public boolean sendBlockRemoved(int i1, int i2, int i3, int i4) {
        int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
        int i6 = this.mc.theWorld.getBlockMetadata(i1, i2, i3);
        boolean z7 = super.sendBlockRemoved(i1, i2, i3, i4);
        ItemStack itemStack8 = this.mc.thePlayer.getCurrentEquippedItem();
        boolean z9 = this.mc.thePlayer.canHarvestBlock(Block.blocksList[i5]);
        if (itemStack8 != null) {
            itemStack8.func_25191_a(i5, i1, i2, i3, this.mc.thePlayer);
            if (itemStack8.stackSize == 0) {
                itemStack8.func_1097_a(this.mc.thePlayer);
                this.mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        if (z7 && z9) {
            Block.blocksList[i5].harvestBlock(this.mc.theWorld, this.mc.thePlayer, i1, i2, i3, i6);
        }
        return z7;
    }

    @Override
    public void clickBlock(int i1, int i2, int i3, int i4) {
        int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
        if (i5 > 0 && this.curBlockDamage == 0.0f) {
            Block.blocksList[i5].onBlockClicked(this.mc.theWorld, i1, i2, i3, this.mc.thePlayer);
        }
        if (i5 > 0 && Block.blocksList[i5].blockStrength(this.mc.thePlayer) >= 1.0f) {
            this.sendBlockRemoved(i1, i2, i3, i4);
        }
    }

    @Override
    public void func_6468_a() {
        this.curBlockDamage = 0.0f;
        this.field_1068_i = 0;
    }

    @Override
    public void sendBlockRemoving(int i1, int i2, int i3, int i4) {
        if (this.field_1068_i > 0) {
            --this.field_1068_i;
        } else if (i1 == this.field_1074_c && i2 == this.field_1073_d && i3 == this.field_1072_e) {
            int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
            if (i5 == 0) {
                return;
            }
            Block block6 = Block.blocksList[i5];
            this.curBlockDamage += block6.blockStrength(this.mc.thePlayer);
            if (this.field_1069_h % 4.0f == 0.0f && block6 != null) {
                this.mc.sndManager.playSound(block6.stepSound.func_1145_d(), (float)i1 + 0.5f, (float)i2 + 0.5f, (float)i3 + 0.5f, (block6.stepSound.func_1147_b() + 1.0f) / 8.0f, block6.stepSound.func_1144_c() * 0.5f);
            }
            this.field_1069_h += 1.0f;
            if (this.curBlockDamage >= 1.0f) {
                this.sendBlockRemoved(i1, i2, i3, i4);
                this.curBlockDamage = 0.0f;
                this.prevBlockDamage = 0.0f;
                this.field_1069_h = 0.0f;
                this.field_1068_i = 5;
            }
        } else {
            this.curBlockDamage = 0.0f;
            this.prevBlockDamage = 0.0f;
            this.field_1069_h = 0.0f;
            this.field_1074_c = i1;
            this.field_1073_d = i2;
            this.field_1072_e = i3;
        }
    }

    @Override
    public void setPartialTime(float f1) {
        if (this.curBlockDamage <= 0.0f) {
            this.mc.ingameGUI.field_6446_b = 0.0f;
            this.mc.renderGlobal.field_1450_i = 0.0f;
        } else {
            float f2;
            this.mc.ingameGUI.field_6446_b = f2 = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * f1;
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
        this.prevBlockDamage = this.curBlockDamage;
        this.mc.sndManager.playRandomMusicIfReady();
    }
}

