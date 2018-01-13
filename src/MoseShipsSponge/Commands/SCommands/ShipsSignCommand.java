package MoseShipsSponge.Commands.SCommands;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Commands.Elements.LiveShipElement;
import MoseShipsSponge.Commands.Elements.LiveShipOwnElement;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;
import MoseShipsSponge.Utils.PermissionUtil;
import MoseShipsSponge.Utils.ShipSignUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipsSignCommand {
	
	static final Text SHIP = Text.of("Ship Name");
	static final Text TIME = Text.of("Time");
	static final Text NEW_OWNER = Text.of("New Owner");
	
	static class SignTrack implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			if(!(src instanceof Player)){
				src.sendMessage(ShipsMain.format("This is currently a player only command", true));
				return CommandResult.empty();
			}
			Player player = (Player)src;
			int time = args.<Integer>getOne(TIME).orElse(10);
			LiveShip ship = args.<LiveShip>getOne(SHIP).orElse(getShip(player).orElse(null));
			if(ship != null) {
				final List<Location<World>> structure = ship.getBasicStructure(); 
				structure.stream().forEach(l -> player.sendBlockChange(l.getBlockPosition(), BlockTypes.BEDROCK.getDefaultState()));
				ShipsMain.getPlugin().getGame().getScheduler().createTaskBuilder().delay(time, TimeUnit.SECONDS).execute(new Runnable(){ 
					@Override 
					public void run() { 
						structure.stream().forEach(l -> player.resetBlockChange(l.getBlockPosition())); 
					} 
				}).submit(ShipsMain.getPlugin().getContainer()); 
				return CommandResult.affectedBlocks(structure.size());
			} 
			throw new CommandException(ShipsMain.format("Could not find a ship", true));
		}
		
	}
	
	static class ChangeOwner implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			User newOwner = args.<User>getOne(NEW_OWNER).get();
			Optional<LiveShip> opShip = args.getOne(SHIP);
			if(!opShip.isPresent()) {
				if(src instanceof Player) {
					opShip = getShip((Player)src);
					if(opShip.isPresent()) {
						if(!((opShip.get().getOwner().isPresent() && opShip.get().getOwner().get().equals(src)) || src.hasPermission(PermissionUtil.CHANGE_USER_CMD_OTHER))) {
							throw new CommandException(ShipsMain.format("Ship must be owned by you", true));
						}
					}else {
						throw new CommandException(ShipsMain.format("Must be looking at a ships sign or specify a ship name", true));
					}
				}
				src.sendMessage(ShipsMain.format("A ship name must be specified", true));
			}
			LiveShip ship = opShip.get();
			if(!PermissionUtil.hasPermissionToUse(newOwner, ship.getStatic())) {
	 			throw new CommandException(ShipsMain.format("User does not have permission to use that ship type", true));
			}
			ship.setOwner(newOwner);
			src.sendMessage(ShipsMain.format(ship.getName() + " ", false));
			return CommandResult.success();
		}
		
	}
	
	private static Optional<LiveShip> getShip(Player player){
		LiveShip ship = null; 
		BlockRay<World> blockRay =
		 BlockRay.from(player).skipFilter(BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(), 5)).build();
		 while(blockRay.hasNext()){ 
			 BlockRayHit<World> hit = blockRay.next(); 
			 Location<World> loc = hit.getLocation(); 
			 Optional<TileEntity> opTile = loc.getTileEntity();
			 if(!opTile.isPresent()){ 
				 continue;
			 }
			 TileEntity tile = opTile.get(); 
			 if(!(tile instanceof Sign)){ 
				 continue;
			 }
			 Sign sign = (Sign)tile;
			 Optional<ShipSign> opSign = ShipSignUtil.getSign(sign);
			 if(!opSign.isPresent()) {
				 continue;
			 } 
			 ShipSign shipSign = opSign.get();
			 Optional<LiveShip> opShip = Loader.safeLoadShip(shipSign, sign, true); 
			if(!opShip.isPresent()){ 
				continue;
			}
			ship = opShip.get(); 
		 }
		 return Optional.ofNullable(ship);
	}
	
	public static CommandSpec createCommands() {
		CommandSpec track = CommandSpec.builder().description(Text.of("Show the detected area of the ship")).permission(PermissionUtil.TRACK_CMD).executor(new SignTrack()).arguments(GenericArguments.optional(new LiveShipElement(SHIP))).arguments(GenericArguments.optional(GenericArguments.integer(TIME))).build();
		CommandSpec changeUser = CommandSpec.builder().description(Text.of("Change the owner of the ship")).permission(PermissionUtil.CHANGE_USER_CMD).executor(new ChangeOwner()).arguments(GenericArguments.optional(new LiveShipOwnElement(SHIP, PermissionUtil.CHANGE_USER_CMD_OTHER))).arguments(GenericArguments.user(NEW_OWNER)).build();
		return CommandSpec.builder().description(Text.of("All ship commands related to signs")).child(track, "track", "t").child(changeUser, "changeowner", "co").build();
	}

}
