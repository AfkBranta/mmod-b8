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

public class ItemSword
extends Item {
    private int weaponDamage;

    public ItemSword(int i1, EnumToolMaterial enumToolMaterial2) {
        super(i1);
        this.maxStackSize = 1;
        this.setMaxDamage(enumToolMaterial2.getMaxUses());
        this.weaponDamage = 4 + enumToolMaterial2.getDamageVsEntity() * 2;
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack1, Block block2) {
        return 1.5f;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack1, EntityLiving entityLiving2, EntityLiving entityLiving3) {
        itemStack1.func_25190_a(1, entityLiving3);
        return true;
    }

    @Override
    public boolean func_25008_a(ItemStack itemStack1, int i2, int i3, int i4, int i5, EntityLiving entityLiving6) {
        itemStack1.func_25190_a(2, entityLiving6);
        return true;
    }

    @Override
    public int getDamageVsEntity(Entity entity1) {
        return this.weaponDamage;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

