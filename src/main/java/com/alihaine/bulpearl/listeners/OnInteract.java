package com.alihaine.bulpearl.listeners;

import com.alihaine.bulpearl.BulPearl;
import com.alihaine.bulpearl.utils.CoolDown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


//This class are loaded only for the versions above 1.11
public class OnInteract implements Listener {

    private final CoolDown coolDown = BulPearl.getBulPearl().getCoolDown();

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if (event.getItem() == null || !event.getItem().getType().equals(Material.ENDER_PEARL))
            return;

        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.getPlayer().launchProjectile(EnderPearl.class);
        else if (coolDown.isPlayerOnCoolDown(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }
}
