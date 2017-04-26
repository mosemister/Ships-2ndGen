package MoseShipsBukkit.Commands;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Configs.BlockList;
import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

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
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug list <type, ships>"));
			return true;
		} else if (args[1].equalsIgnoreCase("load")) {
			if (args.length > 2) {
				Optional<File> opFile = Loader.getFileFromName(args[2]);
				if(opFile.isPresent()){
					List<OpenRAWLoader> loaders = Loader.getLoadersFromFile(opFile.get());
					for(OpenRAWLoader loader : loaders){
						Optional<LiveShip> opShip = loader.RAWLoad(opFile.get());
						if(opShip.isPresent()){
							Loader.LOADED_SHIPS.add(opShip.get());
							source.sendMessage(ShipsMain.format("Ship is loading fine", false));
							return true;
						}else{
							String error = loader.getError(opFile.get());
							source.sendMessage(ShipsMain.format("Ship is failing to load, due to " + error, true));
							return true;
						}
					}
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
		} else if (args[1].equalsIgnoreCase("list")) {
			if (args.length == 2) {
				run(source, "debug");
			} else if (args[2].equalsIgnoreCase("Type")) {
				source.sendMessage("|----[Ships]----|");
				source.sendMessage("[ShipType name] | [Plugin]");
				for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
					source.sendMessage(type.getName() + " | " + type.getPlugin().getName());
				}
				return true;
			} else if (args[2].equalsIgnoreCase("Ships")) {
				source.sendMessage("|----[Ships]----|");
				source.sendMessage("[Ship name] | [Ship Type] | [X] | [Y] | [Z] | [world] | [loaded]");
				for (LiveShip ship : Loader.getShips()) {
					source.sendMessage(
							ship.getName() + " | " + ship.getStatic().getName() + " | " + ship.getLocation().getBlockX()
									+ " | " + ship.getLocation().getBlockY() + " | " + ship.getLocation().getBlockZ()
									+ " | " + ship.getWorld().getName() + " | " + ship.isLoaded());
				}
				return true;
			}
		}
		return false;
	}

}
