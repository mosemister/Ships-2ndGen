package MoseShipsBukkit.Tasks.Types;

import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveFallable;

public class FallingTask implements ShipsTask {

	@Override
	public void onRun(LiveShip ship) {
		LiveFallable ship2 = (LiveFallable)ship;
		if (ship2.shouldFall()){
			ship2.move(0, 2, 0, new ShipsCause(ship2));
		}
	}

	@Override
	public long getDelay() {
		return ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_FALL);
	}

}
