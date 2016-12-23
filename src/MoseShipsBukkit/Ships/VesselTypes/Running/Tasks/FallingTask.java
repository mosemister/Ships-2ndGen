package MoseShipsBukkit.Ships.VesselTypes.Running.Tasks;

import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveFallable;
import MoseShipsBukkit.Ships.VesselTypes.Running.ShipsTask;

public class FallingTask implements ShipsTask {

	@Override
	public void onRun(LiveData ship) {
		LiveFallable ship2 = (LiveFallable)ship;
		if (ship2.shouldFall()){
			ship2.move(0, 2, 0);
		}
	}

	@Override
	public long getDelay() {
		return ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_SCHEDULER_FALL);
	}

}
