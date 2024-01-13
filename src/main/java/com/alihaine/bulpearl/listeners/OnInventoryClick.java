package com.alihaine.bulpearl.listeners;

import com.alihaine.bulpearl.BulPearl;
import com.alihaine.bulpearl.craft.CraftManager;
import com.alihaine.bulpearl.utils.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {

    private final CraftManager craftManager = BulPearl.getBulPearl().getCraftManager();

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equals("§6BULpearl craft"))
            return;
        if (event.getCurrentItem() == null)
            return;

        Material material = event.getCurrentItem().getType();
        if (material.equals(Material.EMERALD_BLOCK) || material.equals(Material.GLASS)) {
            event.setCancelled(true);
            if (!material.equals(Material.EMERALD_BLOCK))
                return;
        }

        if (craftManager.createCraft(craftManager.convertInvToMap(event.getInventory())))
            Messages.sendMessage(p, Messages.CRAFT_CREATED);
        p.closeInventory();
    }
}
