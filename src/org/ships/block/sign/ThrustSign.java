package org.ships.block.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.ships.ship.LoadableShip;

public class ThrustSign implements ShipSign.OnRightClick.OnShift, ShipSign.OnRightClick.OnStand, ShipSign.OnLeftClick.OnShift {
	@Override
	public String[] getLines() {
		String[] lines = new String[] { ChatColor.YELLOW + "[Thrust]", ChatColor.BLUE + "0 V 2", "1", "^" };
		return lines;
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getName() {
		return "Thrust";
	}

	@Override
	public boolean isSign(Sign sign) {
		return sign.getLine(0).equals(this.getLines()[0]);
	}

	@Override
	public boolean onCreate(SignChangeEvent event) {
		String[] lines = this.getLines();
		for (int A = 0; A < lines.length; ++A) {
			event.setLine(A, lines[A]);
		}
		return false;
	}

	@Override
	public void onShiftRightClick(Player player, Sign sign) {
		LoadableShip ship = LoadableShip.getShip(sign.getBlock(), true);
		int currentSpeed = Integer.parseInt(ChatColor.stripColor(sign.getLine(2)));
		if (currentSpeed < ship.getVesselType().getDefaultSpeed()) {
			this.updateSign(sign, currentSpeed + 1);
			return;
		}
		this.updateSign(sign, 1);
	}

	@Override
	public void onRightClick(Player player, Sign sign) {
		LoadableShip ship = LoadableShip.getShip(sign.getBlock(), true);
	}

	@Override
	public void onShiftLeftClick(Player player, Sign sign) {
		LoadableShip ship = LoadableShip.getShip(sign.getBlock(), true);
		int currentSpeed = Integer.parseInt(ChatColor.stripColor(sign.getLine(2)));
		if (currentSpeed > -ship.getVesselType().getDefaultSpeed()) {
			this.updateSign(sign, currentSpeed - 1);
			return;
		}
		this.updateSign(sign, -1);
	}

	private void updateSign(Sign sign, int newSpeed) {
		sign.setLine(1, ChatColor.AQUA + "" + (newSpeed - 1) + " V " + (newSpeed + 1));
		sign.setLine(2, ChatColor.GREEN + "" + newSpeed);
		sign.update();
	}
}
