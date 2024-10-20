/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventBlock;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.BlockPlacement;
import MEDMEX.Utils.BlockPos;
import MEDMEX.Utils.Cell;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.PathfinderMode;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.WorldUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet14BlockDig;

public class Pathfinder
extends Module {
    public static Pathfinder instance;
    ArrayList<Vec3D> mineBlockBlackList = new ArrayList();
    ArrayList<Vec3D> currentPath;
    public int maxPathLength = 0;
    public Vec3D endGoal;
    public int toMineBlockID;
    List<BlockPos> mineBlocks = new ArrayList<BlockPos>();
    public PathfinderMode mode;
    public Cell[] nears = new Cell[]{new Cell(1, 0, 0), new Cell(0, 0, 1), new Cell(-1, 0, 0), new Cell(0, 0, -1), new Cell(1, 1, 0), new Cell(0, 1, 1), new Cell(-1, 1, 0), new Cell(0, 1, -1), new Cell(1, -1, 0), new Cell(0, -1, 1), new Cell(-1, -1, 0), new Cell(0, -1, -1)};

    public Pathfinder() {
        super("Pathfinder", "Find path to position", 0, Category.Player);
        instance = this;
    }

    @Override
    public void onEnable() {
        this.currentPath = null;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            if (this.mode == PathfinderMode.Walk) {
                this.walkLoop();
            }
            if (this.mode == PathfinderMode.Mine) {
                this.mineLoop();
            }
        }
        if (e instanceof EventRenderEntities) {
            if (this.mode == PathfinderMode.Walk) {
                this.renderWalk();
            }
            if (this.mode == PathfinderMode.Mine) {
                this.renderWalk();
                this.renderMine();
            }
        }
        if (e instanceof EventBlock && this.mode == PathfinderMode.Mine) {
            this.processMineBlocks((EventBlock)e);
        }
    }

    public void mineLoop() {
        if (this.mineBlocks.isEmpty()) {
            return;
        }
        Vec3D nearestMineBlock = this.getNearestMineBlock();
        if (nearestMineBlock == null) {
            return;
        }
        Vec3D currentPos = new Vec3D((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.boundingBox.minY, (int)this.mc.thePlayer.posZ);
        if (Vec3D.getDistance(currentPos, nearestMineBlock = nearestMineBlock.add(nearestMineBlock.getX() < 0.0 ? 1 : 0, 0.0, nearestMineBlock.getZ() < 0.0 ? 1 : 0)) < 2.0) {
            this.mc.thePlayer.rotationYaw = this.getYaw(nearestMineBlock);
            this.mc.thePlayer.rotationPitch = this.getPitch(nearestMineBlock);
            PlayerUtils.breakBlockPacket(nearestMineBlock.add(nearestMineBlock.getX() < 0.0 ? -1 : 0, 0.0, nearestMineBlock.getZ() < 0.0 ? -1 : 0));
            return;
        }
        if (this.mc.theWorld.getBlockId((int)this.mc.thePlayer.posX + (this.mc.thePlayer.posX <= 0.0 ? -1 : 0), (int)this.mc.thePlayer.boundingBox.minY - 1, (int)this.mc.thePlayer.posZ + (this.mc.thePlayer.posZ <= 0.0 ? -1 : 0)) == 0) {
            Vec3D[] corners = new Vec3D[4];
            AxisAlignedBB bb = this.mc.thePlayer.boundingBox.copy();
            corners[0] = new Vec3D(bb.minX, bb.maxY, bb.minZ);
            corners[1] = new Vec3D(bb.minX, bb.maxY, bb.maxZ);
            corners[2] = new Vec3D(bb.maxX, bb.maxY, bb.minZ);
            corners[3] = new Vec3D(bb.maxX, bb.maxY, bb.maxZ);
            Vec3D[] vec3DArray = corners;
            int n = corners.length;
            int n2 = 0;
            while (n2 < n) {
                Vec3D vec = vec3DArray[n2];
                if (this.mc.theWorld.getBlockId((int)vec.getX() + (vec.getX() <= 0.0 ? -1 : 0), (int)this.mc.thePlayer.boundingBox.minY - 1, (int)vec.getZ() + (vec.getZ() <= 0.0 ? -1 : 0)) != 0) {
                    currentPos = new Vec3D((int)vec.getX(), (int)this.mc.thePlayer.boundingBox.minY, (int)vec.getZ());
                }
                ++n2;
            }
        }
        if (this.shouldFindPath()) {
            this.currentPath = this.findPath(currentPos, nearestMineBlock, 300);
            this.maxPathLength = this.currentPath.size();
        }
        if (!this.currentPath.get(this.currentPath.size() - 1).equalsVec(nearestMineBlock)) {
            this.mineBlockBlackList.add(nearestMineBlock);
        }
        if (this.currentPath.size() <= 1) {
            return;
        }
        List<Vec3D> blocksToBreak = this.getBlocksToBreak(this.currentPath.get(0), 0);
        if (blocksToBreak.isEmpty()) {
            blocksToBreak = this.getBlocksToBreak(this.currentPath.get(1), 1);
        }
        List<Vec3D> blocksToPlace = this.getBlocksToPlace(this.currentPath.get(1), 1);
        if (blocksToBreak.isEmpty() && blocksToPlace.isEmpty()) {
            this.move();
            return;
        }
        if (!blocksToBreak.isEmpty()) {
            Vec3D breakBlock = blocksToBreak.get(0);
            this.mc.thePlayer.rotationYaw = this.getYaw(breakBlock.add(breakBlock.getX() < 0.0 ? 1 : 0, 0.0, breakBlock.getZ() < 0.0 ? 1 : 0));
            this.mc.thePlayer.rotationPitch = this.getPitch(breakBlock.add(breakBlock.getX() < 0.0 ? 1 : 0, 0.0, breakBlock.getZ() < 0.0 ? 1 : 0));
            int i = 0;
            while (i < 4) {
                Client.sendPacket(new Packet14BlockDig(1, (int)breakBlock.getX(), (int)breakBlock.getY(), (int)breakBlock.getZ(), 0));
                ++i;
            }
            Client.sendPacket(new Packet14BlockDig(3, (int)breakBlock.getX(), (int)breakBlock.getY(), (int)breakBlock.getZ(), 0));
        }
        for (Vec3D placeBlock : blocksToPlace) {
            int blockSlot = InventoryUtils.getHotbarslotBlocks();
            if (blockSlot == -1) {
                return;
            }
            this.mc.thePlayer.inventory.currentItem = blockSlot;
            BlockPlacement placement = PlayerUtils.getPossiblePlacement(placeBlock);
            if (placement == null) continue;
            this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), placement.getX(), placement.getY(), placement.getZ(), placement.getSide());
        }
    }

    public void walkLoop() {
        Vec3D currentPos = new Vec3D((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.boundingBox.minY, (int)this.mc.thePlayer.posZ);
        if (this.mc.theWorld.getBlockId((int)this.mc.thePlayer.posX + (this.mc.thePlayer.posX <= 0.0 ? -1 : 0), (int)this.mc.thePlayer.boundingBox.minY - 1, (int)this.mc.thePlayer.posZ + (this.mc.thePlayer.posZ <= 0.0 ? -1 : 0)) == 0) {
            Vec3D[] corners = new Vec3D[4];
            AxisAlignedBB bb = this.mc.thePlayer.boundingBox.copy();
            corners[0] = new Vec3D(bb.minX, bb.maxY, bb.minZ);
            corners[1] = new Vec3D(bb.minX, bb.maxY, bb.maxZ);
            corners[2] = new Vec3D(bb.maxX, bb.maxY, bb.minZ);
            corners[3] = new Vec3D(bb.maxX, bb.maxY, bb.maxZ);
            Vec3D[] vec3DArray = corners;
            int n = corners.length;
            int n2 = 0;
            while (n2 < n) {
                Vec3D vec = vec3DArray[n2];
                if (this.mc.theWorld.getBlockId((int)vec.getX() + (vec.getX() <= 0.0 ? -1 : 0), (int)this.mc.thePlayer.boundingBox.minY - 1, (int)vec.getZ() + (vec.getZ() <= 0.0 ? -1 : 0)) != 0) {
                    currentPos = new Vec3D((int)vec.getX(), (int)this.mc.thePlayer.boundingBox.minY, (int)vec.getZ());
                }
                ++n2;
            }
        }
        if (this.endGoal == null) {
            return;
        }
        if (this.shouldFindPath()) {
            Vec3D goal = this.endGoal;
            if (Vec3D.getDistance(currentPos, this.endGoal) > 100.0) {
                goal = this.getIntermediateGoal(currentPos, goal);
            }
            this.currentPath = this.findPath(currentPos, goal, 300);
            this.maxPathLength = this.currentPath.size();
        }
        if (this.currentPath.size() <= 1) {
            return;
        }
        List<Vec3D> blocksToBreak = this.getBlocksToBreak(this.currentPath.get(0), 0);
        if (blocksToBreak.isEmpty()) {
            blocksToBreak = this.getBlocksToBreak(this.currentPath.get(1), 1);
        }
        List<Vec3D> blocksToPlace = this.getBlocksToPlace(this.currentPath.get(1), 1);
        if (blocksToBreak.isEmpty() && blocksToPlace.isEmpty()) {
            this.move();
            return;
        }
        if (!blocksToBreak.isEmpty()) {
            Vec3D breakBlock = blocksToBreak.get(0);
            int i = 0;
            while (i < 3) {
                Client.sendPacket(new Packet14BlockDig(1, (int)breakBlock.getX(), (int)breakBlock.getY(), (int)breakBlock.getZ(), 0));
                ++i;
            }
            Client.sendPacket(new Packet14BlockDig(3, (int)breakBlock.getX(), (int)breakBlock.getY(), (int)breakBlock.getZ(), 0));
        }
        for (Vec3D placeBlock : blocksToPlace) {
            int blockSlot = InventoryUtils.getHotbarslotBlocks();
            if (blockSlot == -1) {
                return;
            }
            this.mc.thePlayer.inventory.currentItem = blockSlot;
            BlockPlacement placement = PlayerUtils.getPossiblePlacement(placeBlock);
            if (placement == null) continue;
            this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), placement.getX(), placement.getY(), placement.getZ(), placement.getSide());
        }
    }

    public void renderWalk() {
        if (this.currentPath == null) {
            return;
        }
        RenderUtils.renderLineList(this.currentPath, Color.green);
        int pathIndex = 0;
        for (Vec3D vec : this.currentPath) {
            List<Vec3D> blocksToBreak = this.getBlocksToBreak(vec, pathIndex);
            for (Vec3D breakBlock : blocksToBreak) {
                RenderUtils.boundingESPBox(breakBlock.toBB(), Color.red);
            }
            List<Vec3D> blocksToPlace = this.getBlocksToPlace(vec, pathIndex);
            for (Vec3D placeBlock : blocksToPlace) {
                RenderUtils.boundingESPBox(placeBlock.toBB(), Color.green);
            }
            ++pathIndex;
        }
    }

    public void renderMine() {
        try {
            for (BlockPos bp : this.mineBlocks) {
                if (WorldUtils.getBlockID(bp.getPos()) != bp.getBlockid()) {
                    this.mineBlocks.remove(bp);
                }
                RenderUtils.boundingESPBox(bp.getPos().toBB(), Color.MAGENTA);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void startWalk(Vec3D goal) {
        this.mode = PathfinderMode.Walk;
        this.endGoal = goal;
    }

    public void startMine(int blockid) {
        this.mineBlocks.clear();
        this.mode = PathfinderMode.Mine;
        this.toMineBlockID = blockid;
        this.mc.renderGlobal.loadRenderers();
    }

    public void processMineBlocks(EventBlock eb) {
        if (eb.getBlockid() == this.toMineBlockID) {
            for (BlockPos bp : this.mineBlocks) {
                if (!bp.getPos().equalsVec(eb.getPos())) continue;
                return;
            }
            if (WorldUtils.getBlockID(eb.getPos()) != this.toMineBlockID) {
                return;
            }
            this.mineBlocks.add(new BlockPos(eb.getPos(), eb.getBlockid()));
        }
    }

    public List<Vec3D> getBlocksToBreak(Vec3D vec, int pathIndex) {
        ArrayList<Vec3D> blocks = new ArrayList<Vec3D>();
        Vec3D pos = new Vec3D(vec.getX() + (double)(vec.getX() <= 0.0 ? -1 : 0), vec.getY(), vec.getZ() + (double)(vec.getZ() <= 0.0 ? -1 : 0));
        if (!WorldUtils.isTransparentBlock(pos) && WorldUtils.getBlockID(pos) != 7) {
            blocks.add(pos);
        }
        if (!WorldUtils.isTransparentBlock(pos.add(0.0, 1.0, 0.0)) && WorldUtils.getBlockID(pos.add(0.0, 1.0, 0.0)) != 7) {
            blocks.add(pos.add(0.0, 1.0, 0.0));
        }
        if (pathIndex > 0 && this.currentPath.get(pathIndex).getY() < this.currentPath.get(pathIndex - 1).getY() && !WorldUtils.isTransparentBlock(pos.add(0.0, 2.0, 0.0)) && WorldUtils.getBlockID(pos.add(0.0, 2.0, 0.0)) != 7) {
            blocks.add(pos.add(0.0, 2.0, 0.0));
        }
        if (this.currentPath.size() > pathIndex + 1 && this.currentPath.get(pathIndex).getY() < this.currentPath.get(pathIndex + 1).getY() && !WorldUtils.isTransparentBlock(pos.add(0.0, 2.0, 0.0)) && WorldUtils.getBlockID(pos.add(0.0, 2.0, 0.0)) != 7) {
            blocks.add(pos.add(0.0, 2.0, 0.0));
        }
        return blocks;
    }

    public List<Vec3D> getBlocksToPlace(Vec3D vec, int pathIndex) {
        BlockPlacement bp;
        ArrayList<Vec3D> blocks = new ArrayList<Vec3D>();
        Vec3D pos = new Vec3D(vec.getX() + (double)(vec.getX() <= 0.0 ? -1 : 0), vec.getY(), vec.getZ() + (double)(vec.getZ() <= 0.0 ? -1 : 0));
        if (WorldUtils.getBlockID(pos.add(0.0, -1.0, 0.0)) == 0) {
            blocks.add(pos.add(0.0, -1.0, 0.0));
        }
        if ((bp = PlayerUtils.getPossiblePlacement(pos.add(0.0, -1.0, 0.0))) != null) {
            return blocks;
        }
        bp = PlayerUtils.getPossiblePlacement(pos.add(0.0, -2.0, 0.0));
        if (bp != null) {
            blocks.add(pos.add(0.0, -2.0, 0.0));
            return blocks;
        }
        bp = PlayerUtils.getPossiblePlacement(pos.add(0.0, 0.0, 0.0));
        if (bp != null) {
            blocks.add(pos.add(0.0, 0.0, 0.0));
            return blocks;
        }
        return blocks;
    }

    public void move() {
        float yaw;
        if (this.currentPath == null) {
            return;
        }
        if (this.currentPath.isEmpty()) {
            return;
        }
        if (this.currentPath.size() <= 1) {
            return;
        }
        Vec3D next = this.currentPath.get(1);
        if (this.currentPath.size() > 2 && this.currentPath.get(2).getY() == next.getY() && this.currentPath.get(2).getY() == this.currentPath.get(0).getY() && WorldUtils.getBlockID(this.currentPath.get(2).add(0.0, -1.0, 0.0)) != 0) {
            next = this.currentPath.get(2);
        }
        this.mc.thePlayer.rotationYaw = yaw = this.getYaw(next);
        this.mc.thePlayer.moveEntityWithHeading(this.mc.thePlayer.movementInput.moveStrafe, 0.98f);
    }

    public boolean shouldFindPath() {
        return true;
    }

    public ArrayList<Vec3D> findPath(Vec3D start, Vec3D end, int maxloops) {
        ArrayList<Cell> open = new ArrayList<Cell>();
        HashSet<Cell> closed = new HashSet<Cell>();
        open.add(new Cell(start));
        Cell current = null;
        int loops = 0;
        while (!open.isEmpty() && loops < maxloops) {
            current = (Cell)open.get(0);
            int index = 0;
            ArrayList opens = new ArrayList();
            int i = 1;
            while (i < open.size()) {
                if (((Cell)open.get((int)i)).f < current.f) {
                    current = (Cell)open.get(i);
                    index = i;
                }
                ++i;
            }
            open.remove(index);
            closed.add(current);
            if (Vec3D.getDistance(current.toVec3D(), end) < 2.0) break;
            ArrayList<Cell> children = new ArrayList<Cell>();
            Cell[] cellArray = this.nears;
            int n = this.nears.length;
            int n2 = 0;
            while (n2 < n) {
                Cell near = cellArray[n2];
                Cell child = new Cell(current.x + near.x, current.y + near.y, current.z + near.z);
                child.parent = current;
                children.add(child);
                ++n2;
            }
            for (Cell child : children) {
                if (closed.contains(child)) continue;
                child.g = current.g + 1;
                child.h = (int)this.calcHeuristic(child.toVec3D(), child.parent.toVec3D(), end);
                child.f = child.g + child.h;
                if (open.contains(child) && ((Cell)open.get((int)open.indexOf((Object)child))).g > child.g) continue;
                open.add(child);
            }
            ++loops;
        }
        ArrayList<Vec3D> path = new ArrayList<Vec3D>();
        Cell cur = current;
        while (cur != null) {
            path.add(cur.toVec3D());
            cur = cur.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public boolean isLegalMove(Vec3D current, Vec3D next) {
        int nx = (int)next.getX() + (next.getX() <= 0.0 ? -1 : 0);
        int ny = (int)next.getY();
        int nz = (int)next.getZ() + (next.getZ() <= 0.0 ? -1 : 0);
        int cx = (int)current.getX() + (current.getX() <= 0.0 ? -1 : 0);
        int cy = (int)current.getY();
        int cz = (int)current.getZ() + (current.getZ() <= 0.0 ? -1 : 0);
        if (!WorldUtils.canWalkThrough(nx, ny, nz) || !this.mc.theWorld.isAirBlock(nx, ny + 1, nz)) {
            return false;
        }
        if (!WorldUtils.canWalkOn(nx, ny - 1, nz)) {
            return false;
        }
        if (!(cy <= ny || this.mc.theWorld.isAirBlock(nx, ny + 2, nz) && this.mc.theWorld.getBlockId(nx, ny + 2, nz) != 7)) {
            return false;
        }
        return cy >= ny || this.mc.theWorld.isAirBlock(cx, cy + 2, cz) && this.mc.theWorld.getBlockId(cx, cy + 2, cz) != 7;
    }

    public double calcHeuristic(Vec3D from, Vec3D next, Vec3D to) {
        int fx = (int)from.getX() + (from.getX() <= 0.0 ? -1 : 0);
        int fy = (int)from.getY();
        int fz = (int)from.getZ() + (from.getZ() <= 0.0 ? -1 : 0);
        int nx = (int)next.getX() + (next.getX() <= 0.0 ? -1 : 0);
        int ny = (int)next.getY();
        int nz = (int)next.getZ() + (next.getZ() <= 0.0 ? -1 : 0);
        int tx = (int)to.getX() + (to.getX() <= 0.0 ? -1 : 0);
        int ty = (int)to.getY();
        int tz = (int)to.getZ() + (to.getZ() <= 0.0 ? -1 : 0);
        double dist = Vec3D.getDistance(from, to);
        double penalty = 0.0;
        if (!this.isLegalMove(next, from)) {
            if (this.mode == PathfinderMode.Walk) {
                penalty += 1500.0;
            }
            if (this.mode == PathfinderMode.Mine) {
                penalty += 20.0;
            }
        }
        List<Integer> adjacentBlocksBlacklist = Arrays.asList(8, 9, 10, 11, 12, 13);
        int i = 0;
        while (i < 3) {
            if (WorldUtils.isAdjacentToBlocks(new Vec3D(nx, ny + i, nz), adjacentBlocksBlacklist)) {
                penalty += 100000.0;
            }
            ++i;
        }
        return dist + penalty;
    }

    public Vec3D getNearestMineBlock() {
        Vec3D nearestMineBlock = null;
        for (BlockPos mineBlock : this.mineBlocks) {
            boolean isBlacklisted = false;
            for (Vec3D blacklist : this.mineBlockBlackList) {
                if (!mineBlock.getPos().equalsVec(blacklist)) continue;
                isBlacklisted = true;
            }
            if (isBlacklisted || mineBlock.getPos().getY() < 7.0) continue;
            if (mineBlock.getBlockid() != this.toMineBlockID) {
                this.mineBlocks.remove(mineBlock);
                continue;
            }
            if (nearestMineBlock == null) {
                nearestMineBlock = mineBlock.getPos();
                continue;
            }
            double nearestDist = Vec3D.getDistance(PlayerUtils.getPos(), nearestMineBlock);
            double currentDist = Vec3D.getDistance(PlayerUtils.getPos(), mineBlock.getPos());
            if (!(currentDist < nearestDist)) continue;
            nearestMineBlock = mineBlock.getPos();
        }
        return nearestMineBlock;
    }

    @Override
    public void onDisable() {
    }

    public Vec3D getIntermediateGoal(Vec3D pos, Vec3D endGoal) {
        if (Vec3D.getDistance(pos, endGoal) > 100.0) {
            Vec3D dir = new Vec3D(endGoal.getX() - pos.getX(), endGoal.getY(), endGoal.getZ() - pos.getZ()).normalize();
            Vec3D scaled = new Vec3D(dir.getX() * 100.0, dir.getY(), dir.getZ() * 100.0);
            return new Vec3D(pos.getX() + scaled.getX(), endGoal.getY(), pos.getZ() + scaled.getZ());
        }
        return null;
    }

    public float getYaw(Vec3D v) {
        float diffX = (float)(v.getX() + (v.getX() > 0.0 ? 0.5 : -0.5) - this.mc.thePlayer.posX);
        float diffZ = (float)(v.getZ() + (v.getZ() > 0.0 ? 0.5 : -0.5) - this.mc.thePlayer.posZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        if (!Float.isNaN(yaw = this.unWrapYaw(yaw))) {
            return yaw;
        }
        return -1.0f;
    }

    public float getPitch(Vec3D v) {
        float diffZ;
        float dist;
        float diffX = (float)(v.getX() - this.mc.thePlayer.posX);
        float diffY = (float)(v.getY() - this.mc.thePlayer.posY);
        float pitch = (float)(-(Math.atan2(diffY, dist = MathHelper.sqrt_float(Math.abs(diffX * diffX + (diffZ = (float)(v.getZ() - this.mc.thePlayer.posZ)) * diffZ))) * 180.0 / Math.PI));
        if (!Float.isNaN(pitch)) {
            return pitch;
        }
        return -1.0f;
    }

    public float unWrapYaw(float yaw) {
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return yaw;
    }
}

