/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.Vec3D;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Material;
import net.minecraft.src.MaterialLogic;

public class WorldUtils {
    public static String lastip;
    public static int lastport;

    public static int getBlockID(Vec3D pos) {
        Minecraft mc = Minecraft.theMinecraft;
        return mc.theWorld.getBlockId((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
    }

    public static boolean isTransparentBlock(Vec3D pos) {
        int blockid = WorldUtils.getBlockID(pos);
        return PlayerUtils.transparentBlocks.contains(blockid);
    }

    public static boolean canWalkThrough(int x, int y, int z) {
        Minecraft mc = Minecraft.theMinecraft;
        Material m = mc.theWorld.getBlockMaterial(x, y, z);
        int blockid = mc.theWorld.getBlockId(x, y, z);
        if (m instanceof MaterialLogic) {
            return true;
        }
        return blockid == 0 || blockid == 63;
    }

    public static boolean canWalkOn(int x, int y, int z) {
        Minecraft mc = Minecraft.theMinecraft;
        Material m = mc.theWorld.getBlockMaterial(x, y, z);
        int blockid = mc.theWorld.getBlockId(x, y, z);
        if (blockid == 10 || blockid == 11 || blockid == 51) {
            return false;
        }
        if (m instanceof MaterialLogic) {
            return false;
        }
        return blockid != 0;
    }

    public static boolean isAdjacentToBlocks(Vec3D pos, List<Integer> blockids) {
        Minecraft mc = Minecraft.theMinecraft;
        ArrayList<Vec3D> adjacents = new ArrayList<Vec3D>();
        adjacents.add(pos.add(1.0, 0.0, 0.0));
        adjacents.add(pos.add(-1.0, 0.0, 0.0));
        adjacents.add(pos.add(0.0, 1.0, 0.0));
        adjacents.add(pos.add(0.0, -1.0, 0.0));
        adjacents.add(pos.add(0.0, 0.0, 1.0));
        adjacents.add(pos.add(0.0, 0.0, -1.0));
        for (Vec3D adj : adjacents) {
            if (!blockids.contains(WorldUtils.getBlockID(adj))) continue;
            return true;
        }
        return false;
    }

    public static boolean isAbove(Vec3D pos, List<Integer> blockids) {
        Minecraft mc = Minecraft.theMinecraft;
        int id = WorldUtils.getBlockID(pos);
        int idUp1 = WorldUtils.getBlockID(pos.add(0.0, 1.0, 0.0));
        int idUp2 = WorldUtils.getBlockID(pos.add(0.0, 2.0, 0.0));
        int idUp3 = WorldUtils.getBlockID(pos.add(0.0, 3.0, 0.0));
        return blockids.contains(id) || blockids.contains(idUp1) || blockids.contains(idUp2) || blockids.contains(idUp3);
    }
}

