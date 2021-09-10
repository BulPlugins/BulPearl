package manager;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import race.Main;

import java.io.File;
import java.io.IOException;

public class CraftManager_1_15 implements Listener {
    boolean craft = false;
    private Main cg = Main.getInstance();
    public Inventory craftinv = Bukkit.createInventory(null, 27, "§6BULpearl craft");
    final File file = new File(cg.getDataFolder(), "craftregister.yml");
    final YamlConfiguration confcraft = YamlConfiguration.loadConfiguration(file);

    public void CraftInventory(Player player) {

        ItemStack deco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta deco1 = deco.getItemMeta();
        deco1.setDisplayName(" ");
        deco.setItemMeta(deco1);

        ItemStack valid = new ItemStack(Material.GREEN_WOOL);
        ItemMeta valid1 = valid.getItemMeta();
        valid1.setDisplayName("§aCreate craft");
        valid.setItemMeta(valid1);

        craftinv.setItem(0, deco);
        craftinv.setItem(1, deco);
        craftinv.setItem(2, deco);
        craftinv.setItem(6, deco);
        craftinv.setItem(7, deco);
        craftinv.setItem(8, deco);
        craftinv.setItem(9, deco);
        craftinv.setItem(10, deco);
        craftinv.setItem(11, deco);
        craftinv.setItem(15, deco);
        craftinv.setItem(16, valid);
        craftinv.setItem(17, deco);
        craftinv.setItem(18, deco);
        craftinv.setItem(19, deco);
        craftinv.setItem(20, deco);
        craftinv.setItem(24, deco);
        craftinv.setItem(25, deco);
        craftinv.setItem(26, deco);

        player.openInventory(craftinv);
    }

    @EventHandler()
    public void OnInventoryClick(InventoryClickEvent e) throws IOException {

        Player p = (Player) e.getWhoClicked();

        if (!e.getView().getTitle().equals("§6BULpearl craft")) {
            return;
        }

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) || e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
            e.setCancelled(true);
        }
        if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL)) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                cg.saveResource("craftregister.yml", false);
            }

            confcraft.set("craft.a", e.getClickedInventory().getItem(3));
            confcraft.set("craft.b", e.getClickedInventory().getItem(4));
            confcraft.set("craft.c", e.getClickedInventory().getItem(5));
            confcraft.set("craft.d", e.getClickedInventory().getItem(12));
            confcraft.set("craft.e", e.getClickedInventory().getItem(13));
            confcraft.set("craft.f", e.getClickedInventory().getItem(14));
            confcraft.set("craft.g", e.getClickedInventory().getItem(21));
            confcraft.set("craft.h", e.getClickedInventory().getItem(22));
            confcraft.set("craft.i", e.getClickedInventory().getItem(23));

            confcraft.save(file);

            this.createCraft();

            p.closeInventory();
            p.sendMessage("§a[BULpearl] §eYou have created a custom craft for EnderPearl in your server");
        }
    }

    public void createCraft() {
        ItemStack ep = new ItemStack(Material.ENDER_PEARL);
        ShapedRecipe epp = new ShapedRecipe(ep);
        epp.shape("BCD", "PZO", "MKA");

        if (!(confcraft.getItemStack("craft.a") == null)) {
            epp.setIngredient('B', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.a")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.b") == null)) {
            epp.setIngredient('C', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.b")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.c") == null)) {
            epp.setIngredient('D', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.c")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.d") == null)) {
            epp.setIngredient('P', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.d")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.e") == null)) {
            epp.setIngredient('Z', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.e")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.f") == null)) {
            epp.setIngredient('O', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.f")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.g") == null)) {
            epp.setIngredient('M', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.g")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.h") == null)) {
            epp.setIngredient('K', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.h")));
            craft = true;
        }
        if (!(confcraft.getItemStack("craft.i") == null)) {
            epp.setIngredient('A', new RecipeChoice.ExactChoice(confcraft.getItemStack("craft.i")));
            craft = true;
        }

        System.out.println("[BULpearl] Checking if you have created a custom craft for the enderpearl");
        if (craft) {
            cg.getServer().addRecipe(epp);
            System.out.println("[BULpearl] Found custom craft for EnderPearl");
       } else { System.out.println("[BULpearl] You don't have created a custom craft for EnderPearl");
       }
   }
}