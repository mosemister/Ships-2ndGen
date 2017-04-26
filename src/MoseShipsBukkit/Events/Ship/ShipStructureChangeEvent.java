package MoseShipsBukkit.Events.Ship;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipEvent;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Vessel.Data.ShipsData;

public class ShipStructureChangeEvent extends Event implements ShipEvent, Cancellable {

	boolean g_is_cancelled = false;
	ShipsData g_data;
	ShipsCause g_cause;
	static HandlerList g_handler_list = new HandlerList();
	
	public ShipStructureChangeEvent(ShipsCause cause, ShipsData ship){
		g_data = ship;
		g_cause = cause;
	}
	
	@Override
	public ShipsData getShip() {
		return g_data;
	}

	@Override
	public ShipsCause getCause() {
		return g_cause;
	}

	@Override
	public boolean isCancelled() {
		return g_is_cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		g_is_cancelled = cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return g_handler_list;
	}
	
	public static HandlerList getHandlerList(){
		return g_handler_list;
	}

}
