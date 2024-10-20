/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Config;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigModules {
    public static File directory = new File("mmod");
    public static File file = new File(String.valueOf(directory.getName()) + File.separator + "moduleConfig.txt");

    public static void saveConfig() {
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("No directory found, directory created!");
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            for (Module m : Client.moduleManager.modules) {
                String str = String.valueOf(m.getName()) + ":";
                str = String.valueOf(str) + m.getEnabled() + ":";
                str = String.valueOf(str) + m.getKeybind() + ":";
                for (Setting s : m.getSettings()) {
                    if (s.isCheck()) {
                        str = String.valueOf(str) + s.getValBoolean() + ":";
                    }
                    if (s.isCombo()) {
                        str = String.valueOf(str) + s.getValString() + ":";
                    }
                    if (s.isSlider()) {
                        str = String.valueOf(str) + s.getValDouble() + ":";
                    }
                    if (!s.isList()) continue;
                    str = String.valueOf(str) + s.getList() + ":";
                }
                String result = str.substring(0, str.length() - 1);
                writer.write(String.valueOf(result) + "\n");
            }
            writer.close();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void loadConfig() {
        if (!file.exists()) {
            return;
        }
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(":");
                Module mod = Client.moduleManager.getModuleByName(data[0]);
                mod.setEnabled(Boolean.valueOf(data[1]));
                mod.setKeybind(Integer.valueOf(data[2]));
                System.out.println(mod);
                int index = 3;
                for (Setting s : mod.getSettings()) {
                    if (s.isCheck()) {
                        s.setValBoolean(Boolean.valueOf(data[index]));
                    }
                    if (s.isCombo()) {
                        s.setValString(data[index]);
                    }
                    if (s.isSlider()) {
                        s.setValDouble(Double.valueOf(data[index]));
                    }
                    if (s.isList()) {
                        String[] splits;
                        if (data[index].equalsIgnoreCase("[]")) continue;
                        String[] stringArray = splits = data[index].replace("[", "").replace("]", "").split(", ");
                        int n = splits.length;
                        int n2 = 0;
                        while (n2 < n) {
                            String split = stringArray[n2];
                            if (s.getClasstype() == "Integer") {
                                s.getList().add(Integer.valueOf(split));
                            }
                            if (s.getClasstype() == "String") {
                                s.getList().add(split);
                            }
                            ++n2;
                        }
                    }
                    ++index;
                }
            }
            reader.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

