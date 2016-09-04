package MoseShipsSponge.Events.StaticVessel;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import MoseShipsSponge.Ships.VesselTypes.Satic.StaticShipType;

public class StaticShipEvent<S extends StaticShipType> extends AbstractEvent {

	protected S TYPE;
	protected Cause CAUSE;

	public StaticShipEvent(S type, Cause cause) {
		TYPE = type;
		CAUSE = cause;
	}

	public S getType() {
		return TYPE;
	}

	@Override
	public Cause getCause() {
		return CAUSE;
	}

}
