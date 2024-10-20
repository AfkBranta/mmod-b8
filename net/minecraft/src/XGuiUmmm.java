/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.XErrr;
import org.lwjgl.opengl.GL11;

public class XGuiUmmm
extends Gui {
    private List field_25090_a = new ArrayList();
    private Minecraft field_25089_b;

    public XGuiUmmm(Minecraft minecraft1) {
        this.field_25089_b = minecraft1;
    }

    public void func_25088_a() {
        int i1 = 0;
        while (i1 < this.field_25090_a.size()) {
            XErrr xErrr2 = (XErrr)this.field_25090_a.get(i1);
            xErrr2.func_25127_a();
            xErrr2.func_25125_a(this);
            if (xErrr2.field_25139_h) {
                this.field_25090_a.remove(i1--);
            }
            ++i1;
        }
    }

    public void func_25087_a(float f1) {
        this.field_25089_b.renderEngine.bindTexture(this.field_25089_b.renderEngine.getTexture("/gui/particles.png"));
        int i2 = 0;
        while (i2 < this.field_25090_a.size()) {
            XErrr xErrr3 = (XErrr)this.field_25090_a.get(i2);
            int i4 = (int)(xErrr3.field_25144_c + (xErrr3.field_25146_a - xErrr3.field_25144_c) * (double)f1 - 4.0);
            int i5 = (int)(xErrr3.field_25143_d + (xErrr3.field_25145_b - xErrr3.field_25143_d) * (double)f1 - 4.0);
            float f6 = (float)(xErrr3.field_25129_r + (xErrr3.field_25133_n - xErrr3.field_25129_r) * (double)f1);
            float f7 = (float)(xErrr3.field_25132_o + (xErrr3.field_25136_k - xErrr3.field_25132_o) * (double)f1);
            float f8 = (float)(xErrr3.field_25131_p + (xErrr3.field_25135_l - xErrr3.field_25131_p) * (double)f1);
            float f9 = (float)(xErrr3.field_25130_q + (xErrr3.field_25134_m - xErrr3.field_25130_q) * (double)f1);
            GL11.glColor4f((float)f7, (float)f8, (float)f9, (float)f6);
            this.drawTexturedModalRect(i4, i5, 40, 0, 8, 8);
            ++i2;
        }
    }
}

