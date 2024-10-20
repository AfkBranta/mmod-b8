/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import org.lwjgl.opengl.GL11;

public class GuiButton
extends Gui {
    protected int width = 200;
    protected int height = 20;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled = true;
    public boolean enabled2 = true;

    public GuiButton(int i1, int i2, int i3, String string4) {
        this(i1, i2, i3, 200, 20, string4);
    }

    public GuiButton(int i1, int i2, int i3, int i4, int i5, String string6) {
        this.id = i1;
        this.xPosition = i2;
        this.yPosition = i3;
        this.width = i4;
        this.height = i5;
        this.displayString = string6;
    }

    protected int getHoverState(boolean z1) {
        int b2 = 1;
        if (!this.enabled) {
            b2 = 0;
        } else if (z1) {
            b2 = 2;
        }
        return b2;
    }

    public void drawButton(Minecraft minecraft1, int i2, int i3) {
        if (this.enabled2) {
            FontRenderer fontRenderer4 = minecraft1.fontRenderer;
            GL11.glBindTexture((int)3553, (int)minecraft1.renderEngine.getTexture("/gui/gui.png"));
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            boolean z5 = i2 >= this.xPosition && i3 >= this.yPosition && i2 < this.xPosition + this.width && i3 < this.yPosition + this.height;
            int i6 = this.getHoverState(z5);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i6 * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i6 * 20, this.width / 2, this.height);
            this.mouseDragged(minecraft1, i2, i3);
            if (!this.enabled) {
                this.drawCenteredString(fontRenderer4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -6250336);
            } else if (z5) {
                this.drawCenteredString(fontRenderer4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xFFFFA0);
            } else {
                this.drawCenteredString(fontRenderer4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xE0E0E0);
            }
        }
    }

    protected void mouseDragged(Minecraft minecraft1, int i2, int i3) {
    }

    public void mouseReleased(int i1, int i2) {
    }

    public boolean mousePressed(Minecraft minecraft1, int i2, int i3) {
        return this.enabled && i2 >= this.xPosition && i3 >= this.yPosition && i2 < this.xPosition + this.width && i3 < this.yPosition + this.height;
    }
}

