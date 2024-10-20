/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Modules.Category;
import MEDMEX.Settings.Setting;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

public abstract class Module {
    String name;
    String description;
    int keybind;
    Category category;
    public boolean isEnabled;
    ArrayList<Setting> settings = new ArrayList();
    public boolean alwaysReceiveEvents = false;
    public Minecraft mc = Minecraft.theMinecraft;
    public FontRenderer fr;

    public Module(String name, String description, int keybind, Category category) {
        this.fr = this.mc.fontRenderer;
        this.name = name;
        this.description = description;
        this.keybind = keybind;
        this.category = category;
    }

    public void addSetting(Setting in) {
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public void setSettings(ArrayList<Setting> settings) {
        this.settings = settings;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void turnOnModule() {
        this.setEnabled(true);
        this.onEnable();
    }

    public void turnOffModule() {
        this.setEnabled(false);
        this.onDisable();
    }

    public void toggle() {
        if (this.isEnabled) {
            this.turnOffModule();
            return;
        }
        this.turnOnModule();
    }

    public void onEvent(Event e) {
    }

    public Setting getSet(String s) {
        return Client.settingsManager.getSettingByNameAndMod(s, this);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("Module: <" + this.name + "> Settings: ");
        for (Setting set : this.getSettings()) {
            s.append(String.valueOf(set.toString()) + " ");
        }
        return s.toString();
    }
}

