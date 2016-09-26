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
import MoseShipsBukkit.Ships.Movement.Collide.CollideType;
import MoseShipsBukkit.Ships.Movement.MovementAlgorithm.MovementAlgorithmUtils;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterType;

public class Movement {

	public static Optional<MovementResult> move(LoadableShip ship, int X, int Y, int Z) {
		System.out.println("moved by XYZ");
		MovementResult cause = new MovementResult();
		List<MovingBlock> blocks = new ArrayList<MovingBlock>();
		List<MovingBlock> collide = new ArrayList<MovingBlock>();
		List<Block> structure = ship.getBasicStructure();
		final int D = structure.size();
		if(ship instanceof WaterType){
			System.out.println("is water type");
			for(int B = 0; B < D; B++){
				for(int C = 0; C < D; C++){
					if(B != C){
						Block block = structure.get(B);
						Block block2 = structure.get(C);
					if((block.getX() == block2.getX())){
						Block small = block2;
						Block large = block;
						if(block.getZ() > block2.getZ()){
							small = block;
							large = block2;
						}
						int def = large.getZ() - small.getZ();
						System.out.println("{ \n " + small.getX() + ", " + small.getY() + ", " + small.getZ()
						+ "\n " + large.getX() + ", " + large.getY() + ", " + large.getZ()
						+ "\n " + def);
						
						
						for(int A = 0; A < def; A++){
							Location loc = new Location(block.getWorld(), small.getX(), small.getY(), small.getZ() + A);
							if(loc.getBlock().getType().equals(Material.AIR)){
								System.out.println("Added block on X");
								structure.add(loc.getBlock());
							}
						}
					}else if(block.getZ() == block2.getZ()){
						Block small = block2;
						Block large = block;
						if(block.getZ() > block2.getZ()){
							small = block;
							large = block2;
						}
						int def = large.getX() - small.getX();
						for(int A = 0; A < def; A++){
							Location loc = new Location(block.getWorld(), small.getX() + A, small.getY(), small.getZ());
							if(loc.getBlock().getType().equals(Material.AIR)){
								System.out.println("Added block on Z");
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
		System.out.println("teleport on NO XYZ");
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
		System.out.println("teleport on XYZ");
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
		System.out.println("teleport on stored movement");
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
		final List<Entity> entities = ship.getEntities();
		MovementAlgorithmUtils.getConfig().move(ship, blocks);
		Location origin = blocks.get(0).getOrigin();
		Location to = blocks.get(0).getMovingTo();
		double X = to.getX() - origin.getX();
		double Y = to.getY() - origin.getY();
		double Z = to.getZ() - origin.getZ();
		for(Entity entity : entities){
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

	public static enum Rotate {
		LEFT, RIGHT;
	}

}
