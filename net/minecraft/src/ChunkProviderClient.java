/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.EmptyChunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.World;

public class ChunkProviderClient
implements IChunkProvider {
    private Chunk blankChunk;
    private Map chunkMapping = new HashMap();
    private List field_889_c = new ArrayList();
    private World worldObj;

    public ChunkProviderClient(World world1) {
        this.blankChunk = new EmptyChunk(world1, new byte[32768], 0, 0);
        this.worldObj = world1;
    }

    @Override
    public boolean chunkExists(int i1, int i2) {
        ChunkCoordIntPair chunkCoordIntPair3 = new ChunkCoordIntPair(i1, i2);
        return this.chunkMapping.containsKey(chunkCoordIntPair3);
    }

    public void func_539_c(int i1, int i2) {
        Chunk chunk3 = this.provideChunk(i1, i2);
        if (!chunk3.func_21167_h()) {
            chunk3.onChunkUnload();
        }
        this.chunkMapping.remove(new ChunkCoordIntPair(i1, i2));
        this.field_889_c.remove(chunk3);
    }

    @Override
    public Chunk func_538_d(int i1, int i2) {
        ChunkCoordIntPair chunkCoordIntPair3 = new ChunkCoordIntPair(i1, i2);
        byte[] b4 = new byte[32768];
        Chunk chunk5 = new Chunk(this.worldObj, b4, i1, i2);
        Arrays.fill(chunk5.skylightMap.data, (byte)-1);
        this.chunkMapping.put(chunkCoordIntPair3, chunk5);
        chunk5.isChunkLoaded = true;
        return chunk5;
    }

    @Override
    public Chunk provideChunk(int i1, int i2) {
        ChunkCoordIntPair chunkCoordIntPair3 = new ChunkCoordIntPair(i1, i2);
        Chunk chunk4 = (Chunk)this.chunkMapping.get(chunkCoordIntPair3);
        return chunk4 == null ? this.blankChunk : chunk4;
    }

    @Override
    public boolean saveChunks(boolean z1, IProgressUpdate iProgressUpdate2) {
        return true;
    }

    @Override
    public boolean func_532_a() {
        return false;
    }

    @Override
    public boolean func_536_b() {
        return false;
    }

    @Override
    public void populate(IChunkProvider iChunkProvider1, int i2, int i3) {
    }

    @Override
    public String toString() {
        return "MultiplayerChunkCache: " + this.chunkMapping.size();
    }
}

