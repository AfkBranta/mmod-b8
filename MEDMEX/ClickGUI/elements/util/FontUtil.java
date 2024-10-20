/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ClickGUI.elements.util;

import MEDMEX.Utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

public class FontUtil {
    private static FontRenderer fontRenderer;

    public static void setupFontUtils() {
        fontRenderer = Minecraft.theMinecraft.fontRenderer;
    }

    public static int getStringWidth(String text) {
        return fontRenderer.getStringWidth(text);
    }

    public static int getFontHeight() {
        return 9;
    }

    public static void drawString(String text, double x, double y, int color) {
        GuiUtils.drawString(text, (float)x, (float)y, color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    public static void drawCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawTotalCenteredString(String text, double x, double y, int color) {
        FontUtil.drawString(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y - 4.0, color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        FontUtil.drawStringWithShadow(text, x - (double)(fontRenderer.getStringWidth(text) / 2), y - 4.5, color);
    }
}

