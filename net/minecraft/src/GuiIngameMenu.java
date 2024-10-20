/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.AltManager.GuiAltManager;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StatList;

public class GuiIngameMenu
extends GuiScreen {
    private int updateCounter2 = 0;
    private int updateCounter = 0;

    @Override
    public void initGui() {
        this.updateCounter2 = 0;
        this.controlList.clear();
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Save and quit to title"));
        if (this.mc.isMultiplayerWorld()) {
            ((GuiButton)this.controlList.get((int)0)).displayString = "Disconnect";
        }
        this.controlList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24, "Back to game"));
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96, "Options..."));
        this.controlList.add(new GuiButton(69, this.width / 2 - 100, this.height / 4 + 72, "Account Manager"));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (guiButton1.id == 1) {
            this.mc.field_25001_G.func_25100_a(StatList.field_25180_i, 1);
            if (this.mc.isMultiplayerWorld()) {
                this.mc.theWorld.sendQuittingDisconnectingPacket();
            }
            this.mc.changeWorld1(null);
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        if (guiButton1.id == 4) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
        if (guiButton1.id == 69) {
            this.mc.displayGuiScreen(new GuiAltManager(this));
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCounter;
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        boolean z4;
        this.drawDefaultBackground();
        boolean bl = z4 = !this.mc.theWorld.func_650_a(this.updateCounter2++);
        if (z4 || this.updateCounter < 20) {
            float f5 = ((float)(this.updateCounter % 10) + f3) / 10.0f;
            f5 = MathHelper.sin(f5 * (float)Math.PI * 2.0f) * 0.2f + 0.8f;
            int i6 = (int)(255.0f * f5);
            this.drawString(this.fontRenderer, "Saving level..", 8, this.height - 16, i6 << 16 | i6 << 8 | i6);
        }
        this.drawCenteredString(this.fontRenderer, "Game menu", this.width / 2, 40, 0xFFFFFF);
        super.drawScreen(i1, i2, f3);
    }
}

