package MoseShipsBukkit.Events.Vessel.Create;

import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public abstract class ShipsCreateFailedEvent extends ShipsEvent {

	public ShipsCreateFailedEvent(ShipsData ship) {
		super(ship);
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}

	public static class ConflictName extends ShipsCreateFailedEvent {

		protected LoadableShip g_conflict;

		public ConflictName(ShipsData ship, LoadableShip conflict) {
			super(ship);
		}

		public LoadableShip getConflict() {
			return g_conflict;
		}

		public String getName() {
			return g_conflict.getName();
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

	public static class ConflictType extends ShipsCreateFailedEvent {

		protected String g_type;

		public ConflictType(ShipsData ship, String type) {
			super(ship);
			g_type = type;
		}

		public String getAttemptedType() {
			return g_type;
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

}
