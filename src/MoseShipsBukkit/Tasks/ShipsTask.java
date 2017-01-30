package MoseShipsBukkit.Tasks;

import MoseShipsBukkit.Vessel.Data.LiveShip;

public interface ShipsTask {

	public void onRun(LiveShip ship);

	public long getDelay();

}
