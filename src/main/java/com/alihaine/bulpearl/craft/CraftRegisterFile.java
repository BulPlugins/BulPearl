package com.alihaine.bulpearl.craft;

import com.alihaine.bulpearl.BULpearl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CraftRegisterFile {
    private final static File file = new File(BULpearl.getBuLpearl().getDataFolder(), "craftregister.yml");
    private final static YamlConfiguration confcraft = YamlConfiguration.loadConfiguration(file);

    public CraftRegisterFile() {
        HashMap<Character, ItemStack> recipeItemStack;
        BULpearl buLpearl = BULpearl.getBuLpearl();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                Bukkit.getConsoleSender().sendMessage("Error with data.yml file" + ioe);
            }
            buLpearl.saveResource("craftregister.yml", false);
        }
        ConfigurationSection configurationSection = confcraft.getConfigurationSection("craft");
        if (configurationSection == null)
            return;
        recipeItemStack = new HashMap<>();
        for (String key : configurationSection.getKeys(false)) {
            recipeItemStack.put(key.charAt(0), configurationSection.getItemStack(key));
        }
        buLpearl.getCraftManager().createCraft(recipeItemStack);
    }

    public static void saveToCraftRegisterFile(HashMap<Character, ItemStack> recipeItemStack) {
        confcraft.set("craft", recipeItemStack);
        try {
            confcraft.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("An error occured when trying to save to craftregister " + e);
        }
    }



}
