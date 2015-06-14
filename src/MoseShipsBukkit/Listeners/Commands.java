package MoseShipsBukkit.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;

public class Commands implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String length, String[] args) {
		if (sender instanceof Player){
			Player player = (Player)sender;
			if (args.length == 0){
				for(CommandLauncher command : CommandLauncher.getCommands()){
					if (command.isPlayerCommand()){
						if (command.getPermissions() != null){
							if (player.hasPermission(command.getPermissions())){
								player.sendMessage(ChatColor.GOLD + command.getCommand() + " " + command.getExtraArgs() + ChatColor.AQUA + ": " + command.getDescription());
							}
						}else{
							player.sendMessage(ChatColor.GOLD + command.getCommand() + " " + command.getExtraArgs() + ChatColor.AQUA + ": " + command.getDescription());
						}
					}
				}
				return true;
			}else{
				for(CommandLauncher command : CommandLauncher.getCommands()){
					if (args[0].equalsIgnoreCase(command.getCommand())){
						if (command.isPlayerCommand()){
							if (command.getPermissions() == null){
								command.playerCommand(player, args);
								return true;
							}else{
								if (player.hasPermission(command.getPermissions())){
									command.playerCommand(player, args);
									return true;
								}else{
									player.sendMessage(Ships.runShipsMessage("Permission miss-match for " + command.getCommand(), true));
									return true;
								}
							}
						}else{
							player.sendMessage(Ships.runShipsMessage("Sorry but this command " + command.getCommand() + " can not be ran by a player", true));
							return true;
						}
					}
				}
			}
		}else if (sender instanceof ConsoleCommandSender){
			ConsoleCommandSender console = (ConsoleCommandSender)sender;
			if (args.length == 0){
				for(CommandLauncher command : CommandLauncher.getCommands()){
					if (command.isConsoleCommand()){
						console.sendMessage(ChatColor.GOLD + command.getCommand() + ChatColor.AQUA + ": " + command.getDescription());
					}
				}
				return true;
			}else{
				for(CommandLauncher command : CommandLauncher.getCommands()){
					if (args[0].equalsIgnoreCase(command.getCommand())){
						command.consoleCommand(console, args);
					}
				}
				return true;
			}
		}else{
			
		}
		return false;
	}
}
