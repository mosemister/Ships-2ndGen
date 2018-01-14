package MoseShipsSponge.Commands.SCommands;

import java.util.Optional;

import org.spongepowered.api.block.tileentity.CommandBlock;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Commands.Elements.LiveShipOwnElement;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.PermissionUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipsTeleportCommand {
	
	public static class BaseTeleport implements CommandExecutor {

		private static Text SHIP = Text.of("Ship");
		
		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			LiveShip ship = args.<LiveShip>getOne(SHIP).get();
			if(!(src instanceof Player)) {
				src.sendMessage(Text.of("Player only command"));
				return CommandResult.empty();
			}
			Player player = (Player)src;
			if(!player.hasPermission(PermissionUtil.TELEPORT_CMD_OTHER)) {
				return forceTeleport(ship, player);
			}
			if(!ship.getOwner().isPresent()) {
				throw new CommandException(Text.of("Ship is not owned."));
			}
			if(ship.getOwner().get().equals(player)) {
				return forceTeleport(ship, player);
			}
			return null;
		}
		
		private CommandResult forceTeleport(LiveShip ship, Player player) {
			player.setLocationSafely(ship.getTeleportToLocation());
			return CommandResult.success();
		}
		
	}
	
	public static class SetLocation implements CommandExecutor {

		static final Text SHIP = Text.of("ship");
		static final Text LOC = Text.of("location");
		
		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			Optional<LiveShip> opShip = args.getOne(SHIP);
			Optional<Location<World>> opLoc = args.getOne(LOC);
			LiveShip ship = null;
			if(opShip.isPresent()) {
				ship = opShip.get();
			} else if ((src instanceof Player) && (src instanceof CommandBlock)) {
				Location<World> loc = null;
				if(src instanceof Player) {
					loc = ((Player)src).getLocation().getRelative(Direction.DOWN);
				} else {
					loc = ((CommandBlock)src).getLocation();
				}
				opShip = Loader.safeLoadShip(loc, false);
				if(opShip.isPresent()) {
					ship = opShip.get();
				}
			}
			if(ship == null) {
				throw new CommandException(Text.of("Can not find Ship. Specify it"));
			}
			Location<World> loc = null;
			if(opLoc.isPresent()) {
				loc = opLoc.get();
			}else if(src instanceof Player) {
				loc = ((Player)src).getLocation();
			}else if(src instanceof CommandBlock) {
				CommandBlock block = (CommandBlock)src;
				loc = block.getLocation().getRelative(Direction.UP);
			}
			if(loc == null) {
				throw new CommandException(Text.of("Can not find location"));
			}
			final LiveShip ship2 = ship;
			final Location<World> loc2 = loc;
			ship.updateBasicStructureOvertime(new Runnable() {

				@Override
				public void run() {
					if(!ship2.hasLocation(loc2)) {
						src.sendMessage(ShipsMain.format("Location is not part of the ship.", true));
					}	
					ship2.setTeleportToLocation(loc2);
					src.sendMessage(ShipsMain.format("Ships updated its teleport location", false));
				}
				
			});
			return CommandResult.success();
		}
	}
	
	public static CommandSpec createCommands() {
		CommandSpec setTeleport = CommandSpec.builder().arguments(GenericArguments.optional(new LiveShipOwnElement(SetLocation.SHIP)), GenericArguments.optional(GenericArguments.location(SetLocation.LOC))).executor(new SetLocation()).permission(PermissionUtil.SET_TELEPORT_CMD).description(Text.of("Set the teleport location of a ship")).build(); 
		return CommandSpec.builder().child(setTeleport, "setTeleport", "set").arguments(new LiveShipOwnElement(BaseTeleport.SHIP, PermissionUtil.TELEPORT_CMD_OTHER)).executor(new BaseTeleport()).description(Text.of("Teleport to ship")).permission(PermissionUtil.TELEPORT_CMD).build();
	}

}
