package MoseShipsSponge.Ships.VesselTypes.DefaultTypes.AirTypes.MainTypes;

import java.util.Optional;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Movement.Movement;
import MoseShipsSponge.Movement.StoredMovement.StoredMovement;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

public abstract class AbstractAirType extends LoadableShip implements AirType{

	public AbstractAirType(String name, Location<World> sign, Location<World> tel){
		super(name, sign, tel);
	}
	
	public AbstractAirType(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<MovementResult> move(int X, int Y, int Z, Cause cause) {
		return Movement.move(this, X, Y, Z, cause, BlockTypes.AIR.getDefaultState());
	}
	
	@Override
	public Optional<MovementResult> move(Direction dir, int speed, Cause cause) {
		Location<World> loc = new Location<World>(getWorld(), 0, 0, 0);
		for(int A = 0; A < speed; A++){
			loc = loc.getBlockRelative(dir);
		}
		return Movement.move(this, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), cause, BlockTypes.AIR.getDefaultState());
	}
	
	@Override
	public Optional<MovementResult> rotateLeft(Cause cause) {
		return Movement.rotateLeft(this, cause, BlockTypes.AIR.getDefaultState());
	}
	
	@Override
	public Optional<MovementResult> rotateRight(Cause cause) {
		return Movement.rotateRight(this, cause, BlockTypes.AIR.getDefaultState());
	}
	
	@Override
	public Optional<MovementResult> teleport(StoredMovement move, Cause cause) {
		return Movement.teleport(this, move, cause, BlockTypes.AIR.getDefaultState());
	}
	
	@Override
	public Optional<MovementResult> teleport(Location<World> loc, int X, int Y, int Z, Cause cause) {
		return Movement.teleport(this, loc, X, Y, Z, cause, BlockTypes.AIR.getDefaultState());
	}
	
	@Override
	public Optional<MovementResult> teleport(Location<World> loc, Cause cause) {
		return Movement.teleport(this, loc, cause, BlockTypes.AIR.getDefaultState());
	}
	
}
