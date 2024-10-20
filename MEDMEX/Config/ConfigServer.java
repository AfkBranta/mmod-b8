/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Config;

import MEDMEX.Client;
import MEDMEX.ServerManager.Server;
import MEDMEX.ServerManager.ServerManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigServer {
    static String filedir = String.valueOf(Client.clientName) + "\\";
    static String configfiledir = String.valueOf(Client.clientName) + "\\servers";
    public static String input = "servers";

    public static void loadConfig() {
        String config = String.valueOf(Client.clientName) + "\\" + input;
        ConfigServer.createFiles();
        try {
            System.out.println(config);
            String account = "";
            BufferedReader reader = new BufferedReader(new FileReader(new File(config)));
            while ((account = reader.readLine()) != null) {
                ServerManager.registry.add(new Server(account));
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        String config = String.valueOf(Client.clientName) + "\\" + input;
        ConfigServer.createFiles();
        try {
            Throwable throwable = null;
            Object var2_4 = null;
            try (FileWriter writer = new FileWriter(new File(config), false);){
                int i = 0;
                while (i < ServerManager.registry.size()) {
                    writer.write(String.valueOf(ServerManager.registry.get(i).getUsername()) + "\n");
                    ++i;
                }
                writer.flush();
                writer.close();
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void createFiles() {
        if (!new File(filedir).exists()) {
            new File(filedir).mkdir();
        }
        if (!new File(configfiledir).exists()) {
            try {
                new File(configfiledir).createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

