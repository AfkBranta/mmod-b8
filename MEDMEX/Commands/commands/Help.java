/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import java.util.ArrayList;

public class Help
extends Command {
    public Help() {
        super("Help", "Lists all commands", "Help <Command>", "help");
    }

    @Override
    public void onCommand(String[] arguments) {
        if (arguments.length == 0) {
            ArrayList<String> commandNames = new ArrayList<String>();
            for (Command c : Client.commandManager.commands) {
                commandNames.add(c.getName());
            }
            Client.sendLocalChat("" + commandNames);
        }
        if (arguments.length == 1) {
            for (Command c : Client.commandManager.commands) {
                if (!arguments[0].equalsIgnoreCase(c.getName())) continue;
                Client.sendLocalChat(String.valueOf(c.getName()) + " Usage: " + c.getUsage());
            }
        }
    }
}

