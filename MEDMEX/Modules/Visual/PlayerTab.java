/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventGuiIngameRender;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import net.minecraft.src.Gui;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class PlayerTab
extends Module {
    public static PlayerTab instance;
    boolean tabDown = false;
    String players = "";

    public PlayerTab() {
        super("PlayerTab", "Show online players like a tablist", 0, Category.Visual);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            if (!Keyboard.isKeyDown((int)15)) {
                this.players = "";
                this.tabDown = false;
            }
            if (Keyboard.isKeyDown((int)15) && !this.tabDown) {
                this.mc.thePlayer.sendChatMessage("/list");
                this.tabDown = true;
            }
        }
        if (e instanceof EventGuiIngameRender) {
            if (this.players == "") {
                return;
            }
            ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            String[] playerList = this.players.split(",");
            Gui.drawRect(sr.getScaledWidth() / 2 - 40, 2.0f, sr.getScaledWidth() / 2 + 40, 14 + playerList.length * 8, 0x7FBFBFBF);
            int offset = 0;
            this.fr.drawString("Players (" + playerList.length + "): ", sr.getScaledWidth() / 2 - this.fr.getStringWidth("Players (" + playerList.length + "): ") / 2, 4.0f, -1);
            String[] stringArray = playerList;
            int n = playerList.length;
            int n2 = 0;
            while (n2 < n) {
                String pl = stringArray[n2];
                pl = pl.replace(" ", "");
                this.fr.drawString(pl, sr.getScaledWidth() / 2 - this.fr.getStringWidth(pl) / 2, 12 + 8 * offset, -1);
                ++offset;
                ++n2;
            }
        }
        if (e instanceof EventPacket) {
            EventPacket ep = (EventPacket)e;
            if (!(ep.getPacket() instanceof Packet3Chat)) {
                return;
            }
            if (!this.tabDown) {
                return;
            }
            Packet3Chat p = (Packet3Chat)ep.getPacket();
            String msg = p.message;
            if (msg.contains("\u00a77")) {
                this.players = String.valueOf(this.players) + msg.split("\u00a77")[1];
                e.setCancelled(true);
            }
        }
    }

    @Override
    public void onDisable() {
    }
}

