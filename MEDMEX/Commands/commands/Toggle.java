/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;

public class Toggle
extends Command {
    public Toggle() {
        super("Toggle", "Toggles a module", "Toggle <Module>", "t");
    }

    @Override
    public void onCommand(String[] arguments) {
        String module = arguments[0];
        for (Module m : Client.moduleManager.modules) {
            if (!module.equalsIgnoreCase(m.getName())) continue;
            if (m.isEnabled) {
                m.turnOffModule();
                Client.sendLocalChat("Turned off " + m.getName());
                return;
            }
            m.turnOnModule();
            Client.sendLocalChat("Turned on " + m.getName());
            return;
        }
    }
}

