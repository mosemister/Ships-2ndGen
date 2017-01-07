package MoseShipsBukkit.Events.Vessel;

import MoseShipsBukkit.Causes.ShipsCause;

public interface ShipEvent {
	
	public Object getShip();
	public ShipsCause getCause();

}
