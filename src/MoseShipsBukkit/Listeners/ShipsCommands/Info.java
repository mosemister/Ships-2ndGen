package MoseShipsBukkit.Listeners.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.World.Wind.Direction;

public class Info extends CommandLauncher{

	public Info() {
		super("Info", "", "Get info about the ships plugin", null, true, true);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		player.sendMessage(getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		player.sendMessage(getFormat("Faction Support", "" + config.getBoolean("FactionSupport.enabled")));
		player.sendMessage(getFormat("WorldGuard Support", "" + config.getBoolean("WorldGuardSupport.enabled")));
		player.sendMessage(getFormat("WorldBorder Support", "" + config.getBoolean("WorldBorderSupport.enabled")));
		player.sendMessage(getFormat("Towny Support", "" + config.getBoolean("TownySupport.enabled")));
		player.sendMessage(getFormat("Grief Prevention Support", "" + config.getBoolean("GriefPreventionPluginSupport.enabled")));
		player.sendMessage(getFormat("Chairs Reloaded Support", "" + config.getBoolean("ChairsReloadedSupport.enabled")));
		player.sendMessage(getFormat("Vault Support", "" + config.getBoolean("VaultSupport.enabled")));
		player.sendMessage(getFormat("Worlds wind direction", Direction.getDirection(player.getWorld()).getDirection().name()));
		player.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		player.sendMessage(getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		player.sendMessage(getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		player.sendMessage(getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		player.sendMessage(getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		player.sendMessage(getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		player.sendMessage(getFormat("EnderDragon Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		player.sendMessage(getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		player.sendMessage(getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		sender.sendMessage(getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		sender.sendMessage(getFormat("Faction Support", "" + config.getBoolean("FactionSupport.enabled")));
		sender.sendMessage(getFormat("WorldGuard Support", "" + config.getBoolean("WorldGuardSupport.enabled")));
		sender.sendMessage(getFormat("WorldBorder Support", "" + config.getBoolean("WorldBorderSupport.enabled")));
		sender.sendMessage(getFormat("Towny Support", "" + config.getBoolean("TownySupport.enabled")));
		sender.sendMessage(getFormat("Grief Prevention Support", "" + config.getBoolean("GriefPreventionPluginSupport.enabled")));
		sender.sendMessage(getFormat("Chairs Reloaded Support", "" + config.getBoolean("ChairsReloadedSupport.enabled")));
		sender.sendMessage(getFormat("Vault Support", "" + config.getBoolean("VaultSupport.enabled")));
		sender.sendMessage(ChatColor.GOLD + "---[Protected Vessels]---");
		sender.sendMessage(getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.BlockBreak")));
		sender.sendMessage(getFormat("Inventory Protect", "" + config.getBoolean("World.ProtectedVessels.InventoryOpen")));
		sender.sendMessage(getFormat("Fire Protect", "" + config.getBoolean("World.ProtectedVessels.FireProtect2")));
		sender.sendMessage(getFormat("Creeper Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.Creeper")));
		sender.sendMessage(getFormat("TNT Protect", "" + config.getBoolean("World.ProtectedVessels.ExploadeProtect.TNT")));
		sender.sendMessage(getFormat("EnderDragon Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderDragon")));
		sender.sendMessage(getFormat("Wither Protect", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.Wither")));
		sender.sendMessage(getFormat("Block Break", "" + config.getBoolean("World.ProtectedVessels.EntityProtect.EnderMan")));
		sender.sendMessage(ChatColor.GOLD + "---[World Wind Directions]---");
		for(Direction direction : Direction.getDirections()){
			sender.sendMessage(getFormat(direction.getWorld().getName(), direction.getDirection().name()));
		}
		
	}
	
	public static String getFormat(String message, String result){
		String ret = ChatColor.GOLD + "[" + message + "] " + ChatColor.AQUA + result;
		return ret;
	}

}
