/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.AchievementList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import org.lwjgl.opengl.GL11;

public class GuiInventory
extends GuiContainer {
    private float xSize_lo;
    private float ySize_lo;

    public GuiInventory(EntityPlayer entityPlayer1) {
        super(entityPlayer1.inventorySlots);
        this.field_948_f = true;
        entityPlayer1.addStat(AchievementList.field_25195_b, 1);
    }

    @Override
    public void initGui() {
        this.controlList.clear();
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Crafting", 86.0f, 16.0f, 0x404040);
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        super.drawScreen(i1, i2, f3);
        this.xSize_lo = i1;
        this.ySize_lo = i2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {
        int i2 = this.mc.renderEngine.getTexture("/gui/inventory.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(i2);
        int i3 = (this.width - this.xSize) / 2;
        int i4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(i3 + 51), (float)(i4 + 75), (float)50.0f);
        float f5 = 30.0f;
        GL11.glScalef((float)(-f5), (float)f5, (float)f5);
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float f6 = this.mc.thePlayer.renderYawOffset;
        float f7 = this.mc.thePlayer.rotationYaw;
        float f8 = this.mc.thePlayer.rotationPitch;
        float f9 = (float)(i3 + 51) - this.xSize_lo;
        float f10 = (float)(i4 + 75 - 50) - this.ySize_lo;
        GL11.glRotatef((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(f10 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.mc.thePlayer.renderYawOffset = (float)Math.atan(f9 / 40.0f) * 20.0f;
        this.mc.thePlayer.rotationYaw = (float)Math.atan(f9 / 40.0f) * 40.0f;
        this.mc.thePlayer.rotationPitch = -((float)Math.atan(f10 / 40.0f)) * 20.0f;
        GL11.glTranslatef((float)0.0f, (float)this.mc.thePlayer.yOffset, (float)0.0f);
        RenderManager.instance.renderEntityWithPosYaw(this.mc.thePlayer, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.mc.thePlayer.renderYawOffset = f6;
        this.mc.thePlayer.rotationYaw = f7;
        this.mc.thePlayer.rotationPitch = f8;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
    }
}

