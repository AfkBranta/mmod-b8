/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Commands.commands.Bind;
import MEDMEX.Commands.commands.BlockID;
import MEDMEX.Commands.commands.Help;
import MEDMEX.Commands.commands.LastOpen;
import MEDMEX.Commands.commands.Mount;
import MEDMEX.Commands.commands.PathFinder;
import MEDMEX.Commands.commands.Setting;
import MEDMEX.Commands.commands.SpawnTP;
import MEDMEX.Commands.commands.Toggle;
import MEDMEX.Commands.commands.Vclip;
import MEDMEX.Modules.Module;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandManager {
    public CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList();

    public void init() {
        this.commands.add(new Help());
        this.commands.add(new Toggle());
        this.commands.add(new SpawnTP());
        this.commands.add(new Bind());
        this.commands.add(new Vclip());
        this.commands.add(new LastOpen());
        this.commands.add(new Setting());
        this.commands.add(new PathFinder());
        this.commands.add(new Mount());
        this.commands.add(new BlockID());
    }

    public void onChatPrefix(String msg) {
        String[] cSplit = msg.split(" ");
        String commandName = cSplit[0].substring(1);
        String[] arguments = new String[cSplit.length - 1];
        int i = 0;
        while (i < cSplit.length) {
            if (i > 0) {
                arguments[i - 1] = cSplit[i];
            }
            ++i;
        }
        for (Command c : this.commands) {
            if (!commandName.equalsIgnoreCase(c.getAlias()) && !commandName.equalsIgnoreCase(c.getName())) continue;
            try {
                c.onCommand(arguments);
            }
            catch (Exception ex) {
                Client.sendLocalChat(String.valueOf(c.getName()) + " Usage: " + c.getUsage());
            }
            return;
        }
        for (Module m : Client.moduleManager.modules) {
            if (!commandName.equalsIgnoreCase(m.getName())) continue;
            try {
                String s = "!setting " + m.getName().toLowerCase() + " ";
                String[] stringArray = arguments;
                int n = arguments.length;
                int n2 = 0;
                while (n2 < n) {
                    String s1 = stringArray[n2];
                    s = String.valueOf(s) + s1 + " ";
                    ++n2;
                }
                this.onChatPrefix(s);
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        Client.sendLocalChat("Command not found!");
    }

    public Command getCommandByName(String s) {
        for (Command c : this.commands) {
            if (!s.equalsIgnoreCase(c.name)) continue;
            return c;
        }
        return null;
    }
}

