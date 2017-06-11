package MoseShipsSponge.Event.Create;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.event.cause.Cause;

import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public class ShipSignCreateEvent extends ShipsCreateEvent {

	public ShipSignCreateEvent(ShipsData ship, Cause cause) {
		super(ship, cause);
	}
	
	public Sign getSign(){
		return getShip().getLicence().get();
	}

}
