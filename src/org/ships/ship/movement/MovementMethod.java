package org.ships.ship.movement;

import java.io.IOException;
import java.util.Optional;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.ships.plugin.Ships;
import org.ships.ship.Ship;

public enum MovementMethod {
	ROTATE_LEFT("Rotate Left", 270),
	ROTATE_RIGHT("Rotate Right", 90),
	MOVE_FORWARD("Move Forward"),
	MOVE_BACKWARD("Move Backward"),
	MOVE_LEFT("Move Left"),
	MOVE_RIGHT("Move Right"),
	MOVE_UP("Move Up"),
	MOVE_DOWN("Move Down"),
	MOVE_POSITIVE_X("Move +X"),
	MOVE_POSITIVE_Z("Move +Z"),
	MOVE_NEGATIVE_X("Move -X"),
	MOVE_NEGATIVE_Z("Move -Z"),
	TELEPORT("Teleport");

int SPEED;
String DIRECTION;

private MovementMethod(String direction) {
	this.DIRECTION = direction;
}

private MovementMethod(String direction, int rotate) {
	this.DIRECTION = direction;
	this.SPEED = rotate;
}

public int getSpeed() {
	return this.SPEED;
}

public String getDirection() {
	return this.DIRECTION;
}

public void setSpeed(int speed) {
	this.SPEED = speed;
}

public void setDirection(String direction) {
	this.DIRECTION = direction;
}

public static MovementMethod getMovementDirection(BlockFace face) {
	switch (face) {
		case NORTH: {
			return MOVE_NEGATIVE_Z;
		}
		case SOUTH: {
			return MOVE_POSITIVE_Z;
		}
		case EAST: {
			return MOVE_POSITIVE_X;
		}
		case WEST: {
			return MOVE_NEGATIVE_Z;
		}
		default:
			break;
	}
	new IOException();
	return null;
}

public static Optional<MovementMethod> getMovingDirection(Ship vessel, BlockFace blockface) {
	BlockFace vesselFace = null;
	BlockData data = vessel.getLocation().getBlock().getBlockData();
	if (data instanceof Rotatable) {
		Rotatable rotatable = (Rotatable) data;
		vesselFace = rotatable.getRotation().getOppositeFace();
	}
	if (vesselFace == null) {
		return Optional.ofNullable(MovementMethod.getMovementDirection(blockface));
	}
	switch (blockface) {
		case EAST_NORTH_EAST:
		case EAST_SOUTH_EAST: {
			blockface = BlockFace.EAST;
			break;
		}
		case NORTH_EAST:
		case NORTH_NORTH_EAST:
		case NORTH_WEST:
		case NORTH_NORTH_WEST: {
			blockface = BlockFace.NORTH;
			break;
		}
		case SOUTH_SOUTH_EAST:
		case SOUTH_EAST:
		case SOUTH_SOUTH_WEST:
		case SOUTH_WEST: {
			blockface = BlockFace.SOUTH;
		}
		case WEST_NORTH_WEST:
		case WEST_SOUTH_WEST: {
			blockface = BlockFace.WEST;
		}
		default:
			break;
	}
	if (vesselFace.equals(blockface)) {
		return Optional.of(MOVE_FORWARD);
	}
	if (vesselFace.getOppositeFace().equals(blockface)) {
		return Optional.of(MOVE_BACKWARD);
	}
	if (Ships.getSideFace(vesselFace, true).equals(blockface)) {
		return Optional.of(MOVE_LEFT);
	}
	if (Ships.getSideFace(vesselFace, false).equals(blockface)) {
		return Optional.of(MOVE_RIGHT);
	}
	new IOException("getMovingDirection does not support that BlockFace. (" + blockface.name() + ")");
	return Optional.empty();
}

}
