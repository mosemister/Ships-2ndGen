package MoseShipsBukkit.Ships.VesselTypes.Loading;

import java.util.List;

import org.bukkit.Bukkit;

import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public class ShipRunnables {

	public static int UNLOADER_TASK;

	@SuppressWarnings("deprecation")
	public static void registerShipsUnloader() {
		int time = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_ALGORITHMS_CLEARFREQUENCY);
		if (time != 0) {
			UNLOADER_TASK = Bukkit.getScheduler().scheduleAsyncRepeatingTask(ShipsMain.getPlugin(), new Runnable() {

				@Override
				public void run() {
					ShipsUnloader();

				}

			}, 0, (time * 20));
		}
	}

	public static void ShipsUnloader() {
		List<LoadableShip> list = LoadableShip.getReasentlyUsedShips();
		for (int A = 0; A < list.size(); A++) {
			LoadableShip ship = list.get(A);
			if (ship.willRemoveNextCycle()) {
				ship.unload();
			} else {
				ship.setRemoveNextCycle(true);
			}
		}
	}

}
