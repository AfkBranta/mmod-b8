/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.net.ConnectException;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiConnectFailed;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet2Handshake;
import net.minecraft.src.Session;

class ThreadConnectToServer
extends Thread {
    final Minecraft mc;
    final String hostName;
    final int port;
    final GuiConnecting connectingGui;

    ThreadConnectToServer(GuiConnecting guiConnecting1, Minecraft minecraft2, String string3, int i4) {
        this.connectingGui = guiConnecting1;
        this.mc = minecraft2;
        this.hostName = string3;
        this.port = i4;
    }

    @Override
    public void run() {
        try {
            GuiConnecting.setNetClientHandler(this.connectingGui, new NetClientHandler(this.mc, this.hostName, this.port));
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            Session cfr_ignored_0 = this.mc.session;
            GuiConnecting.getNetClientHandler(this.connectingGui).addToSendQueue(new Packet2Handshake(Session.username));
        }
        catch (UnknownHostException unknownHostException2) {
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", "Unknown host '" + this.hostName + "'"));
        }
        catch (ConnectException connectException3) {
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", connectException3.getMessage()));
        }
        catch (Exception exception4) {
            if (GuiConnecting.isCancelled(this.connectingGui)) {
                return;
            }
            exception4.printStackTrace();
            this.mc.displayGuiScreen(new GuiConnectFailed("connect.failed", "disconnect.genericReason", exception4.toString()));
        }
    }
}

