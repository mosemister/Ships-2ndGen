package MoseShipsSponge.Ships.Movement.Rotate;

import org.bukkit.block.BlockFace;

public class BlockRotate {

	public static BlockFace getRotation(BlockFace direction, RotateType type) {
		switch (type) {
			case LEFT:
				switch (direction) {
					case DOWN:
						return direction;
					case EAST:
						return BlockFace.NORTH;
					case NORTH:
						return BlockFace.WEST;
					case SOUTH:
						return BlockFace.EAST;
					case UP:
						return direction;
					case WEST:
						return BlockFace.SOUTH;
					default:
						return direction;

				}
			case RIGHT:
				switch (direction) {
					case DOWN:
						return direction;
					case EAST:
						return BlockFace.SOUTH;
					case NORTH:
						return BlockFace.EAST;
					case SOUTH:
						return BlockFace.WEST;
					case UP:
						return direction;
					case WEST:
						return BlockFace.NORTH;
					default:
						return direction;
				}

		}
		return null;
	}

}
