package MoseShipsSponge.Events.Vessel.Create;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Events.Vessel.ShipsEvent;
import MoseShipsSponge.Ships.ShipsData;

public class ShipsCreateEvent<S extends ShipsData> extends ShipsEvent<S> implements Cancellable{

	boolean CANCELLED;
	
	public ShipsCreateEvent(S ship, Cause cause) {
		super(ship, cause);
	}

	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		CANCELLED = arg0;
	}

}
