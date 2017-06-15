package MoseShipsSponge.Utils;

import org.spongepowered.api.util.Direction;

import MoseShipsSponge.Movement.Type.RotateType;

public class BlockRotateUtil {

	public static Direction getRotation(Direction direction, RotateType type) {
		switch (type) {
		case LEFT:
			return rotateLeft(direction);
		case RIGHT:
			return rotateRight(direction);
		}
		return direction;
	}

	public static Direction rotateLeft(Direction direction) {
		switch (direction) {
		case NORTH:
			return Direction.WEST;
		case WEST:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.EAST;
		case EAST:
			return Direction.NORTH;

		case NORTHWEST:
			return Direction.SOUTHWEST;
		case SOUTHWEST:
			return Direction.SOUTHEAST;
		case SOUTHEAST:
			return Direction.NORTHEAST;
		case NORTHEAST:
			return Direction.NORTHWEST;

		default:
			break;
		}
		return direction;
	}

	public static Direction rotateRight(Direction direction) {
		switch (direction) {
		case NORTH:
			return Direction.EAST;
		case EAST:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.WEST;
		case WEST:
			return Direction.NORTH;

		case NORTHWEST:
			return Direction.NORTHEAST;
		case NORTHEAST:
			return Direction.SOUTHEAST;
		case SOUTHEAST:
			return Direction.SOUTHWEST;
		case SOUTHWEST:
			return Direction.NORTHWEST;

		default:
			break;
		}
		return direction;
	}

}
