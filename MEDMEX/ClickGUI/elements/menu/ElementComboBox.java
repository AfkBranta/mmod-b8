/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ClickGUI.elements.menu;

import MEDMEX.ClickGUI.elements.Element;
import MEDMEX.ClickGUI.elements.ModuleButton;
import MEDMEX.ClickGUI.elements.util.FontUtil;
import MEDMEX.Client;
import MEDMEX.Settings.Setting;
import java.awt.Color;
import net.minecraft.src.Gui;

public class ElementComboBox
extends Element {
    public ElementComboBox(ModuleButton iparent, Setting iset) {
        this.parent = iparent;
        this.set = iset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = new Color(Client.clientColors[0]);
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
        FontUtil.drawTotalCenteredString(this.setstrg, this.x + this.width / 2.0, this.y + 7.0, -1);
        int clr1 = color;
        int clr2 = temp.getRGB();
        Gui.drawRect(this.x, this.y + 14.0, this.x + this.width, this.y + 15.0, 0x77000000);
        if (this.comboextended) {
            Gui.drawRect(this.x, this.y + 15.0, this.x + this.width, this.y + this.height, -1441656302);
            double ay = this.y + 15.0;
            for (String sld : this.set.getOptions()) {
                String elementtitle = String.valueOf(sld.substring(0, 1).toUpperCase()) + sld.substring(1, sld.length());
                FontUtil.drawCenteredString(elementtitle, this.x + this.width / 2.0, ay + 2.0, -1);
                if (sld.equalsIgnoreCase(this.set.getValString())) {
                    Gui.drawRect(this.x, ay, this.x + 1.5, ay + (double)FontUtil.getFontHeight() + 2.0, clr1);
                }
                if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay && (double)mouseY < ay + (double)FontUtil.getFontHeight() + 2.0) {
                    Gui.drawRect(this.x + this.width - 1.2, ay, this.x + this.width, ay + (double)FontUtil.getFontHeight() + 2.0, clr2);
                }
                ay += (double)(FontUtil.getFontHeight() + 2);
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.isButtonHovered(mouseX, mouseY)) {
                this.comboextended = !this.comboextended;
                return true;
            }
            if (!this.comboextended) {
                return false;
            }
            double ay = this.y + 15.0;
            for (String slcd : this.set.getOptions()) {
                if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay && (double)mouseY <= ay + (double)FontUtil.getFontHeight() + 2.0) {
                    if (this.clickgui != null && this.clickgui.setmgr != null) {
                        this.set.getParentMod().getSet(this.set.getName()).setValString(slcd);
                    }
                    return true;
                }
                ay += (double)(FontUtil.getFontHeight() + 2);
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isButtonHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + 15.0;
    }
}

