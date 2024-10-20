/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemTool
extends Item {
    private Block[] blocksEffectiveAgainst;
    private float efficiencyOnProperMaterial = 4.0f;
    private int damageVsEntity;
    protected EnumToolMaterial toolMaterial;

    protected ItemTool(int i1, int i2, EnumToolMaterial enumToolMaterial3, Block[] block4) {
        super(i1);
        this.toolMaterial = enumToolMaterial3;
        this.blocksEffectiveAgainst = block4;
        this.maxStackSize = 1;
        this.setMaxDamage(enumToolMaterial3.getMaxUses());
        this.efficiencyOnProperMaterial = enumToolMaterial3.getEfficiencyOnProperMaterial();
        this.damageVsEntity = i2 + enumToolMaterial3.getDamageVsEntity();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack1, Block block2) {
        int i3 = 0;
        while (i3 < this.blocksEffectiveAgainst.length) {
            if (this.blocksEffectiveAgainst[i3] == block2) {
                return this.efficiencyOnProperMaterial;
            }
            ++i3;
        }
        return 1.0f;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack1, EntityLiving entityLiving2, EntityLiving entityLiving3) {
        itemStack1.func_25190_a(2, entityLiving3);
        return true;
    }

    @Override
    public boolean func_25008_a(ItemStack itemStack1, int i2, int i3, int i4, int i5, EntityLiving entityLiving6) {
        itemStack1.func_25190_a(1, entityLiving6);
        return true;
    }

    @Override
    public int getDamageVsEntity(Entity entity1) {
        return this.damageVsEntity;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

