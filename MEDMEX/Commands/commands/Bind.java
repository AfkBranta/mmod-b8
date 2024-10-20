/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    public Bind() {
        super("Bind", "Binds a module by name.", "bind <name/clear> <key>", "b");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 0) {
            Client.sendLocalChat("Usage: bind <module> <key> or bind clear");
        }
        if (args.length == 2) {
            String moduleName = args[0];
            String keyName = args[1];
            for (Module module : Client.moduleManager.modules) {
                if (!module.getName().equalsIgnoreCase(moduleName)) continue;
                module.setKeybind(Keyboard.getKeyIndex((String)keyName.toUpperCase()));
                Client.sendLocalChat(String.format("Bound %s to %s", module.getName(), Keyboard.getKeyName((int)module.getKeybind())));
                break;
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                Client.sendLocalChat("Cleared all binds.");
                for (Module module : Client.moduleManager.modules) {
                    module.setKeybind(0);
                }
            } else {
                Client.sendLocalChat("Usage: bind <module> <key> or bind clear");
            }
        }
    }
}

