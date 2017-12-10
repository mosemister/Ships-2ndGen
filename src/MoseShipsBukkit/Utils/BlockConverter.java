package MoseShipsBukkit.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;

@SuppressWarnings("deprecation")
public class BlockConverter {

	public static byte convertRotation(MovementMethod move, MovingBlock block, byte data) {
		int id = block.getId();
		if ((id == Material.WALL_SIGN.getId()) || (id == Material.FURNACE.getId())
				|| (id == Material.BURNING_FURNACE.getId()) || (id == Material.PISTON_BASE.getId())
				|| (id == Material.PISTON_STICKY_BASE.getId()) || (id == Material.WALL_BANNER.getId())
				|| (id == Material.DROPPER.getId()) || (id == Material.DISPENSER.getId())
				|| (id == Material.HOPPER.getId()) || (id == Material.LEVER.getId()) || (id == Material.LADDER.getId())
				|| (id == Material.ENDER_CHEST.getId())) {
			byte data2 = quadValues(move, data);
			return data2;
		}
		if (block.getSpecialBlock() != null) {
			if (block.getSpecialBlock().getType().equals("chest")) {
				byte data2 = quadValues(move, data);
				return data2;
			}
		}
		if ((id == Material.LOG.getId()) || (id == Material.LOG_2.getId())) {
			byte data2 = logValues(move, data);
			return data2;
		}
		if ((id == Material.REDSTONE_TORCH_ON.getId()) || (id == Material.REDSTONE_TORCH_OFF.getId())
				|| (id == Material.TORCH.getId())) {
			byte data2 = torchValues(move, data);
			return data2;
		}
		if ((id == Material.WOOD_STAIRS.getId()) || (id == Material.COBBLESTONE_STAIRS.getId()) 
				|| (id == Material.JUNGLE_WOOD_STAIRS.getId()) || (id == Material.NETHER_BRICK_STAIRS.getId())
				|| (id == Material.PURPUR_STAIRS.getId()) || (id == Material.RED_SANDSTONE_STAIRS.getId())
				|| (id == Material.SMOOTH_STAIRS.getId()) || (id == Material.SPRUCE_WOOD_STAIRS.getId())
				|| (id == Material.ACACIA_STAIRS.getId()) || (id == Material.SANDSTONE_STAIRS.getId())
				|| (id == Material.BIRCH_WOOD_STAIRS.getId()) || (id == Material.BRICK_STAIRS.getId())
				|| (id == Material.DARK_OAK_STAIRS.getId()) || (id == Material.QUARTZ_STAIRS.getId())) {
			byte data2 = stairValues(move, data);
			return data2;
		}
		if ((id == Material.SIGN_POST.getId()) || (id == Material.STANDING_BANNER.getId())) {
			byte data2 = fithteenValues(move, data);
			return data2;
		}
		if (id == Material.QUARTZ_BLOCK.getId()) {
			byte data2 = quartzValues(move, data);
			return data2;
		}
		if (id == Material.ANVIL.getId()) {
			byte data2 = anvilValues(move, data);
			return data2;
		}
		if ((id == 77) || (id == 143)) {
			byte data2 = button(move, data);
			return data2;
		}
		if ((id == 64) || (id == 71)) {
			byte data2 = door(move, data);
			return data2;
		}
		return data;
	}

	static byte LaddersFix(MovementMethod move, byte data) {
		if (move.equals(MovementMethod.ROTATE_RIGHT)) {
			if (data == 4) {
				return 2;
			}
			if (data == 2) {
				return 5;
			}
			if (data == 5) {
				return 3;
			}
			if (data == 3) {
				return 4;
			}
		} else {
			if (data == 2) {
				return 4;
			}
			if (data == 5) {
				return 2;
			}
			if (data == 3) {
				return 5;
			}
			if (data == 4) {
				return 3;
			}
		}
		return data;
	}

	static byte anvilValues(MovementMethod move, byte data) {
		if (data == 0) {
			return 1;
		}
		if (data == 1) {
			return 0;
		}
		if (data == 2) {
			return 3;
		}
		if (data == 3) {
			return 2;
		}
		if (data == 4) {
			return 5;
		}
		if (data == 5) {
			return 4;
		}
		if (data == 6) {
			return 7;
		}
		if (data == 7) {
			return 6;
		}
		if (data == 8) {
			return 9;
		}
		if (data == 9) {
			return 8;
		}
		if (data == 10) {
			return 11;
		}
		if (data == 11) {
			return 10;
		}
		return data;
	}

	static byte quartzValues(MovementMethod move, byte data) {
		if (data == 3) {
			return 4;
		}
		if (data == 4) {
			return 3;
		}
		return data;
	}

