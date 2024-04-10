package com.alihaine.bulpearl.utils;

import com.alihaine.bulpearl.BulPearl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public enum Messages {
    USE_PEARL,
    ON_COOLDOWN,
    END_COOLDOWN,
    CRAFT_CREATED,
    RELOAD,
    NO_PERMISSION,
    ERROR_ONLY_INGAME,
    ERROR_UNKNOW;

    private static FileConfiguration config = BulPearl.getBulPearl().getConfig();

    public static void sendMessage(Player p, Messages msg) {
        config = BulPearl.getBulPearl().getConfig();

        List<String> msgList = config.getConfigurationSection(("messages")).getStringList(msg.name().toLowerCase());
        if (msgList.isEmpty()) {
            String value = config.getConfigurationSection(("messages")).getString(msg.name().toLowerCase());
            if (value == null || value.isEmpty())
                return;
            msgList.add(value);
        }
        for (String line : msgList) {
            if (p == null)
                Bukkit.getConsoleSender().sendMessage(line.replace('&', '§'));
            else
                p.sendMessage(line.replace('&', '§'));
        }
        msgList.clear();
    }

    public static void sendMessage(Player p, String msg) {
        if (p == null)
            Bukkit.getConsoleSender().sendMessage(msg.replace('&', '§'));
        else
            p.sendMessage(msg.replace('&', '§'));
    }
}
