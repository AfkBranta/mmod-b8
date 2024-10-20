/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Events.events;

import MEDMEX.Events.Event;

public class EventChat
extends Event {
    String msg;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EventChat(String msg) {
        this.msg = msg;
    }
}

