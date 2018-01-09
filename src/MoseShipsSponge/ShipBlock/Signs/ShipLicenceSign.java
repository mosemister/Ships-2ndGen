package MoseShipsSponge.ShipBlock.Signs;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Configs.ShipsConfig;
import MoseShipsSponge.Event.Create.ShipSignCreateEvent;
import MoseShipsSponge.Event.Create.ShipsCreateEvent;
import MoseShipsSponge.Event.Create.Fail.ShipCreateFailedFromConflictingNames;
import MoseShipsSponge.Event.Create.Fail.ShipCreateFailedFromMissingType;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.PermissionUtil;
import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.AbstractShipsData;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class ShipLicenceSign implements ShipSign {
	
	public ShipLicenceSign(){
		ShipSign.SHIP_SIGNS.add(this);
	}

	@Override
	public void onCreation(ChangeSignEvent event, Player player) {
		SignData signD = event.getText();
		Text line2;
		Text line3;
		Optional<Text> line4 = signD.get(3);
		if ((signD.get(0).isPresent()) && (signD.get(1).isPresent()) && (signD.get(2).isPresent())) {
			line2 = signD.get(1).get();
			line3 = signD.get(2).get();
		} else {
			return;
		}
		Optional<StaticShipType> opShipType = StaticShipTypeUtil.getType(line2.toPlain());
		if (!opShipType.isPresent()) {
			Cause cause = Cause.builder().from(event.getCause()).append(this).build(event.getContext());
			ShipsData data = new AbstractShipsData(line3.toPlain(), event.getTargetTile().getLocation(),
					player.getLocation().getBlockPosition());
			ShipCreateFailedFromMissingType conflictType = new ShipCreateFailedFromMissingType(data, player, line2.toPlain(), cause);
			Sponge.getEventManager().post(conflictType);
			Text message = conflictType.getMessage();
			if (message.toPlain().contains("%Type%")) {
				message = Text.builder(message.toPlain().replace("%Type%", line2.toPlain())).color(message.getColor())
						.style(message.getStyle()).format(message.getFormat()).build();
			}
			if (conflictType.shouldMessageDisplay()) {
				player.sendMessage(message);
			}
			return;
		}

		StaticShipType type = opShipType.get();
		if (!PermissionUtil.hasPermissionToMake(player, type)) {
			player.sendMessage(ShipsMain.format("Missing permission", true));
			return;
		}

		Optional<LiveShip> opConflict = Loader.safeLoadShip(line3);
		if (opConflict.isPresent()) {
			Cause cause = Cause.builder().from(event.getCause()).append(this).build(event.getContext());
			ShipsData data = new AbstractShipsData(line3.toPlain(), event.getTargetTile().getLocation(),
					player.getLocation().getBlockPosition());
			//Cause cause = Cause.builder().named("event", event).named("player", player).named("sign", this).build();
			ShipCreateFailedFromConflictingNames conflictName = new ShipCreateFailedFromConflictingNames(data, player, opConflict.get(), cause);
			Sponge.getEventManager().post(conflictName);
			Text message = conflictName.getMessage();
			if (message.toPlain().contains("%Name%")) {
				message = Text.builder(message.toPlain().replace("%Type%", line2.toPlain())).color(message.getColor())
						.style(message.getStyle()).format(message.getFormat()).build();
			}
			if (message.toPlain().contains("%Owner%")) {
				Optional<User> opOwner = opConflict.get().getOwner();
				if (opOwner.isPresent()) {
					message = Text.builder(message.toPlain().replace("%Owner%", opOwner.get().getName()))
							.color(message.getColor()).style(message.getStyle()).format(message.getFormat()).build();
				} else {
					message = Text.builder(message.toPlain().replace("%Owner%", "<Server>")).color(message.getColor())
							.style(message.getStyle()).format(message.getFormat()).build();
				}
			}
			if (conflictName.shouldMessageDisplay()) {
				player.sendMessage(message);
			}
			return;
		}
		Optional<LiveShip> opShip = type.createVessel(line3.toPlain(), event.getTargetTile().getLocation());
		if (opShip.isPresent()) {
			final LiveShip ship = opShip.get();
			ship.setOwner(player);
			Cause cause = Cause.builder().from(event.getCause()).append(this).build(event.getContext());
			ShipsCreateEvent SCEvent = new ShipSignCreateEvent(ship, cause);
			Sponge.getEventManager().post(SCEvent);
			if (!SCEvent.isCancelled()) {
				ShipsConfig config = ShipsConfig.CONFIG;
				player.sendMessage(ShipsMain.format("Ship created", false));
				ship.load(cause);
				signD.setElement(0, Text.builder("[Ships]").color(TextColors.YELLOW).build());
				signD.setElement(1, Text.builder(type.getName()).color(TextColors.BLUE).build());
				signD.setElement(2, Text.builder(ship.getName()).color(TextColors.GREEN).build());
				if (config.get(Boolean.class, ShipsConfig.PATH_SIGN_FORCE_USERNAME)) {
					signD.setElement(3, Text.builder(player.getName()).color(TextColors.GREEN).build());
				} else if (line4.isPresent()) {
					signD.setElement(3, line4.get().toBuilder().color(TextColors.GREEN).build());
				}
				ship.save();
			}
		}
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		ship.updateBasicStructureOvertime(new Runnable() {

			@Override
			public void run() {
				ship.setBasicStructure(ship.getBasicStructure(), sign.getLocation());
				ship.save();
				player.sendMessage(ShipsMain.format("Ship updated it's structure", false));
			}
			
		});
	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		Map<String, Object> info = ship.getInfo();
		player.sendMessage(ShipsMain.format("Information about " + ship.getName(), false));
		ship.getBasicData().entrySet().stream().forEach(e -> {
			Text left = Text.builder(e.getKey() + ": ").color(TextColors.GOLD).style(TextStyles.BOLD).build();
			Text right = Text.builder(e.getValue()).color(TextColors.AQUA).build();
			player.sendMessage(Text.join(left, right));
		});
		info.entrySet().stream().forEach(e -> {
			Text left = Text.builder(e.getKey() + ": ").color(TextColors.GOLD).style(TextStyles.BOLD).build();
			Text right = Text.builder(e.getValue() + "").color(TextColors.AQUA).build();
			player.sendMessage(Text.join(left, right));
		});
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
	}

	@Override
	public boolean onRemove(Player player, Sign sign) {
		Optional<LiveShip> opShip = getAttachedShip(sign);
		if(!opShip.isPresent()) {
			return true;
		}
		LiveShip ship = opShip.get();
		Optional<User> opOwner = ship.getOwner();
		if(player.hasPermission(PermissionUtil.REMOVE_SHIP_LICENCE)) {
			ship.remove(player);
			return true;
		}
		if(opOwner.isPresent() && (opOwner.get().equals(player))) {
			ship.remove(player);
			return true;
		}
		player.sendMessage(ShipsMain.format("You are not the owner of this ship.", true));
		return false;
	}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[Ships]");
	}

	@Override
	public boolean isSign(Sign sign) {
		SignData data = sign.getSignData();
		Optional<Text> opLine = data.get(0);
		if (opLine.isPresent()) {
			Text line = opLine.get();
			if ((line.toPlain().equals("[Ships]")) && (line.getColor().equals(TextColors.YELLOW))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply(Sign sign) {
	}

}
