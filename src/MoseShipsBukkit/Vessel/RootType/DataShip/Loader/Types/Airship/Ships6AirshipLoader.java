package MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.Airship;

import java.util.Optional;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.DataLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticAirship;

public class Ships6AirshipLoader extends DataLoader{

	@Override
	public String getLoaderName() {
		return "Airship - Ships 6 - DataLoader";
	}

	@Override
	public int[] getLoaderVersion() {
		int[] version = {0, 0, 1, 0};
		return version;
	}

	@Override
	public Optional<LiveShip> load(ShipsData data, BasicConfig config) {
		return StaticShipTypeUtil.getType(StaticAirship.class).get().loadVessel(data, config);
	}

	@Override
	public OpenLoader save(LiveShip ship, BasicConfig config) {
		return this;
	}

}
