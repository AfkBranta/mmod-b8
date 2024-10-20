/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import net.minecraft.src.Packet11PlayerPosition;

public class SpawnTP
extends Command {
    public SpawnTP() {
        super("SpawnTP", "Teleport to spawn", "SpawnTP", "stp");
    }

    @Override
    public void onCommand(String[] args) {
        Client.sendPacket(new Packet11PlayerPosition(Double.NaN, Double.NaN, Double.NaN, Double.NaN, false));
    }
}

