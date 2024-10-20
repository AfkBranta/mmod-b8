/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.File;
import java.util.List;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.McRegionChunkLoader;
import net.minecraft.src.SaveHandler;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class SaveOldDir
extends SaveHandler {
    public SaveOldDir(File file1, String string2, boolean z3) {
        super(file1, string2, z3);
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider worldProvider1) {
        File file2 = this.getSaveDirectory();
        if (worldProvider1 instanceof WorldProviderHell) {
            File file3 = new File(file2, "DIM-1");
            file3.mkdirs();
            return new McRegionChunkLoader(file3);
        }
        return new McRegionChunkLoader(file2);
    }

    @Override
    public void saveWorldInfoAndPlayer(WorldInfo worldInfo1, List list2) {
        worldInfo1.setSaveVersion(19132);
        super.saveWorldInfoAndPlayer(worldInfo1, list2);
    }
}

