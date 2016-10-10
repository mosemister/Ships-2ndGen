package MoseShipsBukkit.Events.Vessel.Transform;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.Movement.Movement;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public abstract class ShipsTransformEvent extends ShipsEvent {

	List<MovingBlock> BLOCKS;
	StoredMovement MOVEMENT;

	public ShipsTransformEvent(LoadableShip ship, StoredMovement move, List<MovingBlock> structure) {
		super(ship);
		BLOCKS = structure;
		MOVEMENT = move;
	}

	public List<MovingBlock> getStructure() {
		return BLOCKS;
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}

	public static class Move extends ShipsTransformEvent {

		public Move(LoadableShip ship, StoredMovement movement, List<MovingBlock> structure) {
			super(ship, movement, structure);
			MOVEMENT = movement;
		}

		public Move(LoadableShip ship, Location loc, List<MovingBlock> structure) {
			super(ship, new StoredMovement(loc, null, 0, 0, 0), structure);
		}

		public Move(LoadableShip ship, Location loc, Movement.Rotate rotate, List<MovingBlock> structure) {
			super(ship, new StoredMovement(loc, rotate, 0, 0, 0), structure);
		}

		public Move(LoadableShip ship, Location loc, Movement.Rotate rotate, int X, int Y, int Z, List<MovingBlock> structure) {
			super(ship, new StoredMovement(loc, rotate, X, Y, Z), structure);
		}

		public Move(LoadableShip ship, int X, int Y, int Z, List<MovingBlock> structure) {
			super(ship, new StoredMovement(null, null, X, Y, Z), structure);
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

	public static class Teleport extends ShipsTransformEvent {

		public Teleport(LoadableShip ship, StoredMovement movement, List<MovingBlock> structure) {
			super(ship, movement, structure);
		}

	}

	public static class Rotate extends ShipsTransformEvent {

		public Rotate(LoadableShip ship, StoredMovement movement, List<MovingBlock> structure) {
			super(ship, movement, structure);
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

}
