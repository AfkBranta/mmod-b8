/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Events.Event;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import java.util.concurrent.CopyOnWriteArrayList;

public class Friends
extends Module {
    public static Friends instance;
    public static CopyOnWriteArrayList<String> friends;

    static {
        friends = new CopyOnWriteArrayList();
    }

    public Friends() {
        super("Friends", "Client friends", 0, Category.Player);
        this.addSetting(new Setting("list", (Module)this, friends, "String"));
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

