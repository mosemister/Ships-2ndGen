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

public class ShipWheelSign implements ShipSign {

	@Override
	public void apply(Sign sign) {
		sign.setLine(0, ChatColor.YELLOW + "[Wheel]");
		sign.setLine(1, ChatColor.RED + "\\\\||//");
		sign.setLine(2, ChatColor.RED + "==||==");
		sign.setLine(3, ChatColor.RED + "//||\\\\");
		sign.update();
	}
	
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
		SOptional<FailedMovement> result = ship.rotateRight(new ShipsCause(player, sign));
		if (result.isPresent()) {
			result.get().process(player);
		}

	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		SOptional<FailedMovement> result = ship.rotateLeft(new ShipsCause(player, sign));
		if (result.isPresent()) {
			result.get().process(player);
		}
	}

	@Override
	public void onRemove(Player player, Sign sign) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[Wheel]");
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")) {
			return true;
		}
		return false;
	}

	@Override
	public SOptional<LiveShip> getAttachedShip(Sign sign) {
		SOptional<LiveShip> opShip = Loader.safeLoadShip(this, sign, false);
		if (opShip.isPresent()) {
			return new SOptional<LiveShip>((LiveShip) opShip.get());
		}
		return new SOptional<LiveShip>();
	}

}
