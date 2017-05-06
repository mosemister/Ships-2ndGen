package MoseShipsBukkit.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Events.Transform.ShipAboutToMoveEvent;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.Movement.StoredMovement.StoredMovement;
import MoseShipsBukkit.Movement.Type.CollideType;
import MoseShipsBukkit.Movement.Type.MovementType;
import MoseShipsBukkit.Movement.Type.MovementType.Rotate;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.MovementAlgorithmUtil;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.LoadableShip;
import MoseShipsBukkit.Vessel.RootType.LoadableShip.Type.WaterType;

public abstract class Movement {

	public static Optional<FailedMovement> move(ShipsCause cause2, LiveShip ship, int X, int Y, int Z,
			BlockState... movingTo) {
		if ((X == 0) && (Y == 0) && (Z == 0)) {
			return Optional.of(new FailedMovement(ship, MovementResult.NO_SPEED_SET, 0));
		}
		return move(cause2, ship, MovementType.DIRECTIONAL,
				new StoredMovement.Builder().setX(X).setY(Y).setZ(Z).build(), movingTo);
	}

	public static Optional<FailedMovement> rotateRight(ShipsCause cause2, LiveShip ship, BlockState... movingTo) {
		return move(cause2, ship, MovementType.ROTATE_RIGHT,
				new StoredMovement.Builder().setRotation(Rotate.RIGHT).build(), movingTo);
	}

	public static Optional<FailedMovement> rotateLeft(ShipsCause cause2, LiveShip ship, BlockState... movingTo) {
		return move(cause2, ship, MovementType.ROTATE_LEFT,
				new StoredMovement.Builder().setRotation(Rotate.LEFT).build(), movingTo);
	}

	public static Optional<FailedMovement> rotate(ShipsCause cause, LoadableShip ship, Rotate rotate,
			BlockState... movingTo) {
		switch (rotate) {
		case LEFT:
			return rotateLeft(cause, ship, movingTo);
		case RIGHT:
			return rotateRight(cause, ship, movingTo);
		}
		return Optional.of(new FailedMovement(ship, MovementResult.PLUGIN_CANCELLED, true));
	}

	public static Optional<FailedMovement> teleport(ShipsCause cause2, LoadableShip ship, Location tel,
			BlockState... movingTo) {
		return move(cause2, ship, MovementType.TELEPORT, new StoredMovement.Builder().setTeleportTo(tel).build(),
				movingTo);
	}

	public static Optional<FailedMovement> teleport(ShipsCause cause2, LiveShip ship, StoredMovement movement,
			BlockState... movingTo) {
		return move(cause2, ship, MovementType.TELEPORT, movement, movingTo);
	}

	private static Optional<FailedMovement> move(ShipsCause cause, LiveShip ship, MovementType type,
			StoredMovement movement, BlockState... movingTo) {
		MovingBlockList blocks = new MovingBlockList();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Block> structure = ship.getBasicStructure();
		if (structure.isEmpty()) {
			return Optional.of(new FailedMovement(ship, MovementResult.NO_BLOCKS, true));
		}
		waterTypeFix(ship, structure);
		for (Block loc : structure) {
			MovingBlock block = new MovingBlock(loc, movement.getEndResult(loc));
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
		ShipsCause cause3 = new ShipsCause(cause, structure);
		ship.load(cause3);
		return move(ship, type, blocks, cause3);
	}

	private static Optional<FailedMovement> move(LiveShip ship, MovementType type, MovingBlockList blocks,
			ShipsCause cause) {
		ship.setRemoveNextCycle(false);
		ShipAboutToMoveEvent event = new ShipAboutToMoveEvent(cause, ship, type, blocks);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			return Optional.of(new FailedMovement(ship, MovementResult.PLUGIN_CANCELLED, false));
		}
		Optional<FailedMovement> opFail = ship.hasRequirements(blocks);
		if (opFail.isPresent()) {
			return opFail;
		}
		final List<Entity> entities = ship.getEntities();
		if (MovementAlgorithmUtil.getConfig().move(ship, blocks, entities)) {
			Location origin = blocks.get(0).getOrigin();
			Location to = blocks.get(0).getMovingTo();
			double X = to.getX() - origin.getX();
			double Y = to.getY() - origin.getY();
			double Z = to.getZ() - origin.getZ();
			for (Entity entity : entities) {
				Location eLoc = entity.getLocation();
				double tX = eLoc.getX() + X;
				double tY = eLoc.getY() + Y;
				double tZ = eLoc.getZ() + Z;
				Location eTo = new Location(eLoc.getWorld(), tX, tY, tZ);
				eTo.setDirection(eLoc.getDirection());
				eTo.setPitch(eLoc.getPitch());
				eTo.setYaw(eLoc.getYaw());
				entity.teleport(eTo);
			}
			return Optional.empty();
		}
		return Optional.empty();
	}

	private static void waterTypeFix(LiveShip ship, List<Block> structure) {
		if (ship instanceof WaterType) {
			final int D = structure.size();
			for (int B = 0; B < D; B++) {
				for (int C = 0; C < D; C++) {
					if (B != C) {
						Block block = structure.get(B);
						Block block2 = structure.get(C);
						if ((block.getX() == block2.getX())) {
							Block small = block2;
							Block large = block;
							if (block.getZ() > block2.getZ()) {
								small = block;
								large = block2;
							}
							int def = large.getZ() - small.getZ();

							for (int A = 0; A < def; A++) {
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(),
										small.getZ() + A);
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						} else if (block.getZ() == block2.getZ()) {
							Block small = block2;
							Block large = block;
							if (block.getZ() > block2.getZ()) {
								small = block;
								large = block2;
							}
							int def = large.getX() - small.getX();
							for (int A = 0; A < def; A++) {
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(),
										small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
	}

}
