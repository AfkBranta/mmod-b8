/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderClient;
import net.minecraft.src.Entity;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.MCHashTable;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.SaveHandlerMP;
import net.minecraft.src.World;
import net.minecraft.src.WorldBlockPositionType;
import net.minecraft.src.WorldProvider;

public class WorldClient
extends World {
    private LinkedList field_1057_z = new LinkedList();
    private NetClientHandler sendQueue;
    private ChunkProviderClient field_20915_C;
    private MCHashTable field_1055_D = new MCHashTable();
    private Set field_20914_E = new HashSet();
    private Set field_1053_F = new HashSet();

    public WorldClient(NetClientHandler netClientHandler1, long j2, int i4) {
        super((ISaveHandler)new SaveHandlerMP(), "MpServer", WorldProvider.func_4101_a(i4), j2);
        this.sendQueue = netClientHandler1;
        this.setSpawnPoint(new ChunkCoordinates(8, 64, 8));
    }

    @Override
    public void tick() {
        int i2;
        this.setWorldTime(this.getWorldTime() + 1L);
        int i1 = this.calculateSkylightSubtracted(1.0f);
        if (i1 != this.skylightSubtracted) {
            this.skylightSubtracted = i1;
            i2 = 0;
            while (i2 < this.worldAccesses.size()) {
                ((IWorldAccess)this.worldAccesses.get(i2)).updateAllRenderers();
                ++i2;
            }
        }
        i2 = 0;
        while (i2 < 10 && !this.field_1053_F.isEmpty()) {
            Entity entity3 = (Entity)this.field_1053_F.iterator().next();
            if (!this.loadedEntityList.contains(entity3)) {
                this.entityJoinedWorld(entity3);
            }
            ++i2;
        }
        this.sendQueue.processReadPackets();
        i2 = 0;
        while (i2 < this.field_1057_z.size()) {
            WorldBlockPositionType worldBlockPositionType4 = (WorldBlockPositionType)this.field_1057_z.get(i2);
            if (--worldBlockPositionType4.field_1206_d == 0) {
                super.setBlockAndMetadata(worldBlockPositionType4.field_1202_a, worldBlockPositionType4.field_1201_b, worldBlockPositionType4.field_1207_c, worldBlockPositionType4.field_1205_e, worldBlockPositionType4.field_1204_f);
                super.markBlockNeedsUpdate(worldBlockPositionType4.field_1202_a, worldBlockPositionType4.field_1201_b, worldBlockPositionType4.field_1207_c);
                this.field_1057_z.remove(i2--);
            }
            ++i2;
        }
    }

    public void func_711_c(int i1, int i2, int i3, int i4, int i5, int i6) {
        int i7 = 0;
        while (i7 < this.field_1057_z.size()) {
            WorldBlockPositionType worldBlockPositionType8 = (WorldBlockPositionType)this.field_1057_z.get(i7);
            if (worldBlockPositionType8.field_1202_a >= i1 && worldBlockPositionType8.field_1201_b >= i2 && worldBlockPositionType8.field_1207_c >= i3 && worldBlockPositionType8.field_1202_a <= i4 && worldBlockPositionType8.field_1201_b <= i5 && worldBlockPositionType8.field_1207_c <= i6) {
                this.field_1057_z.remove(i7--);
            }
            ++i7;
        }
    }

    @Override
    protected IChunkProvider getChunkProvider() {
        this.field_20915_C = new ChunkProviderClient(this);
        return this.field_20915_C;
    }

    @Override
    public void setSpawnLocation() {
        this.setSpawnPoint(new ChunkCoordinates(8, 64, 8));
    }

    @Override
    protected void updateBlocksAndPlayCaveSounds() {
    }

    @Override
    public void scheduleBlockUpdate(int i1, int i2, int i3, int i4, int i5) {
    }

    @Override
    public boolean TickUpdates(boolean z1) {
        return false;
    }

    public void func_713_a(int i1, int i2, boolean z3) {
        if (z3) {
            this.field_20915_C.func_538_d(i1, i2);
        } else {
            this.field_20915_C.func_539_c(i1, i2);
        }
        if (!z3) {
            this.markBlocksDirty(i1 * 16, 0, i2 * 16, i1 * 16 + 15, 128, i2 * 16 + 15);
        }
    }

    @Override
    public boolean entityJoinedWorld(Entity entity1) {
        boolean z2 = super.entityJoinedWorld(entity1);
        this.field_20914_E.add(entity1);
        if (!z2) {
            this.field_1053_F.add(entity1);
        }
        return z2;
    }

    @Override
    public void setEntityDead(Entity entity1) {
        super.setEntityDead(entity1);
        this.field_20914_E.remove(entity1);
    }

    @Override
    protected void obtainEntitySkin(Entity entity1) {
        super.obtainEntitySkin(entity1);
        if (this.field_1053_F.contains(entity1)) {
            this.field_1053_F.remove(entity1);
        }
    }

    @Override
    protected void releaseEntitySkin(Entity entity1) {
        super.releaseEntitySkin(entity1);
        if (this.field_20914_E.contains(entity1)) {
            this.field_1053_F.add(entity1);
        }
    }

    public void func_712_a(int i1, Entity entity2) {
        Entity entity3 = this.func_709_b(i1);
        if (entity3 != null) {
            this.setEntityDead(entity3);
        }
        this.field_20914_E.add(entity2);
        entity2.entityId = i1;
        if (!this.entityJoinedWorld(entity2)) {
            this.field_1053_F.add(entity2);
        }
        this.field_1055_D.addKey(i1, entity2);
    }

    public Entity func_709_b(int i1) {
        return (Entity)this.field_1055_D.lookup(i1);
    }

    public Entity removeEntityFromWorld(int i1) {
        Entity entity2 = (Entity)this.field_1055_D.removeObject(i1);
        if (entity2 != null) {
            this.field_20914_E.remove(entity2);
            this.setEntityDead(entity2);
        }
        return entity2;
    }

    @Override
    public boolean setBlockMetadata(int i1, int i2, int i3, int i4) {
        int i5 = this.getBlockId(i1, i2, i3);
        int i6 = this.getBlockMetadata(i1, i2, i3);
        if (super.setBlockMetadata(i1, i2, i3, i4)) {
            this.field_1057_z.add(new WorldBlockPositionType(this, i1, i2, i3, i5, i6));
            return true;
        }
        return false;
    }

    @Override
    public boolean setBlockAndMetadata(int i1, int i2, int i3, int i4, int i5) {
        int i6 = this.getBlockId(i1, i2, i3);
        int i7 = this.getBlockMetadata(i1, i2, i3);
        if (super.setBlockAndMetadata(i1, i2, i3, i4, i5)) {
            this.field_1057_z.add(new WorldBlockPositionType(this, i1, i2, i3, i6, i7));
            return true;
        }
        return false;
    }

    @Override
    public boolean setBlock(int i1, int i2, int i3, int i4) {
        int i5 = this.getBlockId(i1, i2, i3);
        int i6 = this.getBlockMetadata(i1, i2, i3);
        if (super.setBlock(i1, i2, i3, i4)) {
            this.field_1057_z.add(new WorldBlockPositionType(this, i1, i2, i3, i5, i6));
            return true;
        }
        return false;
    }

    public boolean func_714_c(int i1, int i2, int i3, int i4, int i5) {
        this.func_711_c(i1, i2, i3, i1, i2, i3);
        if (super.setBlockAndMetadata(i1, i2, i3, i4, i5)) {
            this.notifyBlockChange(i1, i2, i3, i4);
            return true;
        }
        return false;
    }

    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.addToSendQueue(new Packet255KickDisconnect("Quitting"));
    }
}

