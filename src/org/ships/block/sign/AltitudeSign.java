package org.ships.block.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class AltitudeSign implements ShipSign.OnLeftClick.OnStand, ShipSign.OnRightClick.OnStand {
	@Override
	public String[] getLines() {
		String[] lines = new String[4];
		lines[0] = ChatColor.YELLOW + "[Altitude]";
		return lines;
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getName() {
		return "Altitude";
	}

	@Override
	public boolean isSign(Sign sign) {
		return sign.getLine(0).equals(this.getLines()[0]);
	}

	@Override
	public boolean onCreate(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Altitude]");
		event.setLine(1, "[right] up");
		event.setLine(2, "[left] down");
		return false;
	}

	@Override
	public void onRightClick(Player player, Sign sign) {
	}

	@Override
	public void onLeftClick(Player player, Sign sign) {
	}
}
