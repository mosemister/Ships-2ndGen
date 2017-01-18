package MoseShipsBukkit.Events.Load;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipEvent;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Vessel.Data.LiveShip;


/**
 * implemented
 * @author Mose
 *
 */
public class ShipLoadEvent extends Event implements ShipEvent, Cancellable{

	LiveShip g_ship;
	boolean g_cancelled;
	ShipsCause g_cause;
	static HandlerList g_handlers = new HandlerList();
	
	public ShipLoadEvent(ShipsCause cause, LiveShip ship){
		g_ship = ship;
		g_cause = cause;
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
	public LiveShip getShip() {
		return g_ship;
	}

	@Override
	public HandlerList getHandlers() {
		return g_handlers;
	}
	
	@Override
	public ShipsCause getCause() {
		return g_cause;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}
