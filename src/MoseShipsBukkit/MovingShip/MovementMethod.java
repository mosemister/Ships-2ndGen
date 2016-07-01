package MoseShipsBukkit.MovingShip;

import java.io.IOException;

import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.StillShip.Vessel.Vessel;

public enum MovementMethod {

	ROTATE_LEFT("Rotate Left", 270), ROTATE_RIGHT("Rotate Right", 90), MOVE_FORWARD("Move Forward"), MOVE_BACKWARD(
			"Move Backward"), MOVE_LEFT("Move Left"), MOVE_RIGHT("Move Right"), MOVE_UP("Move Up"), MOVE_DOWN(
					"Move Down"), MOVE_POSITIVE_X("Move +X"), MOVE_POSITIVE_Z("Move +Z"), MOVE_NEGATIVE_X(
							"Move -X"), MOVE_NEGATIVE_Z("Move -Z"), TELEPORT("Teleport");

	int SPEED;
	String DIRECTION;

	private MovementMethod(String direction) {
		DIRECTION = direction;
	}

	private MovementMethod(String direction, int rotate) {
		DIRECTION = direction;
		SPEED = rotate;
	}

	public int getSpeed() {
		return SPEED;
	}

	public String getDirection() {
		return DIRECTION;
	}

	public void setSpeed(int speed) {
		SPEED = speed;
	}

	public void setDirection(String direction) {
		DIRECTION = direction;
	}

	public static MovementMethod getMovementDirection(BlockFace face) {
		switch (face) {
		case NORTH:
			return MovementMethod.MOVE_NEGATIVE_Z;
		case SOUTH:
			return MovementMethod.MOVE_POSITIVE_Z;
		case EAST:
			return MovementMethod.MOVE_POSITIVE_X;
		case WEST:
			return MovementMethod.MOVE_NEGATIVE_Z;
		default:
			new IOException();
			return null;
		}
	}

	public static MovementMethod getMovingDirection(Vessel vessel, BlockFace blockface) {
		BlockFace vesselFace = vessel.getFacingDirection();
		switch (blockface) {
		case EAST_NORTH_EAST:
		case EAST_SOUTH_EAST:
			blockface = BlockFace.EAST;
			break;
		case NORTH_EAST:
		case NORTH_NORTH_EAST:
		case NORTH_WEST:
		case NORTH_NORTH_WEST:
			blockface = BlockFace.NORTH;
			break;
		case SOUTH_SOUTH_EAST:
		case SOUTH_EAST:
		case SOUTH_SOUTH_WEST:
		case SOUTH_WEST:
			blockface = BlockFace.SOUTH;
		case WEST_NORTH_WEST:
		case WEST_SOUTH_WEST:
			blockface = BlockFace.WEST;
		default:
			break;
		}
		if (vesselFace.equals(blockface)) {
			return MovementMethod.MOVE_FORWARD;
		} else if (vesselFace.getOppositeFace().equals(blockface)) {
			return MovementMethod.MOVE_BACKWARD;
		} else if (Ships.getSideFace(vesselFace, true).equals(blockface)) {
			return MovementMethod.MOVE_LEFT;
		} else if (Ships.getSideFace(vesselFace, false).equals(blockface)) {
			return MovementMethod.MOVE_RIGHT;
		} else {
			new IOException("getMovingDirection does not support that BlockFace.");
			return null;
		}
	}

}
