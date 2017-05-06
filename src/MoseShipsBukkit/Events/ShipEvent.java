package MoseShipsBukkit.Events;

import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;

public interface ShipEvent {

	public ShipsData getShip();

	public ShipsCause getCause();

}
