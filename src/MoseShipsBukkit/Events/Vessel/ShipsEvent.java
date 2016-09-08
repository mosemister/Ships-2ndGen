package MoseShipsBukkit.Events.Vessel;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.ShipsData;

public abstract class ShipsEvent<T extends ShipsData> extends Event {

	T SHIP;
	protected static HandlerList HANDLER;

	public ShipsEvent(T ship) {
		SHIP = ship;
	}

	public T getShip() {
		return SHIP;
	}
	
	public HandlerList getHandlers(){
		return HANDLER;
	}
	
	public static HandlerList getHandlerList(){
		return HANDLER;
	}
}
