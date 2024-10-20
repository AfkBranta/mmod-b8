/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Comparator;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.WorldRenderer;

public class RenderSorter
implements Comparator {
    private EntityLiving field_4274_a;

    public RenderSorter(EntityLiving entityLiving1) {
        this.field_4274_a = entityLiving1;
    }

    public int func_993_a(WorldRenderer worldRenderer1, WorldRenderer worldRenderer2) {
        double d7;
        boolean z3 = worldRenderer1.isInFrustum;
        boolean z4 = worldRenderer2.isInFrustum;
        if (z3 && !z4) {
            return 1;
        }
        if (z4 && !z3) {
            return -1;
        }
        double d5 = worldRenderer1.distanceToEntitySquared(this.field_4274_a);
        return d5 < (d7 = (double)worldRenderer2.distanceToEntitySquared(this.field_4274_a)) ? 1 : (d5 > d7 ? -1 : (worldRenderer1.field_1735_w < worldRenderer2.field_1735_w ? 1 : -1));
    }

    public int compare(Object object1, Object object2) {
        return this.func_993_a((WorldRenderer)object1, (WorldRenderer)object2);
    }
}

