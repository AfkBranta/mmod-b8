/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Player.Friends;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.RenderUtils;
import java.awt.Color;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;

public class ESP
extends Module {
    public static ESP instance;

    public ESP() {
        super("ESP", "See things through walls", 0, Category.Visual);
        this.addSetting(new Setting("Players", (Module)this, true));
        this.addSetting(new Setting("Monsters", (Module)this, false));
        this.addSetting(new Setting("Animals", (Module)this, false));
        this.addSetting(new Setting("Items", (Module)this, false));
        this.addSetting(new Setting("Vehicles", (Module)this, false));
        this.addSetting(new Setting("Chests", (Module)this, false));
        this.addSetting(new Setting("Storage", (Module)this, false));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRenderEntities) {
            EventRenderEntities event = (EventRenderEntities)e;
            List entities = this.mc.theWorld.loadedEntityList;
            for (Entity ent : entities) {
                if (!this.shouldRenderEntity(ent)) continue;
                AxisAlignedBB BB = ent.boundingBox;
                double xLen = BB.maxX - BB.minX;
                double yLen = BB.maxY - BB.minY;
                double zLen = BB.maxZ - BB.minZ;
                double minX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)event.getPartialTicks();
                double minY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)event.getPartialTicks();
                double minZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)event.getPartialTicks();
                AxisAlignedBB B = new AxisAlignedBB(minX, minY, minZ, minX + xLen, minY + yLen, minZ + zLen).offset(-ent.width / 2.0f, 0.0, -ent.width / 2.0f);
                RenderUtils.boundingESPBoxFilled(B, this.getEntityColor(ent));
            }
            List tileEntities = this.mc.theWorld.loadedTileEntityList;
            for (TileEntity ent : tileEntities) {
                if (!this.shouldRenderEntity(ent) || ent.getBlockType() == null) continue;
                AxisAlignedBB boundingBox = ent.getBlockType().getSelectedBoundingBoxFromPool(this.mc.theWorld, ent.xCoord, ent.yCoord, ent.zCoord);
                RenderUtils.boundingESPBoxFilled(boundingBox, this.getEntityColor(ent));
            }
        }
    }

    public Color getEntityColor(Entity e) {
        if (e instanceof EntityMobs || e instanceof EntitySlime) {
            return new Color(247, 45, 51, 100);
        }
        if (e instanceof EntityAnimals || e instanceof EntitySquid) {
            return new Color(31, 240, 62, 100);
        }
        if (e instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)e;
            if (Friends.instance.isEnabled && Friends.friends.contains(player.username.toLowerCase())) {
                return new Color(110, 250, 150, 100);
            }
            return new Color(170, 31, 240, 100);
        }
        if (e instanceof EntityItem) {
            return new Color(31, 45, 240, 100);
        }
        if (e instanceof EntityMinecart || e instanceof EntityBoat) {
            return new Color(252, 3, 198, 100);
        }
        return Color.WHITE;
    }

    public Color getEntityColor(TileEntity e) {
        if (e instanceof TileEntityChest) {
            return new Color(240, 205, 31, 100);
        }
        if (e instanceof TileEntityFurnace || e instanceof TileEntityDispenser) {
            return new Color(100, 100, 100, 100);
        }
        return Color.WHITE;
    }

    public boolean shouldRenderEntity(TileEntity e) {
        if (e instanceof TileEntityChest && this.getSet("Chests").getValBoolean()) {
            return true;
        }
        return (e instanceof TileEntityFurnace || e instanceof TileEntityDispenser) && this.getSet("Storage").getValBoolean();
    }

    public boolean shouldRenderEntity(Entity e) {
        if (e == this.mc.thePlayer) {
            return false;
        }
        if ((e instanceof EntityMobs || e instanceof EntitySlime) && this.getSet("Monsters").getValBoolean()) {
            return true;
        }
        if ((e instanceof EntityAnimals || e instanceof EntitySquid) && this.getSet("Animals").getValBoolean()) {
            return true;
        }
        if (e instanceof EntityPlayer && this.getSet("Players").getValBoolean()) {
            return true;
        }
        if (e instanceof EntityItem && this.getSet("Items").getValBoolean()) {
            return true;
        }
        return (e instanceof EntityMinecart || e instanceof EntityBoat) && this.getSet("Vehicles").getValBoolean();
    }
}

