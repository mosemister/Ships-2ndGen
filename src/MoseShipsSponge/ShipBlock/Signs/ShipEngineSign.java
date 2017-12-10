package MoseShipsSponge.ShipBlock.Signs;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;

import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;

public class ShipEngineSign implements ShipSign {

	public ShipEngineSign(){
		ShipSign.SHIP_SIGNS.add(this);
	}
	
	@Override
	public void onCreation(ChangeSignEvent event, Player player) {
		SignData data = event.getText();
		data.setElement(0, Text.builder("[Engine]").color(TextColors.YELLOW).build());
		data.setElement(2, Text.of(2));
		Optional<LiveShip> opShip = Loader.safeLoadShip(event.getTargetTile().getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			data.setElement(2, Text.of(speed));
			data.setElement(1, Text.of(speed - 1));
		}
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		if (!data.get(2).isPresent()) {
			apply(sign);
		}
		try {
			int value = Integer.parseInt(data.get(2).get().toPlain());
			// Direction signDirection =
			// sign.get(Keys.DIRECTION).get().getOpposite();
			StaticShipType type = ship.getStatic();
			if (type.getDefaultSpeed() > value) {
				data.setElement(2, Text.of(value + 1));
			} else if (type.getBoostSpeed() > value) {
				// needs worldUtil to be imported
				data.setElement(2, Text.of(value + 1));
			} else if (type.getBoostSpeed() == value) {
				data.setElement(2, Text.of(1));
			} else {
				player.sendMessage(ShipsMain.format("Unknown error", true));
			}
			sign.offer(data);
		} catch (NumberFormatException e) {
			apply(sign);
		}
	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		SignData data = sign.getSignData();
		if (!data.get(2).isPresent()) {
			apply(sign);
		}
		try {
			int speed = Integer.parseInt(data.get(2).get().toPlain());
			Direction direction = sign.getLocation().get(Keys.DIRECTION).get().getOpposite();
			EventContext context = EventContext.builder().add(EventContextKeys.PLAYER, player).add(EventContextKeys.BLOCK_HIT, sign.getLocation().createSnapshot()).build();
			Optional<FailedMovement> result = ship.move(direction, speed, Cause.of(context, "Right Click", ship));
			if (result.isPresent()) {
				result.get().process(player);
			}
		} catch (NumberFormatException e) {
			apply(sign);
		}
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onRemove(Player player, Sign sign) {
		return true;
	}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[engine]", "[move]");
	}

	@Override
	public boolean isSign(Sign sign) {
		SignData data = sign.getSignData();
		if(!data.get(0).isPresent()){
			return false;
		}
		Text text = data.get(0).get();
		if((text.getColor().equals(TextColors.YELLOW)) && (text.toPlain().equals("[Engine]"))){
			return true;
		}
		return false;
	}

	@Override
	public void apply(Sign sign) {
		SignData data = sign.getSignData();
		data.setElement(0, Text.builder("[Engine]").color(TextColors.YELLOW).build());
		data.setElement(2, Text.of(2));
		Optional<LiveShip> opShip = Loader.safeLoadShip(sign.getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			data.setElement(2, Text.of(speed));
			data.setElement(1, Text.of(speed - 1));
		}
		sign.offer(data);
		
	}

}
