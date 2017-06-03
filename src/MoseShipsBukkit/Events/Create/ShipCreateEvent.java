package MoseShipsBukkit.Events.Create;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipEvent;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;

/**
 * implemented
 * 
 * @author Mose
 *
 */
public class ShipCreateEvent extends Event implements ShipEvent, Cancellable {

	boolean g_cancelled = false;
	ShipsData g_ship;
	ShipsCause g_cause;
	protected static HandlerList g_handlers = new HandlerList();

	public ShipCreateEvent(ShipsCause cause, ShipsData data) {
		g_ship = data;
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
	public ShipsData getShip() {
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

	public static HandlerList getHandlerList() {
		return g_handlers;
	}

}
