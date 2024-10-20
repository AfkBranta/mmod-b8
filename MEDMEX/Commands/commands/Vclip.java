/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;

public class Vclip
extends Command {
    public Vclip() {
        super("Vclip", "Changes y coord", "Vclip <blocks>", "value");
    }

    @Override
    public void onCommand(String[] args) {
        int distance = Integer.valueOf(args[0]);
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY + (double)distance, this.mc.thePlayer.posZ);
        Client.sendLocalChat("Vclipped " + args[0] + " blocks.");
    }
}

