package MoseShipsSponge.Ships.VesselTypes.Running;

import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;

public interface ShipsTask {

	public void onRun(LiveData data);
	public long getDelay();
}
