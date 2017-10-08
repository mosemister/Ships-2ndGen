package MoseShipsSponge.Movement.Type;

import java.util.Optional;

import org.spongepowered.api.util.Direction;

public enum RotateType {

	LEFT, RIGHT;

	public static Optional<RotateType> getRotation(Direction direction1, Direction direction2) {
		switch (direction1) {
		case NORTH:
			switch (direction2) {
			case NORTH:
				return Optional.empty();
			case WEST:
				return Optional.of(LEFT);
			case SOUTH:
				return Optional.empty();
			case EAST:
				return Optional.of(RIGHT);
			default:
				System.out.println(
						"Unsupported direction2. RotateType.getDirection(Direction direction1, Direction direction2);");
				return Optional.empty();
			}
		case SOUTH:
			switch (direction2) {
			case NORTH:
				return Optional.empty();
			case WEST:
				return Optional.of(RIGHT);
			case SOUTH:
				return Optional.empty();
			case EAST:
				return Optional.of(LEFT);
			default:
				System.out.println(
						"Unsupported direction2. RotateType.getDirection(Direction direction1, Direction direction2);");
				return Optional.empty();
			}
		case EAST:
			switch (direction2) {
			case NORTH:
				return Optional.of(RIGHT);
			case WEST:
				return Optional.empty();
			case SOUTH:
				return Optional.of(LEFT);
			case EAST:
				return Optional.empty();
			default:
				System.out.println(
						"Unsupported direction2. RotateType.getDirection(Direction direction1, Direction direction2);");
				return Optional.empty();
			}
		case WEST:
			switch (direction2) {
			case NORTH:
				return Optional.of(LEFT);
			case WEST:
				return Optional.empty();
			case SOUTH:
				return Optional.of(RIGHT);
			case EAST:
				return Optional.empty();
			default:
				System.out.println(
						"Unsupported direction2. RotateType.getDirection(Direction direction1, Direction direction2);");
				return Optional.empty();
			}
		default:
			System.out.println(
					"Unsupported direction1. RotateType.getDirection(Direction direction1, Direction direction2);");
			return Optional.empty();
		}
	}

}
