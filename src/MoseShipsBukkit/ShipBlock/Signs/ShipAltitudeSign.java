package MoseShipsBukkit.ShipBlock.Signs;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.Data.LoadableShip;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveLockedAltitude;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public class ShipAltitudeSign implements ShipSign {

	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Altitude]");
		event.setLine(2, "0");
		Optional<LoadableShip> opShip = LoadableShip.getShip(event.getBlock(), true);
		if (opShip.isPresent()) {
			LoadableShip ship = opShip.get();
			int speed = ship.getStatic().getAltitudeSpeed();
			event.setLine(2, speed + "");
		}
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		int speed = Integer.parseInt(sign.getLine(2));
		StaticShipType staticShip = ship.getStatic();
		if (staticShip.getAltitudeSpeed() < speed) {
			sign.setLine(2, (speed + 1) + "");
			sign.update();
		} else {
			sign.setLine(2, "1");
			sign.update();
		}

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		try {
			int speed = Integer.parseInt(sign.getLine(2));
			if (ship instanceof LiveLockedAltitude) {
				return;
			}
			Optional<FailedMovement> causeMove = ship.move(0, speed, 0, new ShipsCause(player, sign, "Up"));
			if (causeMove.isPresent()) {
				causeMove.get().process(player);
			}
		} catch (NumberFormatException e) {
			player.sendMessage("[Warning] old Ships sign, please change this to Ships 6 '[altitude]' sign");
		}
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		try {
			int speed = Integer.parseInt(sign.getLine(2));
			if (ship instanceof LiveLockedAltitude) {
				return;
			}
			Optional<FailedMovement> causeMove = ship.move(0, -speed, 0, new ShipsCause(player, sign, "Down"));
			if (causeMove.isPresent()) {
				causeMove.get().process(player);
			}
		} catch (NumberFormatException e) {
			player.sendMessage("[Warning] old Ships sign, please change this to Ships 6 '[altitude]' sign");
		}
	}

	@Override
	public void onRemove(Player player, Sign sign) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFirstLine() {
		return "[Altitude]";
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equalsIgnoreCase(ChatColor.YELLOW + "[Altitude]")) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<LiveShip> getAttachedShip(Sign sign) {
		Optional<LoadableShip> opShip = LoadableShip.getShip(this, sign, false);
		if (opShip.isPresent()) {
			return Optional.of((LiveShip) opShip.get());
		}
		return Optional.empty();
	}

}