	static byte fithteenValues(MovementMethod move, byte data) {
		if (move.equals(MovementMethod.ROTATE_RIGHT)) {
			if (data == 0) {
				return 4;
			}
			if (data == 4) {
				return 8;
			}
			if (data == 8) {
				return 12;
			}
			if (data == 12) {
				return 0;
			}
			if (data == 14) {
				return 2;
			}
			if (data == 10) {
				return 14;
			}
			if (data == 6) {
				return 10;
			}
			if (data == 2) {
				return 6;
			}
		} else if (move.equals(MovementMethod.ROTATE_LEFT)) {
			if (data == 4) {
				return 0;
			}
			if (data == 8) {
				return 4;
			}
			if (data == 12) {
				return 8;
			}
			if (data == 0) {
				return 12;
			}
			if (data == 2) {
				return 14;
			}
			if (data == 14) {
				return 10;
			}
			if (data == 10) {
				return 6;
			}
			if (data == 6) {
				return 2;
			}
		}
		return data;
	}

	static byte stairValues(MovementMethod move, byte data) {
		if (MovementMethod.ROTATE_RIGHT.equals(move)) {
			if (data == 0) {
				return 2;
			}
			if (data == 1) {
				return 3;
			}
			if (data == 2) {
				return 1;
			}
			if (data == 3) {
				return 0;
			}
			if (data == 6) {
				return 5;
			}
			if (data == 5) {
				return 7;
			}
			if (data == 7) {
				return 4;
			}
			if (data == 4) {
				return 6;
			}
		}
		if (MovementMethod.ROTATE_LEFT.equals(move)) {
			if (data == 0) {
				return 3;
			}
			if (data == 1) {
				return 2;
			}
			if (data == 2) {
				return 0;
			}
			if (data == 3) {
				return 1;
			}
			if (data == 6) {
				return 4;
			}
			if (data == 4) {
				return 7;
			}
			if (data == 7) {
				return 5;
			}
			if (data == 5) {
				return 6;
			}
		}
		return data;
	}

	static byte torchValues(MovementMethod move, byte data) {
		if (MovementMethod.ROTATE_RIGHT.equals(move)) {
			if (data == 1) {
				return 3;
			}
			if (data == 2) {
				return 4;
			}
			if (data == 3) {
				return 2;
			}
			if (data == 4) {
				return 1;
			}
		}
		if (MovementMethod.ROTATE_LEFT.equals(move)) {
			if (data == 1) {
				return 4;
			}
			if (data == 2) {
				return 3;
			}
			if (data == 3) {
				return 1;
			}
			if (data == 4) {
				return 2;
			}
		}
		return data;
	}

	static byte logValues(MovementMethod move, byte data) {
		if (data == 4) {
			return 8;
		}
		if (data == 5) {
			return 9;
		}
		if (data == 6) {
			return 10;
		}
		if (data == 7) {
			return 11;
		}
		if (data == 8) {
			return 4;
		}
		if (data == 9) {
			return 5;
		}
		if (data == 10) {
			return 6;
		}
		if (data == 11) {
			return 7;
		}
		return data;
	}

	static byte quadValues(MovementMethod move, byte data) {
		if (move.equals(MovementMethod.ROTATE_RIGHT)) {
			if (data == 2) {
				return 5;
			}
			if (data == 3) {
				return 4;
			}
			if (data == 4) {
				return 2;
			}
			if (data == 5) {
				return 3;
			}
		} else if (move.equals(MovementMethod.ROTATE_LEFT)) {
			if (data == 2) {
				return 4;
			}
			if (data == 3) {
				return 5;
			}
			if (data == 4) {
				return 3;
			}
			if (data == 5) {
				return 2;
			}
		} else {
			Bukkit.getConsoleSender().sendMessage("error");
		}
		return data;
	}

	static byte door(MovementMethod move, byte data) {
		if (move.equals(MovementMethod.ROTATE_RIGHT)) {
			if (data == 3) {
				return 0;
			}
			if (data == 0) {
				return 1;
			}
			if (data == 1) {
				return 2;
			}
			if (data == 2) {
				return 3;
			}
			if (data == 4) {
				return 5;
			}
			if (data == 5) {
				return 6;
			}
			if (data == 6) {
				return 7;
			}
			if (data == 7) {
				return 4;
			}
		} else if (move.equals(MovementMethod.ROTATE_LEFT)) {
			if (data == 0) {
				return 3;
			}
			if (data == 1) {
				return 0;
			}
			if (data == 2) {
				return 1;
			}
			if (data == 3) {
				return 2;
			}
			if (data == 5) {
				return 4;
			}
			if (data == 6) {
				return 5;
			}
			if (data == 7) {
				return 6;
			}
			if (data == 4) {
				return 7;
			}
		}
		return data;
	}

	static byte button(MovementMethod move, byte data) {
		if (MovementMethod.ROTATE_RIGHT.equals(move)) {
			if (data == 3) {
				return 2;
			} else if (data == 2) {
				return 4;
			} else if (data == 4) {
				return 1;
			} else if (data == 1) {
				return 3;
			}
		} else if (MovementMethod.ROTATE_LEFT.equals(move)) {
			if (data == 3) {
				return 1;
			} else if (data == 1) {
				return 4;
			} else if (data == 4) {
				return 2;
			} else if (data == 2) {
				return 3;
			}
		}
		return data;
	}

	// TODO other blocks
}
