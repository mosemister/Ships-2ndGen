package MoseShipsSponge.Commands.SCommands;

import java.io.File;
import java.util.Optional;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Commands.Elements.LiveShipFileElement;
import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.PermissionUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class ShipsDebugCommand {
	
	static final Text SHIP = Text.of("Ship");
	
	static class KeyBlockTypeInfo implements CommandExecutor {

		static final Text TYPE = Text.of("Type");
		
		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			if(!(src instanceof ConsoleSource)) {
				src.sendMessage(Text.of("This command is a console only command"));
				return CommandResult.empty();
			}
			Optional<BlockType> opType = args.<BlockType>getOne(TYPE);
			if(!opType.isPresent()) {
				src.sendMessage(Text.of("Unknown blockType, remeber to put minecraft: first. Use tab to help"));
				return CommandResult.empty();
			}
			BlockType type = opType.get();
			type.getAllBlockStates().stream().forEach(s -> {
				src.sendMessage(Text.of("-----[" + s.getId() + "]-----"));
				s.getKeys().stream().forEach(k -> {
					src.sendMessage(Text.of(k.getName() + ": " + s.get(k).get()));
				});
			});
			return CommandResult.success();
		}
		
	}
	
	static class ListVesselType implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			if(src instanceof Player) {
				throw new CommandException(Text.of("This is a console only command currently."));
			}
			src.sendMessage(Text.of(ShipsMain.formatCMDHelp("|-----[Types]-----|")));
			StaticShipType.TYPES.stream().forEach(t -> src.sendMessage(ShipsMain.formatCMDHelp("Name: " + t.getName() + " | Plugin: " + t.getPlugin().getName())));
			return CommandResult.success();
		}
		
	}
	
	static class ListShips implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			if(src instanceof Player) {
				throw new CommandException(Text.of("This is a console only command currently."));
			}
			src.sendMessage(Text.of(ShipsMain.formatCMDHelp("|-----[Ships]-----|")));
			Loader.safeLoadAllShips().stream().forEach(t -> src.sendMessage(ShipsMain.formatCMDHelp("Name: " + t.getName() + " | Type: " + t.getStatic().getName() + " | Owner: " + (t.getOwner().isPresent() ? t.getOwner().get().getName() : "Server") + " | Loaded: " + t.isLoaded())));
			return CommandResult.success();
		}
		
	}
	
	static class LoadShip implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			File shipFile = args.<File>getOne(SHIP).get();
			//EventContext context = EventContext.builder().add(EventContextKeys.PLUGIN, ShipsMain.getPlugin().getContainer()).build();
			//Cause cause = Cause.builder().append(this).build(context);
			
			Optional<OpenRAWLoader> opLoader = Loader.getOpenLoader(shipFile);
			if(!opLoader.isPresent()) {
				src.sendMessage(ShipsMain.format("Could not find the loader for that ship", false));
				return CommandResult.success();
			}
			OpenRAWLoader loader = opLoader.get();
			Optional<LiveShip> opShip = loader.RAWLoad(shipFile);
			if(opShip.isPresent()) {
				src.sendMessage(ShipsMain.format("successfully loaded " + opShip.get().getName() + " with no errors", false));
				return CommandResult.success();
			}
			String error = loader.getError(shipFile);
			throw new CommandException(ShipsMain.format(error, true));
		}
	}
	
	static class Reload implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			BlockList.BLOCK_LIST.reload();
			src.sendMessage(ShipsMain.format("BlockList has reloaded", false));
			return CommandResult.success();
		}
		
	}
	
	public static CommandSpec createCommand() {
		CommandSpec listTypes = CommandSpec.builder().description(Text.of("List all ship types")).executor(new ListVesselType()).permission(PermissionUtil.LIST_TYPES_CMD).build();
		CommandSpec listShips = CommandSpec.builder().description(Text.of("List all ships")).executor(new ListShips()).permission(PermissionUtil.LIST_SHIPS_CMD).build();
		CommandSpec loadShip = CommandSpec.builder().description(Text.of("Manually load ship")).arguments(new LiveShipFileElement(SHIP)).executor(new LoadShip()).permission(PermissionUtil.LOAD_SHIP_CMD).build();
		CommandSpec reload = CommandSpec.builder().description(Text.of("All ship commands for if something goes wrong")).executor(new Reload()).permission(PermissionUtil.RELOAD_CMD).build();
		CommandSpec listKeys = CommandSpec.builder().description(Text.of("List all data keys for BlockType")).arguments(GenericArguments.catalogedElement(KeyBlockTypeInfo.TYPE, BlockType.class)).executor(new KeyBlockTypeInfo()).build();
		return CommandSpec.builder().description(Text.of()).child(listKeys, "listkeys", "keys").child(listTypes, "listtypes", "types", "lit").child(listShips, "listships", "ships", "lis").child(loadShip, "loadship", "load", "lds").child(reload, "reload", "r").build();
	}

}
