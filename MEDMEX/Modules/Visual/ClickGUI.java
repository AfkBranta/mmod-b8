/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import java.util.ArrayList;

public class ClickGUI
extends Module {
    public static ClickGUI instance;
    public MEDMEX.ClickGUI.ClickGUI gui;

    public ClickGUI() {
        super("ClickGUI", "Clickable GUI", 54, Category.Visual);
        ArrayList<String> options = new ArrayList<String>();
        options.add("New");
        options.add("JellyLike");
        this.addSetting(new Setting("Design", (Module)this, "New", options));
        this.addSetting(new Setting("Sound", (Module)this, false));
        this.addSetting(new Setting("Rainbow", (Module)this, true));
        this.addSetting(new Setting("R Speed", this, 4.0, 1.0, 10.0, false));
        this.addSetting(new Setting("R Saturation", this, 0.6, 0.0, 1.0, false));
        this.addSetting(new Setting("R Brightness", this, 1.0, 0.0, 1.0, false));
        this.addSetting(new Setting("GuiRed", this, 255.0, 0.0, 255.0, true));
        this.addSetting(new Setting("GuiGreen", this, 26.0, 0.0, 255.0, true));
        this.addSetting(new Setting("GuiBlue", this, 42.0, 0.0, 255.0, true));
        instance = this;
    }

    @Override
    public void onEnable() {
        this.gui = new MEDMEX.ClickGUI.ClickGUI();
        this.mc.displayGuiScreen(this.gui);
        this.toggle();
    }

    @Override
    public void onDisable() {
    }
}

