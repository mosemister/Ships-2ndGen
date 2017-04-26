package MoseShipsBukkit.ShipBlock.Signs;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Utils.WorldUtil;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Static.StaticShipType;

public class ShipEngineSign implements ShipSign {

	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Engine]");
		event.setLine(2, "0");

		Optional<LiveShip> opShip = Loader.getShip(event.getBlock(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			event.setLine(2, speed + "");
			event.setLine(1, (speed - 1) + "");
			Sign sign = (Sign) event.getBlock().getState();
			if (WorldUtil.WIND_DIRECTIONS.get(ship.getWorld())
					.equals(((org.bukkit.material.Sign) sign.getData()).getFacing().getOppositeFace())) {
				event.setLine(3, (speed + 1) + "");
			}
		}

	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		int value = Integer.parseInt(sign.getLine(2));
		BlockFace direction = WorldUtil.WIND_DIRECTIONS.get(ship.getWorld());
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
		Optional<FailedMovement> result = ship.move(direction, speed, new ShipsCause(sign, player));
		if (result.isPresent()) {
			result.get().process(player);
		}
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
		Optional<LiveShip> opShip = Loader.getShip(this, sign, false);
		if (opShip.isPresent()) {
			return Optional.of((LiveShip) opShip.get());
		}
		return Optional.empty();
	}

}
