package MoseShipsBukkit.Tasks.Types;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Vessel.Data.LiveShip;

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
