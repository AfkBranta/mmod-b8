/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventGuiIngameRender;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.ModuleManager;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.GuiUtils;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.RenderUtils;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Slot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class HUD
extends Module {
    public static HUD instance;
    HashMap<Category, Integer> colorMap = new HashMap();

    public HUD() {
        super("HUD", "Heads Up Display", 0, Category.Visual);
        this.addSetting(new Setting("Watermark", (Module)this, true));
        this.addSetting(new Setting("Modules", (Module)this, true));
        this.addSetting(new Setting("Coords", (Module)this, true));
        this.addSetting(new Setting("Inventory", (Module)this, false));
        this.colorMap.put(Category.Player, 65349);
        this.colorMap.put(Category.Combat, 15417396);
        this.colorMap.put(Category.World, 16711866);
        this.colorMap.put(Category.Movement, 3422955);
        this.colorMap.put(Category.Exploit, 15777290);
        this.colorMap.put(Category.Visual, 15760650);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventGuiIngameRender) {
            ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            if (Keyboard.isKeyDown((int)61)) {
                return;
            }
            if (this.getSet("Watermark").getValBoolean()) {
                GuiUtils.drawString(Client.clientName, 4.0f, 4.0f, Client.clientColors[0]);
                GuiUtils.drawString(Client.clientVersion, 6 + this.fr.getStringWidth(Client.clientName), 4.0f, Client.clientColors[1]);
                double speed = PlayerUtils.getAveragePlayerSpeedOverSecond();
                DecimalFormat df = new DecimalFormat("#.#");
                GuiUtils.drawString("v: ", 4.0f, 12.0f, Client.clientColors[0]);
                GuiUtils.drawString(df.format(speed), 4 + this.fr.getStringWidth("v: "), 12.0f, Client.clientColors[1]);
            }
            if (this.getSet("Modules").getValBoolean()) {
                Collections.sort(ModuleManager.getModules(), new ModuleComparator());
                int count = 0;
                int enabledMods = Client.moduleManager.getEnabledModCount();
                for (Module m : ModuleManager.getModules()) {
                    if (!m.isEnabled) continue;
                    String mode = "";
                    if (m.getSet("Mode") != null) {
                        mode = " \u00a78" + m.getSet("Mode").getValString();
                    }
                    GuiUtils.drawString(String.valueOf(m.getName()) + mode, sr.getScaledWidth() - this.fr.getStringWidth(String.valueOf(m.getName()) + mode) - 4, 4 + count * 8, GuiUtils.getGradientValue(Client.clientColors[0], Client.clientColors[1], count, enabledMods));
                    ++count;
                }
            }
            if (this.getSet("Coords").getValBoolean()) {
                double x = (int)this.mc.thePlayer.posX;
                double y = (int)this.mc.thePlayer.posY;
                double z = (int)this.mc.thePlayer.posZ;
                String s = "[" + x + ", " + y + ", " + z + "]";
                int strLen = this.mc.fontRenderer.getStringWidth(s);
                GuiUtils.drawString(s, sr.getScaledWidth() - 4 - strLen, sr.getScaledHeight() - 10, GuiUtils.getGradientValue(Client.clientColors[0], Client.clientColors[1], 6, 10));
                GuiUtils.drawString("XYZ", sr.getScaledWidth() - 4 - strLen - 20, sr.getScaledHeight() - 10, GuiUtils.getGradientValue(Client.clientColors[0], Client.clientColors[1], 3, 10));
                if (this.mc.thePlayer.dimension == -1) {
                    String s2 = "(" + x * 8.0 + ", " + y + ", " + z * 8.0 + ")";
                    int strLen2 = this.mc.fontRenderer.getStringWidth(s2);
                    GuiUtils.drawString(s2, sr.getScaledWidth() - 4 - strLen2, sr.getScaledHeight() - 20, GuiUtils.getGradientValue(Client.clientColors[0], Client.clientColors[1], 3, 10));
                }
            }
            if (this.getSet("Inventory").getValBoolean()) {
                GL11.glPushMatrix();
                GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslatef((float)0.0f, (float)-60.0f, (float)0.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)32826);
                int i7 = 9;
                while (i7 < this.mc.thePlayer.craftingInventory.slots.size()) {
                    Slot slot8 = (Slot)this.mc.thePlayer.craftingInventory.slots.get(i7);
                    GL11.glDisable((int)2896);
                    GL11.glDisable((int)2929);
                    int i9 = slot8.xDisplayPosition;
                    int i10 = slot8.yDisplayPosition;
                    GuiUtils.drawGradientRect(i9, i10, i9 + 16, i10 + 16, -2130706433, -2130706433);
                    GL11.glEnable((int)2896);
                    GL11.glEnable((int)2929);
                    RenderUtils.drawSlotInventory(slot8);
                    ++i7;
                }
                GL11.glDisable((int)32826);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable((int)2896);
                GL11.glPopMatrix();
            }
        }
    }

    public static class ModuleComparator
    implements Comparator<Module> {
        @Override
        public int compare(Module o1, Module o2) {
            String o1mode = "";
            if (o1.getSet("Mode") != null) {
                o1mode = " " + o1.getSet("Mode").getValString();
            }
            String o2mode = "";
            if (o2.getSet("Mode") != null) {
                o2mode = " " + o2.getSet("Mode").getValString();
            }
            if (Minecraft.theMinecraft.fontRenderer.getStringWidth(String.valueOf(o1.getName()) + o1mode) > Minecraft.theMinecraft.fontRenderer.getStringWidth(String.valueOf(o2.getName()) + o2mode)) {
                return -1;
            }
            if (Minecraft.theMinecraft.fontRenderer.getStringWidth(String.valueOf(o1.getName()) + o1mode) < Minecraft.theMinecraft.fontRenderer.getStringWidth(String.valueOf(o2.getName()) + o2mode)) {
                return 1;
            }
            return 0;
        }
    }
}

