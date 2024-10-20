/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.World;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Timer;
import MEDMEX.Utils.Vec3D;
import java.awt.Color;
import net.minecraft.src.Item;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet14BlockDig;

public class CivBreaker
extends Module {
    public static Timer timer = new Timer();
    public static Timer timer2 = new Timer();
    public static boolean runningloop = false;
    int prevItem = -1;
    boolean ESP = true;
    boolean swing = false;
    boolean ifBlock = true;
    boolean onDone = false;
    boolean onPick = false;
    boolean sameItem = true;
    int delay = 0;
    boolean hasStarted = false;
    public Vec3D pos;
    int facing;
    float progress = 0.0f;
    int cooldown = 0;
    Item usedToBreak = null;
    public static CivBreaker instance;

    public CivBreaker() {
        super("CivBreaker", "Break Blocks Fast", 0, Category.World);
        instance = this;
    }

    public boolean cancelMiningAbortPacket() {
        return this.getEnabled();
    }

    public void onRender() {
        if (this.getEnabled() && this.pos != null) {
            RenderUtils.boundingESPBox(this.pos.toBB(), new Color(200, 200, 200, 120));
            int b = this.mc.theWorld.getBlockId((int)this.pos.getX(), (int)this.pos.getY(), (int)this.pos.getZ());
            if (b != 0) {
                float i = Math.min(1.0f, this.progress);
                RenderUtils.boundingESPBoxFilled(this.pos.toBB(), new Color((int)(40.0f + 100.0f * i), (int)(160.0f - 140.0f * i), 40, 130));
            }
        }
    }

    @Override
    public void onEvent(Event e) {
        int b;
        if (e instanceof EventPlayer) {
            if (this.cooldown > 0) {
                --this.cooldown;
            } else if (this.pos != null) {
                b = this.mc.theWorld.getBlockId((int)this.pos.getX(), (int)this.pos.getY(), (int)this.pos.getZ());
                if (this.hasStarted && (b != 0 || !this.ifBlock)) {
                    if (!runningloop) {
                        this.prevItem = this.mc.thePlayer.inventory.currentItem;
                    }
                    if (this.swing) {
                        this.mc.thePlayer.swingItem();
                    }
                    this.progress = 1.0f;
                    if (this.onDone && this.progress < 1.0f) {
                        return;
                    }
                    if (this.cooldown > 0) {
                        return;
                    }
                    int bestSlot = -1;
                    int i = 0;
                    while (i < 9) {
                        ItemStack stack = this.mc.thePlayer.inventory.getStackInSlot(i);
                        if (stack != null && stack.getItem() instanceof ItemPickaxe) {
                            this.mc.thePlayer.inventory.currentItem = bestSlot = i;
                            if (timer.hasTimeElapsed(50L, true)) {
                                runningloop = true;
                            }
                        }
                        ++i;
                    }
                }
                if (runningloop && timer.hasTimeElapsed(55L, true)) {
                    this.mc.thePlayer.inventory.currentItem = this.prevItem;
                    runningloop = false;
                }
                if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.sameItem && this.usedToBreak != this.mc.thePlayer.inventory.getCurrentItem().getItem()) {
                    return;
                }
                Client.sendPacket(new Packet14BlockDig(2, (int)this.pos.getX(), (int)this.pos.getY(), (int)this.pos.getZ(), this.facing));
                this.cooldown = this.delay;
                if (this.mc.thePlayer.inventory.getCurrentItem() != null) {
                    this.usedToBreak = this.mc.thePlayer.inventory.getCurrentItem().getItem();
                }
            }
        }
        if (e instanceof EventRenderEntities && this.pos != null) {
            RenderUtils.boundingESPBox(this.pos.toBB(), new Color(200, 200, 200, 120));
            b = this.mc.theWorld.getBlockId((int)this.pos.getX(), (int)this.pos.getY(), (int)this.pos.getZ());
            if (b != 0) {
                float i = Math.min(1.0f, this.progress);
                RenderUtils.boundingESPBoxFilled(this.pos.toBB(), new Color((int)(40.0f + 100.0f * i), (int)(160.0f - 140.0f * i), 40, 130));
            }
        }
    }

    public boolean onBreak(Vec3D posBlock, int directionFacing) {
        if (!this.getEnabled()) {
            return false;
        }
        if (this.onPick && Item.pickaxeDiamond != this.mc.thePlayer.inventory.getCurrentItem().getItem()) {
            return true;
        }
        if (this.pos == null || !this.pos.equals(posBlock)) {
            this.progress = 0.0f;
            this.hasStarted = false;
            this.usedToBreak = null;
        }
        this.pos = posBlock;
        this.facing = directionFacing;
        if (!this.hasStarted) {
            Client.sendPacket(new Packet14BlockDig(0, (int)this.pos.getX(), (int)this.pos.getY(), (int)this.pos.getZ(), this.facing));
            Client.sendPacket(new Packet14BlockDig(0, (int)this.pos.getX(), (int)this.pos.getY(), (int)this.pos.getZ(), this.facing));
            if (this.mc.thePlayer.inventory.getCurrentItem() != null) {
                this.usedToBreak = this.mc.thePlayer.inventory.getCurrentItem().getItem();
            }
            this.hasStarted = true;
        }
        return false;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}

