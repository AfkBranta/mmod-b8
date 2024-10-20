/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemRecord
extends Item {
    private String recordName;

    protected ItemRecord(int i1, String string2) {
        super(i1);
        this.recordName = string2;
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
        if (world3.getBlockId(i4, i5, i6) == Block.jukebox.blockID && world3.getBlockMetadata(i4, i5, i6) == 0) {
            world3.setBlockMetadataWithNotify(i4, i5, i6, this.shiftedIndex - Item.record13.shiftedIndex + 1);
            world3.playRecord(this.recordName, i4, i5, i6);
            --itemStack1.stackSize;
            return true;
        }
        return false;
    }
}

