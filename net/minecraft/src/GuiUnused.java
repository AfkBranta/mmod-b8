/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.GuiScreen;

public class GuiUnused
extends GuiScreen {
    private String message1;
    private String message2;

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 90, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 110, 0xFFFFFF);
        super.drawScreen(i1, i2, f3);
    }

    @Override
    protected void keyTyped(char c1, int i2) {
    }
}

