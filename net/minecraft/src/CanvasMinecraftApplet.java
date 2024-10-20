/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.Canvas;
import net.minecraft.client.MinecraftApplet;

public class CanvasMinecraftApplet
extends Canvas {
    final MinecraftApplet mcApplet;

    public CanvasMinecraftApplet(MinecraftApplet minecraftApplet1) {
        this.mcApplet = minecraftApplet1;
    }

    @Override
    public synchronized void addNotify() {
        super.addNotify();
        this.mcApplet.startMainThread();
    }

    @Override
    public synchronized void removeNotify() {
        this.mcApplet.shutdown();
        super.removeNotify();
    }
}

