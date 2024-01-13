package com.alihaine.bulpearl.listeners;

import com.alihaine.bulpearl.utils.Config;
import com.alihaine.bulpearl.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class OnTeleport implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleportEvent(PlayerTeleportEvent event) {
        if (event.getCause() != TeleportCause.ENDER_PEARL)
            return;

        Player player = event.getPlayer();
        event.setCancelled(true);
        player.teleport(event.getTo());
        setPlayerHealth(player);
        Messages.sendMessage(event.getPlayer(), Messages.USE_PEARL);

        if (!Config.getConfigBoolean("sound"))
            return;
        try {
            player.playSound(player.getLocation(), Sound.valueOf(Config.getConfigString("sound")), 10F, 10F);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("BULpearl: An error occured, it seems that the sound you defined does not exist" + e);
        }
    }

    private void setPlayerHealth(Player player) {
        double dmg = Config.getConfigInt("damage");

        if (player.getHealth() - dmg > 0)
            player.setHealth(player.getHealth() - dmg);
        else if (!Config.getConfigBoolean("can_kill") || player.hasPermission("bulpearl.bypass.death") || player.getGameMode().equals(GameMode.CREATIVE))
            player.setHealth(0.5);
        else
            player.setHealth(0);
    }
}
