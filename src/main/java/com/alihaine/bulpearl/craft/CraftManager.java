package com.alihaine.bulpearl.craft;

import com.alihaine.bulpearl.BULpearl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CraftManager {
    private final BULpearl buLpearl = BULpearl.getBuLpearl();
    private final Inventory craftInventory;

    public CraftManager() {
        craftInventory = Bukkit.createInventory(null, 27, "§6BULpearl craft");

        ItemStack itemstack = new ItemStack(Material.GLASS, 1, (byte)15);
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemstack.setItemMeta(itemMeta);
        final ArrayList<Integer> decoSlots = new ArrayList<>(Arrays.asList(0,1,2,6,7,8,9,10,11,15,17,18,19,20,24,25,26));
        for (final Integer position : decoSlots) {
            craftInventory.setItem(position, itemstack);
        }

        itemstack = new ItemStack(Material.EMERALD_BLOCK, 1);
        itemMeta = itemstack.getItemMeta();
        itemMeta.setDisplayName("§aCreate craft");
        itemstack.setItemMeta(itemMeta);
        craftInventory.setItem(16, itemstack);
    }

    public HashMap<Character, ItemStack> convertInvToMap(Inventory inventory) {
        int buffer = 0;
        HashMap<Character, ItemStack> recipeItemStack = new HashMap<>();
        for (int i = 3; i < 12; i++) {
            if (inventory.getItem(i + buffer) != null)
                recipeItemStack.put((char) (i + 45), inventory.getItem(i + buffer));
            if (i == 5 || i + buffer == 14)
                buffer += 6;
        }
        return recipeItemStack;
    }

    public boolean createCraft(HashMap<Character, ItemStack> recipeItemStack) {
        ShapedRecipe enderPearlRecipe;

        if (recipeItemStack.isEmpty())
            return false;

        enderPearlRecipe = new ShapedRecipe(new ItemStack(Material.ENDER_PEARL));
        enderPearlRecipe.shape("012", "345", "678");

        for (Map.Entry<Character, ItemStack> items : recipeItemStack.entrySet())
            enderPearlRecipe.setIngredient(items.getKey(), items.getValue().getType(), items.getValue().getDurability());
        buLpearl.getServer().addRecipe(enderPearlRecipe);
        CraftRegisterFile.saveToCraftRegisterFile(recipeItemStack);

        return true;
    }

    public void craftOpenGui(Player player) {
        player.openInventory(craftInventory);
    }
}
