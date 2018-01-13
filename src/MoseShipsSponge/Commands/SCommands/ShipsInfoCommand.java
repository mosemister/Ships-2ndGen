package MoseShipsSponge.Commands.SCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.Platform.Component;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Commands.Elements.LiveShipOwnElement;
import MoseShipsSponge.Commands.Elements.ShipTypeElement;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.BlockFinderUtil;
import MoseShipsSponge.Utils.MovementAlgorithmUtil;
import MoseShipsSponge.Utils.PermissionUtil;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class ShipsInfoCommand {
	
	public static final Text SHIP = Text.of("Ship");
	public static final Text SHIP_TYPE = Text.of("ShipType");

	static class BasicInfo implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			String MCVersion = ShipsMain.getPlugin().getGame().getPlatform().getMinecraftVersion().getName();
			int aPIVersion = Integer.parseInt("" + Sponge.getPlatform().getContainer(Component.API).getVersion().get().charAt(0)); 
			String tMCVersion = null;
			List<Integer> testedAPI = new ArrayList<>();
			String tAPIVersion = null;
			String[] shipsVersion = ShipsMain.VERSION.split(Pattern.quote("|"));
			for (String mc : ShipsMain.TESTED_MC) {
				if (tMCVersion == null) {
					tMCVersion = mc;
				} else {
					tMCVersion = tMCVersion + ", " + mc;
				}
			}
			for(int api : ShipsMain.TESTED_API){
				if(tAPIVersion == null){
					tAPIVersion = api + "";
					testedAPI.add(api);
				}else{
					tAPIVersion = tAPIVersion + ", " + api;
					testedAPI.add(api);
				}
			}

			src.sendMessage(ShipsMain.formatCMDHelp("|----[Ships info]----|"));
			src.sendMessage(ShipsMain.formatCMDHelp("Ships Version: " + shipsVersion[0] + "-Sponge"));
			src.sendMessage(ShipsMain.formatCMDHelp("Ships Version Name: " + shipsVersion[1]));
			if(testedAPI.contains(aPIVersion)){
				src.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended API version: ("),
						Text.builder(aPIVersion + "").color(TextColors.GREEN).build(),
						ShipsMain.formatCMDHelp(") " + tAPIVersion)));
			} else {
				src.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended API version: ("),
						Text.builder(aPIVersion + "").color(TextColors.RED).build(), ShipsMain.formatCMDHelp(") " + tAPIVersion)));
			}
			if (Arrays.asList(ShipsMain.TESTED_MC).contains(MCVersion)) {
				src.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended MC version: ("),
						Text.builder(MCVersion).color(TextColors.GREEN).build(),
						ShipsMain.formatCMDHelp(") " + tMCVersion)));
			} else {
				src.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended MC version: ("),
						Text.builder(MCVersion).color(TextColors.RED).build(), ShipsMain.formatCMDHelp(") " + tMCVersion)));
			}
			src.sendMessage(ShipsMain.formatCMDHelp("BlockFinder Algorithm: " + BlockFinderUtil.getConfigSelected().getName()));
			src.sendMessage(ShipsMain.formatCMDHelp("Movement Algorithm: " + MovementAlgorithmUtil.getConfig().getName()));
			return CommandResult.success();
		}
		
	}
	
	static class TypeInfo  implements CommandExecutor {
	
		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			StaticShipType type = args.<StaticShipType>getOne(SHIP_TYPE).get();
			src.sendMessage(ShipsMain.formatCMDHelp("|----Ship Type----|"));
			src.sendMessage(ShipsMain.formatCMDHelp("Name: " + type.getName()));
			src.sendMessage(ShipsMain.formatCMDHelp("Speed: " + type.getDefaultSpeed()));
			src.sendMessage(ShipsMain.formatCMDHelp("Boost: " + type.getBoostSpeed()));
			src.sendMessage(ShipsMain.formatCMDHelp("Altitude: " + type.getAltitudeSpeed()));
			src.sendMessage(ShipsMain.formatCMDHelp("Plugin: " + type.getPlugin().getName()));
			return CommandResult.success();
		}
		
	}
	
	static class ShipInfo  implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			LiveShip ship = args.<LiveShip>getOne(SHIP).get();
			ship.getInfo().entrySet().stream().forEach(e -> src.sendMessage(ShipsMain.formatCMDHelp(e.getKey() + ": " + e.getValue())));
			return CommandResult.success();
		}
		
	}
	
	public static CommandSpec createCommands() {
		CommandSpec type = CommandSpec.builder().executor(new TypeInfo()).arguments(new ShipTypeElement(SHIP_TYPE)).description(Text.of("Info about a ship type")).permission(PermissionUtil.INFO_TYPES_CMD).build();
		CommandSpec ship = CommandSpec.builder().executor(new ShipInfo()).arguments(new LiveShipOwnElement(SHIP, PermissionUtil.INFO_SHIPS_CMD_OTHER)).description(Text.of("info about a ship")).permission(PermissionUtil.INFO_SHIPS_CMD).build();
		return CommandSpec.builder().executor(new BasicInfo()).description(Text.of("Gets Ships plugin info")).permission(PermissionUtil.INFO_CMD).child(type, "type").child(ship, "ship").build();
	}
	
}
