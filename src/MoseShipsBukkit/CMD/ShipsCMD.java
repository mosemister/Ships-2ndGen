package MoseShipsBukkit.CMD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface ShipsCMD {

	public static final List<ShipsCMD> SHIPS_COMMANDS = new ArrayList<ShipsCMD>();

	public String[] getAliases();

	public String getDescription();

	public String getPermission();

	public interface ShipsPlayerCMD extends ShipsCMD {
		public boolean execute(Player player, String... args);
	}

	public interface ShipsConsoleCMD extends ShipsCMD {
		public boolean execute(ConsoleCommandSender console, String... args);
	}
	public interface ShipsBlockCMD extends ShipsCMD {
		public boolean execute(BlockCommandSender sender, String... args);
	}

	public class Executer implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String length, String[] args) {
			if (sender instanceof Player) {
				ShipsPlayerCMD cmd2 = null;
				for(ShipsCMD cmd3 : SHIPS_COMMANDS){
					if(cmd3 instanceof ShipsPlayerCMD){
						for(String arg : cmd3.getAliases()){
							if(arg.equalsIgnoreCase(args[0])){
								cmd2 = (ShipsPlayerCMD)cmd3;
							}
						}
					}
				}
				if (cmd2 != null) {
					Player player = (Player) sender;
					if (cmd2.getPermission() != null) {
						if (player.hasPermission(cmd.getPermission())) {
							return cmd2.execute(player, args);
						}
					} else {
						return cmd2.execute(player, args);
					}
				}
			} else if (sender instanceof ConsoleCommandSender) {
				ShipsConsoleCMD cmd2 = null;
				for(ShipsCMD cmd3 : SHIPS_COMMANDS){
					if(cmd3 instanceof ShipsConsoleCMD){
						for(String arg : cmd3.getAliases()){
							if(arg.equalsIgnoreCase(args[0])){
								cmd2 = (ShipsConsoleCMD)cmd3;
							}
						}
					}
				}
				if (cmd2 != null) {
					return ((ShipsConsoleCMD) cmd2).execute((ConsoleCommandSender) sender, args);
				}
			} else if (sender instanceof BlockCommandSender) {
				ShipsBlockCMD cmd2 = null;
				for(ShipsCMD cmd3 : SHIPS_COMMANDS){
					if(cmd3 instanceof ShipsBlockCMD){
						for(String arg : cmd3.getAliases()){
							if(arg.equalsIgnoreCase(args[0])){
								cmd2 = (ShipsBlockCMD)cmd3;
							}
						}
					}
				}
				if (cmd2 != null) {
					return ((ShipsConsoleCMD) cmd2).execute((ConsoleCommandSender) sender, args);
				}
			} else {
				new IOException("The source " + sender + " is not regonised.");
			}
			return false;
		}

	}

}
