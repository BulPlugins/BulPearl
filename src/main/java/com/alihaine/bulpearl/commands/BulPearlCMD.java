package com.alihaine.bulpearl.commands;

import com.alihaine.bulpearl.BulPearl;
import com.alihaine.bulpearl.craft.CraftManager;
import com.alihaine.bulpearl.utils.Config;
import com.alihaine.bulpearl.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BulPearlCMD implements CommandExecutor {
    private final CraftManager craftManager = BulPearl.getBulPearl().getCraftManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {

        if (!(sender instanceof Player)) {
            Messages.sendMessage(null, Messages.ERROR_ONLY_INGAME);
            return true;
        }

        if (!sender.hasPermission("bulpearl.admin")) {
            Messages.sendMessage((Player) sender, Messages.NO_PERMISSION);
            return true;
        }

        if (strings.length == 0) {
            Messages.sendMessage((Player) sender, Messages.ERROR_UNKNOW);
            return true;
        }

        if (strings[0].equalsIgnoreCase("craft")) {
            if (Config.getConfigBoolean("enable_craft_creator"))
                craftManager.craftOpenGui((Player) sender);
            else
                Messages.sendMessage((Player) sender, Messages.ERROR_CRAFT_CREATOR_DISABLE);

        }
        else if (strings[0].equalsIgnoreCase("reload")) {
            Config.reloadConfig();
            Messages.sendMessage((Player) sender, Messages.RELOAD);
        }
        else
            Messages.sendMessage((Player) sender, Messages.ERROR_UNKNOW);
        return true;
    }
}
