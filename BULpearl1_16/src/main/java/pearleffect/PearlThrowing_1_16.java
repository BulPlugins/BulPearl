package pearleffect;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import race.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class PearlThrowing_1_16 implements Listener {


    private final Main cg = Main.getInstance();
    private final long time = cg.getConfig().getLong("cooldown");
    public HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItem(ProjectileLaunchEvent e) {
        Player p = (Player) e.getEntity().getShooter();

        if (!(e.getEntity() instanceof EnderPearl)) {
            return;
        }
        if (cooldown.containsKey(p.getUniqueId())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    long timeleft1 = (cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
                    e.setCancelled(true);
                    ((Player) e.getEntity().getShooter()).setCooldown(Material.ENDER_PEARL, (int) timeleft1 * 20);
                    cancel();
                }
            }.runTaskTimer(cg, 1, 0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemThrowing(PlayerInteractEvent event) {

        Player p = event.getPlayer();
        ArrayList<String> ww = new ArrayList<String>(cg.getConfig().getStringList("cooldown_blacklist_world"));

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (p.getInventory().getItemInMainHand() == null && p.getInventory().getItemInOffHand() == null) {
            return;
        }

        if (p.getInventory().getItemInMainHand().getType() != Material.ENDER_PEARL && p.getInventory().getItemInOffHand().getType() != Material.ENDER_PEARL) {
            return;
        }

        if (p.hasPermission("bulpearl.bypass.cd")) {
            return;
        }

        if (cg.getConfig().getInt("cooldown") == 0) {
            return;
        }

        for (final String wwlist : ww) {
            if (p.getWorld().getName().equalsIgnoreCase(wwlist)) {
                return;
            }
        }

        if (!cooldown.containsKey(p.getUniqueId())) {
            cooldown.put(p.getUniqueId(), System.currentTimeMillis() + (time * 1000));
        } else {
            long timeleft1 = (cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
            if (timeleft1 > 0) {
                if (!cg.getConfig().getString("message.OnCooldown").isEmpty()) {
                    p.sendMessage(cg.getConfig().getString("message.OnCooldown").replace('&', '§').replace("%time%", String.valueOf(timeleft1)));
                }
                event.setCancelled(true);
                p.updateInventory();
                return;
            }
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                if (cooldown.containsKey(p.getUniqueId())) {
                    long timeleft2 = (cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(cg.getConfig().getString("actionbar.OnCooldown").replace('&', '§').replace("%time%", String.valueOf(timeleft2))));

                    if (timeleft2 <= 0) {
                        cooldown.remove(p.getUniqueId());
                        if (!cg.getConfig().getString("message.EndCooldown").isEmpty()) {
                            p.sendMessage(cg.getConfig().getString("message.EndCooldown").replace('&', '§'));
                        }
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(cg.getConfig().getString("actionbar.EndCooldown").replace('&', '§')));
                        cancel();
                    }
                }
            }
        }.runTaskTimer(cg, 0,5);
    }
}

