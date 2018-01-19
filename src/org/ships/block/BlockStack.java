package org.ships.block;

import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import MoseShipsBukkit.StillShip.Vessel.LoadableShip;

public class BlockStack extends ArrayList<Block>{

	private static final long serialVersionUID = 1L;

	public boolean isVaild() {
		for (Block block : this) {
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				if (sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
					LoadableShip vessel = LoadableShip.getShip(sign);
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
	
	public Sign getLicence() {
		Optional<Block> opBlock = stream().filter(b -> {
			if(!(b.getState() instanceof Sign)) {
				return false;
			}
			Sign sign = (Sign)b.getState();
			if(sign.getLine(0).equals(ChatColor.YELLOW + "[Ships]")) {
				return true;
			}
			return false;
		}).findAny();
		if(!opBlock.isPresent()) {
			return null;
		}
		BlockState state = opBlock.get().getState();
		if(state instanceof Sign) {
			return (Sign)state;
		}
		return null;
	}
}
