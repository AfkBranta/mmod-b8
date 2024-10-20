/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiChat
extends GuiScreen {
    protected String message = "";
    private int updateCounter = 0;
    private static final String field_20082_i = FontAllowedCharacters.allowedCharacters;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void keyTyped(char c1, int i2) {
        if (i2 == 1) {
            this.mc.displayGuiScreen(null);
        } else if (i2 == 28) {
            String string4;
            String string3 = this.message.trim();
            if (string3.length() > 0 && !this.mc.lineIsCommand(string4 = this.message.trim())) {
                this.mc.thePlayer.sendChatMessage(string4);
            }
            this.mc.displayGuiScreen(null);
        } else {
            if (i2 == 14 && this.message.length() > 0) {
                this.message = this.message.substring(0, this.message.length() - 1);
            }
            if (field_20082_i.indexOf(c1) >= 0 && this.message.length() < 100) {
                this.message = String.valueOf(this.message) + c1;
            }
        }
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        GuiChat.drawRect(2.0f, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.drawString(this.fontRenderer, "> " + this.message + (this.updateCounter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 0xE0E0E0);
        super.drawScreen(i1, i2, f3);
    }

    @Override
    protected void mouseClicked(int i1, int i2, int i3) {
        if (i3 == 0) {
            if (this.mc.ingameGUI.field_933_a != null) {
                if (this.message.length() > 0 && !this.message.endsWith(" ")) {
                    this.message = String.valueOf(this.message) + " ";
                }
                this.message = String.valueOf(this.message) + this.mc.ingameGUI.field_933_a;
                int b4 = 100;
                if (this.message.length() > b4) {
                    this.message = this.message.substring(0, b4);
                }
            } else {
                super.mouseClicked(i1, i2, i3);
            }
        }
    }
}

