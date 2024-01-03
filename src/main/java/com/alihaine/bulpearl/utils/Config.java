package com.alihaine.bulpearl.utils;

import com.alihaine.bulpearl.BULpearl;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    private static final BULpearl buLpearl = BULpearl.getBuLpearl();
    private static FileConfiguration config = buLpearl.getConfig();

    public static String getConfigString(String path) {
        return config.getString(path);
    }

    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    public static boolean getConfigBoolean(String path) {
        return config.getBoolean(path);
    }

    public static int getConfigInt(String path) {
        return config.getInt(path);
    }

    public static void reloadConfig() {
        buLpearl.reloadConfig();
        config = buLpearl.getConfig();
    }

}
