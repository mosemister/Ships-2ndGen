package MoseShipsBukkit.CMD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import MoseShipsBukkit.CMD.Commands.HelpCMD;

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

	public interface ShipsPlayerTabComplete extends ShipsCMD {
		public List<String> onTab(Player player, String... args);
	}

	public interface ShipsConsoleTabComplete extends ShipsCMD {
		public List<String> onTab(ConsoleCommandSender console, String... args);
	}

	public interface ShipsBlockTabComplete extends ShipsCMD {
		public List<String> onTab(BlockCommandSender sender, String... args);
	}

	public class Executer implements CommandExecutor, TabCompleter {

		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String length, String[] args) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					HelpCMD.HELP.execute((Player) sender, args);
					return true;
				}
				ShipsPlayerCMD cmd2 = null;
				for (ShipsCMD cmd3 : SHIPS_COMMANDS) {
					if (cmd3 instanceof ShipsPlayerCMD) {
						for (String arg : cmd3.getAliases()) {
							if (arg.equalsIgnoreCase(args[0])) {
								cmd2 = (ShipsPlayerCMD) cmd3;
							}
						}
					}
				}
				if (cmd2 != null) {
					Player player = (Player) sender;
					if (cmd2.getPermission() != null) {
						if (player.hasPermission(cmd2.getPermission())) {
							return cmd2.execute(player, args);
						}
					} else {
						return cmd2.execute(player, args);
					}
				}
			} else if (sender instanceof ConsoleCommandSender) {
				if (args.length == 0) {
					HelpCMD.HELP.execute((ConsoleCommandSender) sender, args);
					return true;
				}
				ShipsConsoleCMD cmd2 = null;
				for (ShipsCMD cmd3 : SHIPS_COMMANDS) {
					if (cmd3 instanceof ShipsConsoleCMD) {
						for (String arg : cmd3.getAliases()) {
							if (arg.equalsIgnoreCase(args[0])) {
								cmd2 = (ShipsConsoleCMD) cmd3;
							}
						}
					}
				}
				if (cmd2 != null) {
					return ((ShipsConsoleCMD) cmd2).execute((ConsoleCommandSender) sender, args);
				}
			} else if (sender instanceof BlockCommandSender) {
				if (args.length == 0) {
					HelpCMD.HELP.execute((BlockCommandSender) sender, args);
					return true;
				}
				ShipsBlockCMD cmd2 = null;
				for (ShipsCMD cmd3 : SHIPS_COMMANDS) {
					if (cmd3 instanceof ShipsBlockCMD) {
						for (String arg : cmd3.getAliases()) {
							if (arg.equalsIgnoreCase(args[0])) {
								cmd2 = (ShipsBlockCMD) cmd3;
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

		@Override
		public List<String> onTabComplete(CommandSender sender, Command cmd, String length, String[] args) {
			String target = args[args.length - 1];
			if (sender instanceof Player) {
				if (args.length == 1) {
					List<String> ret = new ArrayList<String>();
					for (ShipsCMD sCmd : SHIPS_COMMANDS) {
						if (sCmd instanceof ShipsPlayerCMD) {
							if (((sCmd.getPermission() != null) && (((Player) sender).hasPermission(sCmd.getPermission()))) || (sCmd.getPermission() == null)) {
								for (String ali : sCmd.getAliases()) {
									if (ali.toLowerCase().startsWith(target.toLowerCase())) {
										ret.add(ali);
									}
								}
							}
						}
					}
					return ret;
				} else {
					for (ShipsCMD sCmd : SHIPS_COMMANDS) {
						if (sCmd instanceof ShipsPlayerTabComplete) {
							ShipsPlayerTabComplete tab = (ShipsPlayerTabComplete) sCmd;
							tab.onTab((Player) sender, args);
						}
					}
				}
			} else if (sender instanceof ConsoleCommandSender) {
				if (args.length == 1) {
					List<String> ret = new ArrayList<String>();
					for (ShipsCMD sCmd : SHIPS_COMMANDS) {
						if (sCmd instanceof ShipsConsoleCMD) {
							for (String ali : sCmd.getAliases()) {
								if (ali.toLowerCase().startsWith(target.toLowerCase())) {
									ret.add(ali);
								}
							}
						}
					}
					return ret;
				} else {
					for (ShipsCMD sCmd : SHIPS_COMMANDS) {
						if (sCmd instanceof ShipsConsoleTabComplete) {
							ShipsConsoleTabComplete tab = (ShipsConsoleTabComplete) sCmd;
							tab.onTab((ConsoleCommandSender) sender, args);
						}
					}
				}
			} else if (sender instanceof BlockCommandSender) {
				if (args.length == 1) {
					List<String> ret = new ArrayList<String>();
					for (ShipsCMD sCmd : SHIPS_COMMANDS) {
						if (sCmd instanceof ShipsBlockCMD) {
							for (String ali : sCmd.getAliases()) {
								if (ali.toLowerCase().startsWith(target.toLowerCase())) {
									ret.add(ali);
								}
							}
						}
					}
					return ret;
				} else {
					for (ShipsCMD sCmd : SHIPS_COMMANDS) {
						if (sCmd instanceof ShipsBlockTabComplete) {
							ShipsBlockTabComplete tab = (ShipsBlockTabComplete) sCmd;
							tab.onTab((BlockCommandSender) sender, args);
						}
					}
				}
			}
			return new ArrayList<String>();
		}

	}

}
