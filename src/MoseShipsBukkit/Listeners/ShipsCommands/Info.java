package MoseShipsBukkit.Listeners.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.Utils.ConfigLinks.Config;

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
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		sender.sendMessage(getFormat("Version", Ships.getPlugin().getDescription().getVersion()));
		sender.sendMessage(getFormat("Faction Support", "" + config.getBoolean("FactionSupport.enabled")));
		sender.sendMessage(getFormat("WorldGuard Support", "" + config.getBoolean("WorldGuardSupport.enabled")));
		sender.sendMessage(getFormat("WorldBorder Support", "" + config.getBoolean("WorldBorderSupport.enabled")));
		sender.sendMessage(getFormat("Towny Support", "" + config.getBoolean("TownySupport.enabled")));
	}
	
	public static String getFormat(String message, String result){
		String ret = ChatColor.GOLD + "[" + message + "] " + ChatColor.AQUA + result;
		return ret;
	}

}
