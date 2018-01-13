package MoseShipsSponge.Commands;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;
import MoseShipsSponge.Utils.PermissionUtil;
import MoseShipsSponge.Utils.ShipSignUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class SignCMD implements ShipsCMD.ShipsPlayerCMD {

	public SignCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
				"sign" };
		return args;
	}

	@Override
	public Text getDescription() {
		return Text.of("Sign tools");
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public CommandResult execute(Player player, String... args) throws CommandException {
		if (args.length == 1) {
			player.sendMessage(ShipsMain.formatCMDHelp("/ships sign track [time]"));
			return CommandResult.success();
		} else if (args[1].equalsIgnoreCase("track")) {
			if (args.length == 2) {
				track(player, 3);
			} else {
				try {
					track(player, Integer.parseInt(args[2]));
				} catch (NumberFormatException e) {
					player.sendMessage(ShipsMain.format(args[2] + " is not a whole number", true));
				}
			}
		}
		return CommandResult.empty();
	}
	
	public void switchUser(Player origin, String to) {
		Optional<ProviderRegistration<UserStorageService>> service = Sponge.getServiceManager().getRegistration(UserStorageService.class);
		if(!service.isPresent()) {
			origin.sendMessage(ShipsMain.format("Can not find UserStorageService from Sponge. Can not complete", true));
			return;
		}
		UserStorageService storage = service.get().getProvider();
		UUID uuid = UUID.fromString(to);
		User user = null;
		if(uuid == null) {
			Optional<User> opProfile = storage.get(to);
			if(opProfile.isPresent()) {
				user = opProfile.get();
			}else {
				origin.sendMessage(ShipsMain.format("Unknown player", true));
				return;
			}
		}else {
			Optional<User> opProfile = storage.get(uuid);
			if(opProfile.isPresent()) {
				user = opProfile.get();
			}else {
				origin.sendMessage(ShipsMain.format("Unknown Uniquie ID", true));
				return;
			}
		}
		Optional<LiveShip> opShip = getShip(origin);
		if(!opShip.isPresent()) {
			origin.sendMessage(ShipsMain.format("Can not find a Ship", true));
			return;
		}
		LiveShip ship = opShip.get();
		if((ship.getOwner().isPresent() && ship.getOwner().get().equals(origin)) || origin.hasPermission(PermissionUtil.CHANGE_USER_CMD)) {
			ship.setOwner(user);
			ship.save();
			origin.sendMessage(Text.builder("Ship transferred ownership to " + user.getName()).color(TextColors.AQUA).build());
			if(user.getPlayer().isPresent()) {
				user.getPlayer().get().sendMessage(Text.join(origin.getDisplayNameData().displayName().get(), Text.builder(" has given you ownership to " + ship.getName()).color(TextColors.AQUA).build()));
			}
			return;
		}
	}

	public void track(Player player, int sec) {
		Optional<LiveShip> opShip = getShip(player);
		if(opShip.isPresent()) {
			LiveShip ship = opShip.get();
			final List<Location<World>> structure = ship.getBasicStructure(); 
			structure.stream().forEach(l -> player.sendBlockChange(l.getBlockPosition(), BlockTypes.BEDROCK.getDefaultState()));
			ShipsMain.getPlugin().getGame().getScheduler().createTaskBuilder().delay(sec, TimeUnit.SECONDS).execute(new Runnable(){ 
				@Override 
				public void run() { 
					structure.stream().forEach(l -> player.resetBlockChange(l.getBlockPosition())); 
				} 
			}).submit(ShipsMain.getPlugin().getContainer()); 
		} 
	}

	private Optional<LiveShip> getShip(Player player){
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

}
