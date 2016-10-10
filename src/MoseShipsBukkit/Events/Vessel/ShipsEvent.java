package MoseShipsBukkit.Events.Vessel;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.ShipsData;

public abstract class ShipsEvent extends Event {

	ShipsData SHIP;
	protected static HandlerList HANDLER;

	public ShipsEvent(ShipsData ship) {
		SHIP = ship;
	}

	public ShipsData getShip() {
		return SHIP;
	}

	public HandlerList getHandlers() {
		return HANDLER;
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}
}
