package pearleffect;

import manager.ActionBarManager_1_9;
import net.minecraft.server.v1_9_R2.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
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


public class PearlThrowing_1_9 implements Listener {


    private final Main cg = Main.getInstance();
    private final long time = cg.getConfig().getLong("cooldown");
    public HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    ActionBarManager_1_9 abm = new ActionBarManager_1_9();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItem(ProjectileLaunchEvent e) {
        Player p = (Player) e.getEntity().getShooter();
        if (e.getEntity() instanceof EnderPearl) {
            if (cooldown.containsKey(p.getUniqueId())) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        long timeleft1 = (cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
                        e.setCancelled(true);
                        ((CraftPlayer) e.getEntity().getShooter()).getHandle().a(Item.getById(368), (int) timeleft1);
                        cancel();
                    }
                }.runTaskTimer(cg, 1, 0);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemThrowing(PlayerInteractEvent event) {

        Player p = event.getPlayer();
        ArrayList<String> ww = new ArrayList<String>(cg.getConfig().getStringList("cooldown_blacklist_world"));

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (p.getInventory().getItemInHand() == null && p.getInventory().getItemInHand() == null) {
            return;
        }

        if (p.getInventory().getItemInHand().getType() != Material.ENDER_PEARL && p.getInventory().getItemInHand().getType() !=Material.ENDER_PEARL) {
            return;
        }

        if (p.hasPermission("bulpearl.bypass.cd")) {
            return;
        }

        if (cg.getConfig().getInt("cooldown") == 0) {
            return;
        }

        for (final String wwlist : ww) {
            if (p.getWorld().getName().equalsIgnoreCase(wwlist)){
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
                    abm.sendActionBar(p, cg.getConfig().getString("actionbar.OnCooldown").replace('&', '§').replace("%time%", String.valueOf(timeleft2)));

                    if (timeleft2 <= 0) {
                        cooldown.remove(p.getUniqueId());
                        if (!cg.getConfig().getString("message.EndCooldown").isEmpty()) {
                            p.sendMessage(cg.getConfig().getString("message.EndCooldown").replace('&', '§'));
                        }
                        abm.sendActionBar(p, cg.getConfig().getString("actionbar.EndCooldown").replace('&', '§'));
                        cancel();
                    }
                }
            }
        }.runTaskTimer(cg, 0,5);


    }
}
