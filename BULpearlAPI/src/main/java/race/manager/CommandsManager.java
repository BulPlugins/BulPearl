package race.manager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import race.Main;

public class CommandsManager implements CommandExecutor {
	
	private Main cg = Main.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args0) {

		if (args0.length == 1) {

			if (label.equalsIgnoreCase("bulpearl")) {
				
				if (!(sender.hasPermission("bulpearl.reload"))) {
					sender.sendMessage(cg.getConfig().getString("message.reload_noperm").replace('&', '§'));
					return false;
				}
				
				if (args0[0].equalsIgnoreCase("reload") || args0[0].equalsIgnoreCase("rl")) {
					if (sender instanceof Player) {
						sender.sendMessage(cg.getConfig().getString("message.reload").replace('&', '§'));
	                    Bukkit.getPluginManager().disablePlugin(Main.getInstance());
	                    Bukkit.getPluginManager().getPlugin("BULpearl").reloadConfig();
	                    Bukkit.getPluginManager().enablePlugin(Main.getInstance());
						return true;
					} else {
						System.out.println(cg.getConfig().getString("message.reload").replace('&', '§'));
	                    Bukkit.getPluginManager().disablePlugin(Main.getInstance());
	                    Bukkit.getPluginManager().getPlugin("BULpearl").reloadConfig();
	                    Bukkit.getPluginManager().enablePlugin(Main.getInstance());
						return true;
					}
				}
			}

		}
		
		if (args0.length == 0) {
			sender.sendMessage("§a[BULpearl]§c Command cannot be found");
		}
		return false;
	}
}
