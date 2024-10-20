/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenHellLava
extends WorldGenerator {
    private int field_4158_a;

    public WorldGenHellLava(int i1) {
        this.field_4158_a = i1;
    }

    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        if (world1.getBlockId(i3, i4 + 1, i5) != Block.bloodStone.blockID) {
            return false;
        }
        if (world1.getBlockId(i3, i4, i5) != 0 && world1.getBlockId(i3, i4, i5) != Block.bloodStone.blockID) {
            return false;
        }
        int i6 = 0;
        if (world1.getBlockId(i3 - 1, i4, i5) == Block.bloodStone.blockID) {
            ++i6;
        }
        if (world1.getBlockId(i3 + 1, i4, i5) == Block.bloodStone.blockID) {
            ++i6;
        }
        if (world1.getBlockId(i3, i4, i5 - 1) == Block.bloodStone.blockID) {
            ++i6;
        }
        if (world1.getBlockId(i3, i4, i5 + 1) == Block.bloodStone.blockID) {
            ++i6;
        }
        if (world1.getBlockId(i3, i4 - 1, i5) == Block.bloodStone.blockID) {
            ++i6;
        }
        int i7 = 0;
        if (world1.isAirBlock(i3 - 1, i4, i5)) {
            ++i7;
        }
        if (world1.isAirBlock(i3 + 1, i4, i5)) {
            ++i7;
        }
        if (world1.isAirBlock(i3, i4, i5 - 1)) {
            ++i7;
        }
        if (world1.isAirBlock(i3, i4, i5 + 1)) {
            ++i7;
        }
        if (world1.isAirBlock(i3, i4 - 1, i5)) {
            ++i7;
        }
        if (i6 == 4 && i7 == 1) {
            world1.setBlockWithNotify(i3, i4, i5, this.field_4158_a);
            world1.scheduledUpdatesAreImmediate = true;
            Block.blocksList[this.field_4158_a].updateTick(world1, i3, i4, i5, random2);
            world1.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }
}

