package org.ships.event.commandsNew.executor.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ships.configuration.Config;
import org.ships.event.commandsNew.argument.SingleCatagoryArgument;
import org.ships.event.commandsNew.executor.CMDArgumentResults;
import org.ships.event.commandsNew.executor.CMDExecutor;
import org.ships.plugin.Ships;

public class ShipsInfoCommand extends CMDExecutor {
	
	public ShipsInfoCommand() {
		super(new SingleCatagoryArgument("info"));
	}

	@Override
	public boolean onExecute(CommandSender sender, CMDArgumentResults results) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		sender.sendMessage(ShipsInfoCommand.getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		sender.sendMessage(ShipsInfoCommand.getFormat("Ships track limit", config.getInt("Structure.StructureLimits.trackLimit")));
		sender.sendMessage(ShipsInfoCommand.getFormat("Submarine air check limit", config.getInt("Structure.StructureLimits.airCheckGap")));
		sender.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		sender.sendMessage(ShipsInfoCommand.getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		sender.sendMessage(ShipsInfoCommand.getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		sender.sendMessage(ShipsInfoCommand.getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		sender.sendMessage(ShipsInfoCommand.getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		sender.sendMessage(ShipsInfoCommand.getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		sender.sendMessage(ShipsInfoCommand.getFormat("EnderDragon Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		sender.sendMessage(ShipsInfoCommand.getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		sender.sendMessage(ShipsInfoCommand.getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
		return true;
	}

	private static String getFormat(String message, Object result) {
		String ret = ChatColor.GOLD + "[" + message + "] " + ChatColor.AQUA + result;
		return ret;
	}
}
