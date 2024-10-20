/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityWaterMob;
import net.minecraft.src.IMobs;
import net.minecraft.src.Material;

public enum EnumCreatureType {
    monster(IMobs.class, 70, Material.air, false),
    creature(EntityAnimals.class, 15, Material.air, true),
    waterCreature(EntityWaterMob.class, 5, Material.water, true);

    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;
    private final boolean isPeacefulCreature;

    private EnumCreatureType(Class class3, int i4, Material material5, boolean z6) {
        this.creatureClass = class3;
        this.maxNumberOfCreature = i4;
        this.creatureMaterial = material5;
        this.isPeacefulCreature = z6;
    }

    public Class getCreatureClass() {
        return this.creatureClass;
    }

    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }

    public Material getCreatureMaterial() {
        return this.creatureMaterial;
    }

    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }
}

