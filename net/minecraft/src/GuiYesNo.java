/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;

public class GuiYesNo
extends GuiScreen {
    private GuiScreen parentScreen;
    private String message1;
    private String message2;
    private String field_22106_k;
    private String field_22105_l;
    private int worldNumber;

    public GuiYesNo(GuiScreen guiScreen1, String string2, String string3, String string4, String string5, int i6) {
        this.parentScreen = guiScreen1;
        this.message1 = string2;
        this.message2 = string3;
        this.field_22106_k = string4;
        this.field_22105_l = string5;
        this.worldNumber = i6;
    }

    @Override
    public void initGui() {
        this.controlList.add(new GuiSmallButton(0, this.width / 2 - 155 + 0, this.height / 6 + 96, this.field_22106_k));
        this.controlList.add(new GuiSmallButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.field_22105_l));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        this.parentScreen.deleteWorld(guiButton1.id == 0, this.worldNumber);
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 70, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 90, 0xFFFFFF);
        super.drawScreen(i1, i2, f3);
    }
}

