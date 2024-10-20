/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Slot;
import net.minecraft.src.StringTranslate;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainer
extends GuiScreen {
    public static RenderItem itemRenderer = new RenderItem();
    protected int xSize = 176;
    protected int ySize = 166;
    public CraftingInventoryCB inventorySlots;

    public GuiContainer(CraftingInventoryCB craftingInventoryCB1) {
        this.inventorySlots = craftingInventoryCB1;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.craftingInventory = this.inventorySlots;
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        String string13;
        int i10;
        int i9;
        this.drawDefaultBackground();
        int i4 = (this.width - this.xSize) / 2;
        int i5 = (this.height - this.ySize) / 2;
        this.drawGuiContainerBackgroundLayer(f3);
        GL11.glPushMatrix();
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)i4, (float)i5, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)32826);
        Slot slot6 = null;
        int i7 = 0;
        while (i7 < this.inventorySlots.slots.size()) {
            Slot slot8 = (Slot)this.inventorySlots.slots.get(i7);
            this.drawSlotInventory(slot8);
            if (this.getIsMouseOverSlot(slot8, i1, i2)) {
                slot6 = slot8;
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                i9 = slot8.xDisplayPosition;
                i10 = slot8.yDisplayPosition;
                this.drawGradientRect(i9, i10, i9 + 16, i10 + 16, -2130706433, -2130706433);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
            }
            ++i7;
        }
        InventoryPlayer inventoryPlayer12 = this.mc.thePlayer.inventory;
        if (inventoryPlayer12.getItemStack() != null) {
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)32.0f);
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryPlayer12.getItemStack(), i1 - i4 - 8, i2 - i5 - 8);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryPlayer12.getItemStack(), i1 - i4 - 8, i2 - i5 - 8);
        }
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        this.drawGuiContainerForegroundLayer();
        if (inventoryPlayer12.getItemStack() == null && slot6 != null && slot6.getHasStack() && (string13 = StringTranslate.getInstance().translateNamedKey(slot6.getStack().func_20109_f()).trim()).length() > 0) {
            i9 = i1 - i4 + 12;
            i10 = i2 - i5 - 12;
            int i11 = this.fontRenderer.getStringWidth(string13);
            this.drawGradientRect(i9 - 3, i10 - 3, i9 + i11 + 3, i10 + 8 + 3, -1073741824, -1073741824);
            this.fontRenderer.drawStringWithShadow(string13, i9, i10, -1);
        }
        GL11.glPopMatrix();
        super.drawScreen(i1, i2, f3);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
    }

    protected void drawGuiContainerForegroundLayer() {
    }

    protected abstract void drawGuiContainerBackgroundLayer(float var1);

    private void drawSlotInventory(Slot slot1) {
        int i5;
        int i2 = slot1.xDisplayPosition;
        int i3 = slot1.yDisplayPosition;
        ItemStack itemStack4 = slot1.getStack();
        if (itemStack4 == null && (i5 = slot1.getBackgroundIconIndex()) >= 0) {
            GL11.glDisable((int)2896);
            this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
            this.drawTexturedModalRect(i2, i3, i5 % 16 * 16, i5 / 16 * 16, 16, 16);
            GL11.glEnable((int)2896);
            return;
        }
        itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack4, i2, i3);
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack4, i2, i3);
    }

    private Slot getSlotAtPosition(int i1, int i2) {
        int i3 = 0;
        while (i3 < this.inventorySlots.slots.size()) {
            Slot slot4 = (Slot)this.inventorySlots.slots.get(i3);
            if (this.getIsMouseOverSlot(slot4, i1, i2)) {
                return slot4;
            }
            ++i3;
        }
        return null;
    }

    private boolean getIsMouseOverSlot(Slot slot1, int i2, int i3) {
        int i4 = (this.width - this.xSize) / 2;
        int i5 = (this.height - this.ySize) / 2;
        return (i2 -= i4) >= slot1.xDisplayPosition - 1 && i2 < slot1.xDisplayPosition + 16 + 1 && (i3 -= i5) >= slot1.yDisplayPosition - 1 && i3 < slot1.yDisplayPosition + 16 + 1;
    }

    @Override
    protected void mouseClicked(int i1, int i2, int i3) {
        super.mouseClicked(i1, i2, i3);
        if (i3 == 0 || i3 == 1) {
            Slot slot4 = this.getSlotAtPosition(i1, i2);
            int i5 = (this.width - this.xSize) / 2;
            int i6 = (this.height - this.ySize) / 2;
            boolean z7 = i1 < i5 || i2 < i6 || i1 >= i5 + this.xSize || i2 >= i6 + this.ySize;
            int i8 = -1;
            if (slot4 != null) {
                i8 = slot4.slotNumber;
            }
            if (z7) {
                i8 = -999;
            }
            if (i8 != -1) {
                this.mc.playerController.func_20085_a(this.inventorySlots.windowId, i8, i3, this.mc.thePlayer);
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(int i1, int i2, int i3) {
        if (i3 == 0) {
            // empty if block
        }
    }

    @Override
    protected void keyTyped(char c1, int i2) {
        if (i2 == 1 || i2 == this.mc.gameSettings.keyBindInventory.keyCode) {
            this.mc.thePlayer.func_20059_m();
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.mc.playerController.func_20086_a(this.inventorySlots.windowId, this.mc.thePlayer);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

