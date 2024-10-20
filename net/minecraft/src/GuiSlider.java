/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiSlider
extends GuiButton {
    public float sliderValue = 1.0f;
    public boolean dragging = false;
    private EnumOptions idFloat = null;

    public GuiSlider(int i1, int i2, int i3, EnumOptions enumOptions4, String string5, float f6) {
        super(i1, i2, i3, 150, 20, string5);
        this.idFloat = enumOptions4;
        this.sliderValue = f6;
    }

    @Override
    protected int getHoverState(boolean z1) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft minecraft1, int i2, int i3) {
        if (this.enabled2) {
            if (this.dragging) {
                this.sliderValue = (float)(i2 - (this.xPosition + 4)) / (float)(this.width - 8);
                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }
                if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
                minecraft1.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
                this.displayString = minecraft1.gameSettings.getKeyBinding(this.idFloat);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft1, int i2, int i3) {
        if (super.mousePressed(minecraft1, i2, i3)) {
            this.sliderValue = (float)(i2 - (this.xPosition + 4)) / (float)(this.width - 8);
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            minecraft1.gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
            this.displayString = minecraft1.gameSettings.getKeyBinding(this.idFloat);
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int i1, int i2) {
        this.dragging = false;
    }
}

