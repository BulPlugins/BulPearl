package com.alihaine.bulpearl.listeners;

import com.alihaine.bulpearl.BulPearl;
import com.alihaine.bulpearl.utils.Config;
import com.alihaine.bulpearl.utils.CoolDown;
import com.alihaine.bulpearl.utils.Messages;
import com.alihaine.bulpearl.utils.Reflections;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class OnProjectileLaunch implements Listener {

    private final BulPearl bulPearl = BulPearl.getBulPearl();
    private final CoolDown coolDown = bulPearl.getCoolDown();

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

        if (coolDown.isPlayerOnCoolDown(player.getUniqueId())) {
            Messages.sendMessage(player, Config.getConfigString("messages.on_cooldown").replace("%time%", String.valueOf(coolDown.getCoolDownTimeLeft(player.getUniqueId()))));
            event.setCancelled(true);
            return;
        }

        coolDown.addPlayerCoolDown(player.getUniqueId());
        displayActionBar(player);

        if (Reflections.isAbove1_11Version())
            return;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setCooldown(Material.ENDER_PEARL, coolDownTime * 19);
                cancel();
            }
        }.runTaskTimer(bulPearl, 1, 0);
    }

    private void displayActionBar(final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                long timeLeft = coolDown.getCoolDownTimeLeft(player.getUniqueId());
                Reflections.sendActionBarMessage(player, Config.getConfigString("actionbar.on_cooldown").replace("%time%", String.valueOf(timeLeft)));
                if (timeLeft <= 0) {
                    coolDown.removePlayerCoolDown(player.getUniqueId());
                    Messages.sendMessage(player, Messages.END_COOLDOWN);
                    Reflections.sendActionBarMessage(player, Config.getConfigString("actionbar.end_cooldown"));
                    cancel();
                }
            }
        }.runTaskTimer(bulPearl, 0,10);
    }
}
