package MoseShipsSponge.Events.Vessel.Create;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import MoseShipsSponge.Events.Vessel.ShipsEvent;
import MoseShipsSponge.Ships.ShipsData;

public class ShipsCreateEvent<S extends ShipsData> extends ShipsEvent<S> implements Cancellable {

	boolean CANCELLED;

	public ShipsCreateEvent(S ship) {
		super(ship);
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		CANCELLED = arg0;
	}
	
	public static HandlerList getHandlerList(){
		return HANDLER;
	}

}
