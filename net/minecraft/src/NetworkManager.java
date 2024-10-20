/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.EventBound;
import MEDMEX.Events.events.EventPacket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetworkMasterThread;
import net.minecraft.src.NetworkReaderThread;
import net.minecraft.src.NetworkWriterThread;
import net.minecraft.src.Packet;

public class NetworkManager {
    public static final Object threadSyncObject = new Object();
    public static int numReadThreads;
    public static int numWriteThreads;
    private Object sendQueueLock = new Object();
    private Socket networkSocket;
    private final SocketAddress remoteSocketAddress;
    private DataInputStream socketInputStream;
    private DataOutputStream socketOutputStream;
    private boolean isRunning = true;
    private List readPackets = Collections.synchronizedList(new ArrayList());
    private List dataPackets = Collections.synchronizedList(new ArrayList());
    private List chunkDataPackets = Collections.synchronizedList(new ArrayList());
    private NetHandler netHandler;
    private boolean isServerTerminating = false;
    private Thread writeThread;
    private Thread readThread;
    private boolean isTerminating = false;
    private String terminationReason = "";
    private Object[] field_20101_t;
    private int timeSinceLastRead = 0;
    private int sendQueueByteLength = 0;
    public int chunkDataSendCounter = 0;
    private int field_20100_w = 50;

    public NetworkManager(Socket socket1, String string2, NetHandler netHandler3) throws IOException {
        this.networkSocket = socket1;
        this.remoteSocketAddress = socket1.getRemoteSocketAddress();
        this.netHandler = netHandler3;
        try {
            socket1.setTrafficClass(24);
        }
        catch (SocketException socketException5) {
            System.err.println(socketException5.getMessage());
        }
        this.socketInputStream = new DataInputStream(socket1.getInputStream());
        this.socketOutputStream = new DataOutputStream(socket1.getOutputStream());
        this.readThread = new NetworkReaderThread(this, String.valueOf(string2) + " read thread");
        this.writeThread = new NetworkWriterThread(this, String.valueOf(string2) + " write thread");
        this.readThread.start();
        this.writeThread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addToSendQueue(Packet packet1) {
        if (!this.isServerTerminating) {
            Object object2 = this.sendQueueLock;
            Object object = this.sendQueueLock;
            synchronized (object) {
                this.sendQueueByteLength += packet1.getPacketSize() + 1;
                if (packet1.isChunkDataPacket) {
                    this.chunkDataPackets.add(packet1);
                } else {
                    this.dataPackets.add(packet1);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendPacket() {
        block18: {
            try {
                Object event;
                Packet packet2;
                Object object3;
                boolean z1 = true;
                if (!(this.dataPackets.isEmpty() || this.chunkDataSendCounter != 0 && System.currentTimeMillis() - ((Packet)this.dataPackets.get((int)0)).creationTimeMillis < (long)this.chunkDataSendCounter)) {
                    z1 = false;
                    object3 = this.sendQueueLock;
                    Object object = this.sendQueueLock;
                    synchronized (object) {
                        packet2 = (Packet)this.dataPackets.remove(0);
                        this.sendQueueByteLength -= packet2.getPacketSize() + 1;
                    }
                    if (!Client.c.noEventPackets.contains(packet2)) {
                        event = new EventPacket(packet2, EventBound.OUT);
                        Client.onEvent((Event)event);
                        if (((Event)event).getCancelled()) {
                            return;
                        }
                    }
                    if (Client.c.noEventPackets.contains(packet2)) {
                        Client.c.noEventPackets.remove(packet2);
                    }
                    Packet.writePacket(packet2, this.socketOutputStream);
                }
                if (!(!z1 && this.field_20100_w-- > 0 || this.chunkDataPackets.isEmpty() || this.chunkDataSendCounter != 0 && System.currentTimeMillis() - ((Packet)this.chunkDataPackets.get((int)0)).creationTimeMillis < (long)this.chunkDataSendCounter)) {
                    z1 = false;
                    object3 = this.sendQueueLock;
                    event = this.sendQueueLock;
                    synchronized (event) {
                        packet2 = (Packet)this.chunkDataPackets.remove(0);
                        this.sendQueueByteLength -= packet2.getPacketSize() + 1;
                    }
                    if (!Client.c.noEventPackets.contains(packet2)) {
                        event = new EventPacket(packet2, EventBound.OUT);
                        Client.onEvent((Event)event);
                        if (((Event)event).getCancelled()) {
                            return;
                        }
                    }
                    if (Client.c.noEventPackets.contains(packet2)) {
                        Client.c.noEventPackets.remove(packet2);
                    }
                    Packet.writePacket(packet2, this.socketOutputStream);
                    this.field_20100_w = 50;
                }
                if (z1) {
                    Thread.sleep(10L);
                }
            }
            catch (InterruptedException z1) {
            }
            catch (Exception exception9) {
                if (this.isTerminating) break block18;
                this.onNetworkError(exception9);
            }
        }
    }

    private void readPacket() {
        block5: {
            try {
                Packet packet1 = Packet.readPacket(this.socketInputStream);
                EventPacket event = new EventPacket(packet1, EventBound.IN);
                Client.onEvent(event);
                if (event.getCancelled()) {
                    return;
                }
                if (packet1 != null) {
                    this.readPackets.add(packet1);
                } else {
                    this.networkShutdown("disconnect.endOfStream", new Object[0]);
                }
            }
            catch (Exception exception2) {
                if (this.isTerminating) break block5;
                this.onNetworkError(exception2);
            }
        }
    }

    private void onNetworkError(Exception exception1) {
        exception1.printStackTrace();
        this.networkShutdown("disconnect.genericReason", "Internal exception: " + exception1.toString());
    }

    public void networkShutdown(String string1, Object ... object2) {
        if (this.isRunning) {
            this.isTerminating = true;
            this.terminationReason = string1;
            this.field_20101_t = object2;
            new NetworkMasterThread(this).start();
            this.isRunning = false;
            try {
                this.socketInputStream.close();
                this.socketInputStream = null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                this.socketOutputStream.close();
                this.socketOutputStream = null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                this.networkSocket.close();
                this.networkSocket = null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public void processReadPackets() {
        if (this.sendQueueByteLength > 0x100000) {
            this.networkShutdown("disconnect.overflow", new Object[0]);
        }
        if (this.readPackets.isEmpty()) {
            if (this.timeSinceLastRead++ == 1200) {
                this.networkShutdown("disconnect.timeout", new Object[0]);
            }
        } else {
            this.timeSinceLastRead = 0;
        }
        int i1 = 100;
        while (!this.readPackets.isEmpty() && i1-- >= 0) {
            Packet packet2 = (Packet)this.readPackets.remove(0);
            packet2.processPacket(this.netHandler);
        }
        if (this.isTerminating && this.readPackets.isEmpty()) {
            this.netHandler.handleErrorMessage(this.terminationReason, this.field_20101_t);
        }
    }

    static boolean isRunning(NetworkManager networkManager0) {
        return networkManager0.isRunning;
    }

    static boolean isServerTerminating(NetworkManager networkManager0) {
        return networkManager0.isServerTerminating;
    }

    static void readNetworkPacket(NetworkManager networkManager0) {
        networkManager0.readPacket();
    }

    static void sendNetworkPacket(NetworkManager networkManager0) {
        networkManager0.sendPacket();
    }

    static Thread getReadThread(NetworkManager networkManager0) {
        return networkManager0.readThread;
    }

    static Thread getWriteThread(NetworkManager networkManager0) {
        return networkManager0.writeThread;
    }
}

