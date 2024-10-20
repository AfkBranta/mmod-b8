/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ClickGUI.elements.menu;

import MEDMEX.ClickGUI.ClickGUI;
import MEDMEX.ClickGUI.elements.Element;
import MEDMEX.ClickGUI.elements.ModuleButton;
import MEDMEX.ClickGUI.elements.util.FontUtil;
import MEDMEX.Client;
import MEDMEX.Settings.Setting;
import java.awt.Color;
import net.minecraft.src.Gui;

public class ElementCheckBox
extends Element {
    public ElementCheckBox(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = new Color(Client.clientColors[0]);
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();
        Gui.drawRect(this.x, this.y + (double)ClickGUI.offset, this.x + this.width, this.y + this.height + (double)ClickGUI.offset, -15066598);
        FontUtil.drawString(this.setstrg, this.x + this.width - (double)FontUtil.getStringWidth(this.setstrg), this.y + (double)(FontUtil.getFontHeight() / 2) - 0.5 + (double)ClickGUI.offset, -1);
        Gui.drawRect(this.x + 1.0, this.y + 2.0 + (double)ClickGUI.offset, this.x + 12.0, this.y + 13.0 + (double)ClickGUI.offset, this.set.getValBoolean() ? color : -16777216);
        if (this.isCheckHovered(mouseX, mouseY)) {
            Gui.drawRect(this.x + 1.0, this.y + 2.0 + (double)ClickGUI.offset, this.x + 12.0, this.y + 13.0 + (double)ClickGUI.offset, 0x55111111);
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isCheckHovered(mouseX, mouseY - ClickGUI.offset)) {
            this.set.setValBoolean(!this.set.getValBoolean());
            return true;
        }
        return super.mouseClicked(mouseX, mouseY - ClickGUI.offset, mouseButton);
    }

    public boolean isCheckHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x + 1.0 && (double)mouseX <= this.x + 12.0 && (double)mouseY >= this.y + 2.0 && (double)mouseY <= this.y + 13.0;
    }
}

