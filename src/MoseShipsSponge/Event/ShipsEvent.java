package MoseShipsSponge.Event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public abstract class ShipsEvent extends AbstractEvent {

	ShipsData g_ship;
	Cause g_cause;

	public ShipsEvent(ShipsData ship, Cause cause) {
		g_ship = ship;
		g_cause = cause;
	}
	
	public ShipsData getShip() {
		return g_ship;
	}

	@Override
	public Cause getCause() {
		return g_cause;
	}

}
