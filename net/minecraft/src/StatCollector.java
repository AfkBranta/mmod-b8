/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.StringTranslate;

public class StatCollector {
    private static StringTranslate field_25201_a = StringTranslate.getInstance();

    public static String func_25200_a(String string0) {
        return field_25201_a.translateKey(string0);
    }

    public static String func_25199_a(String string0, Object ... object1) {
        return field_25201_a.translateKeyFormat(string0, object1);
    }
}

