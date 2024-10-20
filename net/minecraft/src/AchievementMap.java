/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap {
    public static AchievementMap field_25210_a = new AchievementMap();
    private Map field_25209_b = new HashMap();

    private AchievementMap() {
        try {
            String string2;
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));
            while ((string2 = bufferedReader1.readLine()) != null) {
                String[] string3 = string2.split(",");
                int i4 = Integer.parseInt(string3[0]);
                this.field_25209_b.put(i4, string3[1]);
            }
        }
        catch (Exception exception5) {
            exception5.printStackTrace();
        }
    }

    public static String func_25208_a(int i0) {
        return (String)AchievementMap.field_25210_a.field_25209_b.get(i0);
    }
}

