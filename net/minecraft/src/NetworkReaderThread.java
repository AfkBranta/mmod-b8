/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NetworkManager;

class NetworkReaderThread
extends Thread {
    final NetworkManager netManager;

    NetworkReaderThread(NetworkManager networkManager1, String string2) {
        super(string2);
        this.netManager = networkManager1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Object object1;
        block27: {
            Object object;
            Object object5;
            boolean z12;
            block26: {
                object1 = NetworkManager.threadSyncObject;
                Object object2 = NetworkManager.threadSyncObject;
                synchronized (object2) {
                    ++NetworkManager.numReadThreads;
                }
                while (true) {
                    z12 = false;
                    z12 = true;
                    if (!NetworkManager.isRunning(this.netManager)) break block26;
                    if (NetworkManager.isServerTerminating(this.netManager)) break;
                    NetworkManager.readNetworkPacket(this.netManager);
                    try {
                        NetworkReaderThread.sleep(0L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    if (!z12) continue;
                    object5 = NetworkManager.threadSyncObject;
                    object = NetworkManager.threadSyncObject;
                    synchronized (object) {
                        --NetworkManager.numReadThreads;
                    }
                }
                try {
                    z12 = false;
                    if (!z12) break block27;
                    object5 = NetworkManager.threadSyncObject;
                    object = NetworkManager.threadSyncObject;
                }
                catch (Throwable throwable) {
                    if (z12) {
                        object5 = NetworkManager.threadSyncObject;
                        object = NetworkManager.threadSyncObject;
                        synchronized (object) {
                            --NetworkManager.numReadThreads;
                        }
                    }
                    throw throwable;
                }
                synchronized (object) {
                    --NetworkManager.numReadThreads;
                }
            }
            z12 = false;
            if (!z12) break block27;
            object5 = NetworkManager.threadSyncObject;
            object = NetworkManager.threadSyncObject;
            synchronized (object) {
                --NetworkManager.numReadThreads;
            }
        }
        object1 = NetworkManager.threadSyncObject;
        Object object = NetworkManager.threadSyncObject;
        synchronized (object) {
            --NetworkManager.numReadThreads;
        }
    }
}

