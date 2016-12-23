package MoseShipsBukkit.Events.Vessel.Load;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipEvent;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public class ShipLoadEvent extends Event implements ShipEvent, Cancellable{

	LiveData g_ship;
	boolean g_cancelled;
	static HandlerList g_handlers = new HandlerList();
	
	@Override
	public boolean isCancelled() {
		return g_cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		g_cancelled = arg0;
	}

	@Override
	public LiveData getShip() {
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
