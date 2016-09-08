package MoseShipsBukkit.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Causes.MovementResult.CauseKeys;
import MoseShipsBukkit.Ships.Movement.Collide.CollideType;
import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.MovementAlgorithmUtils;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class Movement {

	public static Optional<MovementResult> move(LoadableShip ship, int X, int Y, int Z) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		for (Block loc : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc, X, Y, Z);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks);
	}

	public static Optional<MovementResult> rotateRight(LoadableShip ship) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location centre = ship.getLocation();
		for (Block loc : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateRight(centre.getBlock());
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks);
	}

	public static Optional<MovementResult> rotateLeft(LoadableShip ship) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location centre = ship.getLocation();
		for (Block loc : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateLeft(centre.getBlock());
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks);
	}

	public static Optional<MovementResult> rotate(LoadableShip ship, Rotate rotate) {
		switch (rotate) {
			case LEFT:
				return rotateLeft(ship);
			case RIGHT:
				return rotateRight(ship);
		}
		return Optional.of(new MovementResult());
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location loc) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		for (Block loc2 : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc2, loc);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks);
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location loc, int X, int Y, int Z) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location loc2 = loc.add(X, Y, Z);
		for (Block loc3 : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc3, loc2);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks);
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, StoredMovement movement) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Block loc2 = movement.getEndResult(ship.getLocation().getBlock());
		for (Block loc3 : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc3, loc2.getLocation());
			if (movement.getRotation().isPresent()) {
				block.rotate(movement.getRotation().get(), ship.getLocation().getBlock());
			}
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(MovementResult.CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks);
	}

	private static Optional<MovementResult> move(LoadableShip ship, List<MovingBlock> blocks) {
		Optional<MovementResult> opFail = ship.hasRequirements(blocks);
		if (opFail.isPresent()) {
			return opFail;
		}
		MovementAlgorithmUtils.getConfig().move(ship, blocks);
		return Optional.empty();
	}

	public static enum Rotate {
		LEFT,
		RIGHT;
	}

}
