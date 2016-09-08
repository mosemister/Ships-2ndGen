package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.Movement.Movement;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

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

		public Move(S ship, Location loc, Movement.Rotate rotate, List<MovingBlock> structure) {
			super(ship, new StoredMovement(loc, rotate, 0, 0, 0), structure);
		}

		public Move(S ship, Location loc, Movement.Rotate rotate, int X, int Y, int Z, List<MovingBlock> structure) {
			super(ship, new StoredMovement(loc, rotate, X, Y, Z), structure);
		}

		public Move(S ship, int X, int Y, int Z, List<MovingBlock> structure) {
			super(ship, new StoredMovement(null, null, X, Y, Z), structure);
		}
		
		public static HandlerList getHandlerList(){
			return HANDLER;
		}

	}

	public static class Teleport<S extends LoadableShip> extends ShipsTransformEvent<S> {

		public Teleport(S ship, StoredMovement movement, List<MovingBlock> structure) {
			super(ship, movement, structure);
		}

	}

	public static class Rotate<S extends LoadableShip> extends ShipsTransformEvent<S> {

		public Rotate(S ship, StoredMovement movement, List<MovingBlock> structure) {
			super(ship, movement, structure);
		}
		
		public static HandlerList getHandlerList(){
			return HANDLER;
		}

	}

}
