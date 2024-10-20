/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPickupFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiChest;
import net.minecraft.src.GuiCrafting;
import net.minecraft.src.GuiDispenser;
import net.minecraft.src.GuiEditSign;
import net.minecraft.src.GuiFurnace;
import net.minecraft.src.IInventory;
import net.minecraft.src.MouseFilter;
import net.minecraft.src.MovementInput;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Session;
import net.minecraft.src.StatBasic;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;

public class EntityPlayerSP
extends EntityPlayer {
    public MovementInput movementInput;
    protected Minecraft mc;
    public int field_9373_b = 20;
    private boolean inPortal = false;
    public float timeInPortal;
    public float prevTimeInPortal;
    private MouseFilter field_21903_bJ = new MouseFilter();
    private MouseFilter field_21904_bK = new MouseFilter();
    private MouseFilter field_21902_bL = new MouseFilter();

    public EntityPlayerSP(Minecraft minecraft1, World world2, Session session3, int i4) {
        super(world2);
        this.mc = minecraft1;
        this.dimension = i4;
        if (session3 != null && Session.username != null && Session.username.length() > 0) {
            this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + Session.username + ".png";
        }
        this.username = Session.username;
    }

    @Override
    public void moveEntity(double d1, double d3, double d5) {
        super.moveEntity(d1, d3, d5);
    }

    @Override
    public void updatePlayerActionState() {
        super.updatePlayerActionState();
        this.moveStrafing = this.movementInput.moveStrafe;
        this.moveForward = this.movementInput.moveForward;
        this.isJumping = this.movementInput.jump;
    }

    @Override
    public void onLivingUpdate() {
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.timeInPortal == 0.0f) {
                this.mc.sndManager.func_337_a("portal.trigger", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
                this.field_9373_b = 10;
                this.mc.sndManager.func_337_a("portal.travel", 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
                this.mc.usePortal();
            }
            this.inPortal = false;
        } else {
            if (this.timeInPortal > 0.0f) {
                this.timeInPortal -= 0.05f;
            }
            if (this.timeInPortal < 0.0f) {
                this.timeInPortal = 0.0f;
            }
        }
        if (this.field_9373_b > 0) {
            --this.field_9373_b;
        }
        this.movementInput.updatePlayerMoveState(this);
        if (this.movementInput.sneak && this.ySize < 0.2f) {
            this.ySize = 0.2f;
        }
        super.onLivingUpdate();
    }

    public void resetPlayerKeyState() {
        this.movementInput.resetKeyState();
    }

    public void handleKeyPress(int i1, boolean z2) {
        this.movementInput.checkKeyForMovementInput(i1, z2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
        nBTTagCompound1.setInteger("Score", this.score);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
        this.score = nBTTagCompound1.getInteger("Score");
    }

    @Override
    public void func_20059_m() {
        super.func_20059_m();
        this.mc.displayGuiScreen(null);
    }

    @Override
    public void displayGUIEditSign(TileEntitySign tileEntitySign1) {
        this.mc.displayGuiScreen(new GuiEditSign(tileEntitySign1));
    }

    @Override
    public void displayGUIChest(IInventory iInventory1) {
        this.mc.displayGuiScreen(new GuiChest(this.inventory, iInventory1));
    }

    @Override
    public void displayWorkbenchGUI(int i1, int i2, int i3) {
        this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, i1, i2, i3));
    }

    @Override
    public void displayGUIFurnace(TileEntityFurnace tileEntityFurnace1) {
        this.mc.displayGuiScreen(new GuiFurnace(this.inventory, tileEntityFurnace1));
    }

    @Override
    public void displayGUIDispenser(TileEntityDispenser tileEntityDispenser1) {
        this.mc.displayGuiScreen(new GuiDispenser(this.inventory, tileEntityDispenser1));
    }

    @Override
    public void onItemPickup(Entity entity1, int i2) {
        this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, entity1, this, -0.5f));
    }

    public int getPlayerArmorValue() {
        return this.inventory.getTotalArmorValue();
    }

    public void sendChatMessage(String string1) {
    }

    @Override
    public boolean isSneaking() {
        return this.movementInput.sneak;
    }

    @Override
    public void setInPortal() {
        if (this.field_9373_b > 0) {
            this.field_9373_b = 10;
        } else {
            this.inPortal = true;
        }
    }

    public void setHealth(int i1) {
        int i2 = this.health - i1;
        if (i2 <= 0) {
            this.health = i1;
        } else {
            this.field_9346_af = i2;
            this.prevHealth = this.health;
            this.field_9306_bj = this.field_9366_o;
            this.damageEntity(i2);
            this.maxHurtTime = 10;
            this.hurtTime = 10;
        }
    }

    @Override
    public void respawnPlayer() {
        this.mc.respawn(false);
    }

    @Override
    public void func_6420_o() {
    }

    @Override
    public void addChatMessage(String string1) {
        this.mc.ingameGUI.func_22064_c(string1);
    }

    @Override
    public void addStat(StatBasic statBasic1, int i2) {
    }
}

