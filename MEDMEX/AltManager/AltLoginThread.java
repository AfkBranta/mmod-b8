/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.AltManager;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Session;

public final class AltLoginThread
extends Thread {
    private String status;
    private final String username;
    private Minecraft mc = Minecraft.theMinecraft;

    public AltLoginThread(String username) {
        super("Alt Login Thread");
        this.username = username;
        this.status = "\u00a7aAlt logged in (" + username + ")";
    }

    private void createSession(String username) {
        Session.username = username;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        Session.username = this.username;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

