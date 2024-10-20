/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;

public class NoSlow
extends Module {
    public static NoSlow instance;

    public NoSlow() {
        super("NoSlow", "Remove slowdowns", 0, Category.Movement);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}

