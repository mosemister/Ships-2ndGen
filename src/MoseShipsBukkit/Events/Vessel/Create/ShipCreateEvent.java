package MoseShipsBukkit.Events.Vessel.Create;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipEvent;
import MoseShipsBukkit.Ships.ShipsData;

public class ShipCreateEvent extends Event implements ShipEvent, Cancellable {
	
	boolean g_cancelled = false;
	ShipsData g_ship;
	protected static HandlerList g_handlers = new HandlerList();
	
	public ShipCreateEvent(ShipsData data){
		g_ship = data;
	}

	@Override
	public boolean isCancelled() {
		return g_cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		g_cancelled = arg0;
	}

	@Override
	public ShipsData getShip() {
		return g_ship;
	}

	@Override
	public HandlerList getHandlers() {
		return g_handlers;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}
