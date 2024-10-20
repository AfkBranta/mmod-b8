/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ServerManager;

import MEDMEX.ServerManager.Server;
import java.util.ArrayList;

public class ServerManager {
    public static Server lastServer;
    public static ArrayList<Server> registry;

    static {
        registry = new ArrayList();
    }

    public ArrayList<Server> getRegistry() {
        return registry;
    }

    public void setLastServer(Server server) {
        lastServer = server;
    }
}

