package MoseShipsSponge.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.HandlerList;

import MoseShipsSponge.Events.Vessel.ShipsEvent;
import MoseShipsSponge.Ships.Movement.StoredMovement;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

public abstract class ShipsTransformEvent<S extends LoadableShip> extends ShipsEvent<S> {

	List<MovingBlock> BLOCKS;
	StoredMovement MOVEMENT;

	public ShipsTransformEvent(S ship, StoredMovement move, List<MovingBlock> structure) {
		super(ship);
		BLOCKS = structure;
		MOVEMENT = move;
	}

	public List<MovingBlock> getStructure() {
		return BLOCKS;
	}
	
	public static HandlerList getHandlerList(){
		return HANDLER;
	}

	public static class Move<S extends LoadableShip> extends ShipsTransformEvent<S> {

		public Move(S ship, StoredMovement movement, List<MovingBlock> structure) {
			super(ship, movement, structure);
			MOVEMENT = movement;
		}

		public Move(S ship, Location loc, List<MovingBlock> structure) {
			super(ship, new StoredMovement(loc, null, 0, 0, 0), structure);
		}

		public Move(S ship, Location loc, Rotate rotate, List<MovingBlock> structure, Cause cause) {
			super(ship, new StoredMovement(loc, rotate, 0, 0, 0, cause), structure, cause);
		}

		public Move(S ship, Location<World> loc, MoseShipsSponge.Ships.Movement.Movement.Rotate rotate, int X, int Y, int Z, List<MovingBlock> structure, Cause cause) {
			super(ship, new StoredMovement(loc, rotate, X, Y, Z, cause), structure, cause);
		}

		public Move(S ship, int X, int Y, int Z, List<MovingBlock> structure, Cause cause) {
			super(ship, new StoredMovement(null, null, X, Y, Z, cause), structure, cause);
		}

	}

	public static class Teleport<S extends LoadableShip> extends ShipsTransformEvent<S> {

		public Teleport(S ship, StoredMovement movement, List<MovingBlock> structure, Cause cause) {
			super(ship, movement, structure, cause);
		}

	}

	public static class Rotate<S extends LoadableShip> extends ShipsTransformEvent<S> {

		public Rotate(S ship, StoredMovement movement, List<MovingBlock> structure, Cause cause) {
			super(ship, movement, structure, cause);
		}

	}

}
