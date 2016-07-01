package MoseShipsBukkit.World.Wind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.block.BlockFace;

public class Direction {

	World WORLD;
	BlockFace DIRECTION;

	static List<Direction> DIRECTIONS = new ArrayList<Direction>();

	public Direction(World world) {
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

	public static List<Direction> getDirections() {
		return DIRECTIONS;
	}

	public static Direction getDirection(World world) {
		for (Direction direction : getDirections()) {
			if (direction.getWorld().equals(world)) {
				return direction;
			}
		}
		return null;
	}

}
