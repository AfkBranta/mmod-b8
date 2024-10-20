/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;

public class Hand
extends Module {
    public static Hand instance;

    public Hand() {
        super("Hand", "Move hand in space", 0, Category.Visual);
        this.addSetting(new Setting("Scale", this, 1.5, 0.0, 3.0, false));
        this.addSetting(new Setting("TranslateX", this, -0.9375, -2.0, 2.0, false));
        this.addSetting(new Setting("TranslateY", this, -0.0625, -1.0, 1.0, false));
        this.addSetting(new Setting("TranslateZ", this, 0.0, -1.0, 1.0, false));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
    }

    @Override
    public void onDisable() {
    }
}

