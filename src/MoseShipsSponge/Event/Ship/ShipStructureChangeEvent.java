package MoseShipsSponge.Event.Ship;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Event.ShipsEvent;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public class ShipStructureChangeEvent extends ShipsEvent implements Cancellable {

	boolean g_cancelled;

	public ShipStructureChangeEvent(ShipsData ship, Cause cause) {
		super(ship, cause);
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
