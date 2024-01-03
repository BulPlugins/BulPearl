package com.alihaine.bulpearl.listeners;

import com.alihaine.bulpearl.BULpearl;
import com.alihaine.bulpearl.utils.Config;
import com.alihaine.bulpearl.utils.Messages;
import com.alihaine.bulpearl.utils.Reflections;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OnProjectileLaunch implements Listener {

    private final BULpearl buLpearl = BULpearl.getBuLpearl();
    private final HashMap<UUID, Long> coolDownList = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        ArrayList<String> worldBlackList;
        Player player;
        int coolDownTime;

        try {
            player = ((Player) event.getEntity().getShooter());
        } catch (ClassCastException exception) {
            return;
        }

        if (!event.getEntity().getType().equals(EntityType.ENDER_PEARL))
            return ;

        if (player.hasPermission("bulpearl.bypass.cd"))
            return;
        coolDownTime = Config.getConfigInt("cooldown");
        if (coolDownTime <= 0)
            return;
        worldBlackList = new ArrayList<>(Config.getConfigStringList("cooldown_blacklist_world"));
        if (worldBlackList.contains(player.getWorld().getName()))
            return;

        if (coolDownList.containsKey(player.getUniqueId())) {
            Messages.sendMessage(player, Config.getConfigString("messages.on_cooldown").replace("%time%", String.valueOf((coolDownList.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000L)));
            event.setCancelled(true);
            return;
        }

        coolDownList.put(player.getUniqueId(), System.currentTimeMillis() + (coolDownTime * 1000L));
        displayActionBar(player);

        if (Reflections.is1_8Version() || Reflections.is1_9Version())
            return;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setCooldown(Material.ENDER_PEARL, coolDownTime * 16);
                cancel();
            }
        }.runTaskTimer(buLpearl, 1, 0);
    }

    private void displayActionBar(final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                long timeLeft = (coolDownList.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                Reflections.sendActionBarMessage(player, Config.getConfigString("actionbar.on_cooldown").replace("%time%", String.valueOf(timeLeft)));
                if (timeLeft <= 0) {
                    coolDownList.remove(player.getUniqueId());
                    Messages.sendMessage(player, Messages.END_COOLDOWN);
                    Reflections.sendActionBarMessage(player, Config.getConfigString("actionbar.end_cooldown"));
                    cancel();
                }
            }
        }.runTaskTimer(buLpearl, 0,10);
    }
}
