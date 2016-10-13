package MoseShipsBukkit.Events.Vessel.Load;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipUnloadEvent extends ShipsEvent{

	public ShipUnloadEvent(LoadableShip ship) {
		super(ship);
	}
	
	@Override
	public LoadableShip getShip() {
		return (LoadableShip)super.getShip();
	}

}
