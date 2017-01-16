package MoseShipsBukkit.Signs.Types;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.Running.Tasks.EOTTask;
import MoseShipsBukkit.Ships.VesselTypes.Satic.StaticShipType;
import MoseShipsBukkit.Signs.ShipSign;

public class ShipEOTSign implements ShipSign{

	@Override
	public void onCreation(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[E.O.T]");
		event.setLine(1, ChatColor.GREEN + "Ahead" + ChatColor.BLACK + " {Stop}");
		event.setLine(2, "0");
		Optional<LoadableShip> opShip = LoadableShip.getShip(event.getBlock(), true);
		if (opShip.isPresent()) {
			LoadableShip ship = opShip.get();
			int speed = ship.getStatic().getDefaultSpeed();
			event.setLine(2, speed + "");
		}
		
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign, LiveShip ship) {
		int speed = Integer.parseInt(sign.getLine(2));
		StaticShipType staticShip = ship.getStatic();
		if(staticShip.getDefaultSpeed() < speed){
			sign.setLine(2, (speed + 1) + "");
			sign.update();
		}else{
			sign.setLine(2, "1");
			sign.update();
		}
		
	}

	@Override
	public void onRightClick(Player player, Sign sign, LiveShip ship) {
		String aheadLine = "{" + ChatColor.GREEN + "Ahead" + ChatColor.BLACK + "} Stop";
		String stopLine = ChatColor.GREEN + "Ahead" + ChatColor.BLACK + " {Stop}";
		
		String line = sign.getLine(1);
		if(line.equals(aheadLine)){
			sign.setLine(1, stopLine);
			sign.update();
			for (EOTTask task : ship.getTaskRunner().getTasks(EOTTask.class)){
				ship.getTaskRunner().unregister(task);
			}
		}else{
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
		Optional<LiveShip> opShip = getAttachedShip(sign);
		if(opShip.isPresent()){
			LiveShip ship = opShip.get();
			for (EOTTask task: ship.getTaskRunner().getTasks(EOTTask.class)){
				ship.getTaskRunner().unregister(task);
			}
		}
		
	}

	@Override
	public String getFirstLine() {
		return "[EOT]";
	}

	@Override
	public boolean isSign(Sign sign) {
		if(sign.getLine(0).equalsIgnoreCase(ChatColor.YELLOW + "[E.O.T]")){
			return true;
		}
		return false;
	}

	@Override
	public Optional<LiveShip> getAttachedShip(Sign sign) {
		Optional<LoadableShip> opShip = LoadableShip.getShip(this, sign, false);
		if(opShip.isPresent()){
			return Optional.of((LiveShip)opShip.get());
		}
		return Optional.empty();
	}

}
