/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands;

import MEDMEX.Events.Event;
import net.minecraft.client.Minecraft;

public abstract class Command {
    String name;
    String description;
    String usage;
    String alias;
    public Minecraft mc = Minecraft.theMinecraft;

    public Command(String name, String description, String usage, String alias) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.alias = alias;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public abstract void onCommand(String[] var1);

    public void onEvent(Event e) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

