package org.ships.block.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

public class EngineSign implements ShipSign.OnRightClick.OnStand {
	@Override
	public String[] getLines() {
		String[] lines = new String[4];
		lines[0] = ChatColor.YELLOW + "[Move]";
		lines[1] = "{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}";
		lines[2] = "Boost";
		return lines;
	}

	@Override
	public String[] getAliases() {
		return new String[] { "Engine" };
	}

	@Override
	public String getName() {
		return "Engine";
	}

	@Override
	public boolean isSign(Sign sign) {
		return sign.getLine(0).equals(this.getLines()[0]);
	}

	@Override
	public boolean onCreate(SignChangeEvent event) {
		event.setLine(0, ChatColor.YELLOW + "[Move]");
		event.setLine(1, "{" + ChatColor.GREEN + "Engine" + ChatColor.BLACK + "}");
		event.setLine(2, "Boost");
		return false;
	}

	@Override
	public void onRightClick(Player player, Sign sign) {
	}
}
