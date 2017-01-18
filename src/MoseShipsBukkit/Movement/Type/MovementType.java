package MoseShipsBukkit.Movement.Type;

public enum MovementType {

	DIRECTIONAL,
	ROTATE_LEFT,
	ROTATE_RIGHT,
	TELEPORT;

	public static enum Rotate {
		LEFT,
		RIGHT;
	}
}
