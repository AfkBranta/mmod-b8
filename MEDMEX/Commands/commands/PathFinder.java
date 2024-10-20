/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Player.Pathfinder;
import MEDMEX.Utils.Vec3D;

public class PathFinder
extends Command {
    public PathFinder() {
        super("PathFinder", "Automatically pathfind to a position", "PathFinder <x> <y> <z> | <mine> <blockid>", "pf");
    }

    @Override
    public void onCommand(String[] args) {
        if (args[0].equalsIgnoreCase("mine")) {
            int blockid = Integer.valueOf(args[1]);
            Pathfinder.instance.startMine(blockid);
            Client.sendLocalChat("Now auto mining blockid: " + blockid);
            return;
        }
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        Vec3D goal = new Vec3D(x, y, z);
        Pathfinder.instance.startWalk(goal);
        Client.sendLocalChat("Set PathFinder Goal to: " + goal);
    }
}

