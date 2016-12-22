package MoseShipsBukkit.Ships.VesselTypes.Running;

import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public interface ShipsTask {
	
	public void onRun(LoadableShip ship);
	public long getDelay();

}
