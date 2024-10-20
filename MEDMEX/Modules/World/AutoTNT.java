/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.World;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.BlockPlacement;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.Vec3D;
import java.util.ArrayList;
import java.util.List;

public class AutoTNT
extends Module {
    public static AutoTNT instance;
    private List<Integer> blacklist = new ArrayList<Integer>();

    public AutoTNT() {
        super("AutoTNT", "Places TNT automatically", 0, Category.World);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof EventPlayer)) {
            return;
        }
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        int slot = InventoryUtils.getItemInHotbar(46);
        if (slot == -1) {
            return;
        }
        this.mc.thePlayer.inventory.currentItem = slot;
        int dist = 4;
        int i = -3;
        while (i < 4) {
            int j = -3;
            while (j < 4) {
                int x = (int)this.mc.thePlayer.posX - (int)this.mc.thePlayer.posX % dist - i * dist;
                int z = (int)this.mc.thePlayer.posZ - (int)this.mc.thePlayer.posZ % dist - j * dist;
                boolean skip = false;
                int l = 0;
                while (l < this.blacklist.size()) {
                    if (x == this.blacklist.get(l) && z == this.blacklist.get(l + 1)) {
                        skip = true;
                        break;
                    }
                    l += 2;
                }
                if (!skip) {
                    int k = -3;
                    while (k < 4) {
                        BlockPlacement placement;
                        int y = (int)this.mc.thePlayer.posY;
                        if (this.mc.thePlayer.getDistance((double)x + 0.5, (double)y + 0.5, (double)z + 0.5) < 4.0 && this.mc.theWorld.isAirBlock(x, y, z) && (placement = PlayerUtils.getPossiblePlacement(new Vec3D(x, y - 1, z))) != null) {
                            if (this.mc.thePlayer.inventory.getCurrentItem() == null) {
                                return;
                            }
                            if (this.mc.thePlayer.inventory.getCurrentItem().itemID != 46) {
                                return;
                            }
                            this.mc.thePlayer.swingItem();
                            this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), placement.getX(), placement.getY(), placement.getZ(), placement.getSide());
                            this.blacklist.add(x);
                            this.blacklist.add(z);
                            return;
                        }
                        ++k;
                    }
                }
                ++j;
            }
            ++i;
        }
    }

    @Override
    public void onDisable() {
    }
}

