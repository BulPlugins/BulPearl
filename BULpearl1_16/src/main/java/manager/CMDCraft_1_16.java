package manager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import race.Main;

public class CMDCraft_1_16 implements CommandExecutor {

    Main cg = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("bulpearl")) {

            if (!(sender.hasPermission("bulpearl.create.craft"))) {
                sender.sendMessage(cg.getConfig().getString("message.createcraft_noperm").replace('&', '§'));
                return false;
            }

            if (args[0].equalsIgnoreCase("craft")) {
                if (!(sender instanceof Player)) {
                    System.out.println("You can't make this command from the console");
                    return false;
                } else {
                    CraftManager_1_16 cm16 = new CraftManager_1_16();
                    cm16.CraftInventory((Player) sender);
                    return true;
                }
            }
        }
        return false;
    }
}
