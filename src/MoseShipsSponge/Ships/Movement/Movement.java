package MoseShipsSponge.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Causes.FailedCause.CauseKeys;
import MoseShipsSponge.Causes.FailedCause.FailedKeys;
import MoseShipsSponge.Ships.Movement.MovingBlock.Collision;
import MoseShipsSponge.Ships.Utils.StoredMovement;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public class Movement {
	
	public static Optional<FailedCause> move(ShipType ship, Vector3i vector){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		for(Location<World> loc : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc, vector);
			if(block.getCollision().equals(Collision.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return Optional.empty();
	}
	
	public static Optional<FailedCause> move(ShipType ship, int X, int Y, int Z){
		return move(ship, new Vector3i(X, Y, Z));
	}
	
	public static Optional<FailedCause> rotateRight(ShipType ship){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		for(Location<World> loc : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateRight(centre);
			if(block.getCollision().equals(Collision.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return Optional.empty();
	}
	
	public static Optional<FailedCause> rotateLeft(ShipType ship){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		for(Location<World> loc : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateLeft(centre);
			if(block.getCollision().equals(Collision.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return Optional.empty();
	}
	
	public static Optional<FailedCause> rotate(ShipType ship, Rotate rotate){
		switch(rotate){
			case LEFT:
				return rotateLeft(ship);
			case RIGHT:
				return rotateRight(ship);
		}
		return Optional.empty();
	}
	
	public static Optional<FailedCause> teleport(ShipType ship, Location<World> loc){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		for(Location<World> loc2 : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc2, loc);
			if(block.getCollision().equals(Collision.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return Optional.empty();
	}
	
	public static Optional<FailedCause> teleport(ShipType ship, Location<World> loc, int X, int Y, int Z){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> loc2 = loc.add(X, Y, Z);
		for(Location<World> loc3 : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc3, loc2);
			if(block.getCollision().equals(Collision.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return Optional.empty();
	}
	
	public static Optional<FailedCause> move(ShipType ship, StoredMovement movement){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> loc2 = movement.getEndResult(ship.getLocation());
		for(Location<World> loc3 : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc3, loc2);
			if(movement.getRotation().isPresent()){
				block.rotate(movement.getRotation().get(), ship.getLocation());
			}
			if(block.getCollision().equals(Collision.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks);
	}
	
	private static Optional<FailedCause> move(ShipType ship, List<MovingBlock> blocks){
		
		return Optional.empty();
	}
	
	public static enum Rotate{
		LEFT,
		RIGHT;
	}

}
