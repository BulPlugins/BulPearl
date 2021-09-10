package pearleffect;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import race.Main;


public class PearlDamage_1_16 implements Listener {

	private Main cg = Main.getInstance();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnderEvent(PlayerTeleportEvent event) {

		double dmg;
		dmg = cg.getConfig().getInt("damage");
		
		Player player = event.getPlayer();
		Location loc = event.getTo();

		if (event.getCause() == TeleportCause.ENDER_PEARL) {

			event.setCancelled(true);

			if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE) && !cg.getConfig().getBoolean("creative_die_from_enderpearl_damage")){
				player.setHealth(player.getHealth() - 0);
				player.teleport(loc);
			} else if (player.getHealth() - dmg > 0) {
				player.setHealth(player.getHealth() - dmg);
				player.teleport(loc);

			} else if (!cg.getConfig().getBoolean("can_kill") || player.hasPermission("bulpearl.bypass.death")) {
				player.setHealth(0.5);
				player.teleport(loc);

			} else {
				player.teleport(loc);
				player.setHealth(0);
			}

			if (!cg.getConfig().getString("message.UsePearl").isEmpty()) {
				player.sendMessage(cg.getConfig().getString("message.UsePearl").replace('&', '§'));
			}

			if (!cg.getConfig().get("sound").equals(false)) {
				try {
					player.playSound(player.getLocation(), Sound.valueOf(cg.getConfig().getString("sound")), 10F, 10F);
				} catch (Exception e) {
					System.out
							.println("BULPearl: An error occured, it seems that the sound you defined does not exist");
					System.out.println("Error detail : ");
					System.out.println(e);
				}
			}
		}
	}
}
