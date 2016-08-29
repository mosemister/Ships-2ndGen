package MoseShipsSponge.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShips.Bypasses.FinalBypass;

import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Causes.MovementResult.CauseKeys;
import MoseShipsSponge.Ships.Movement.Collide.CollideType;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

public class Movement {

	public static Optional<MovementResult> move(LoadableShip ship, int X, int Y, int Z, Cause intCause) {
		return move(ship, new Vector3i(X, Y, Z), intCause);
	}

	public static Optional<MovementResult> rotateRight(LoadableShip ship, Cause intCause) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		for (Location<World> loc : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateRight(centre);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> rotateLeft(LoadableShip ship, Cause intCause) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		for (Location<World> loc : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateLeft(centre);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> rotate(LoadableShip ship, Cause intCause, Rotate rotate) {
		switch (rotate) {
			case LEFT:
				return rotateLeft(ship, intCause);
			case RIGHT:
				return rotateRight(ship, intCause);
		}
		return Optional.of(new MovementResult());
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location<World> loc, Cause intCause) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		for (Location<World> loc2 : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc2, loc);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location<World> loc, int X, int Y, int Z, Cause intCause) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		Location<World> loc2 = loc.add(X, Y, Z);
		for (Location<World> loc3 : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc3, loc2);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, StoredMovement movement) {
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		Location<World> loc2 = movement.getEndResult(ship.getLocation());
		for (Location<World> loc3 : ship.getBasicStructure()) {
			MovingBlock block = new MovingBlock(loc3, loc2);
			if (movement.getRotation().isPresent()) {
				block.rotate(movement.getRotation().get(), ship.getLocation());
			}
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.put(MovementResult.CauseKeys.MOVING_BLOCKS, collide);
		return move(ship, blocks, movement.getCause());
	}

	public static Optional<MovementResult> move(LoadableShip ship, Vector3i vector, Cause intCause) {
		System.out.println("move from Movement");
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		FinalBypass<Boolean> check = new FinalBypass<>(false);
		ship.getBasicStructure().stream().forEach(loc -> {
			MovingBlock block = new MovingBlock(loc, vector);
			if (block.getCollision(ship.getBasicStructure()).equals(CollideType.COLLIDE)) {
				check.set(true);
				collide.add(block);
			}
			blocks.add(block);
		});
		cause.put(MovementResult.CauseKeys.MOVING_BLOCKS, collide);
		if (check.get()) {
			return Optional.of(cause);
		}
		System.out.println("moving method");
		return move(ship, blocks, intCause);
	}

	private static Optional<MovementResult> move(LoadableShip ship, List<MovingBlock> blocks, Cause intCase) {
		Optional<MovementResult> opFail = ship.hasRequirements(blocks, intCase);
		if (opFail.isPresent()) {
			return opFail;
		}
		MovementAlgorithm.getConfig().move(ship, blocks);
		return Optional.empty();
	}

	public static enum Rotate {
		LEFT,
		RIGHT;
	}

}
