package MoseShipsBukkit.Signs.Types;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Signs.ShipSign;
import MoseShipsBukkit.Utils.WorldUtils;

public class ShipEngineSign implements ShipSign {

	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Engine]");
		event.setLine(2, "0");

		Optional<LoadableShip> opShip = LoadableShip.getShip(event.getBlock(), true);
		if (opShip.isPresent()) {
			LoadableShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			event.setLine(2, speed + "");
			event.setLine(1, (speed - 1) + "");
			Sign sign = (Sign) event.getBlock();
			if (WorldUtils.WIND_DIRECTIONS.get(ship.getWorld())
					.equals(((org.bukkit.material.Sign) sign.getData()).getFacing().getOppositeFace())) {
				event.setLine(3, (speed + 1) + "");
			}
		}

	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		int value = Integer.parseInt(sign.getLine(2));
		BlockFace direction = WorldUtils.WIND_DIRECTIONS.get(ship.getWorld());
		BlockFace signDirection = ((org.bukkit.material.Sign) sign.getData()).getFacing().getOppositeFace();
		StaticShipType shipType = ship.getStatic();
		if (shipType.getDefaultSpeed() > value) {
			sign.setLine(2, (value + 1) + "");
			sign.update();
			return;
		} else if ((shipType.getBoostSpeed() > value) && (direction.equals(signDirection))) {
			sign.setLine(2, (value + 1) + "");
			sign.update();
			return;
		} else if ((shipType.getBoostSpeed() == value) && (!direction.equals(signDirection))) {
			sign.setLine(2, "1");
			sign.update();
			return;
		} else {
			player.sendMessage("Unknown error");
		}

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		int speed = Integer.parseInt(sign.getLine(2));
		BlockFace direction = ((org.bukkit.material.Sign) sign.getData()).getFacing().getOppositeFace();
		ship.move(direction, speed, new ShipsCause(sign, player));
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemove(Player player, Sign sign) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFirstLine() {
		return "[Engine]";
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Engine]")) {
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
