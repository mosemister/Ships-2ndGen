package MoseShipsSponge.Event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public abstract class ShipsEvent extends AbstractEvent {

	ShipsData SHIP;
	Cause CAUSE;

	public ShipsEvent(ShipsData ship, Cause cause) {
		SHIP = ship;
	}

	public ShipsData getShip() {
		return SHIP;
	}

	@Override
	public Cause getCause() {
		return CAUSE;
	}

}
