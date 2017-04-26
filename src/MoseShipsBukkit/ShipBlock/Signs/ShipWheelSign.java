package MoseShipsBukkit.ShipBlock.Signs;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.OpenLoader.Loader;

public class ShipWheelSign implements ShipSign {

	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Wheel]");
		event.setLine(1, ChatColor.RED + "\\\\||//");
		event.setLine(2, ChatColor.RED + "==||==");
		event.setLine(3, ChatColor.RED + "//||\\\\");

	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		Optional<FailedMovement> result = ship.rotateRight(new ShipsCause(player, sign));
		if (result.isPresent()) {
			result.get().process(player);
		}

	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		Optional<FailedMovement> result = ship.rotateLeft(new ShipsCause(player, sign));
		if (result.isPresent()) {
			result.get().process(player);
		}
	}

	@Override
	public void onRemove(Player player, Sign sign) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFirstLine() {
		return "[Wheel]";
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<LiveShip> getAttachedShip(Sign sign) {
		Optional<LiveShip> opShip = Loader.getShip(this, sign, false);
		if (opShip.isPresent()) {
			return Optional.of((LiveShip) opShip.get());
		}
		return Optional.empty();
	}

}
