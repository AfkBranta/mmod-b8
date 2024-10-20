/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class LoadingScreenRenderer
implements IProgressUpdate {
    private String field_1004_a = "";
    private Minecraft mc;
    private String field_1007_c = "";
    private long field_1006_d = System.currentTimeMillis();
    private boolean field_1005_e = false;

    public LoadingScreenRenderer(Minecraft minecraft1) {
        this.mc = minecraft1;
    }

    public void printText(String string1) {
        this.field_1005_e = false;
        this.func_597_c(string1);
    }

    @Override
    public void func_594_b(String string1) {
        this.field_1005_e = true;
        this.func_597_c(this.field_1007_c);
    }

    public void func_597_c(String string1) {
        if (!this.mc.running) {
            if (!this.field_1005_e) {
                throw new MinecraftError();
            }
        } else {
            this.field_1007_c = string1;
            ScaledResolution scaledResolution2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            GL11.glClear((int)256);
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)scaledResolution2.field_25121_a, (double)scaledResolution2.field_25120_b, (double)0.0, (double)100.0, (double)300.0);
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-200.0f);
        }
    }

    @Override
    public void displayLoadingString(String string1) {
        if (!this.mc.running) {
            if (!this.field_1005_e) {
                throw new MinecraftError();
            }
        } else {
            this.field_1006_d = 0L;
            this.field_1004_a = string1;
            this.setLoadingProgress(-1);
            this.field_1006_d = 0L;
        }
    }

    @Override
    public void setLoadingProgress(int i1) {
        if (!this.mc.running) {
            if (!this.field_1005_e) {
                throw new MinecraftError();
            }
        } else {
            long j2 = System.currentTimeMillis();
            if (j2 - this.field_1006_d >= 20L) {
                this.field_1006_d = j2;
                ScaledResolution scaledResolution4 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int i5 = scaledResolution4.getScaledWidth();
                int i6 = scaledResolution4.getScaledHeight();
                GL11.glClear((int)256);
                GL11.glMatrixMode((int)5889);
                GL11.glLoadIdentity();
                GL11.glOrtho((double)0.0, (double)scaledResolution4.field_25121_a, (double)scaledResolution4.field_25120_b, (double)0.0, (double)100.0, (double)300.0);
                GL11.glMatrixMode((int)5888);
                GL11.glLoadIdentity();
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-200.0f);
                GL11.glClear((int)16640);
                Tessellator tessellator7 = Tessellator.instance;
                int i8 = this.mc.renderEngine.getTexture("/gui/background.png");
                GL11.glBindTexture((int)3553, (int)i8);
                float f9 = 32.0f;
                tessellator7.startDrawingQuads();
                tessellator7.setColorOpaque_I(0x404040);
                tessellator7.addVertexWithUV(0.0, i6, 0.0, 0.0, (float)i6 / f9);
                tessellator7.addVertexWithUV(i5, i6, 0.0, (float)i5 / f9, (float)i6 / f9);
                tessellator7.addVertexWithUV(i5, 0.0, 0.0, (float)i5 / f9, 0.0);
                tessellator7.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                tessellator7.draw();
                if (i1 >= 0) {
                    int b10 = 100;
                    int b11 = 2;
                    int i12 = i5 / 2 - b10 / 2;
                    int i13 = i6 / 2 + 16;
                    GL11.glDisable((int)3553);
                    tessellator7.startDrawingQuads();
                    tessellator7.setColorOpaque_I(0x808080);
                    tessellator7.addVertex(i12, i13, 0.0);
                    tessellator7.addVertex(i12, i13 + b11, 0.0);
                    tessellator7.addVertex(i12 + b10, i13 + b11, 0.0);
                    tessellator7.addVertex(i12 + b10, i13, 0.0);
                    tessellator7.setColorOpaque_I(0x80FF80);
                    tessellator7.addVertex(i12, i13, 0.0);
                    tessellator7.addVertex(i12, i13 + b11, 0.0);
                    tessellator7.addVertex(i12 + i1, i13 + b11, 0.0);
                    tessellator7.addVertex(i12 + i1, i13, 0.0);
                    tessellator7.draw();
                    GL11.glEnable((int)3553);
                }
                this.mc.fontRenderer.drawStringWithShadow(this.field_1007_c, (i5 - this.mc.fontRenderer.getStringWidth(this.field_1007_c)) / 2, i6 / 2 - 4 - 16, 0xFFFFFF);
                this.mc.fontRenderer.drawStringWithShadow(this.field_1004_a, (i5 - this.mc.fontRenderer.getStringWidth(this.field_1004_a)) / 2, i6 / 2 - 4 + 8, 0xFFFFFF);
                Display.update();
                try {
                    Thread.yield();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

