package MoseShipsBukkit.ShipBlock.Signs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.WorldUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public class ShipEngineSign implements ShipSign {

	@Override
	public void apply(Sign sign) {
		sign.setLine(0, ChatColor.YELLOW + "[Engine]");
		sign.setLine(2, "0");

		SOptional<LiveShip> opShip = Loader.safeLoadShip(sign.getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			sign.setLine(2, speed + "");
			sign.setLine(1, (speed - 1) + "");
			if (WorldUtil.WIND_DIRECTIONS.get(ship.getWorld())
					.equals(((org.bukkit.material.Sign) sign.getData()).getFacing().getOppositeFace())) {
				sign.setLine(3, (speed + 1) + "");
			}
		}
		sign.update();
	}
	
	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Engine]");
		event.setLine(2, "0");

		SOptional<LiveShip> opShip = Loader.safeLoadShip(event.getBlock().getLocation(), true);
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
		try{
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
		}catch(NumberFormatException e){
			apply(sign);
		}

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		try{
		int speed = Integer.parseInt(sign.getLine(2));
		BlockFace direction = ((org.bukkit.material.Sign) sign.getData()).getFacing().getOppositeFace();
		SOptional<FailedMovement> result = ship.move(direction, speed, new ShipsCause(sign, player));
		if (result.isPresent()) {
			result.get().process(player);
		}
		}catch(NumberFormatException e){
			apply(sign);
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
	public List<String> getFirstLine() {
		return Arrays.asList("[engine]", "[move]");
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equals(ChatColor.YELLOW + "[Engine]")) {
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
