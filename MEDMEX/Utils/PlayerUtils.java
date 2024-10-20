/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import MEDMEX.Client;
import MEDMEX.Modules.Player.SpeedMine;
import MEDMEX.Utils.BlockPlacement;
import MEDMEX.Utils.Vec2D;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.WorldUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.World;

public class PlayerUtils {
    Minecraft mc = Minecraft.theMinecraft;
    public static List<Integer> transparentBlocks = Arrays.asList(0, 78, 64, 68, 323, 50, 51, 55, 59, 65, 66, 69, 70, 72, 75, 76, 77, 83);
    private int field_9445_c = -1;
    private int field_9444_d = -1;
    private int field_9443_e = -1;
    private float field_9442_f = 0.0f;
    private float field_9441_h = 0.0f;
    private int field_9440_i = 0;
    private boolean field_9439_j = false;
    private float field_1080_g = 0.0f;
    public static CopyOnWriteArrayList<Double> speeds = new CopyOnWriteArrayList();

    public static Vec3D getPos() {
        Minecraft mc = Minecraft.theMinecraft;
        return new Vec3D(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    public static List<Vec3D> getBlockCollisionsWithPlayer() {
        Minecraft mc = Minecraft.theMinecraft;
        ArrayList<Vec3D> vecs = new ArrayList<Vec3D>();
        Vec3D pos = PlayerUtils.getBelowPlayerPos();
        AxisAlignedBB playerBB = mc.thePlayer.boundingBox.expand(0.2, 0.0, 0.2);
        Vec3D x1 = pos.add(1.0, 1.0, 0.0);
        Vec3D x2 = pos.add(-1.0, 1.0, 0.0);
        Vec3D z1 = pos.add(0.0, 1.0, 1.0);
        Vec3D z2 = pos.add(0.0, 1.0, -1.0);
        if (playerBB.intersectsWith(x1.toBB()) && !transparentBlocks.contains(WorldUtils.getBlockID(x1))) {
            vecs.add(x1);
        }
        if (playerBB.intersectsWith(x2.toBB()) && !transparentBlocks.contains(WorldUtils.getBlockID(x2))) {
            vecs.add(x2);
        }
        if (playerBB.intersectsWith(z1.toBB()) && !transparentBlocks.contains(WorldUtils.getBlockID(z1))) {
            vecs.add(z1);
        }
        if (playerBB.intersectsWith(z2.toBB()) && !transparentBlocks.contains(WorldUtils.getBlockID(z2))) {
            vecs.add(z2);
        }
        return vecs;
    }

    public static Vec3D getBelowPlayerPos() {
        Vec3D pos = PlayerUtils.getPos();
        pos.setX((int)pos.getX());
        pos.setY((int)pos.getY());
        pos.setZ((int)pos.getZ());
        int x = (int)pos.getX();
        int y = (int)pos.getY();
        int z = (int)pos.getZ();
        return pos.offset(x < 0 ? -1 : 0, -2.0, z < 0 ? -1 : 0);
    }

    public static Vec3D getBelowPos(Vec3D pos) {
        pos.setX((int)pos.getX());
        pos.setY((int)pos.getY());
        pos.setZ((int)pos.getZ());
        int x = (int)pos.getX();
        int y = (int)pos.getY();
        int z = (int)pos.getZ();
        return pos.offset(x < 0 ? -1 : 0, -2.0, z < 0 ? -1 : 0);
    }

    public static Vec3D getOffsetPos(Vec3D pos) {
        pos.setX((int)pos.getX());
        pos.setY((int)pos.getY());
        pos.setZ((int)pos.getZ());
        int x = (int)pos.getX();
        int y = (int)pos.getY();
        int z = (int)pos.getZ();
        return pos.offset(x < 0 ? -1 : 0, 0.0, z < 0 ? -1 : 0);
    }

    public static Vec3D getDirectionOffset() {
        Minecraft mc = Minecraft.theMinecraft;
        Vec3D vec = new Vec3D(0.0, 0.0, 0.0);
        int dir = MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (dir == 0) {
            vec.setZ(1.0);
        }
        if (dir == 1) {
            vec.setX(-1.0);
        }
        if (dir == 2) {
            vec.setZ(-1.0);
        }
        if (dir == 3) {
            vec.setX(1.0);
        }
        return vec;
    }

    public static boolean canPlaceBlockOn(Vec3D pos) {
        Minecraft mc = Minecraft.theMinecraft;
        int id = WorldUtils.getBlockID(pos);
        if (id == 0) {
            return false;
        }
        if (id == 10 || id == 11) {
            return false;
        }
        return id != 8 && id != 9;
    }

    public static Vec2D getRotationsToVec3D(Vec3D v) {
        Minecraft mc = Minecraft.theMinecraft;
        float diffX = (float)(v.getX() - mc.thePlayer.posX);
        float diffY = (float)(v.getY() - mc.thePlayer.posY);
        float diffZ = (float)(v.getZ() - mc.thePlayer.posZ);
        float dist = MathHelper.sqrt_float(Math.abs(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        if (!Float.isNaN(pitch) && !Float.isNaN(yaw)) {
            return new Vec2D(yaw, pitch);
        }
        return null;
    }

    public static BlockPlacement getPossiblePlacement(Vec3D pos) {
        int z;
        int y;
        Vec3D posCopy = new Vec3D(pos.x, pos.y, pos.z);
        Minecraft mc = Minecraft.theMinecraft;
        int x = (int)posCopy.getX();
        if (PlayerUtils.canPlaceBlockOn(new Vec3D(x, (y = (int)posCopy.getY()) - 1, z = (int)posCopy.getZ()))) {
            return new BlockPlacement(posCopy.offset(0.0, -1.0, 0.0), 1);
        }
        if (PlayerUtils.canPlaceBlockOn(new Vec3D(x + 1, y, z))) {
            return new BlockPlacement(posCopy.offset(1.0, 0.0, 0.0), 4);
        }
        if (PlayerUtils.canPlaceBlockOn(new Vec3D(x - 1, y, z))) {
            return new BlockPlacement(posCopy.offset(-1.0, 0.0, 0.0), 5);
        }
        if (PlayerUtils.canPlaceBlockOn(new Vec3D(x, y, z + 1))) {
            return new BlockPlacement(posCopy.offset(0.0, 0.0, 1.0), 2);
        }
        if (PlayerUtils.canPlaceBlockOn(new Vec3D(x, y, z - 1))) {
            return new BlockPlacement(posCopy.offset(0.0, 0.0, -1.0), 3);
        }
        return null;
    }

    public static void breakBlockPacket(Vec3D pos) {
        Client.sendPacket(new Packet14BlockDig(0, (int)pos.getX(), (int)pos.getY(), (int)pos.getZ(), 1));
        Client.sendPacket(new Packet14BlockDig(2, (int)pos.getX(), (int)pos.getY(), (int)pos.getZ(), 1));
    }

    public void breakBlock(Vec3D pos) {
        int i1 = (int)pos.getX();
        int i2 = (int)pos.getY();
        int i3 = (int)pos.getZ();
        int i4 = 1;
        if (SpeedMine.instance.isEnabled && SpeedMine.instance.getSet("Mode").getValString().equalsIgnoreCase("Speed")) {
            this.field_9440_i = 0;
        }
        if (i1 == this.field_9445_c && i2 == this.field_9444_d && i3 == this.field_9443_e) {
            int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
            if (i5 == 0) {
                this.field_9439_j = false;
                return;
            }
            Block block6 = Block.blocksList[i5];
            this.field_9442_f = (float)((double)this.field_9442_f + (SpeedMine.instance.isEnabled && SpeedMine.instance.getSet("Mode").getValString().equalsIgnoreCase("Speed") ? (double)block6.blockStrength(this.mc.thePlayer) * SpeedMine.instance.getSet("Speed").getValDouble() : (double)block6.blockStrength(this.mc.thePlayer)));
            if (this.field_9441_h % 4.0f == 0.0f && block6 != null) {
                this.mc.sndManager.playSound(block6.stepSound.func_1145_d(), (float)i1 + 0.5f, (float)i2 + 0.5f, (float)i3 + 0.5f, (block6.stepSound.func_1147_b() + 1.0f) / 8.0f, block6.stepSound.func_1144_c() * 0.5f);
            }
            this.field_9441_h += 1.0f;
            if (this.field_9442_f >= 1.0f) {
                this.field_9439_j = false;
                Client.sendPacket(new Packet14BlockDig(2, i1, i2, i3, i4));
                this.sendBlockRemoved(i1, i2, i3, i4);
                this.field_9442_f = 0.0f;
                this.field_1080_g = 0.0f;
                this.field_9441_h = 0.0f;
                this.field_9440_i = 5;
            }
        } else {
            this.clickBlock(i1, i2, i3, i4);
        }
    }

    public boolean sendBlockRemoved(int i1, int i2, int i3, int i4) {
        int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
        boolean z6 = this.sendBlockRemoved2(i1, i2, i3, i4);
        ItemStack itemStack7 = this.mc.thePlayer.getCurrentEquippedItem();
        if (itemStack7 != null) {
            itemStack7.func_25191_a(i5, i1, i2, i3, this.mc.thePlayer);
            if (itemStack7.stackSize == 0) {
                itemStack7.func_1097_a(this.mc.thePlayer);
                this.mc.thePlayer.destroyCurrentEquippedItem();
            }
        }
        return z6;
    }

    public boolean sendBlockRemoved2(int i1, int i2, int i3, int i4) {
        this.mc.effectRenderer.addBlockDestroyEffects(i1, i2, i3);
        World world5 = this.mc.theWorld;
        Block block6 = Block.blocksList[world5.getBlockId(i1, i2, i3)];
        int i7 = world5.getBlockMetadata(i1, i2, i3);
        boolean z8 = world5.setBlockWithNotify(i1, i2, i3, 0);
        if (block6 != null && z8) {
            this.mc.sndManager.playSound(block6.stepSound.func_1146_a(), (float)i1 + 0.5f, (float)i2 + 0.5f, (float)i3 + 0.5f, (block6.stepSound.func_1147_b() + 1.0f) / 2.0f, block6.stepSound.func_1144_c() * 0.8f);
            block6.onBlockDestroyedByPlayer(world5, i1, i2, i3, i7);
        }
        return z8;
    }

    public void clickBlock(int i1, int i2, int i3, int i4) {
        if (!this.field_9439_j || i1 != this.field_9445_c || i2 != this.field_9444_d || i3 != this.field_9443_e) {
            Client.sendPacket(new Packet14BlockDig(0, i1, i2, i3, i4));
            int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
            if (i5 > 0 && this.field_9442_f == 0.0f) {
                Block.blocksList[i5].onBlockClicked(this.mc.theWorld, i1, i2, i3, this.mc.thePlayer);
            }
            if (i5 > 0 && Block.blocksList[i5].blockStrength(this.mc.thePlayer) >= 1.0f) {
                this.sendBlockRemoved(i1, i2, i3, i4);
            } else {
                this.field_9439_j = true;
                this.field_9445_c = i1;
                this.field_9444_d = i2;
                this.field_9443_e = i3;
                this.field_9442_f = 0.0f;
                this.field_1080_g = 0.0f;
                this.field_9441_h = 0.0f;
            }
        }
    }

    public static double getAveragePlayerSpeedOverSecond() {
        Minecraft mc = Minecraft.theMinecraft;
        if (mc.thePlayer.movementInput.moveForward == 0.0f && mc.thePlayer.movementInput.moveStrafe == 0.0f) {
            speeds.clear();
        }
        if (speeds.size() == 150) {
            speeds.remove(0);
        }
        double dx = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
        double dy = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
        double bps = Math.sqrt(dx * dx + dy * dy) * 20.0;
        speeds.add(bps);
        return PlayerUtils.averageOfList(speeds);
    }

    public static double averageOfList(CopyOnWriteArrayList<Double> list) {
        double total = 0.0;
        for (double d : list) {
            total += d;
        }
        return total / (double)list.size();
    }
}

