package com.alihaine.bulpearl.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnInteract implements Listener {

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if (event.getItem() == null || !event.getItem().getType().equals(Material.ENDER_PEARL))
            return;

        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.getPlayer().launchProjectile(EnderPearl.class);
    }
}
