package com.alihaine.bulpearl;

import com.alihaine.bulpearl.commands.BULpearlCMD;
import com.alihaine.bulpearl.craft.CraftManager;
import com.alihaine.bulpearl.craft.CraftRegisterFile;
import com.alihaine.bulpearl.listeners.OnInteract;
import com.alihaine.bulpearl.listeners.OnTeleport;
import com.alihaine.bulpearl.listeners.OnInventoryClick;
import com.alihaine.bulpearl.listeners.OnProjectileLaunch;
import com.alihaine.bulpearl.utils.CoolDown;
import com.alihaine.bulpearl.utils.Reflections;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class BULpearl extends JavaPlugin {

    private static BULpearl buLpearl;
    private CraftManager craftManager;
    private CoolDown coolDown;

    @Override
    public void onEnable() {
        updateChecker();
        buLpearl = this;
        this.saveDefaultConfig();
        new Metrics(this, 20655);

        craftManager = new CraftManager();
        coolDown = new CoolDown();

        this.getCommand("bulpearl").setExecutor(new BULpearlCMD());
        getServer().getPluginManager().registerEvents(new OnTeleport(), this);
        getServer().getPluginManager().registerEvents(new OnProjectileLaunch(), this);
        getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
        if (Reflections.isAbove1_11Version())
            getServer().getPluginManager().registerEvents(new OnInteract(), this);


        new CraftRegisterFile();

        Bukkit.getConsoleSender().sendMessage("[BULpearl] Enable BULpearl");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[BULpearl] Disable BULPearl");
    }

    private void updateChecker() {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=93260").openStream(); Scanner scanner = new Scanner(inputStream)) {
            if (!scanner.next().equals(this.getDescription().getVersion())) {
                Bukkit.getConsoleSender().sendMessage("------------------------------------------------------------------");
                Bukkit.getConsoleSender().sendMessage("There is a new update available for BULPearl !");
                Bukkit.getConsoleSender().sendMessage("Download here : https://www.spigotmc.org/resources/bulpearl-ender-pearl-cooldown-damage-modifier-and-other.93260/updates");
                Bukkit.getConsoleSender().sendMessage("------------------------------------------------------------------");
            }
        } catch (IOException exception) {
            this.getLogger().info("[BULpearl] Cannot look for updates please contact dev in private: " + exception.getMessage());
        }
    }

    public static BULpearl getBuLpearl() {
        return buLpearl;
    }

    public CraftManager getCraftManager() {
        return craftManager;
    }

    public CoolDown getCoolDown() {
        return coolDown;
    }
}