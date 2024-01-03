package com.alihaine.bulpearl.commands;

import com.alihaine.bulpearl.BULpearl;
import com.alihaine.bulpearl.craft.CraftManager;
import com.alihaine.bulpearl.utils.Config;
import com.alihaine.bulpearl.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BULpearlCMD implements CommandExecutor {
    private final CraftManager craftManager = BULpearl.getBuLpearl().getCraftManager();

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

        if (strings[0].equalsIgnoreCase("craft"))
            craftManager.craftOpenGui((Player) sender);
        else if (strings[0].equalsIgnoreCase("reload")) {
            Config.reloadConfig();
            Messages.sendMessage((Player) sender, Messages.RELOAD);
        }
        else
            Messages.sendMessage((Player) sender, Messages.ERROR_UNKNOW);
        return true;
    }
}
