/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.Utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiUtils {
    public static void drawString(String par1Str, float par2, float par3, int par4) {
        FontRenderer fr = Minecraft.theMinecraft.fontRenderer;
        fr.drawString(par1Str, par2, par3, par4);
    }

    public static void drawTexturedModalRect(int i1, int i2, int i3, int i4, int i5, int i6) {
        float f7 = 0.00390625f;
        float f8 = 0.00390625f;
        Tessellator tessellator9 = Tessellator.instance;
        tessellator9.startDrawingQuads();
        tessellator9.addVertexWithUV(i1 + 0, i2 + i6, 100.0, (float)(i3 + 0) * f7, (float)(i4 + i6) * f8);
        tessellator9.addVertexWithUV(i1 + i5, i2 + i6, 100.0, (float)(i3 + i5) * f7, (float)(i4 + i6) * f8);
        tessellator9.addVertexWithUV(i1 + i5, i2 + 0, 100.0, (float)(i3 + i5) * f7, (float)(i4 + 0) * f8);
        tessellator9.addVertexWithUV(i1 + 0, i2 + 0, 100.0, (float)(i3 + 0) * f7, (float)(i4 + 0) * f8);
        tessellator9.draw();
    }

    public static void drawGradientRect(int i1, int i2, int i3, int i4, int i5, int i6) {
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

    public static Color numToColor(double num, double min, double max) {
        double normalized = (max - num) / (max - min);
        double color = normalized * 1.6777215E7;
        int r = ((int)color & 0xFF0000) >> 16;
        int g = ((int)color & 0xFF00) >> 8;
        int b = (int)color & 0xFF;
        return new Color(r, g, b, 100);
    }

    public static int getGradientValue(int start, int end, int step, int maxSteps) {
        int r1 = start >> 16 & 0xFF;
        int g1 = start >> 8 & 0xFF;
        int b1 = start & 0xFF;
        int r2 = end >> 16 & 0xFF;
        int g2 = end >> 8 & 0xFF;
        int b2 = end & 0xFF;
        int rDiff = r2 - r1;
        int gDiff = g2 - g1;
        int bDiff = b2 - b1;
        int newR = r1 + rDiff * step / (maxSteps - 1);
        int newG = g1 + gDiff * step / (maxSteps - 1);
        int newB = b1 + bDiff * step / (maxSteps - 1);
        return newR << 16 | newG << 8 | newB;
    }
}

