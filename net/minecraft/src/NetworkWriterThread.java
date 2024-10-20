/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NetworkManager;

class NetworkWriterThread
extends Thread {
    final NetworkManager netManager;

    NetworkWriterThread(NetworkManager networkManager1, String string2) {
        super(string2);
        this.netManager = networkManager1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Object object1 = NetworkManager.threadSyncObject;
        Object object = NetworkManager.threadSyncObject;
        synchronized (object) {
            ++NetworkManager.numWriteThreads;
        }
        while (true) {
            Object object2;
            Object object5;
            boolean z11;
            block20: {
                z11 = false;
                try {
                    z11 = true;
                    if (NetworkManager.isRunning(this.netManager)) break block20;
                    z11 = false;
                    if (!z11) break;
                    object5 = NetworkManager.threadSyncObject;
                    object2 = NetworkManager.threadSyncObject;
                }
                catch (Throwable throwable) {
                    if (z11) {
                        object5 = NetworkManager.threadSyncObject;
                        object2 = NetworkManager.threadSyncObject;
                        synchronized (object2) {
                            --NetworkManager.numWriteThreads;
                        }
                    }
                    throw throwable;
                }
                synchronized (object2) {
                    --NetworkManager.numWriteThreads;
                    break;
                }
            }
            NetworkManager.sendNetworkPacket(this.netManager);
            if (!z11) continue;
            object5 = NetworkManager.threadSyncObject;
            object2 = NetworkManager.threadSyncObject;
            synchronized (object2) {
                --NetworkManager.numWriteThreads;
            }
        }
        object1 = NetworkManager.threadSyncObject;
        Object object3 = NetworkManager.threadSyncObject;
        synchronized (object3) {
            --NetworkManager.numWriteThreads;
        }
    }
}

