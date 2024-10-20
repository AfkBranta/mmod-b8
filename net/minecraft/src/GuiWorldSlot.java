/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Date;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.MathHelper;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.Tessellator;

class GuiWorldSlot
extends GuiSlot {
    final GuiSelectWorld parentWorldGui;

    public GuiWorldSlot(GuiSelectWorld guiSelectWorld1) {
        super(guiSelectWorld1.mc, guiSelectWorld1.width, guiSelectWorld1.height, 32, guiSelectWorld1.height - 64, 36);
        this.parentWorldGui = guiSelectWorld1;
    }

    @Override
    protected int getSize() {
        return GuiSelectWorld.getSize(this.parentWorldGui).size();
    }

    @Override
    protected void elementClicked(int i1, boolean z2) {
        boolean z3;
        GuiSelectWorld.onElementSelected(this.parentWorldGui, i1);
        GuiSelectWorld.getSelectButton((GuiSelectWorld)this.parentWorldGui).enabled = z3 = GuiSelectWorld.getSelectedWorld(this.parentWorldGui) >= 0 && GuiSelectWorld.getSelectedWorld(this.parentWorldGui) < this.getSize();
        GuiSelectWorld.getRenameButton((GuiSelectWorld)this.parentWorldGui).enabled = z3;
        GuiSelectWorld.getDeleteButton((GuiSelectWorld)this.parentWorldGui).enabled = z3;
        if (z2 && z3) {
            this.parentWorldGui.selectWorld(i1);
        }
    }

    @Override
    protected boolean isSelected(int i1) {
        return i1 == GuiSelectWorld.getSelectedWorld(this.parentWorldGui);
    }

    @Override
    protected int getContentHeight() {
        return GuiSelectWorld.getSize(this.parentWorldGui).size() * 36;
    }

    @Override
    protected void drawBackground() {
        this.parentWorldGui.drawDefaultBackground();
    }

    @Override
    protected void drawSlot(int i1, int i2, int i3, int i4, Tessellator tessellator5) {
        SaveFormatComparator saveFormatComparator6 = (SaveFormatComparator)GuiSelectWorld.getSize(this.parentWorldGui).get(i1);
        String string7 = saveFormatComparator6.getDisplayName();
        if (string7 == null || MathHelper.stringNullOrLengthZero(string7)) {
            string7 = String.valueOf(GuiSelectWorld.func_22087_f(this.parentWorldGui)) + " " + (i1 + 1);
        }
        String string8 = saveFormatComparator6.getFileName();
        string8 = String.valueOf(string8) + " (" + GuiSelectWorld.getDateFormatter(this.parentWorldGui).format(new Date(saveFormatComparator6.func_22163_e()));
        long j9 = saveFormatComparator6.func_22159_c();
        string8 = String.valueOf(string8) + ", " + (float)(j9 / 1024L * 100L / 1024L) / 100.0f + " MB)";
        String string11 = "";
        if (saveFormatComparator6.func_22161_d()) {
            string11 = String.valueOf(GuiSelectWorld.func_22088_h(this.parentWorldGui)) + " " + string11;
        }
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, string7, i2 + 2, i3 + 1, 0xFFFFFF);
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, string8, i2 + 2, i3 + 12, 0x808080);
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, string11, i2 + 2, i3 + 12 + 10, 0x808080);
    }
}

