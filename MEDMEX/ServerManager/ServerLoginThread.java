/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ServerManager;

import MEDMEX.ServerManager.Connect;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Session;

public final class ServerLoginThread
extends Thread {
    private String status;
    private final String server;
    private Minecraft mc = Minecraft.theMinecraft;

    public ServerLoginThread(String server) {
        super("Alt Login Thread");
        this.server = server;
        this.status = "\u00a7aAlt logged in (" + server + ")";
    }

    private void createSession(String server) {
        Session.username = server;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        Connect.connectToServer(this.server, 25565);
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

