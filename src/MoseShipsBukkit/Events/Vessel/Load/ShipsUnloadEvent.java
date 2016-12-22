package MoseShipsBukkit.Events.Vessel.Load;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipsUnloadEvent extends Event implements Cancellable, ShipsEvent{
	
	LoadableShip g_ship;
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
	public LoadableShip getShip() {
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
