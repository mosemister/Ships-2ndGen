package MoseShipsBukkit.Tasks.Types;

import MoseShipsBukkit.Configs.ShipsConfig;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Tasks.ShipsTask;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.Implementations.FallableShip;

public class FallingTask implements ShipsTask {

	@Override
	public void onRun(LiveShip ship) {
		FallableShip ship2 = (FallableShip)ship;
		if (ship2.shouldFall()) {
			ship.move(0, 2, 0, new ShipsCause(ship));
		}
	}

	@Override
	public long getDelay() {
		return ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_FALL);
	}

}
