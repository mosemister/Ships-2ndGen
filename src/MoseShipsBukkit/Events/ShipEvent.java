package MoseShipsBukkit.Events;

import MoseShipsBukkit.Vessel.Data.ShipsData;

public interface ShipEvent {

	public ShipsData getShip();

	public ShipsCause getCause();

}
