package MoseShipsSponge.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Causes.MovementResult.CauseKeys;
import MoseShipsSponge.Ships.Movement.Collide.CollideType;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.WaterType;

public class Movement {

	public static Optional<MovementResult> move(LoadableShip ship, int X, int Y, int Z, Cause intCause,
			BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
			waterShipFix((WaterType) ship, D, structure);
		}
		structure.stream().forEach(loc -> {
			MovingBlock block = new MovingBlock(loc, X, Y, Z);
			CollideType collideType = block.getCollision(ship.getBasicStructure(), movingTo);
			switch (collideType) {
				case COLLIDE:
					collide.add(block);
					break;
				default:
					blocks.add(block);
					break;
			}
		});
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> rotateRight(LoadableShip ship, Cause intCause, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
			waterShipFix((WaterType) ship, D, structure);
		}
		structure.stream().forEach(loc -> {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateRight(centre);
			CollideType type = block.getCollision(ship.getBasicStructure(), movingTo);
			switch (type) {
				case COLLIDE:
					collide.add(block);
					break;
				default:
					blocks.add(block);
			}
		});
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> rotateLeft(LoadableShip ship, Cause intCause, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<>();
		List<MovingBlock> collide = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
			waterShipFix((WaterType) ship, D, structure);
		}
		structure.stream().forEach(loc -> {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateLeft(centre);
			CollideType type = block.getCollision(ship.getBasicStructure(), movingTo);
			switch (type) {
				case COLLIDE:
					collide.add(block);
					break;
				default:
					blocks.add(block);
			}
		});
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> rotate(LoadableShip ship, Cause intCause, Rotate rotate,
			BlockState... movingTo) {
		switch (rotate) {
			case LEFT:
				return rotateLeft(ship, intCause);
			case RIGHT:
				return rotateRight(ship, intCause);
		}
		return Optional.of(new MovementResult());
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location<World> tel, Cause intCause,
			BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
			waterShipFix((WaterType) ship, D, structure);
		}
		structure.stream().forEach(loc2 -> {
			MovingBlock block = new MovingBlock(loc2, tel);
			CollideType type = block.getCollision(ship.getBasicStructure(), movingTo);
			switch (type) {
				case COLLIDE:
					collide.add(block);
					break;
				default:
					blocks.add(block);
			}
		});
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks, intCause);
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location<World> tel, int X, int Y, int Z,
			Cause intCause, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location<World> loc2 = tel.add(X, Y, Z);
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
			waterShipFix((WaterType) ship, D, structure);
		}
		structure.stream().forEach(loc3 -> {
			MovingBlock block = new MovingBlock(loc3, loc2);
			CollideType type = block.getCollision(ship.getBasicStructure(), movingTo);
			switch (type) {
				case COLLIDE:
					collide.add(block);
					break;
				default:
					blocks.add(block);
			}
		});
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks, intCause);

	}

	public static Optional<MovementResult> teleport(LoadableShip ship, StoredMovement movement, Cause intCause,
			BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			ship.updateBasicStructure();
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
			waterShipFix((WaterType) ship, D, structure);
		}
		structure.stream().forEach(loc -> {
			Location<World> result = movement.getEndResult(loc);
			MovingBlock block = new MovingBlock(loc, result);
			CollideType type = block.getCollision(ship.getBasicStructure(), movingTo);
			switch (type) {
				case COLLIDE:
					collide.add(block);
					break;
				default:
					blocks.add(block);
			}
		});
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks, intCause);
	}

	private static Optional<MovementResult> move(LoadableShip ship, List<MovingBlock> blocks, Cause intCase) {
		Optional<MovementResult> opFail = ship.hasRequirements(blocks, intCase);
		if (opFail.isPresent()) {
			return opFail;
		}
		final List<Entity> entities = ship.getEntities();
		if (MovementAlgorithm.getConfig().move(ship, blocks, entities)) {
			Location<World> origin = blocks.get(0).getOrigin();
			Location<World> to = blocks.get(0).getMovingTo();
			double X = to.getX() - origin.getX();
			double Y = to.getY() - origin.getY();
			double Z = to.getZ() - origin.getZ();
			for (Entity entity : entities) {
				Location<World> eLoc = entity.getLocation();
				double tX = eLoc.getX() + X;
				double tY = eLoc.getY() + Y;
				double tZ = eLoc.getZ() + Z;
				Location<World> eTo = new Location<>(eLoc.getExtent(), tX, tY, tZ);
				Vector3d rotation = entity.getRotation();
				entity.setLocationAndRotation(eTo, rotation);
			}
		}
		return Optional.empty();
	}

	private static void waterShipFix(WaterType ship, int D, List<Location<World>> structure) {
		for (int B = 0; B < D; B++) {
			for (int C = 0; C < D; C++) {
				if (B != C) {
					Location<World> loc = structure.get(B);
					Location<World> loc2 = structure.get(C);
					if ((loc.getBlockX() == loc2.getBlockX())) {
						Location<World> small = loc2;
						Location<World> large = loc;
						if (loc.getBlockZ() > loc2.getBlockZ()) {
							small = loc;
							large = loc2;
						}
						int def = large.getBlockZ() - small.getBlockZ();

						for (int A = 0; A < def; A++) {
							Location<World> loc3 = new Location<>(loc.getExtent(), small.getBlockX(), small.getBlockY(),
									small.getBlockZ() + A);
							if (loc3.getBlockType().equals(BlockTypes.AIR)) {
								structure.add(loc3);
							}
						}
					} else if (loc.getBlockZ() == loc2.getBlockZ()) {
						Location<World> small = loc2;
						Location<World> large = loc;
						if (loc.getBlockZ() > loc2.getBlockZ()) {
							small = loc;
							large = loc2;
						}
						int def = large.getBlockX() - small.getBlockX();
						for (int A = 0; A < def; A++) {
							Location<World> loc3 = new Location<>(loc.getExtent(), small.getBlockX() + A,
									small.getBlockY(), small.getBlockZ());
							if (loc.getBlockType().equals(BlockTypes.AIR)) {
								structure.add(loc3);
							}
						}
					}
				}
			}
		}
	}

	public static enum Rotate {
		LEFT,
		RIGHT;
	}

}
