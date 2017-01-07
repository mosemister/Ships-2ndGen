package MoseShipsBukkit.Ships.VesselTypes.Running.Tasks;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTask;

public class UnloadTask implements ShipsTask {

	@Override
	public void onRun(LiveShip ship) {
		if (ship.willRemoveNextCycle()) {
			ship.unload(new ShipsCause(this, ship));
		} else {
			ship.setRemoveNextCycle(true);
		}
	}

	@Override
	public long getDelay() {
		return 200;
	}

}
