/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules;

import MEDMEX.Client;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Combat.AutoAttack;
import MEDMEX.Modules.Combat.AutoHeal;
import MEDMEX.Modules.Combat.CombatLog;
import MEDMEX.Modules.Combat.Velocity;
import MEDMEX.Modules.Exploit.MountForward;
import MEDMEX.Modules.Exploit.PacketCancel;
import MEDMEX.Modules.Exploit.PacketLogger;
import MEDMEX.Modules.Exploit.Selfhit;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Movement.AutoWalk;
import MEDMEX.Modules.Movement.Fly;
import MEDMEX.Modules.Movement.Glide;
import MEDMEX.Modules.Movement.Jesus;
import MEDMEX.Modules.Movement.NoFall;
import MEDMEX.Modules.Movement.NoSlow;
import MEDMEX.Modules.Movement.Noclip;
import MEDMEX.Modules.Movement.Phase;
import MEDMEX.Modules.Movement.Speed;
import MEDMEX.Modules.Movement.Step;
import MEDMEX.Modules.Player.AutoTool;
import MEDMEX.Modules.Player.Automine;
import MEDMEX.Modules.Player.Freecam;
import MEDMEX.Modules.Player.Friends;
import MEDMEX.Modules.Player.Inventory;
import MEDMEX.Modules.Player.Nuker;
import MEDMEX.Modules.Player.Pathfinder;
import MEDMEX.Modules.Player.Scaffold;
import MEDMEX.Modules.Player.SpeedMine;
import MEDMEX.Modules.Player.Yaw;
import MEDMEX.Modules.Visual.ClickGUI;
import MEDMEX.Modules.Visual.ESP;
import MEDMEX.Modules.Visual.Fullbright;
import MEDMEX.Modules.Visual.HUD;
import MEDMEX.Modules.Visual.Hand;
import MEDMEX.Modules.Visual.Nametags;
import MEDMEX.Modules.Visual.PlayerTab;
import MEDMEX.Modules.Visual.Search;
import MEDMEX.Modules.Visual.Tracers;
import MEDMEX.Modules.Visual.Waypoints;
import MEDMEX.Modules.Visual.Xray;
import MEDMEX.Modules.World.AutoTNT;
import MEDMEX.Modules.World.CivBreaker;
import MEDMEX.Modules.World.NewChunks;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {
    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList();

    public void init() {
        this.modules.add(new HUD());
        this.modules.add(new Phase());
        this.modules.add(new Fly());
        this.modules.add(new Speed());
        this.modules.add(new ClickGUI());
        this.modules.add(new Fullbright());
        this.modules.add(new ESP());
        this.modules.add(new Xray());
        this.modules.add(new Jesus());
        this.modules.add(new Freecam());
        this.modules.add(new SpeedMine());
        this.modules.add(new AutoAttack());
        this.modules.add(new Glide());
        this.modules.add(new NoSlow());
        this.modules.add(new NoFall());
        this.modules.add(new MountForward());
        this.modules.add(new PacketLogger());
        this.modules.add(new Selfhit());
        this.modules.add(new Scaffold());
        this.modules.add(new PacketCancel());
        this.modules.add(new Step());
        this.modules.add(new Search());
        this.modules.add(new Friends());
        this.modules.add(new Velocity());
        this.modules.add(new Nametags());
        this.modules.add(new AutoWalk());
        this.modules.add(new Automine());
        this.modules.add(new Inventory());
        this.modules.add(new Nuker());
        this.modules.add(new Tracers());
        this.modules.add(new Hand());
        this.modules.add(new AutoTool());
        this.modules.add(new NewChunks());
        this.modules.add(new Noclip());
        this.modules.add(new AutoHeal());
        this.modules.add(new AutoTNT());
        this.modules.add(new Pathfinder());
        this.modules.add(new PlayerTab());
        this.modules.add(new Yaw());
        this.modules.add(new Waypoints());
        this.modules.add(new CivBreaker());
        this.modules.add(new CombatLog());
    }

    public static CopyOnWriteArrayList<Module> getModules() {
        return Client.moduleManager.modules;
    }

    public Module getModuleByName(String s) {
        for (Module m : this.modules) {
            if (!m.name.equalsIgnoreCase(s)) continue;
            return m;
        }
        return null;
    }

    public static ArrayList<Module> getModulesPerCategory(Category c) {
        ArrayList<Module> modules = new ArrayList<Module>();
        for (Module m : ModuleManager.getModules()) {
            if (m.category != c) continue;
            modules.add(m);
        }
        return modules;
    }

    public int getEnabledModCount() {
        int count = 0;
        for (Module m : Client.moduleManager.getModules()) {
            if (!m.isEnabled) continue;
            ++count;
        }
        return count;
    }
}

