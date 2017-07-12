package MoseShipsSponge.Commands;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

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
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug list <type, ship>"));
			source.sendMessage(ShipsMain.formatCMDHelp("/ships debug showStructure <vessel name>"));
			return CommandResult.success();
		} else if (args[1].equalsIgnoreCase("load")) {
			Optional<File> opFile = Loader.getShipFile(args[2]);
			if (args.length > 2) {
				Optional<OpenRAWLoader> opLoader = Loader.getOpenLoader(opFile.get());
				if(opLoader.isPresent()){
					Optional<LiveShip> opShip = opLoader.get().RAWLoad(opFile.get());
					if(opShip.isPresent()){
						Loader.LOADED_SHIPS.add(opShip.get());
						source.sendMessage(ShipsMain.format("Ship is loading correctly", false));
						return CommandResult.success();
					}else{
						String error = opLoader.get().getError(opFile.get());
						source.sendMessage(ShipsMain.format("Ship is failing to load, due to " + error, true));
						return CommandResult.success();
					}
				}else{
					source.sendMessage(ShipsMain.format("Ship is failing to load, due to a issue finding the correct loader. Maybe the loader name is misspelled or a missing loader ships addon plugin?", true));
					return CommandResult.success();
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
					source.sendMessage(ShipsMain.format("Block list has been refreshed", false));
				} else {
					source.sendMessage(ShipsMain.format("Can not find configuration file", true));
				}
			}
		}else if (args[1].equalsIgnoreCase("list")){
			if(args.length == 2){
				run(source, "debug");
			}else if(args[2].equalsIgnoreCase("Type")){
				source.sendMessage(Text.of("|----[Ships]----|"));
				source.sendMessage(Text.of("[ShipsType name] | [Plugin]"));
				StaticShipTypeUtil.getTypes().stream().forEach(type -> source.sendMessage(Text.of(type.getName() + " | " + type.getPlugin().getName())));
				return CommandResult.success();
			}else if (args[2].equalsIgnoreCase("Ships")){
				source.sendMessage(Text.of("|----[Ships]----|"));
				source.sendMessage(Text.of("[Ship name] | [Ship type] | [Location: X] | [Location: Y] | [Location: Z] | [Location: World] | [Structure size]"));
				Loader.safeLoadAllShips().stream().forEach(s -> {
					source.sendMessage(Text.of(
						s.getName() + " | " + s.getStatic().getName() + " | " + s.getLocation().getBlockX()
								+ " | " + s.getLocation().getBlockY() + " | " + s.getLocation().getBlockZ()
								+ " | " + s.getWorld().getName() + " | " + s.getBasicStructure().size()));
				});
				return CommandResult.success();
			}
		}else if(args[1].equalsIgnoreCase("showStructure")) {
			if(!(source instanceof Player)) {
				source.sendMessage(ShipsMain.format("Must be a player", true));
				return CommandResult.success();
			}
			Player player = (Player)source;
			if(args.length > 2) {
				Optional<LiveShip> opShip = Loader.safeLoadShip(args[2]);
				if(!opShip.isPresent()) {
					source.sendMessage(ShipsMain.format(args[2] + " is not a Ship", true));
					return CommandResult.success();
				}
				LiveShip ship = opShip.get();
				ship.getBasicStructure().forEach(l -> player.sendBlockChange(l.getBlockPosition(), BlockTypes.BEDROCK.getDefaultState()));
				Sponge.getScheduler().createTaskBuilder().delay(5, TimeUnit.SECONDS).execute(new Runnable() {

					@Override
					public void run() {
						ship.getBasicStructure().forEach(l -> player.resetBlockChange(l.getBlockPosition()));
					}
					
				}).submit(ShipsMain.getPlugin().getContainer());
			}
		}
		return CommandResult.empty();
	}

}
