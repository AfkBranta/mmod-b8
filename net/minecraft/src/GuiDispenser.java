/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryDispenserCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntityDispenser;
import org.lwjgl.opengl.GL11;

public class GuiDispenser
extends GuiContainer {
    public GuiDispenser(InventoryPlayer inventoryPlayer1, TileEntityDispenser tileEntityDispenser2) {
        super(new CraftingInventoryDispenserCB(inventoryPlayer1, tileEntityDispenser2));
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Dispenser", 60.0f, 6.0f, 0x404040);
        this.fontRenderer.drawString("Inventory", 8.0f, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {
        int i2 = this.mc.renderEngine.getTexture("/gui/trap.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(i2);
        int i3 = (this.width - this.xSize) / 2;
        int i4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
    }
}

