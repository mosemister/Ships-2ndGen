package MoseShipsSponge.Event;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import MoseShipsSponge.Ships.ShipsData;

public abstract class ShipsEvent<T extends ShipsData> extends AbstractEvent {

	T SHIP;
	Cause CAUSE;

	public ShipsEvent(T ship, Cause cause) {
		SHIP = ship;
	}

	public T getShip() {
		return SHIP;
	}

	@Override
	public Cause getCause() {
		return CAUSE;
	}

}
