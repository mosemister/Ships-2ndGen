package MoseShipsBukkit.ShipBlock.Signs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.ShipCommands.ShipCommands;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public class ShipAltitudeSign implements ShipSign {

	@Override
	public void apply(Sign sign) {
		sign.setLine(0, ChatColor.YELLOW + "[Altitude]");
		sign.setLine(2, "0");
		SOptional<LiveShip> opShip = Loader.safeLoadShip(sign.getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getAltitudeSpeed();
			sign.setLine(2, speed + "");
		}
		sign.update();
	}
	
	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Altitude]");
		event.setLine(2, "0");
		SOptional<LiveShip> opShip = Loader.safeLoadShip(event.getBlock().getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getAltitudeSpeed();
			event.setLine(2, speed + "");
		}
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		try{
		int speed = Integer.parseInt(sign.getLine(2));
		StaticShipType staticShip = ship.getStatic();
		if (staticShip.getAltitudeSpeed() < speed) {
			sign.setLine(2, (speed + 1) + "");
			sign.update();
		} else {
			sign.setLine(2, "1");
			sign.update();
		}
		}catch(NumberFormatException e){
			apply(sign);
		}

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		try {
			int speed = Integer.parseInt(sign.getLine(2));
			if (ship.getCommands().contains(ShipCommands.LOCK_ALTITUDE)) {
				return;
			}
			SOptional<FailedMovement> causeMove = ship.move(0, speed, 0, new ShipsCause(player, sign, "Up"));
			if (causeMove.isPresent()) {
				causeMove.get().process(player);
			}
		} catch (NumberFormatException e) {
			apply(sign);
		}
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		try {
			int speed = Integer.parseInt(sign.getLine(2));
			if (ship.getCommands().contains(ShipCommands.LOCK_ALTITUDE)) {
				return;
			}
			SOptional<FailedMovement> causeMove = ship.move(0, -speed, 0, new ShipsCause(player, sign, "Down"));
			if (causeMove.isPresent()) {
				causeMove.get().process(player);
			}
		} catch (NumberFormatException e) {
			apply(sign);
		}
	}

	@Override
	public void onRemove(Player player, Sign sign) {
	}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[altitude]");
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equalsIgnoreCase(ChatColor.YELLOW + "[Altitude]")) {
			return true;
		}
		return false;
	}

	@Override
	public SOptional<LiveShip> getAttachedShip(Sign sign) {
		SOptional<LiveShip> opShip = Loader.safeLoadShip(this, sign, false);
		if (opShip.isPresent()) {
			return new SOptional<LiveShip>(opShip.get());
		}
		return new SOptional<LiveShip>();
	}

}
