/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NetworkManager;

class NetworkMasterThread
extends Thread {
    final NetworkManager netManager;

    NetworkMasterThread(NetworkManager networkManager1) {
        this.netManager = networkManager1;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000L);
            if (NetworkManager.getReadThread(this.netManager).isAlive()) {
                try {
                    NetworkManager.getReadThread(this.netManager).stop();
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (NetworkManager.getWriteThread(this.netManager).isAlive()) {
                try {
                    NetworkManager.getWriteThread(this.netManager).stop();
                }
                catch (Throwable throwable) {}
            }
        }
        catch (InterruptedException interruptedException4) {
            interruptedException4.printStackTrace();
        }
    }
}

