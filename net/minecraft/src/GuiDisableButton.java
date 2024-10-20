/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

public class GuiDisableButton
extends Gui {
    private final FontRenderer fontRenderer;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private String text;
    private int maxStringLength;
    private int cursorCounter;
    public boolean isFocused = false;
    public boolean isEnabled = true;

    public GuiDisableButton(FontRenderer fontRenderer1, int i2, int i3, int i4, int i5, String string6) {
        this.fontRenderer = fontRenderer1;
        this.xPos = i2;
        this.yPos = i3;
        this.width = i4;
        this.height = i5;
        this.setText(string6);
    }

    public void setText(String string1) {
        this.text = string1;
    }

    public String getText() {
        return this.text;
    }

    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    public void textboxKeyTyped(char c1, int i2) {
        if (this.isEnabled && this.isFocused) {
            if (c1 == '\u0016') {
                int i4;
                String string3 = GuiScreen.getClipboardString();
                if (string3 == null) {
                    string3 = "";
                }
                if ((i4 = 32 - this.text.length()) > string3.length()) {
                    i4 = string3.length();
                }
                if (i4 > 0) {
                    this.text = String.valueOf(this.text) + string3.substring(0, i4);
                }
            }
            if (i2 == 14 && this.text.length() > 0) {
                this.text = this.text.substring(0, this.text.length() - 1);
            }
            if (FontAllowedCharacters.allowedCharacters.indexOf(c1) >= 0 && (this.text.length() < this.maxStringLength || this.maxStringLength == 0)) {
                this.text = String.valueOf(this.text) + c1;
            }
        }
    }

    public void mouseClicked(int i1, int i2, int i3) {
        boolean z4;
        boolean bl = z4 = this.isEnabled && i1 >= this.xPos && i1 < this.xPos + this.width && i2 >= this.yPos && i2 < this.yPos + this.height;
        if (z4 && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = z4;
    }

    public void drawTextBox() {
        GuiDisableButton.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
        GuiDisableButton.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
        if (this.isEnabled) {
            boolean z1 = this.isFocused && this.cursorCounter / 6 % 2 == 0;
            this.drawString(this.fontRenderer, String.valueOf(this.text) + (z1 ? "_" : ""), this.xPos + 4, this.yPos + (this.height - 8) / 2, 0xE0E0E0);
        } else {
            this.drawString(this.fontRenderer, this.text, this.xPos + 4, this.yPos + (this.height - 8) / 2, 0x707070);
        }
    }

    public void setMaxStringLength(int i1) {
        this.maxStringLength = i1;
    }
}

