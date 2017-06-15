package MoseShipsSponge.Tasks;

import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public interface ShipsTask {

	public void onRun(LiveShip ship);

	public long getDelay();

}
