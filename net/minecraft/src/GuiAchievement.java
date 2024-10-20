/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class GuiAchievement
extends Gui {
    private Minecraft field_25082_a;
    private int field_25081_b;
    private int field_25086_c;
    private String field_25085_d;
    private String field_25084_e;
    private long field_25083_f;

    public GuiAchievement(Minecraft minecraft1) {
        this.field_25082_a = minecraft1;
    }

    private void func_25079_b() {
        GL11.glViewport((int)0, (int)0, (int)this.field_25082_a.displayWidth, (int)this.field_25082_a.displayHeight);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        this.field_25081_b = this.field_25082_a.displayWidth;
        this.field_25086_c = this.field_25082_a.displayHeight;
        ScaledResolution scaledResolution1 = new ScaledResolution(this.field_25082_a.gameSettings, this.field_25082_a.displayWidth, this.field_25082_a.displayHeight);
        this.field_25081_b = scaledResolution1.getScaledWidth();
        this.field_25086_c = scaledResolution1.getScaledHeight();
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)this.field_25081_b, (double)this.field_25086_c, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public void func_25080_a() {
        if (this.field_25083_f != 0L) {
            double d1 = (double)(System.currentTimeMillis() - this.field_25083_f) / 3000.0;
            if (d1 >= 0.0 && d1 <= 1.0) {
                this.func_25079_b();
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                double d3 = d1 * 2.0;
                if (d3 > 1.0) {
                    d3 = 2.0 - d3;
                }
                d3 *= 4.0;
                if ((d3 = 1.0 - d3) < 0.0) {
                    d3 = 0.0;
                }
                d3 *= d3;
                d3 *= d3;
                int i5 = this.field_25081_b - 160;
                int i6 = 0 - (int)(d3 * 36.0);
                int i7 = this.field_25082_a.renderEngine.getTexture("/achievement/bg.png");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3553);
                GL11.glBindTexture((int)3553, (int)i7);
                this.drawTexturedModalRect(i5, i6, 0, 188, 160, 32);
                this.field_25082_a.fontRenderer.drawString(this.field_25085_d, i5 + 30, i6 + 7, -256);
                this.field_25082_a.fontRenderer.drawString(this.field_25084_e, i5 + 30, i6 + 18, -1);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
            } else {
                this.field_25083_f = 0L;
            }
        }
    }
}

