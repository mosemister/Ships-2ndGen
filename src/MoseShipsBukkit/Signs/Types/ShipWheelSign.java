package MoseShipsBukkit.Signs.Types;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Signs.ShipSign;

public class ShipWheelSign implements ShipSign{

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
		ship.rotateRight(new ShipsCause(player, sign));
		
	}

	@Override
	public void onLeftClick(Player player, Sign sign, LiveShip ship) {
		ship.rotateLeft(new ShipsCause(player, sign));
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
		if(sign.getLine(0).equals(ChatColor.YELLOW + "[Wheel]")){
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
