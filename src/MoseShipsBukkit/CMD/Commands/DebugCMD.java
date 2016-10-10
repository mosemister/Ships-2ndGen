package MoseShipsBukkit.CMD.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Configs.Files.BlockList;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipLoader;
import MoseShipsBukkit.Ships.VesselTypes.Loading.ShipLoadingError;

public class DebugCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public DebugCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
			"debug"
		};
		return args;
	}

	@Override
	public String getDescription() {
		return "All commands that could be usful when attempting to fix a issue";
	}

	@Override
	public String getPermission() {
		return "Ships.command.debug";
	}

	@Override
	public boolean execute(Player player, String... args) {
		return run(player, args);
	}

	@Override
	public boolean execute(ConsoleCommandSender console, String... args) {
		return run(console, args);
	}

	private boolean run(CommandSender source, String... args) {
		if (args.length == 1) {
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug load <vessel name>"));
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug reload <config/materials>"));
			return true;
		} else if (args[1].equalsIgnoreCase("load")) {
			if (args.length > 2) {
				ShipLoadingError error = ShipLoader.getError(args[2]);
				if (error.equals(ShipLoadingError.NOT_CURRUPT)) {
					source.sendMessage(ShipsMain.format("Ship is loading fine", false));
					return true;
				} else {
					source.sendMessage(ShipsMain.format("Ship is failing to load, due to " + error.name(), true));
					return true;
				}
			} else {
				source.sendMessage(ShipsMain.formatCMDHelp("/ships debug load <vessel name>"));
			}
		} else if (args[1].equalsIgnoreCase("reload")) {
			if (args.length > 2) {
				if (args[2].equalsIgnoreCase("config")) {
					ShipsConfig.CONFIG.applyMissing();
					source.sendMessage(ShipsMain.format("Configuration has been refreshed", false));
				} else if (args[2].equalsIgnoreCase("materials")) {
					BlockList.BLOCK_LIST.applyMissing();
					BlockList.BLOCK_LIST.reload();
					source.sendMessage(ShipsMain.format("Block list has been refreshed", false));
				} else {
					source.sendMessage(ShipsMain.format("Can not find configuration file", true));
				}
			}
		}
		return false;
	}

}
