package MoseShipsBukkit.Tasks;

import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface ShipsTask {

	public void onRun(LiveShip ship);

	public long getDelay();

}
