/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPig;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.EntityZombieSimple;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityList {
    private static Map stringToClassMapping = new HashMap();
    private static Map classToStringMapping = new HashMap();
    private static Map IDtoClassMapping = new HashMap();
    private static Map classToIDMapping = new HashMap();

    static {
        EntityList.addMapping(EntityArrow.class, "Arrow", 10);
        EntityList.addMapping(EntitySnowball.class, "Snowball", 11);
        EntityList.addMapping(EntityItem.class, "Item", 1);
        EntityList.addMapping(EntityPainting.class, "Painting", 9);
        EntityList.addMapping(EntityLiving.class, "Mob", 48);
        EntityList.addMapping(EntityMobs.class, "Monster", 49);
        EntityList.addMapping(EntityCreeper.class, "Creeper", 50);
        EntityList.addMapping(EntitySkeleton.class, "Skeleton", 51);
        EntityList.addMapping(EntitySpider.class, "Spider", 52);
        EntityList.addMapping(EntityZombieSimple.class, "Giant", 53);
        EntityList.addMapping(EntityZombie.class, "Zombie", 54);
        EntityList.addMapping(EntitySlime.class, "Slime", 55);
        EntityList.addMapping(EntityGhast.class, "Ghast", 56);
        EntityList.addMapping(EntityPigZombie.class, "PigZombie", 57);
        EntityList.addMapping(EntityPig.class, "Pig", 90);
        EntityList.addMapping(EntitySheep.class, "Sheep", 91);
        EntityList.addMapping(EntityCow.class, "Cow", 92);
        EntityList.addMapping(EntityChicken.class, "Chicken", 93);
        EntityList.addMapping(EntitySquid.class, "Squid", 94);
        EntityList.addMapping(EntityWolf.class, "Wolf", 95);
        EntityList.addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        EntityList.addMapping(EntityFallingSand.class, "FallingSand", 21);
        EntityList.addMapping(EntityMinecart.class, "Minecart", 40);
        EntityList.addMapping(EntityBoat.class, "Boat", 41);
    }

    private static void addMapping(Class class0, String string1, int i2) {
        stringToClassMapping.put(string1, class0);
        classToStringMapping.put(class0, string1);
        IDtoClassMapping.put(i2, class0);
        classToIDMapping.put(class0, i2);
    }

    public static Entity createEntityInWorld(String string0, World world1) {
        Entity entity2 = null;
        try {
            Class class3 = (Class)stringToClassMapping.get(string0);
            if (class3 != null) {
                entity2 = (Entity)class3.getConstructor(World.class).newInstance(world1);
            }
        }
        catch (Exception exception4) {
            exception4.printStackTrace();
        }
        return entity2;
    }

    public static Entity createEntityFromNBT(NBTTagCompound nBTTagCompound0, World world1) {
        Entity entity2 = null;
        try {
            Class class3 = (Class)stringToClassMapping.get(nBTTagCompound0.getString("id"));
            if (class3 != null) {
                entity2 = (Entity)class3.getConstructor(World.class).newInstance(world1);
            }
        }
        catch (Exception exception4) {
            exception4.printStackTrace();
        }
        if (entity2 != null) {
            entity2.readFromNBT(nBTTagCompound0);
        } else {
            System.out.println("Skipping Entity with id " + nBTTagCompound0.getString("id"));
        }
        return entity2;
    }

    public static Entity createEntity(int i0, World world1) {
        Entity entity2 = null;
        try {
            Class class3 = (Class)IDtoClassMapping.get(i0);
            if (class3 != null) {
                entity2 = (Entity)class3.getConstructor(World.class).newInstance(world1);
            }
        }
        catch (Exception exception4) {
            exception4.printStackTrace();
        }
        if (entity2 == null) {
            System.out.println("Skipping Entity with id " + i0);
        }
        return entity2;
    }

    public static int getEntityID(Entity entity0) {
        return (Integer)classToIDMapping.get(entity0.getClass());
    }

    public static String getEntityString(Entity entity0) {
        return (String)classToStringMapping.get(entity0.getClass());
    }
}

