package MoseShipsSponge.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Event.Transform.ShipAboutToMoveEvent;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Movement.Result.MovementResult;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Movement.Type.CollideType;
import MoseShipsSponge.Movement.Type.MovementType;
import MoseShipsSponge.Movement.Type.RotateType;
import MoseShipsSponge.Utils.MovementAlgorithmUtil;
import MoseShipsSponge.Utils.Lists.MovingBlockList;
import MoseShipsSponge.Vessel.Common.GeneralTypes.WaterType;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class Movement {

	public static Optional<FailedMovement> move(LiveShip ship, int x, int y, int z, Cause cause,
			BlockState... movingTo) {
		if ((x == 0) && (y == 0) && (z == 0)) {
			return Optional.of(new FailedMovement(ship, MovementResult.NO_SPEED_SET, 0));
		}
		return move(ship, MovementType.DIRECTIONAL, new StoredMovement.Builder().setX(x).setY(y).setZ(z).build(), cause,
				movingTo);
	}

	public static Optional<FailedMovement> rotateRight(LiveShip ship, Cause cause, BlockState... movingTo) {
		return move(ship, MovementType.ROTATE_RIGHT, new StoredMovement.Builder().setRotation(RotateType.RIGHT).build(),
				cause, movingTo);
	}

	public static Optional<FailedMovement> rotateLeft(LiveShip ship, Cause cause, BlockState... movingTo) {
		return move(ship, MovementType.ROTATE_LEFT, new StoredMovement.Builder().setRotation(RotateType.LEFT).build(),
				cause, movingTo);
	}

	public static Optional<FailedMovement> rotate(LiveShip ship, RotateType rotate, Cause cause, BlockState... state) {
		switch (rotate) {
		case LEFT:
			return rotateLeft(ship, cause, state);
		case RIGHT:
			return rotateRight(ship, cause, state);
		default:
			return Optional.of(new FailedMovement(ship, MovementResult.PLUGIN_CANCELLED, true));
		}
	}

	public static Optional<FailedMovement> teleport(LiveShip ship, StoredMovement movement, BlockState... states) {
		return move(ship, MovementType.TELEPORT, movement, movement.getCause(), states);
	}

	public static Optional<FailedMovement> teleport(LiveShip ship, StoredMovement movement, Cause cause,
			BlockState... movingTo) {
		return move(ship, MovementType.TELEPORT, movement, cause, movingTo);
	}

	public static Optional<FailedMovement> move(LiveShip ship, MovementType type, StoredMovement movement, Cause cause,
			BlockState... movingTo) {
		MovingBlockList blocks = new MovingBlockList();
		List<MovingBlock> collide = new ArrayList<>();
		List<Location<World>> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new FailedMovement(ship, MovementResult.NO_BLOCKS, true));
		}
		waterTypeFix(ship, structure);
		for (Location<World> loc : structure) {
			MovingBlock block = new MovingBlock(loc, movement.getEndResult(loc, ship.getLocation()), type);
			CollideType collideType = block.getCollision(ship.getBasicStructure(), movingTo);
			if (collideType.equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		if (!collide.isEmpty()) {
			return Optional.of(new FailedMovement(ship, MovementResult.COLLIDE_WITH, collide));
		}
		cause = Cause.builder().from(cause).append(structure).build(cause.getContext());
		ship.load(cause);
		return move(ship, type, blocks, cause);
	}

	private static Optional<FailedMovement> move(LiveShip ship, MovementType type, MovingBlockList blocks,
			Cause cause) {
		ship.setRemoveNextCycle(false);
		ShipAboutToMoveEvent event = new ShipAboutToMoveEvent(cause, ship, type, blocks);
		Sponge.getEventManager().post(event);
		if (event.isCancelled()) {
			return Optional.of(new FailedMovement(ship, MovementResult.PLUGIN_CANCELLED, false));
		}
		Optional<FailedMovement> opFail = ship.hasRequirements(blocks);
		if (opFail.isPresent()) {
			return opFail;
		}
		final List<Entity> entities = ship.getEntities();
		if (MovementAlgorithmUtil.getConfig().move(ship, blocks, entities)) {
			Location<World> origin = blocks.get(0).getOrigin();
			Location<World> to = blocks.get(0).getMovingTo();
			double X = to.getX() - origin.getX();
			double Y = to.getY() - origin.getY();
			double Z = to.getZ() - origin.getZ();
			for (Entity entity : entities) {
				Location<World> eLoc = entity.getLocation().copy().add(X, Y, Z);
				entity.setLocationAndRotation(eLoc, entity.getRotation());

			}
			return Optional.empty();
		}
		return Optional.empty();
	}

	private static void waterTypeFix(LiveShip ship, List<Location<World>> structure) {
		if (ship instanceof WaterType) {
			final int D = structure.size();
			for (int B = 0; B < D; B++) {
				for (int C = 0; C < D; C++) {
					if (B != C) {
						Location<World> block = structure.get(B);
						Location<World> block2 = structure.get(C);
						if (block.getBlockX() == block2.getBlockX()) {
							Location<World> small = block2;
							Location<World> large = block;
							if (block.getBlockZ() > block2.getBlockZ()) {
								small = block;
								large = block2;
							}
							int def = large.getBlockZ() - small.getBlockZ();
							for (int A = 0; A < def; A++) {
								Location<World> loc = small.copy().add(0, 0, A);
								if (loc.getBlockType().equals(BlockTypes.AIR)) {
									structure.add(loc);
								}
							}
						} else if (block.getBlockZ() == block2.getBlockZ()) {
							Location<World> small = block2;
							Location<World> large = block;
							if (block.getZ() > block2.getZ()) {
								small = block;
								large = block2;
							}
							int def = large.getBlockX() - small.getBlockX();
							for (int A = 0; A < def; A++) {
								Location<World> loc = small.copy().add(A, 0, 0);
								if (loc.getBlockType().equals(BlockTypes.AIR)) {
									structure.add(loc);
								}
							}
						}
					}
				}
			}
		}
	}
}
