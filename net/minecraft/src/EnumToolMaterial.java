/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public enum EnumToolMaterial {
    WOOD(0, 59, 2.0f, 0),
    STONE(1, 131, 4.0f, 1),
    IRON(2, 250, 6.0f, 2),
    EMERALD(3, 1561, 8.0f, 3),
    GOLD(0, 32, 12.0f, 0);

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiencyOnProperMaterial;
    private final int damageVsEntity;

    private EnumToolMaterial(int i3, int i4, float f5, int i6) {
        this.harvestLevel = i3;
        this.maxUses = i4;
        this.efficiencyOnProperMaterial = f5;
        this.damageVsEntity = i6;
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getEfficiencyOnProperMaterial() {
        return this.efficiencyOnProperMaterial;
    }

    public int getDamageVsEntity() {
        return this.damageVsEntity;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }
}

