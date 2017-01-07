package MoseShipsBukkit.Ships.VesselTypes.Running;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;

public interface ShipsTask {
	
	public void onRun(LiveShip ship);
	public long getDelay();

}
