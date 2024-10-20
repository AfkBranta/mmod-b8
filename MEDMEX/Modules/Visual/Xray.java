/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import java.util.concurrent.CopyOnWriteArrayList;

public class Xray
extends Module {
    public static Xray instance;
    public CopyOnWriteArrayList<Integer> xrayblocks = new CopyOnWriteArrayList();

    public Xray() {
        super("Xray", "See through blocks", 0, Category.Visual);
        this.xrayblocks.add(54);
        this.xrayblocks.add(14);
        this.xrayblocks.add(15);
        this.xrayblocks.add(16);
        this.xrayblocks.add(21);
        this.xrayblocks.add(56);
        this.xrayblocks.add(73);
        this.xrayblocks.add(74);
        this.xrayblocks.add(41);
        this.xrayblocks.add(42);
        this.xrayblocks.add(57);
        this.xrayblocks.add(50);
        this.xrayblocks.add(61);
        this.xrayblocks.add(58);
        this.xrayblocks.add(355);
        this.xrayblocks.add(129);
        this.xrayblocks.add(90);
        instance = this;
    }

    @Override
    public void onDisable() {
        this.mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onEnable() {
        this.mc.renderGlobal.loadRenderers();
    }
}

