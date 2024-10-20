/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class Gui {
    protected float zLevel = 0.0f;

    public static void drawRect(float i1, float i2, float i3, float i4, int i5) {
        float i6;
        if (i1 < i3) {
            i6 = i1;
            i1 = i3;
            i3 = i6;
        }
        if (i2 < i4) {
            i6 = i2;
            i2 = i4;
            i4 = i6;
        }
        float f11 = (float)(i5 >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(i5 >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(i5 >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(i5 & 0xFF) / 255.0f;
        Tessellator tessellator10 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f7, (float)f8, (float)f9, (float)f11);
        tessellator10.startDrawingQuads();
        tessellator10.addVertex(i1, i4, 0.0);
        tessellator10.addVertex(i3, i4, 0.0);
        tessellator10.addVertex(i3, i2, 0.0);
        tessellator10.addVertex(i1, i2, 0.0);
        tessellator10.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawRect(double i1, double i2, double i3, double i4, int i5) {
        double i6;
        if (i1 < i3) {
            i6 = i1;
            i1 = i3;
            i3 = i6;
        }
        if (i2 < i4) {
            i6 = i2;
            i2 = i4;
            i4 = i6;
        }
        float f11 = (float)(i5 >> 24 & 0xFF) / 255.0f;
        float f7 = (float)(i5 >> 16 & 0xFF) / 255.0f;
        float f8 = (float)(i5 >> 8 & 0xFF) / 255.0f;
        float f9 = (float)(i5 & 0xFF) / 255.0f;
        Tessellator tessellator10 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f7, (float)f8, (float)f9, (float)f11);
        tessellator10.startDrawingQuads();
        tessellator10.addVertex(i1, i4, 0.0);
        tessellator10.addVertex(i3, i4, 0.0);
        tessellator10.addVertex(i3, i2, 0.0);
        tessellator10.addVertex(i1, i2, 0.0);
        tessellator10.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    protected void drawGradientRect(int i1, int i2, int i3, int i4, int i5, int i6) {
        float f7 = (float)(i5 >> 24 & 0xFF) / 255.0f;
        float f8 = (float)(i5 >> 16 & 0xFF) / 255.0f;
        float f9 = (float)(i5 >> 8 & 0xFF) / 255.0f;
        float f10 = (float)(i5 & 0xFF) / 255.0f;
        float f11 = (float)(i6 >> 24 & 0xFF) / 255.0f;
        float f12 = (float)(i6 >> 16 & 0xFF) / 255.0f;
        float f13 = (float)(i6 >> 8 & 0xFF) / 255.0f;
        float f14 = (float)(i6 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Tessellator tessellator15 = Tessellator.instance;
        tessellator15.startDrawingQuads();
        tessellator15.setColorRGBA_F(f8, f9, f10, f7);
        tessellator15.addVertex(i3, i2, 0.0);
        tessellator15.addVertex(i1, i2, 0.0);
        tessellator15.setColorRGBA_F(f12, f13, f14, f11);
        tessellator15.addVertex(i1, i4, 0.0);
        tessellator15.addVertex(i3, i4, 0.0);
        tessellator15.draw();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    public void drawCenteredString(FontRenderer fontRenderer1, String string2, int i3, int i4, int i5) {
        fontRenderer1.drawStringWithShadow(string2, i3 - fontRenderer1.getStringWidth(string2) / 2, i4, i5);
    }

    public void drawString(FontRenderer fontRenderer1, String string2, int i3, int i4, int i5) {
        fontRenderer1.drawStringWithShadow(string2, i3, i4, i5);
    }

    public void drawTexturedModalRect(int i1, int i2, int i3, int i4, int i5, int i6) {
        float f7 = 0.00390625f;
        float f8 = 0.00390625f;
        Tessellator tessellator9 = Tessellator.instance;
        tessellator9.startDrawingQuads();
        tessellator9.addVertexWithUV(i1 + 0, i2 + i6, this.zLevel, (float)(i3 + 0) * f7, (float)(i4 + i6) * f8);
        tessellator9.addVertexWithUV(i1 + i5, i2 + i6, this.zLevel, (float)(i3 + i5) * f7, (float)(i4 + i6) * f8);
        tessellator9.addVertexWithUV(i1 + i5, i2 + 0, this.zLevel, (float)(i3 + i5) * f7, (float)(i4 + 0) * f8);
        tessellator9.addVertexWithUV(i1 + 0, i2 + 0, this.zLevel, (float)(i3 + 0) * f7, (float)(i4 + 0) * f8);
        tessellator9.draw();
    }
}

