package MoseShipsBukkit.Ships.VesselTypes.Running;

import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;

public interface ShipsTask {
	
	public void onRun(LiveData ship);
	public long getDelay();

}
