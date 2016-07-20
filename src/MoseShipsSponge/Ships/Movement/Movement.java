package MoseShipsSponge.Ships.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShips.Bypasses.FinalBypass;
import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Causes.FailedCause.CauseKeys;
import MoseShipsSponge.Causes.FailedCause.FailedKeys;
import MoseShipsSponge.Ships.Movement.Collide.CollideType;
import MoseShipsSponge.Ships.Movement.MovementAlgorithm.MovementAlgorithm;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.ShipType;

public class Movement {
	
	public static Optional<FailedCause> move(ShipType ship, int X, int Y, int Z, Cause intCause){
		return move(ship, new Vector3i(X, Y, Z), intCause);
	}
	
	public static Optional<FailedCause> rotateRight(ShipType ship, Cause intCause){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		for(Location<World> loc : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateRight(centre);
			if(block.getCollision().equals(CollideType.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks, intCause);
	}
	
	public static Optional<FailedCause> rotateLeft(ShipType ship, Cause intCause){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> centre = ship.getLocation();
		for(Location<World> loc : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc, 0, 0, 0).rotateLeft(centre);
			if(block.getCollision().equals(CollideType.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks, intCause);
	}
	
	public static Optional<FailedCause> rotate(ShipType ship, Cause intCause, Rotate rotate){
		switch(rotate){
			case LEFT:
				return rotateLeft(ship, intCause);
			case RIGHT:
				return rotateRight(ship, intCause);
		}
		return Optional.of(new FailedCause());
	}
	
	public static Optional<FailedCause> teleport(ShipType ship, Location<World> loc, Cause intCause){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		for(Location<World> loc2 : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc2, loc);
			if(block.getCollision().equals(CollideType.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks, intCause);
	}
	
	public static Optional<FailedCause> teleport(ShipType ship, Location<World> loc, int X, int Y, int Z, Cause intCause){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> loc2 = loc.add(X, Y, Z);
		for(Location<World> loc3 : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc3, loc2);
			if(block.getCollision().equals(CollideType.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks, intCause);
	}
	
	public static Optional<FailedCause> teleport(ShipType ship, StoredMovement movement){
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		Location<World> loc2 = movement.getEndResult(ship.getLocation());
		for(Location<World> loc3 : ship.getBasicStructure()){
			MovingBlock block = new MovingBlock(loc3, loc2);
			if(movement.getRotation().isPresent()){
				block.rotate(movement.getRotation().get(), ship.getLocation());
			}
			if(block.getCollision().equals(CollideType.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				return Optional.of(cause);
			}
			blocks.add(block);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks, movement.getCause());
	}
	
	public static Optional<FailedCause> move(ShipType ship, Vector3i vector, Cause intCause){
		System.out.println("move from Movement");
		FailedCause cause = new FailedCause();
		List<MovingBlock> blocks = new ArrayList<>();
		FinalBypass<Boolean> check = new FinalBypass<>(false);
		ship.getBasicStructure().stream().forEach(loc -> {
			MovingBlock block = new MovingBlock(loc, vector);
			if(block.getCollision().equals(CollideType.COLLIDE)){
				cause.getCauses().put(FailedKeys.COLLIDE, block);
				check.set(true);
			}
			blocks.add(block);
		});
		if(check.get()){
			return Optional.of(cause);
		}
		cause.getCauses().put(CauseKeys.MOVING_BLOCKS, blocks);
		return move(ship, blocks, intCause);
	}
	
	private static Optional<FailedCause> move(ShipType ship, List<MovingBlock> blocks, Cause intCase){
		Optional<FailedCause> opFail = ship.hasRequirements(blocks, intCase);
		if(opFail.isPresent()){
			return opFail;
		}
		MovementAlgorithm.getConfig().move(ship, blocks);
		return Optional.empty();
	}
	
	public static enum Rotate{
		LEFT,
		RIGHT;
	}

}
