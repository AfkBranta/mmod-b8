/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class ThreadSleepForever
extends Thread {
    final Minecraft mc;

    public ThreadSleepForever(Minecraft minecraft1, String string2) {
        super(string2);
        this.mc = minecraft1;
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        while (this.mc.running) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }
}

