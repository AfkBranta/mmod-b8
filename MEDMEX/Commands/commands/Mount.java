/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.Packet7;

public class Mount
extends Command {
    public Mount() {
        super("Mount", "Interact with mounts", "Mount <break/nearest>", "mount");
    }

    @Override
    public void onCommand(String[] args) {
        if (args[0].equalsIgnoreCase("Break")) {
            if (this.mc.thePlayer.ridingEntity == null) {
                Client.sendLocalChat("Not mounted!");
                return;
            }
            int i = 0;
            while (i < 6) {
                this.mc.thePlayer.swingItem();
                this.mc.playerController.func_6472_b(this.mc.thePlayer, this.mc.thePlayer.ridingEntity);
                ++i;
            }
        }
        if (args[0].equalsIgnoreCase("Nearest")) {
            List entities = this.mc.theWorld.loadedEntityList;
            for (Entity e : entities) {
                if (this.mc.thePlayer.getDistanceToEntity(e) > 5.0f || !(e instanceof EntityMinecart) && !(e instanceof EntityBoat)) continue;
                Client.sendPacket(new Packet7(this.mc.thePlayer.entityId, e.entityId, 0));
            }
        }
    }
}

