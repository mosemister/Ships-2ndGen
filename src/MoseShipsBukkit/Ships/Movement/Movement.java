package MoseShipsBukkit.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Causes.MovementResult.CauseKeys;
import MoseShipsBukkit.Ships.Movement.MovementType.Rotate;
import MoseShipsBukkit.Ships.Movement.Collide.CollideType;
import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.MovementAlgorithmUtils;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes.MainTypes.WaterType;
import MoseShipsBukkit.Utils.State.BlockState;

public class Movement {

	public static Optional<MovementResult> move(LoadableShip ship, int X, int Y, int Z, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Block> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
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
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
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
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
		for (Block loc : structure) {
			MovingBlock block = new MovingBlock(loc, X, Y, Z);
			CollideType collideType = block.getCollision(ship.getBasicStructure(), movingTo);
			if (collideType.equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks);
	}

	public static Optional<MovementResult> rotateRight(LoadableShip ship, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location centre = ship.getLocation();
		List<Block> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
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
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
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
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
		for (Block loc : structure) {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateRight(centre.getBlock());
			if (block.getCollision(ship.getBasicStructure(), movingTo).equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks);
	}

	public static Optional<MovementResult> rotateLeft(LoadableShip ship, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location centre = ship.getLocation();
		List<Block> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
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
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
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
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
		for (Block loc : structure) {
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateLeft(centre.getBlock());
			if (block.getCollision(ship.getBasicStructure(), movingTo).equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks);
	}

	public static Optional<MovementResult> rotate(LoadableShip ship, Rotate rotate, BlockState... movingTo) {
		switch (rotate) {
			case LEFT:
				return rotateLeft(ship, movingTo);
			case RIGHT:
				return rotateRight(ship, movingTo);
		}
		return Optional.of(new MovementResult());
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location tel, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Block> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
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
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
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
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
		for (Block loc2 : structure) {
			MovingBlock block = new MovingBlock(loc2, tel);
			if (block.getCollision(ship.getBasicStructure(), movingTo).equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks);
	}

	public static Optional<MovementResult> teleport(LoadableShip ship, Location tel, int X, int Y, int Z, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		Location loc2 = tel.add(X, Y, Z);
		List<Block> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			return Optional.of(new MovementResult().put(CauseKeys.MISSING_BLOCKS, true));
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
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
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
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
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
		for (Block loc3 : structure) {
			MovingBlock block = new MovingBlock(loc3, loc2);
			if (block.getCollision(ship.getBasicStructure(), movingTo).equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks);
	}
	
	public static Optional<MovementResult> teleport(LoadableShip ship, StoredMovement movement, BlockState... movingTo) {
		ship.load();
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Block> structure = ship.getBasicStructure();
		if(structure.isEmpty()){
			ship.updateBasicStructure();
		}
		final int D = structure.size();
		if (ship instanceof WaterType) {
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
								Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
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
								Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
								if (loc.getBlock().getType().equals(Material.AIR)) {
									structure.add(loc.getBlock());
								}
							}
						}
					}
				}
			}
		}
		for (Block loc : structure) {
			Block result = movement.getEndResult(loc);
			MovingBlock block = new MovingBlock(loc, result);
			CollideType collideType = block.getCollision(ship.getBasicStructure(), movingTo);
			if (collideType.equals(CollideType.COLLIDE)) {
				collide.add(block);
			} else {
				blocks.add(block);
			}
		}
		cause.put(CauseKeys.MOVING_BLOCKS, collide);
		if (!collide.isEmpty()) {
			return Optional.of(cause);
		}
		return move(ship, blocks);
	}

	private static Optional<MovementResult> move(LoadableShip ship, List<MovingBlock> blocks) {
		Optional<MovementResult> opFail = ship.hasRequirements(blocks);
		if (opFail.isPresent()) {
			return opFail;
		}
		final List<Entity> entities = ship.getEntities();
		if (MovementAlgorithmUtils.getConfig().move(ship, blocks, entities)) {
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
		}
		return Optional.empty();
	}

}
