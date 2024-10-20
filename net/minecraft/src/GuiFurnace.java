/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryFurnaceCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntityFurnace;
import org.lwjgl.opengl.GL11;

public class GuiFurnace
extends GuiContainer {
    private TileEntityFurnace furnaceInventory;

    public GuiFurnace(InventoryPlayer inventoryPlayer1, TileEntityFurnace tileEntityFurnace2) {
        super(new CraftingInventoryFurnaceCB(inventoryPlayer1, tileEntityFurnace2));
        this.furnaceInventory = tileEntityFurnace2;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Furnace", 60.0f, 6.0f, 0x404040);
        this.fontRenderer.drawString("Inventory", 8.0f, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {
        int i5;
        int i2 = this.mc.renderEngine.getTexture("/gui/furnace.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(i2);
        int i3 = (this.width - this.xSize) / 2;
        int i4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
        if (this.furnaceInventory.isBurning()) {
            i5 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(i3 + 56, i4 + 36 + 12 - i5, 176, 12 - i5, 14, i5 + 2);
        }
        i5 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(i3 + 79, i4 + 34, 176, 14, i5 + 1, 16);
    }
}

