package org.ships.block;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShipsBukkit.StillShip.Vessel.Vessel;

public class BlockStack extends ArrayList<Block>{

	private static final long serialVersionUID = 1L;

	public boolean isVaild() {
		for (Block block : this) {
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
					Vessel vessel = Vessel.getVessel(sign);
					if (vessel == null) {
						return false;
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}
}
