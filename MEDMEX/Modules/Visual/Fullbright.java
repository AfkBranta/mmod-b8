/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;

public class Fullbright
extends Module {
    public static Fullbright instance;

    public Fullbright() {
        super("Fullbright", "Makes the game bright", 0, Category.Visual);
        instance = this;
    }

    @Override
    public void onEnable() {
        this.mc.renderGlobal.updateAllRenderers();
    }

    @Override
    public void onEvent(Event e) {
    }

    @Override
    public void onDisable() {
        this.mc.renderGlobal.updateAllRenderers();
    }
}

