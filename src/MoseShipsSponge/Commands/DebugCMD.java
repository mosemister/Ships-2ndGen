package MoseShipsSponge.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Plugin.ShipsMain;

public class DebugCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public DebugCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
				"debug" };
		return args;
	}

	@Override
	public Text getDescription() {
		return Text.of("All commands that could be usful when attempting to fix a issue");
	}

	@Override
	public String getPermission() {
		return "Ships.command.debug";
	}

	@Override
	public CommandResult execute(Player player, String... args) throws CommandException {
		return run(player, args);
	}

	@Override
	public CommandResult execute(ConsoleSource console, String... args) throws CommandException {
		return run(console, args);
	}

	private CommandResult run(CommandSource source, String... args) {
		if (args.length == 1) {
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug load <vessel name>"));
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug reload <config/materials>"));
			return CommandResult.success();
		} else if (args[1].equalsIgnoreCase("load")) {
			if (args.length > 2) {
				/*ShipLoadingError error = ShipLoader.getError(args[2]);
				if (error.equals(ShipLoadingError.NOT_CURRUPT)) {
					source.sendMessage(ShipsMain.format("Ship is loading fine", false));
					return CommandResult.success();
				} else {
					source.sendMessage(ShipsMain.format("Ship is failing to load, due to " + error.name(), true));
					return CommandResult.success();
				}*/
			} else {
				source.sendMessage(ShipsMain.formatCMDHelp("/ships debug load <vessel name>"));
			}
		} else if (args[1].equalsIgnoreCase("reload")) {
			if (args.length > 2) {
				if (args[2].equalsIgnoreCase("config")) {
					System.out.println("t");
					ShipsConfig.CONFIG.applyMissing();
					source.sendMessage(ShipsMain.format("Configuration has been refreshed", false));
				} else if (args[2].equalsIgnoreCase("materials")) {
					BlockList.BLOCK_LIST.applyMissing();
					source.sendMessage(ShipsMain.format("Block list has been refreshed", false));
				} else {
					source.sendMessage(ShipsMain.format("Can not find configuration file", true));
				}
			}
		}
		return CommandResult.empty();
	}

}
