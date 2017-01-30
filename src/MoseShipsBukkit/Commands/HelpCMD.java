package MoseShipsBukkit.Commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HelpCMD implements ShipsCMD.ShipsPlayerCMD, ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsBlockCMD {

	public static HelpCMD HELP;

	public HelpCMD() {
		HELP = this;
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
				"help" };
		return args;
	}

	@Override
	public String getDescription() {
		return "Displays all ships commands";
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public boolean execute(ConsoleCommandSender console, String... args) {
		for (ShipsCMD cmd : ShipsCMD.SHIPS_COMMANDS) {
			if (cmd instanceof ShipsCMD.ShipsConsoleCMD) {
				console.sendMessage("/ships " + cmd.getAliases()[0] + ": " + cmd.getDescription());
			}
		}
		return true;
	}

	@Override
	public boolean execute(Player player, String... args) {
		for (ShipsCMD cmd : ShipsCMD.SHIPS_COMMANDS) {
			if (cmd instanceof ShipsCMD.ShipsPlayerCMD) {
				player.sendMessage("/ships " + cmd.getAliases()[0] + ": " + cmd.getDescription());
			}
		}
		return true;
	}

	@Override
	public boolean execute(BlockCommandSender sender, String... args) {
		for (ShipsCMD cmd : ShipsCMD.SHIPS_COMMANDS) {
			if (cmd instanceof ShipsCMD.ShipsBlockCMD) {
				sender.sendMessage("/ships " + cmd.getAliases()[0] + ": " + cmd.getDescription());
			}
		}
		return true;
	}

}
