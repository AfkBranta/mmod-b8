/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.ClickGUI.elements;

import MEDMEX.ClickGUI.ClickGUI;
import MEDMEX.ClickGUI.Panel;
import MEDMEX.ClickGUI.elements.Element;
import MEDMEX.ClickGUI.elements.menu.ElementCheckBox;
import MEDMEX.ClickGUI.elements.menu.ElementComboBox;
import MEDMEX.ClickGUI.elements.menu.ElementSlider;
import MEDMEX.ClickGUI.elements.util.FontUtil;
import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.GuiUtils;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.src.Gui;
import org.lwjgl.input.Keyboard;

public class ModuleButton {
    public Module mod;
    public ArrayList<Element> menuelements;
    public Panel parent;
    public double x;
    public double y;
    public double width;
    public double height;
    public boolean extended = false;
    public boolean listening = false;
    public ClickGUI clickgui;

    public ModuleButton(Module m, Panel pl) {
        this.mod = m;
        this.height = 11.0;
        this.parent = pl;
        this.menuelements = new ArrayList();
        this.clickgui = this.parent.clickgui;
        if (Client.settingsManager.getSettingsByMod(m) != null) {
            for (Setting s : Client.settingsManager.getSettingsByMod(m)) {
                if (s.isCheck()) {
                    this.menuelements.add(new ElementCheckBox(this, s));
                    continue;
                }
                if (s.isSlider()) {
                    this.menuelements.add(new ElementSlider(this, s));
                    continue;
                }
                if (!s.isCombo()) continue;
                this.menuelements.add(new ElementComboBox(this, s));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = new Color(Client.clientColors[0]);
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
        int textcolor = GuiUtils.getGradientValue(Client.clientColors[0], Client.clientColors[1], 3, 10);
        if (this.mod.isEnabled) {
            Gui.drawRect(this.x - 2.0, this.y + (double)ClickGUI.offset, this.x + this.width + 2.0, this.y + this.height + 1.0 + (double)ClickGUI.offset, color);
            textcolor = GuiUtils.getGradientValue(Client.clientColors[0], Client.clientColors[1], 11, 20);
        }
        if (this.isHovered(mouseX, mouseY)) {
            Gui.drawRect(this.x - 2.0, this.y + (double)ClickGUI.offset, this.x + this.width + 2.0, this.y + this.height + 1.0 + (double)ClickGUI.offset, 0x55111111);
        }
        FontUtil.drawTotalCenteredStringWithShadow(this.mod.getName(), this.x + this.width / 2.0, this.y + 1.0 + this.height / 2.0 + (double)ClickGUI.offset, textcolor);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.isHovered(mouseX, mouseY)) {
            return false;
        }
        if (mouseButton == 0) {
            this.mod.toggle();
        } else if (mouseButton == 1) {
            if (this.menuelements != null && this.menuelements.size() > 0) {
                boolean b = !this.extended;
                MEDMEX.Modules.Visual.ClickGUI.instance.gui.closeAllSettings();
                this.extended = b;
            }
        } else if (mouseButton == 2) {
            this.listening = true;
        }
        return true;
    }

    public boolean keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.listening) {
            if (keyCode != 1) {
                Client.sendLocalChat("Bound '" + this.mod.getName() + "'" + " to '" + Keyboard.getKeyName((int)keyCode) + "'");
                this.mod.setKeybind(keyCode);
            } else {
                Client.sendLocalChat("Unbound '" + this.mod.getName() + "'");
                this.mod.setKeybind(0);
            }
            this.listening = false;
            return true;
        }
        return false;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

