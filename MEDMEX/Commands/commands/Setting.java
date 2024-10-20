/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Events.events.EventListUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Visual.Waypoints;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.Waypoint;

public class Setting
extends Command {
    public Setting() {
        super("Setting", "Changes module settings", "setting <module> <setting> <value>", "s");
    }

    @Override
    public void onCommand(String[] args) {
        Module m = Client.moduleManager.getModuleByName(args[0]);
        if (args.length == 1) {
            Client.sendLocalChat(m.toString());
            return;
        }
        MEDMEX.Settings.Setting s = m.getSet(args[1]);
        if (args.length == 2) {
            Client.sendLocalChat(s.toString());
            return;
        }
        if (s.isCheck()) {
            s.setValBoolean(Boolean.valueOf(args[2]));
            Client.sendLocalChat("Set " + s.getName() + " to: " + Boolean.valueOf(args[2]));
        }
        if (s.isCombo()) {
            s.setValString(args[2]);
            Client.sendLocalChat("Set " + s.getName() + " to: " + args[2]);
        }
        if (s.isSlider()) {
            s.setValDouble(Double.valueOf(args[2]));
            Client.sendLocalChat("Set " + s.getName() + " to: " + Double.valueOf(args[2]));
        }
        if (s.isList()) {
            if (args[2].equalsIgnoreCase("add")) {
                if (s.getClasstype() == "Integer") {
                    if (s.getList().contains(Integer.valueOf(args[3]))) {
                        return;
                    }
                    s.getList().add(Integer.valueOf(args[3]));
                }
                if (s.getClasstype() == "String") {
                    if (s.getList().contains(args[3].toLowerCase())) {
                        return;
                    }
                    s.getList().add(args[3].toLowerCase());
                }
                if (s.getClasstype() == "Waypoint") {
                    int i = 3;
                    while (i < 7) {
                        if (args[i] == null) {
                            Client.sendLocalChat("Waypoint syntax: waypoints list add <x> <y> <z> <name> <dimension>");
                            return;
                        }
                        ++i;
                    }
                    int x = Integer.valueOf(args[3]);
                    int y = Integer.valueOf(args[4]);
                    int z = Integer.valueOf(args[5]);
                    String name = args[6];
                    int dimension = 0;
                    if (args[7] != null) {
                        if (args[7].equalsIgnoreCase("Overworld")) {
                            dimension = 0;
                        }
                        if (args[7].equalsIgnoreCase("Nether")) {
                            dimension = -1;
                        }
                    } else {
                        boolean isNether;
                        boolean bl = isNether = this.mc.theWorld.getBlockId((int)this.mc.thePlayer.posX, 127, this.mc.thePlayer.serverPosZ) == 7;
                        if (isNether) {
                            dimension = -1;
                        }
                    }
                    s.getList().add(new Waypoint(new Vec3D(x, y, z), name, dimension));
                    Client.sendLocalChat("Added Waypoint: " + name);
                    return;
                }
                s.getParentMod().onEvent(new EventListUpdate());
                Client.sendLocalChat("Added " + args[3] + " to " + s.getParentMod().getName());
            }
            if (args[2].equalsIgnoreCase("del")) {
                if (s.getClasstype() == "Integer") {
                    s.getList().remove(Integer.valueOf(args[3]));
                }
                if (s.getClasstype() == "String") {
                    s.getList().remove(args[3].toLowerCase());
                }
                if (s.getClasstype() == "Waypoint") {
                    for (Waypoint w : Waypoints.waypoints) {
                        if (!w.getName().equalsIgnoreCase(args[3])) continue;
                        Waypoints.waypoints.remove(w);
                    }
                }
                s.getParentMod().onEvent(new EventListUpdate());
                Client.sendLocalChat("Removed " + args[3] + " from " + s.getParentMod().getName());
            }
        }
    }
}

