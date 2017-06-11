package MoseShipsSponge.Event.Load;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Event.ShipsEvent;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class ShipUnloadEvent extends ShipsEvent implements Cancellable{

	boolean g_cancelled;
	
	public ShipUnloadEvent(LiveShip ship, Cause cause) {
		super(ship, cause);
	}
	
	@Override
	public LiveShip getShip() {
		return (LiveShip)super.getShip();
	}

	@Override
	public boolean isCancelled() {
		return g_cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		g_cancelled = cancel;
	}
	
	

}
