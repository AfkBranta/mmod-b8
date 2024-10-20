/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.ClickGUI;

import MEDMEX.ClickGUI.Panel;
import MEDMEX.ClickGUI.elements.Element;
import MEDMEX.ClickGUI.elements.ModuleButton;
import MEDMEX.ClickGUI.elements.menu.ElementSlider;
import MEDMEX.ClickGUI.elements.util.FontUtil;
import MEDMEX.Client;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.SettingsManager;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClickGUI
extends GuiScreen {
    public static ArrayList<Panel> panels;
    public static ArrayList<Panel> rpanels;
    private ModuleButton mb = null;
    public SettingsManager setmgr = Client.settingsManager;
    public static int offset;

    public ClickGUI() {
        offset = 0;
        FontUtil.setupFontUtils();
        panels = new ArrayList();
        double pwidth = 80.0;
        double pheight = 15.0;
        double px = 10.0;
        double py = 10.0;
        double pyplus = pheight + 10.0;
        Category[] categoryArray = Category.values();
        int n = categoryArray.length;
        int n2 = 0;
        while (n2 < n) {
            final Category c = categoryArray[n2];
            String title = String.valueOf(Character.toUpperCase(c.name().toLowerCase().charAt(0))) + c.name().toLowerCase().substring(1);
            panels.add(new Panel(title, py * 4.0 - 10.0, 10.0, pwidth, pheight, true, this){

                @Override
                public void setup() {
                    for (Module m : Client.moduleManager.modules) {
                        if (!m.getCategory().equals((Object)c)) continue;
                        this.Elements.add(new ModuleButton(m, this));
                    }
                }
            });
            py += pyplus;
            ++n2;
        }
        rpanels = new ArrayList();
        for (Panel p : panels) {
            rpanels.add(p);
        }
        Collections.reverse(rpanels);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                if ((offset += 26) > 0) {
                    offset = 0;
                }
            } else if (wheel > 0) {
                offset -= 26;
            }
        }
        for (Panel p : panels) {
            p.drawScreen(mouseX, mouseY, partialTicks);
        }
        ScaledResolution s = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        this.mb = null;
        block1: for (Panel p : panels) {
            if (p == null || !p.visible || !p.extended || p.Elements == null || p.Elements.size() <= 0) continue;
            for (ModuleButton e : p.Elements) {
                if (!e.listening) continue;
                this.mb = e;
                break block1;
            }
        }
        for (Panel panel : panels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended || b.menuelements == null || b.menuelements.isEmpty()) continue;
                double off = 0.0;
                Color temp = new Color(Client.clientColors[0]);
                int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
                for (Element e : b.menuelements) {
                    e.offset = off;
                    e.update();
                    if (MEDMEX.Modules.Visual.ClickGUI.instance.getSet("Design").getValString().equalsIgnoreCase("New")) {
                        Gui.drawRect(e.x, e.y + (double)offset, e.x + e.width + 2.0, e.y + e.height + (double)offset, outlineColor);
                    }
                    e.drawScreen(mouseX, mouseY, partialTicks);
                    off += e.height;
                }
            }
        }
        if (this.mb != null) {
            ClickGUI.drawRect(0.0f, 0.0f, this.width, this.height, -2012213232);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(s.getScaledWidth() / 2), (float)(s.getScaledHeight() / 2), (float)0.0f);
            GL11.glScalef((float)4.0f, (float)4.0f, (float)0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0.0, -10.0, -1);
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.0f);
            FontUtil.drawTotalCenteredStringWithShadow("Press 'ESCAPE' to unbind " + this.mb.mod.getName() + (this.mb.mod.getKeybind() > -1 ? " (" + Keyboard.getKeyName((int)this.mb.mod.getKeybind()) + ")" : ""), 0.0, 0.0, -1);
            GL11.glScalef((float)0.25f, (float)0.25f, (float)0.0f);
            GL11.glPopMatrix();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.mb != null) {
            return;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended) continue;
                for (Element e : b.menuelements) {
                    if (!e.mouseClicked(mouseX, mouseY, mouseButton)) continue;
                    return;
                }
            }
        }
        for (Panel p : rpanels) {
            if (!p.mouseClicked(mouseX, mouseY - offset, mouseButton)) continue;
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        if (this.mb != null) {
            return;
        }
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended) continue;
                for (Element e : b.menuelements) {
                    e.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
        for (Panel p : rpanels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseMovedOrUp(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Panel p : rpanels) {
            if (p == null || !p.visible || !p.extended || p.Elements == null || p.Elements.size() <= 0) continue;
            for (ModuleButton e : p.Elements) {
                try {
                    if (!e.keyTyped(typedChar, keyCode)) continue;
                    return;
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
    }

    @Override
    public void onGuiClosed() {
        for (Panel panel : rpanels) {
            if (!panel.extended || !panel.visible || panel.Elements == null) continue;
            for (ModuleButton b : panel.Elements) {
                if (!b.extended) continue;
                for (Element e : b.menuelements) {
                    if (!(e instanceof ElementSlider)) continue;
                    ((ElementSlider)e).dragging = false;
                }
            }
        }
    }

    public void closeAllSettings() {
        for (Panel p : rpanels) {
            if (p == null || !p.visible || !p.extended || p.Elements == null || p.Elements.size() <= 0) continue;
            for (ModuleButton moduleButton : p.Elements) {
            }
        }
    }
}

