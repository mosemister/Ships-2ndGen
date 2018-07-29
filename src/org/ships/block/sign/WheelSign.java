package org.ships.block.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class WheelSign implements ShipSign.OnRightClick.OnStand, ShipSign.OnLeftClick.OnStand {
	@Override
	public String[] getLines() {
		String[] lines = new String[] { ChatColor.YELLOW + "[Wheel]", ChatColor.RED + "\\\\||//", ChatColor.RED + "==||==", ChatColor.RED + "//||\\\\" };
		return lines;
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getName() {
		return "Wheel";
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
	public void onRightClick(Player player, Sign sign) {
	}

	@Override
	public void onLeftClick(Player player, Sign sign) {
	}
}
