package MoseShipsBukkit.Ships.VesselTypes.Running.Tasks;

import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTask;

public class UnloadTask implements ShipsTask {

	@Override
	public void onRun(LoadableShip ship) {
		if (ship.willRemoveNextCycle()) {
			ship.unload();
		} else {
			ship.setRemoveNextCycle(true);
		}
	}

	@Override
	public long getDelay() {
		return 200;
	}

}
