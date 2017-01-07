package MoseShipsBukkit.Ships.VesselTypes.Running.Tasks;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveShip;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveFallable;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTask;

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
