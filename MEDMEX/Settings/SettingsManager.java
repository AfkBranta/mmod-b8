/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Settings;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import java.util.ArrayList;

public class SettingsManager {
    private ArrayList<Setting> settings = new ArrayList();

    public void addSetting(Setting in) {
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public ArrayList<Setting> getSettingsByMod(Module mod) {
        return mod.getSettings();
    }

    public Setting getSettingByName(String name) {
        for (Setting set : this.getSettings()) {
            if (!set.getName().equalsIgnoreCase(name)) continue;
            return set;
        }
        System.err.println("[" + Client.clientName + "] Error Setting NOT found: '" + name + "'!");
        return null;
    }

    public Setting getSettingByNameAndMod(String name, Module m) {
        for (Setting set : m.getSettings()) {
            if (!set.getName().equalsIgnoreCase(name)) continue;
            return set;
        }
        return null;
    }
}

