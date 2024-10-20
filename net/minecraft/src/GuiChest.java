/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryChestCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import org.lwjgl.opengl.GL11;

public class GuiChest
extends GuiContainer {
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows = 0;

    public GuiChest(IInventory iInventory1, IInventory iInventory2) {
        super(new CraftingInventoryChestCB(iInventory1, iInventory2));
        this.upperChestInventory = iInventory1;
        this.lowerChestInventory = iInventory2;
        this.field_948_f = false;
        int s3 = 222;
        int i4 = s3 - 108;
        this.inventoryRows = iInventory2.getSizeInventory() / 9;
        this.ySize = i4 + this.inventoryRows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString(this.lowerChestInventory.getInvName(), 8.0f, 6.0f, 0x404040);
        this.fontRenderer.drawString(this.upperChestInventory.getInvName(), 8.0f, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {
        int i2 = this.mc.renderEngine.getTexture("/gui/container.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(i2);
        int i3 = (this.width - this.xSize) / 2;
        int i4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i3, i4 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}

