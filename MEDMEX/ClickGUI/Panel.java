/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ClickGUI;

import MEDMEX.ClickGUI.ClickGUI;
import MEDMEX.ClickGUI.elements.ModuleButton;
import MEDMEX.ClickGUI.elements.util.FontUtil;
import MEDMEX.Client;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.src.Gui;

public class Panel {
    public String title;
    public double x;
    public double y;
    private double x2;
    private double y2;
    public double width;
    public double height;
    public boolean dragging;
    public boolean extended;
    public boolean visible;
    public ArrayList<ModuleButton> Elements = new ArrayList();
    public ClickGUI clickgui;

    public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
        this.title = ititle;
        this.x = ix;
        this.y = iy;
        this.width = iwidth;
        this.height = iheight;
        this.extended = iextended;
        this.dragging = false;
        this.visible = true;
        this.clickgui = parent;
        this.setup();
    }

    public void setup() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) {
            return;
        }
        if (this.dragging) {
            this.x = this.x2 + (double)mouseX;
            this.y = this.y2 + (double)mouseY;
        }
        Color temp = new Color(Client.clientColors[0]);
        int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
        Gui.drawRect(this.x, this.y + (double)ClickGUI.offset, this.x + this.width, this.y + this.height + (double)ClickGUI.offset, -15592942);
        if (MEDMEX.Modules.Visual.ClickGUI.instance.getSet("Design").getValString().equalsIgnoreCase("New")) {
            Gui.drawRect(this.x - 2.0, this.y + (double)ClickGUI.offset, this.x, this.y + this.height + (double)ClickGUI.offset, outlineColor);
            FontUtil.drawStringWithShadow(this.title, this.x + 2.0, this.y + this.height / 2.0 - (double)(FontUtil.getFontHeight() / 2) + (double)ClickGUI.offset, -1052689);
        } else if (MEDMEX.Modules.Visual.ClickGUI.instance.getSet("Design").getValString().equalsIgnoreCase("JellyLike")) {
            Gui.drawRect(this.x + 4.0, this.y + 2.0, this.x + 4.3, this.y + this.height - 2.0, -5592406);
            Gui.drawRect(this.x - 4.0 + this.width, this.y + 2.0, this.x - 4.3 + this.width, this.y + this.height - 2.0, -5592406);
            FontUtil.drawTotalCenteredStringWithShadow(this.title, this.x + this.width / 2.0, this.y + this.height / 2.0, -1052689);
        }
        if (this.extended && !this.Elements.isEmpty()) {
            double startY = this.y + this.height;
            int epanelcolor = MEDMEX.Modules.Visual.ClickGUI.instance.getSet("Design").getValString().equalsIgnoreCase("New") ? -14474461 : (MEDMEX.Modules.Visual.ClickGUI.instance.getSet("Design").getValString().equalsIgnoreCase("JellyLike") ? -1156246251 : 0);
            for (ModuleButton et : this.Elements) {
                if (MEDMEX.Modules.Visual.ClickGUI.instance.getSet("Design").getValString().equalsIgnoreCase("New")) {
                    Gui.drawRect(this.x - 2.0, startY + (double)ClickGUI.offset, this.x + this.width, startY + et.height + 1.0 + (double)ClickGUI.offset, outlineColor);
                }
                Gui.drawRect(this.x, startY + (double)ClickGUI.offset, this.x + this.width, startY + et.height + 1.0 + (double)ClickGUI.offset, epanelcolor);
                et.x = this.x + 2.0;
                et.y = startY;
                et.width = this.width - 4.0;
                et.drawScreen(mouseX, mouseY, partialTicks);
                startY += et.height + 1.0;
            }
            Gui.drawRect(this.x, startY + 1.0 + (double)ClickGUI.offset, this.x + this.width, startY + 1.0 + (double)ClickGUI.offset, epanelcolor);
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible) {
            return false;
        }
        if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
            this.x2 = this.x - (double)mouseX;
            this.y2 = this.y - (double)mouseY;
            this.dragging = true;
            return true;
        }
        if (mouseButton == 1 && this.isHovered(mouseX, mouseY)) {
            this.extended = !this.extended;
            return true;
        }
        if (this.extended) {
            for (ModuleButton et : this.Elements) {
                if (!et.mouseClicked(mouseX, mouseY, mouseButton)) continue;
                return true;
            }
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.dragging = false;
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
    }
}

