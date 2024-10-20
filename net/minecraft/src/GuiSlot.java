/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiSlot {
    private final Minecraft mc;
    private final int width;
    private final int height;
    private final int top;
    private final int bottom;
    private final int right;
    private final int left;
    private final int posZ;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    private float initialClickY = -2.0f;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement = -1;
    private long lastClicked = 0L;
    private boolean field_25123_p = true;

    public GuiSlot(Minecraft minecraft1, int i2, int i3, int i4, int i5, int i6) {
        this.mc = minecraft1;
        this.width = i2;
        this.height = i3;
        this.top = i4;
        this.bottom = i5;
        this.posZ = i6;
        this.left = 0;
        this.right = i2;
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int var1, boolean var2);

    protected abstract boolean isSelected(int var1);

    protected abstract int getContentHeight();

    protected abstract void drawBackground();

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5);

    public void registerScrollButtons(List list1, int i2, int i3) {
        this.scrollUpButtonID = i2;
        this.scrollDownButtonID = i3;
    }

    private void bindAmountScrolled() {
        int i1 = this.getContentHeight() - (this.bottom - this.top - 4);
        if (i1 < 0) {
            i1 /= 2;
        }
        if (this.amountScrolled < 0.0f) {
            this.amountScrolled = 0.0f;
        }
        if (this.amountScrolled > (float)i1) {
            this.amountScrolled = i1;
        }
    }

    public void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.enabled) {
            if (guiButton1.id == this.scrollUpButtonID) {
                this.amountScrolled -= (float)(this.posZ * 2 / 3);
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            } else if (guiButton1.id == this.scrollDownButtonID) {
                this.amountScrolled += (float)(this.posZ * 2 / 3);
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
        }
    }

    public void drawScreen(int i1, int i2, float f3) {
        int i12;
        int i11;
        int i18;
        int i9;
        this.drawBackground();
        int i4 = this.getSize();
        int i5 = this.width / 2 + 124;
        int i6 = i5 + 6;
        if (Mouse.isButtonDown((int)0)) {
            if (this.initialClickY == -1.0f) {
                if (i2 >= this.top && i2 <= this.bottom) {
                    int i7 = this.width / 2 - 110;
                    int i8 = this.width / 2 + 110;
                    i9 = (i2 - this.top + (int)this.amountScrolled - 2) / this.posZ;
                    if (i1 >= i7 && i1 <= i8 && i9 >= 0 && i9 < i4) {
                        boolean z10 = i9 == this.selectedElement && System.currentTimeMillis() - this.lastClicked < 250L;
                        this.elementClicked(i9, z10);
                        this.selectedElement = i9;
                        this.lastClicked = System.currentTimeMillis();
                    }
                    if (i1 >= i5 && i1 <= i6) {
                        this.scrollMultiplier = -1.0f;
                        i18 = this.getContentHeight() - (this.bottom - this.top - 4);
                        if (i18 < 1) {
                            i18 = 1;
                        }
                        if ((i11 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight()) < 32) {
                            i11 = 32;
                        }
                        if (i11 > this.bottom - this.top - 8) {
                            i11 = this.bottom - this.top - 8;
                        }
                        this.scrollMultiplier /= (float)(this.bottom - this.top - i11) / (float)i18;
                    } else {
                        this.scrollMultiplier = 1.0f;
                    }
                    this.initialClickY = i2;
                } else {
                    this.initialClickY = -2.0f;
                }
            } else if (this.initialClickY >= 0.0f) {
                this.amountScrolled -= ((float)i2 - this.initialClickY) * this.scrollMultiplier;
                this.initialClickY = i2;
            }
        } else {
            this.initialClickY = -1.0f;
        }
        this.bindAmountScrolled();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        Tessellator tessellator15 = Tessellator.instance;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float f16 = 32.0f;
        tessellator15.startDrawingQuads();
        tessellator15.setColorOpaque_I(0x202020);
        tessellator15.addVertexWithUV(this.left, this.bottom, 0.0, (float)this.left / f16, (float)(this.bottom + (int)this.amountScrolled) / f16);
        tessellator15.addVertexWithUV(this.right, this.bottom, 0.0, (float)this.right / f16, (float)(this.bottom + (int)this.amountScrolled) / f16);
        tessellator15.addVertexWithUV(this.right, this.top, 0.0, (float)this.right / f16, (float)(this.top + (int)this.amountScrolled) / f16);
        tessellator15.addVertexWithUV(this.left, this.top, 0.0, (float)this.left / f16, (float)(this.top + (int)this.amountScrolled) / f16);
        tessellator15.draw();
        i9 = 0;
        while (i9 < i4) {
            i18 = this.width / 2 - 92 - 16;
            i11 = this.top + 4 + i9 * this.posZ - (int)this.amountScrolled;
            i12 = this.posZ - 4;
            if (this.field_25123_p && this.isSelected(i9)) {
                int i13 = this.width / 2 - 110;
                int i14 = this.width / 2 + 110;
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3553);
                tessellator15.startDrawingQuads();
                tessellator15.setColorOpaque_I(0x808080);
                tessellator15.addVertexWithUV(i13, i11 + i12 + 2, 0.0, 0.0, 1.0);
                tessellator15.addVertexWithUV(i14, i11 + i12 + 2, 0.0, 1.0, 1.0);
                tessellator15.addVertexWithUV(i14, i11 - 2, 0.0, 1.0, 0.0);
                tessellator15.addVertexWithUV(i13, i11 - 2, 0.0, 0.0, 0.0);
                tessellator15.setColorOpaque_I(0);
                tessellator15.addVertexWithUV(i13 + 1, i11 + i12 + 1, 0.0, 0.0, 1.0);
                tessellator15.addVertexWithUV(i14 - 1, i11 + i12 + 1, 0.0, 1.0, 1.0);
                tessellator15.addVertexWithUV(i14 - 1, i11 - 1, 0.0, 1.0, 0.0);
                tessellator15.addVertexWithUV(i13 + 1, i11 - 1, 0.0, 0.0, 0.0);
                tessellator15.draw();
                GL11.glEnable((int)3553);
            }
            this.drawSlot(i9, i18, i11, i12, tessellator15);
            ++i9;
        }
        int b17 = 4;
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)3553);
        tessellator15.startDrawingQuads();
        tessellator15.setColorRGBA_I(0, 0);
        tessellator15.addVertexWithUV(this.left, this.top + b17, 0.0, 0.0, 1.0);
        tessellator15.addVertexWithUV(this.right, this.top + b17, 0.0, 1.0, 1.0);
        tessellator15.setColorRGBA_I(0, 255);
        tessellator15.addVertexWithUV(this.right, this.top, 0.0, 1.0, 0.0);
        tessellator15.addVertexWithUV(this.left, this.top, 0.0, 0.0, 0.0);
        tessellator15.draw();
        tessellator15.startDrawingQuads();
        tessellator15.setColorRGBA_I(0, 255);
        tessellator15.addVertexWithUV(this.left, this.bottom, 0.0, 0.0, 1.0);
        tessellator15.addVertexWithUV(this.right, this.bottom, 0.0, 1.0, 1.0);
        tessellator15.setColorRGBA_I(0, 0);
        tessellator15.addVertexWithUV(this.right, this.bottom - b17, 0.0, 1.0, 0.0);
        tessellator15.addVertexWithUV(this.left, this.bottom - b17, 0.0, 0.0, 0.0);
        tessellator15.draw();
        i18 = this.getContentHeight() - (this.bottom - this.top - 4);
        if (i18 > 0) {
            i11 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            if (i11 < 32) {
                i11 = 32;
            }
            if (i11 > this.bottom - this.top - 8) {
                i11 = this.bottom - this.top - 8;
            }
            if ((i12 = (int)this.amountScrolled * (this.bottom - this.top - i11) / i18 + this.top) < this.top) {
                i12 = this.top;
            }
            tessellator15.startDrawingQuads();
            tessellator15.setColorRGBA_I(0, 255);
            tessellator15.addVertexWithUV(i5, this.bottom, 0.0, 0.0, 1.0);
            tessellator15.addVertexWithUV(i6, this.bottom, 0.0, 1.0, 1.0);
            tessellator15.addVertexWithUV(i6, this.top, 0.0, 1.0, 0.0);
            tessellator15.addVertexWithUV(i5, this.top, 0.0, 0.0, 0.0);
            tessellator15.draw();
            tessellator15.startDrawingQuads();
            tessellator15.setColorRGBA_I(0x808080, 255);
            tessellator15.addVertexWithUV(i5, i12 + i11, 0.0, 0.0, 1.0);
            tessellator15.addVertexWithUV(i6, i12 + i11, 0.0, 1.0, 1.0);
            tessellator15.addVertexWithUV(i6, i12, 0.0, 1.0, 0.0);
            tessellator15.addVertexWithUV(i5, i12, 0.0, 0.0, 0.0);
            tessellator15.draw();
            tessellator15.startDrawingQuads();
            tessellator15.setColorRGBA_I(0xC0C0C0, 255);
            tessellator15.addVertexWithUV(i5, i12 + i11 - 1, 0.0, 0.0, 1.0);
            tessellator15.addVertexWithUV(i6 - 1, i12 + i11 - 1, 0.0, 1.0, 1.0);
            tessellator15.addVertexWithUV(i6 - 1, i12, 0.0, 1.0, 0.0);
            tessellator15.addVertexWithUV(i5, i12, 0.0, 0.0, 0.0);
            tessellator15.draw();
        }
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)3042);
    }

    private void overlayBackground(int i1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float f6 = 32.0f;
        tessellator5.startDrawingQuads();
        tessellator5.setColorRGBA_I(0x404040, i4);
        tessellator5.addVertexWithUV(0.0, i2, 0.0, 0.0, (float)i2 / f6);
        tessellator5.addVertexWithUV(this.width, i2, 0.0, (float)this.width / f6, (float)i2 / f6);
        tessellator5.setColorRGBA_I(0x404040, i3);
        tessellator5.addVertexWithUV(this.width, i1, 0.0, (float)this.width / f6, (float)i1 / f6);
        tessellator5.addVertexWithUV(0.0, i1, 0.0, 0.0, (float)i1 / f6);
        tessellator5.draw();
    }
}

