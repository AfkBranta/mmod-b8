/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Comparator;
import net.minecraft.src.Entity;
import net.minecraft.src.WorldRenderer;

public class EntitySorter
implements Comparator {
    private Entity entityForSorting;

    public EntitySorter(Entity entity1) {
        this.entityForSorting = entity1;
    }

    public int sortByDistanceToEntity(WorldRenderer worldRenderer1, WorldRenderer worldRenderer2) {
        return worldRenderer1.distanceToEntitySquared(this.entityForSorting) < worldRenderer2.distanceToEntitySquared(this.entityForSorting) ? -1 : 1;
    }

    public int compare(Object object1, Object object2) {
        return this.sortByDistanceToEntity((WorldRenderer)object1, (WorldRenderer)object2);
    }
}

