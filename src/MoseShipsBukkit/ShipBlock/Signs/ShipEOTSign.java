package MoseShipsBukkit.ShipBlock.Signs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.Tasks.Types.EOTTask;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;

public class ShipEOTSign implements ShipSign {

	@Override
	public void apply(Sign sign) {
		sign.setLine(0, ChatColor.YELLOW + "[E.O.T]");
		sign.setLine(1, ChatColor.GREEN + "Ahead" + ChatColor.BLACK + " {Stop}");
		sign.setLine(2, "0");
		SOptional<LiveShip> opShip = Loader.safeLoadShip(sign.getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			sign.setLine(2, speed + "");
		}
		sign.update();
	}
	
	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[E.O.T]");
		event.setLine(1, ChatColor.GREEN + "Ahead" + ChatColor.BLACK + " {Stop}");
		event.setLine(2, "0");
		SOptional<LiveShip> opShip = Loader.safeLoadShip(event.getBlock().getLocation(), true);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			event.setLine(2, speed + "");
		}

	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		int speed = Integer.parseInt(sign.getLine(2));
		StaticShipType staticShip = ship.getStatic();
		if (staticShip.getDefaultSpeed() < speed) {
			sign.setLine(2, (speed + 1) + "");
			sign.update();
		} else {
			sign.setLine(2, "1");
			sign.update();
		}

	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		String aheadLine = "{" + ChatColor.GREEN + "Ahead" + ChatColor.BLACK + "} Stop";
		String stopLine = ChatColor.GREEN + "Ahead" + ChatColor.BLACK + " {Stop}";

		String line = sign.getLine(1);
		if (line.equals(aheadLine)) {
			sign.setLine(1, stopLine);
			sign.update();
			for (EOTTask task : ship.getTaskRunner().getTasks(EOTTask.class)) {
				ship.getTaskRunner().unregister(task);
			}
		} else {
			sign.setLine(1, aheadLine);
			sign.update();
			ship.getTaskRunner().register(ShipsMain.getPlugin(), new EOTTask(player, sign));
		}

	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		ship.getTaskRunner().getTasks(EOTTask.class).iterator().next();
	}

	@Override
	public void onRemove(Player player, Sign sign) {
		SOptional<LiveShip> opShip = getAttachedShip(sign);
		if (opShip.isPresent()) {
			LiveShip ship = opShip.get();
			for (EOTTask task : ship.getTaskRunner().getTasks(EOTTask.class)) {
				ship.getTaskRunner().unregister(task);
			}
		}

	}

	@Override
	public List<String> getFirstLine() {
		return Arrays.asList("[eot]");
	}

	@Override
	public boolean isSign(Sign sign) {
		if (sign.getLine(0).equalsIgnoreCase(ChatColor.YELLOW + "[E.O.T]")) {
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
