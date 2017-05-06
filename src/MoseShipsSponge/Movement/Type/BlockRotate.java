package MoseShipsSponge.Movement.Type;

import org.spongepowered.api.util.Direction;

public class BlockRotate {

	public static Direction getRotation(Direction direction, RotateType type) {
		switch (type) {
			case LEFT:
				switch (direction) {
					case DOWN:
						return direction;
					case EAST:
						return Direction.NORTH;
					case NORTH:
						return Direction.WEST;
					case SOUTH:
						return Direction.EAST;
					case UP:
						return direction;
					case WEST:
						return Direction.SOUTH;
					default:
						return direction;

				}
			case RIGHT:
				switch (direction) {
					case DOWN:
						return direction;
					case EAST:
						return Direction.SOUTH;
					case NORTH:
						return Direction.EAST;
					case SOUTH:
						return Direction.WEST;
					case UP:
						return direction;
					case WEST:
						return Direction.NORTH;
					default:
						return direction;
				}

		}
		return null;
	}

}
