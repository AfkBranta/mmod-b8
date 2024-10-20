/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiDisableButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer
extends GuiScreen {
    private GuiScreen parentScreen;
    private GuiDisableButton field_22111_h;

    public GuiMultiplayer(GuiScreen guiScreen1) {
        this.parentScreen = guiScreen1;
    }

    @Override
    public void updateScreen() {
        this.field_22111_h.updateCursorCounter();
    }

    @Override
    public void initGui() {
        StringTranslate stringTranslate1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringTranslate1.translateKey("multiplayer.connect")));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.cancel")));
        String string2 = this.mc.gameSettings.lastServer.replaceAll("_", ":");
        ((GuiButton)this.controlList.get((int)0)).enabled = string2.length() > 0;
        this.field_22111_h = new GuiDisableButton(this.fontRenderer, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18, 200, 20, string2);
        this.field_22111_h.isFocused = true;
        this.field_22111_h.setMaxStringLength(32);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.enabled) {
            if (guiButton1.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guiButton1.id == 0) {
                String string2 = this.field_22111_h.getText();
                this.mc.gameSettings.lastServer = string2.replaceAll(":", "_");
                this.mc.gameSettings.saveOptions();
                String[] string3 = string2.split(":");
                this.mc.displayGuiScreen(new GuiConnecting(this.mc, string3[0], string3.length > 1 ? this.func_4067_a(string3[1], 25565) : 25565));
            }
        }
    }

    private int func_4067_a(String string1, int i2) {
        try {
            return Integer.parseInt(string1.trim());
        }
        catch (Exception exception4) {
            return i2;
        }
    }

    @Override
    protected void keyTyped(char c1, int i2) {
        this.field_22111_h.textboxKeyTyped(c1, i2);
        if (c1 == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
        ((GuiButton)this.controlList.get((int)0)).enabled = this.field_22111_h.getText().length() > 0;
    }

    @Override
    protected void mouseClicked(int i1, int i2, int i3) {
        super.mouseClicked(i1, i2, i3);
        this.field_22111_h.mouseClicked(i1, i2, i3);
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        StringTranslate stringTranslate4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.title"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        this.drawString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.info1"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 0xA0A0A0);
        this.drawString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.info2"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 9, 0xA0A0A0);
        this.drawString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.ipinfo"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 36, 0xA0A0A0);
        this.field_22111_h.drawTextBox();
        super.drawScreen(i1, i2, f3);
    }
}

