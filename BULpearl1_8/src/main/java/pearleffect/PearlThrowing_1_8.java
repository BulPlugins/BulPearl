package pearleffect;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import race.Main;
import manager.ActionBarManager_1_8;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class PearlThrowing_1_8 implements Listener {


    private final Main cg = Main.getInstance();
    private final long time = cg.getConfig().getLong("cooldown");
    public HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    ActionBarManager_1_8 abm = new ActionBarManager_1_8();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemThrowing(PlayerInteractEvent event) {

        ArrayList<String> ww = new ArrayList<String>(cg.getConfig().getStringList("cooldown_blacklist_world"));

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player p = event.getPlayer();

        if (p.getInventory().getItemInHand() == null) {
            return;
        }

        if (p.getInventory().getItemInHand().getType() != Material.ENDER_PEARL) {
            return;
        }

        if (cg.getConfig().getBoolean("enderpearl_creative")) {
            if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.getPlayer().setGameMode(GameMode.SURVIVAL);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getPlayer().setGameMode(GameMode.CREATIVE);
                        cancel();
                    }

                }.runTaskTimer(cg, 1, 0);
            }
        }

        if (p.hasPermission("bulpearl.bypass.cd")) {
            return;
        }

        if (cg.getConfig().getInt("cooldown") == 0) {
            return;
        }

        for (final String wwlist : ww) {
            if (p.getWorld().getName().equalsIgnoreCase(wwlist)){
                System.out.println("condition");
                return;
            }
        }

        if (!cooldown.containsKey(p.getUniqueId())) {
            cooldown.put(p.getUniqueId(), System.currentTimeMillis() + (time * 1000));
        } else {
            long timeleft1 = (cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;

            if (timeleft1 > 0) {
                if (!cg.getConfig().getString("message.OnCooldown").isEmpty()) {
                    p.sendMessage(cg.getConfig().getString("message.OnCooldown").replace('&', '§')
                            .replace("%time%", String.valueOf(timeleft1)));
                }
                event.setCancelled(true);
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
