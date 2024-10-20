/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX;

import MEDMEX.AltManager.AltManager;
import MEDMEX.Commands.Command;
import MEDMEX.Commands.CommandManager;
import MEDMEX.Config.ConfigAlts;
import MEDMEX.Config.ConfigModules;
import MEDMEX.Config.ConfigServer;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventChat;
import MEDMEX.Events.events.EventKey;
import MEDMEX.Events.events.EventShutdown;
import MEDMEX.Events.events.EventStartup;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.ModuleManager;
import MEDMEX.ServerManager.ServerManager;
import MEDMEX.Settings.SettingsManager;
import MEDMEX.Utils.PlayerUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet;

public class Client {
    public static String clientName = "mmod";
    public static String clientVersion = "b8";
    public static int[] clientColors = new int[]{212835, 16702615};
    public static Client c;
    public static CommandManager commandManager;
    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static AltManager altManager;
    public static ServerManager serverManager;
    public static PlayerUtils playerUtils;
    public static CopyOnWriteArrayList<Module> drawn;
    public static Minecraft mc;
    public List<Packet> noEventPackets = new ArrayList<Packet>();

    static {
        drawn = new CopyOnWriteArrayList();
        mc = Minecraft.theMinecraft;
    }

    public void onStartup() {
        c = this;
        settingsManager = new SettingsManager();
        commandManager = new CommandManager();
        commandManager.init();
        moduleManager = new ModuleManager();
        moduleManager.init();
        playerUtils = new PlayerUtils();
        ConfigModules.loadConfig();
        ConfigAlts.loadConfig();
        ConfigServer.loadConfig();
        System.out.println(String.valueOf(clientName) + " " + clientVersion + " initialized!");
    }

    public static void onShutdown() {
        ConfigModules.saveConfig();
        ConfigAlts.saveConfig();
        ConfigServer.saveConfig();
    }

    public static void onEvent(Event e) {
        if (e instanceof EventChat) {
            EventChat eventChat = (EventChat)e;
            if (!eventChat.getMsg().startsWith("!")) {
                return;
            }
            e.setCancelled(true);
            commandManager.onChatPrefix(eventChat.getMsg());
        }
        if (e instanceof EventStartup) {
            Client client = new Client();
            client.onStartup();
        }
        if (e instanceof EventShutdown) {
            Client.onShutdown();
        }
        if (e instanceof EventKey) {
            EventKey eventKey = (EventKey)e;
            Client.onKey(eventKey.getKeyCode());
            if (eventKey.getKeyCode() == 38) {
                commandManager.getCommandByName("LastOpen").onCommand(null);
            }
        }
        for (Module module : Client.moduleManager.modules) {
            if (!module.isEnabled && !module.alwaysReceiveEvents) continue;
            module.onEvent(e);
        }
        for (Command command : Client.commandManager.commands) {
            command.onEvent(e);
        }
    }

    public static void onKey(int key) {
        for (Module m : Client.moduleManager.modules) {
            if (m.getKeybind() != key) continue;
            m.toggle();
        }
    }

    public static void sendPacket(Packet p) {
        NetClientHandler.netManager.addToSendQueue(p);
    }

    public static void sendPacketNoEvent(Packet p) {
        Client.c.noEventPackets.add(p);
        NetClientHandler.netManager.addToSendQueue(p);
    }

    public static void sendLocalChat(String text) {
        String prefix = "\u00a7a[\u00a75!\u00a7a]\u00a77 ";
        Client.mc.ingameGUI.addChatMessage(String.valueOf(prefix) + text);
    }
}

