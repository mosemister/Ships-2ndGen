package org.ships.ship.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class BoostDirection {

	World WORLD;
	BlockFace DIRECTION;

	static List<BoostDirection> DIRECTIONS = new ArrayList<BoostDirection>();

	public BoostDirection(World world) {
		Random random = new Random();
		BlockFace[] faces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
		int number = random.nextInt(faces.length);
		BlockFace direction = faces[number];
		WORLD = world;
		DIRECTION = direction;
		DIRECTIONS.add(this);
	}

	public BlockFace getDirection() {
		return DIRECTION;
	}

	public World getWorld() {
		return WORLD;
	}

	public void setDirection(BlockFace direction) {
		DIRECTION = direction;
	}

	public void setDirection() {
		Random random = new Random();
		int number = random.nextInt(50) + 1;
		BlockFace direction = BlockFace.values()[number];
		DIRECTION = direction;
	}

	public static List<BoostDirection> getDirections() {
		return DIRECTIONS;
	}

	public static BoostDirection getDirection(World world) {
		for (BoostDirection direction : getDirections()) {
			if (direction.getWorld().equals(world)) {
				return direction;
			}
		}
		return null;
	}

}
