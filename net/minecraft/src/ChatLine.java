/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class ChatLine {
    public String message;
    public int updateCounter;

    public ChatLine(String string1) {
        this.message = string1;
        this.updateCounter = 0;
    }
}

